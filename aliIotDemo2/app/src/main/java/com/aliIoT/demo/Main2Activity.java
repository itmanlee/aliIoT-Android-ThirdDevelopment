package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.SharedPreferencesUtil;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.iot.aep.sdk.login.ILogoutCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 主页入口，在切换自有账号和阿里账号的时候，需要删除设备，退出登录
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    public final static int ALIYUNSERVICE_DEVICE_RESTOREDEFAULT = 3;
    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    Button mRealPlayButton;
    Button mRePlayButton;
    Button mAlarmButton;
    Button mLogoutButton;
    Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man2);

        mRealPlayButton = findViewById(R.id.main2_realplay);
        mRePlayButton = findViewById(R.id.main2_replay);
        mAlarmButton = findViewById(R.id.main2_alarm);
        mLogoutButton = findViewById(R.id.main2_logout);
        mDeleteButton = findViewById(R.id.main2_delete);

        mRealPlayButton.setOnClickListener(this);
        mRePlayButton.setOnClickListener(this);
        mAlarmButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.main2_realplay:
                intent = new Intent(this, RealPlayActivity.class);
                startActivity(intent);
                break;
            case R.id.main2_replay:
                intent = new Intent(this, RePlayActivity.class);
                startActivity(intent);
                break;
            case R.id.main2_alarm:
                intent = new Intent(this, AlarmActivity.class);
                startActivity(intent);
                break;
            case R.id.main2_logout:
                exitLogin();
                break;
            case R.id.main2_delete:
                restoreDefault(MyApplication.getInstance().getIotID(), 1);
                break;
        }
    }

    public void exitLogin() {
        LoginBusiness.logout(new ILogoutCallback() {
            @Override
            public void onLogoutSuccess() {
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_USERID, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_TOKEN, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_IDENTITYID, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_SERVERSITE, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_AUTHCODE, "");

                Intent intent = new Intent(Main2Activity.this, StartActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLogoutFailed(int i, String s) {
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_USERID, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_TOKEN, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_IDENTITYID, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_SERVERSITE, "");
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_AUTHCODE, "");
            }
        });

    }

    public void restoreDefault(String iotID, int flagValue) { //flagValue对应调用方式，结果公共处理
        flagValue = 1;
        JSONObject jsonObject = new JSONObject();
        try {
            if (flagValue == 0) { //设备列表恢复默认
                jsonObject.put("Network", 1);
                jsonObject.put("Alarm", 1);
                jsonObject.put("Account", 1);
                jsonObject.put("OtherCfg", 1);
                jsonObject.put("IoTReboot", 1);
            } else if (flagValue == 1) { //删除设备时重置（只重置某些设置）
                jsonObject.put("Network", 0);
                jsonObject.put("Alarm", 0);
                jsonObject.put("Account", 0);
                jsonObject.put("OtherCfg", 0);
                jsonObject.put("IoTReboot", 1);//只执行1项设备端恢复到默认设置，依旧会重启，其他设置包括网络信息不变
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean b = aliyunService(ALIYUNSERVICE_DEVICE_RESTOREDEFAULT, iotID
                , "RestoreDefault", jsonObject);
    }

    public boolean aliyunService(int serviceType, String mDeviceId, String
            identifier, JSONObject json) {
        try {
            // Log.e(TAG, "aliyunService=" + "**identifier=" + identifier + "***json=" + json);
            PanelDevice panelDevice = new PanelDevice(mDeviceId);
            JSONObject object = new JSONObject();
            object.put("iotId", mDeviceId);
            object.put("identifier", identifier);
            if (json != null)
                object.put("args", json);
            else {
                object.put("args", new JSONObject());
            }
            panelDevice.invokeService(object.toString(), new IPanelCallback() {
                @Override
                public void onComplete(boolean b, @Nullable Object o) {

                    if (o instanceof CmpError) {

                    } else {
                        try {
                            //Log.e(TAG, "onComplete=" + ((String) o));
                            if (b) {
                                JSONObject jsonObject = new JSONObject(o.toString());
                                if (jsonObject.has("code") && jsonObject.getInt("code") == 200) {
                                    if (serviceType != ALIYUNSERVICE_PTZ_CONTROL && serviceType != ALIYUNSERVICE_PTZ_CONTROL_STOP) {
                                        String string = jsonObject.getString("data");

                                        int checkResult = checkResultCode(string);
                                        if (checkResult == 0) {
                                            Parcelable p = resultFromat(serviceType, string);
                                        } else {

                                        }
                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();

            return false;

        }
        return true;
    }

    private int checkResultCode(String string) {
        int i = 0;
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("ResultCode")) {
                i = jsonObject.getInt("ResultCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    private Parcelable resultFromat(int serviceType, String string) {
        Parcelable p = null;
        switch (serviceType) {
            case ALIYUNSERVICE_DEVICE_RESTOREDEFAULT: {
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_RESTOREDEFAULT==" + string);
                SharedPreferencesUtil.put(Main2Activity.this, ConstUtil.KEY_IOTID, "");
                break;
            }
        }
        return p;
    }


}
