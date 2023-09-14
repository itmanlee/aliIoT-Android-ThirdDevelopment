package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.util.ASlideDialog;
import com.aliIoT.demo.util.MyApplication;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailRegisterCallback;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;


/**
 * 阿里账号体系页面，包含手机号和邮箱注册登录
 */

public class DemoLoginActivity extends LoginActivity {

    // 显示手机号/邮箱 注册，改密入口
    ASlideDialog registerDialog, forgetPwdDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.ali_sdk_openaccount_login3);
        getNextStepButtonWatcher().setNextStepButton((Button) findViewById(R.id.next));

        TextView reg = findViewById(R.id.register);
        reg.setTextColor(getResources().getColor(R.color.hei));
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountShowMenuDialog();
            }
        });

        TextView reset = findViewById(R.id.reset_password);
        reset.setTextColor(getResources().getColor(R.color.hei));
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPwdShowMenuDialog();
            }
        });

        findViewById(R.id.oauth).setVisibility(View.GONE);
    }

    public void emailRegister(View view) {
        OpenAccountUIService openAccountService = OpenAccountSDK.getService(OpenAccountUIService.class);
        try {
            openAccountService.showEmailRegister(this, new EmailRegisterCallback() {

                @Override
                public void onSuccess(OpenAccountSession session) {
                    LoginCallback callback = getLoginCallback();
                    if (callback != null) {
                        callback.onSuccess(session);
                    }
                    finishWithoutCallback();
                }

                @Override
                public void onFailure(int code, String message) {
                    LoginCallback callback = getLoginCallback();
                    if (callback != null) {
                        callback.onFailure(code, message);
                    }
                }

                @Override
                public void onEmailSent(String email) {
                    Toast.makeText(getApplicationContext(), email + " 已经发送了", Toast.LENGTH_LONG).show();
                }

            });
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }


    public void emailResetPassword(View view) {
        OpenAccountUIService openAccountService = OpenAccountSDK.getService(OpenAccountUIService.class);
        try {
            openAccountService.showEmailResetPassword(this, new EmailResetPasswordCallback() {


                @Override
                public void onSuccess(OpenAccountSession session) {
                    LoginCallback callback = getLoginCallback();
                    if (callback != null) {
                        callback.onSuccess(session);
                    }
                    finishWithoutCallback();
                }


                @Override
                public void onFailure(int code, String message) {
                    LoginCallback callback = getLoginCallback();
                    if (callback != null) {
                        callback.onFailure(code, message);
                    }
                }

                @Override
                public void onEmailSent(String email) {
                    Toast.makeText(getApplicationContext(), email + " 已经发送了", Toast.LENGTH_LONG).show();
                }

            });
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }

    private void accountShowMenuDialog() {
        if (registerDialog == null) {
            registerDialog = ASlideDialog.newInstance(this, ASlideDialog.Gravity.Bottom, R.layout.menu_dialog_login);
            ((TextView) registerDialog.findViewById(R.id.menu_op1_textview)).setText("手机注册");
            ((TextView) registerDialog.findViewById(R.id.menu_op2_textview)).setText("邮箱注册");
            registerDialog.findViewById(R.id.menu_op1_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser(view);
                    registerDialog.dismiss();
                }
            });
            registerDialog.findViewById(R.id.menu_op2_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailRegister(view);
                    registerDialog.dismiss();
                }
            });

            registerDialog.findViewById(R.id.menu_cancel_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerDialog.dismiss();
                }
            });

            registerDialog.setCanceledOnTouchOutside(true);
        }

        registerDialog.show();
    }

    private void forgetPwdShowMenuDialog() {
        if (forgetPwdDialog == null) {
            forgetPwdDialog = ASlideDialog.newInstance(this, ASlideDialog.Gravity.Bottom, R.layout.menu_dialog_login);
            ((TextView) forgetPwdDialog.findViewById(R.id.menu_op1_textview)).setText("手机号码密码找回");
            ((TextView) forgetPwdDialog.findViewById(R.id.menu_op2_textview)).setText("邮箱密码找回");
            forgetPwdDialog.findViewById(R.id.menu_op1_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forgetPassword(view);
                    forgetPwdDialog.dismiss();
                }
            });
            forgetPwdDialog.findViewById(R.id.menu_op2_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailResetPassword(view);
                    forgetPwdDialog.dismiss();
                }
            });
            forgetPwdDialog.findViewById(R.id.menu_cancel_textview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forgetPwdDialog.dismiss();
                }
            });
            forgetPwdDialog.setCanceledOnTouchOutside(true);
        }

        forgetPwdDialog.show();
    }

    /**
     * 登录按钮 回调
     *
     * @return
     */
    @Override
    protected LoginCallback getLoginCallback() {
        if (super.getLoginCallback() == null) {
            OpenAccountUIServiceImpl._loginCallback = new LoginCallback() {
                @Override
                public void onFailure(int i, String s) {
                    Log.e("wy", "========ffff=" + s);
                }

                @Override
                public void onSuccess(OpenAccountSession openAccountSession) {
                    Log.e("wy", "========cccccc=" + openAccountSession.isLogin());
                    if (openAccountSession.isLogin()) {
                        IoTCredentialManage ioTCredentialManage = IoTCredentialManageImpl.getInstance(MyApplication.getInstance());
                        if (ioTCredentialManage != null) {
                            ioTCredentialManage.asyncRefreshIoTCredential(new IoTCredentialListener() {
                                @Override
                                public void onRefreshIoTCredentialSuccess(IoTCredentialData ioTCredentialData) {
                                    UserInfo userInfo = LoginBusiness.getUserInfo();
                                    if (LoginBusiness.isLogin() && userInfo != null) {
                                        String iotid = MyApplication.getInstance().getIotID();
                                        Intent intent = null;

                                        if (TextUtils.isEmpty(iotid)) {
                                            intent = new Intent(DemoLoginActivity.this, AddDeviceActivity.class);
                                        } else {
                                            intent = new Intent(DemoLoginActivity.this, Main2Activity.class);
                                        }
                                        startActivity(intent);
                                    } else {
                                        //UserInfoController.getInstance().queryUserInfo(eventType, oauth, oauth.getIdentityId(), ioTCredentialData.identity, StartActivityPersenter.this);
                                    }
                                }

                                @Override
                                public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
                                }
                            });
                        }
                    }
                    //overridePendingTransition(0, 0);
                }
            };
            return OpenAccountUIServiceImpl._loginCallback;
        }

        return super.getLoginCallback();
    }

}
