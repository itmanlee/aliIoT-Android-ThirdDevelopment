package com.aliIoT.demo.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliIoT.demo.R;

/**
 * Created by qianxiaoai on 2016/7/7.
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    // public static final int CODE_CALL_PHONE = 3;
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final int CODE_MULTI_PERMISSION = 100;

    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    //public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            //PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }


    /**
     * Requests permission.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static boolean requestPermission(final Activity activity, final int requestCode, PermissionGrant permissionGrant) {
        if (activity == null) {
            return true;
        }


        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            return true;
        }

        final String requestPermission = requestPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);
//        if (Build.VERSION.SDK_INT < 23) {
//            permissionGrant.onPermissionGranted(requestCode);
//            return;
//        }

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (RuntimeException e) {
            Toast.makeText(activity, "please open this permission", Toast.LENGTH_SHORT)
                    .show();

            return true;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                shouldShowRationale(activity, requestCode, requestPermission);

            } else {
                Log.d(TAG, "requestCameraPermission else");
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
            }
            return false;
        } else {
            Log.d(TAG, "ActivityCompat.checkSelfPermission ==== PackageManager.PERMISSION_GRANTED");
            //  Toast.makeText(activity, "opened:" + requestPermissions[requestCode], Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(requestCode);
            return true;
        }
    }

    public static boolean requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return true;
        }

        //TODO
        Log.d(TAG, "onRequestPermissionsResult permissions length:" + permissions.length);
        Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (!PERMISSION_GET_ACCOUNTS.equals(permissions[i]) || !PERMISSION_READ_PHONE_STATE.equals(permissions[i]) ||
                        !PERMISSION_ACCESS_FINE_LOCATION.equals(permissions[i]) || !PERMISSION_ACCESS_COARSE_LOCATION.equals(permissions[i]))
                    notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
//            Toast.makeText(activity, "all permission success" + notGranted, Toast.LENGTH_SHORT)
//                    .show();
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            openSettingActivity(activity, MyApplication.getInstance().getResources().getString(R.string.application_lacks_necessary_running_permissions));
            return false;
        }
        return true;
    }


    /**
     * 一次申请多个权限
     */
    public static boolean requestMultiPermissions(final Activity activity, PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedPermission(activity, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);

        //TODO checkSelfPermission
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return true;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            Log.d(TAG, "showMessageOKCancel requestPermissions");
            return false;
        } else if (shouldRationalePermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            return false;
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
            return true;
        }

    }

    /**
     * 一次申请多个权限
     */
    public static boolean requestMultiPermissions(final Activity activity, int[] ints, PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedPermission(activity, ints, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, ints, true);

        //TODO checkSelfPermission
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return true;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            Log.d(TAG, "showMessageOKCancel requestPermissions");
            return false;
        } else if (shouldRationalePermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            return false;
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
            return true;
        }

    }

    public static boolean requestMultiPermissions(final Activity activity, String[] permissions, int[] ints, PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedPermission(activity, ints, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, ints, true);
        ArrayList<String> noGrantedPermission = getNoGrantedPermission(activity, ints, false);
        if (noGrantedPermission!=null&&noGrantedPermission.size()>0&&!requestMultiResult(activity, permissions, ints, grant)) {
            return false;
        }
        //TODO checkSelfPermission
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return true;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            Log.d(TAG, "showMessageOKCancel requestPermissions");
            return false;
        } else if (shouldRationalePermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            return false;
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
            return true;
        }

    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        //  String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        ActivityCompat.requestPermissions(activity,
                new String[]{requestPermission},
                requestCode);
//        showMessageOKCancel(activity, "Rationale: " + permissionsHint[requestCode], new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{requestPermission},
//                        requestCode);
//                Log.d(TAG, "showMessageOKCancel requestPermissions:" + requestPermission);
//            }
//        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener okListener2) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(MyApplication.getInstance().getResources().getString(R.string.sure), okListener)
                .setNegativeButton(MyApplication.getInstance().getResources().getString(R.string.cancle), okListener2)
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static boolean requestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                   @NonNull int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return true;
        }
        Log.d(TAG, "requestPermissionsResult requestCode:" + requestCode);

        if (requestCode == CODE_MULTI_PERMISSION) {
            return requestMultiResult(activity, permissions, grantResults, permissionGrant);

        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return true;
        }


        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO success, do something, can use callback
            permissionGrant.onPermissionGranted(requestCode);

        } else {
            //TODO hint user this permission function
            //TODO
            //String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
            //openSettingActivity(activity, "Result" + permissionsHint[requestCode]);
        }
        return true;

    }

    private static void openSettingActivity(final Activity activity, String message) {

        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Log.d(TAG, "getPackageName(): " + activity.getPackageName());
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (activity instanceof LaunchActivity) {
////                    ((LaunchActivity) activity).cancelPermission();
////                }
            }
        });
    }


    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    public static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];


            //TODO checkSelfPermission
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                        .show();

                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {

                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }

    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    public static ArrayList<String> getNoGrantedPermission(Activity activity, int[] ints, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < ints.length; i++) {
            String requestPermission = requestPermissions[ints[i]];


            //TODO checkSelfPermission
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                        .show();

                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }
    public static ArrayList<String> getNoGrantedPermission2(Activity activity, int[] ints, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < ints.length; i++) {
            String requestPermission = requestPermissions[ints[i]];


            //TODO checkSelfPermission
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                        .show();

                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }

}
