package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aliIoT.demo.model.ParameterVerifyBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.EncryptionUtil;
import com.aliIoT.demo.util.HttpUtils;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.SharedPreferencesUtil;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialListener;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManage;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageError;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.data.IoTCredentialData;
import com.aliyun.iot.aep.sdk.login.ILoginCallback;
import com.aliyun.iot.aep.sdk.login.LoginBusiness;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 启动页，包含跳转到自有账号体系和阿里账号体系按钮。
 */
public class StartActivity extends AppCompatActivity {

    Button mButton1;
    Button mButton2;

    public void setAuthorizationIsCheck(boolean authorizationIsCheck) {
        this.authorizationIsCheck = authorizationIsCheck;
    }

    //! 用户跳转到授权界面后返回时再次检查权限的flag。
    boolean authorizationIsCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mButton1 = findViewById(R.id.start_bt1);
        mButton2 = findViewById(R.id.start_bt2);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, DemoLoginActivity.class);
                startActivity(intent);
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String userid = (String) SharedPreferencesUtil.get(StartActivity.this,ConstUtil.KEY_USERID,"");
//                String token = (String) SharedPreferencesUtil.get(StartActivity.this,ConstUtil.KEY_TOKEN,"");
//
//                userid ="";
//                token = "";
//                if(!TextUtils.isEmpty(userid)&&!TextUtils.isEmpty(token)){
//                    login(userid,token);
//                }else{
//                    login2();
//
//                    Intent intent = new Intent(StartActivity.this,LoginActivity.class);
//                    startActivity(intent);
//
//                    Intent intent2 = new Intent(StartActivity.this,DemoLoginActivity.class);
//                    startActivity(intent2);
//                }
//
//            }
//        },1000);

    }

    public void login(String userid, String token) {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(userid);
            list.add(token);
            list.add(ConstUtil.APP_KEY);
            list.add("code");
            ParameterVerifyBean parameterVerifyBean = EncryptionUtil.httParameterMd5(list);

            String code = "code";
            String clientid = ConstUtil.APP_KEY;
            String state = parameterVerifyBean.randomIndexString;
            String encode = parameterVerifyBean.md5VerifyString;

            String url = ConstUtil.mUrl + "/oauth/v1/getCode?response_type=" + code + "&client_id=" + clientid + "&state="
                    + state + "&encode=" + encode + "&userId=" + userid + "&token=" + token;

            Log.e("wy", "===url==" + url);

            HttpUtils.get(url).connTimeOut(5000).execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int id) throws Exception {
                    String result = response.body().string();
                    Log.e("wy", "===response==" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    int resultcode = jsonObject.getInt("resultCode");
                    if (resultcode == 0) {
                        Intent intent = new Intent(StartActivity.this, AddDeviceActivity.class);
                        startActivity(intent);
                    } else {
                        //!参考错误码
                    }

                    return null;
                }

                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(Object response, int id) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void login2() {
        LoginBusiness.login(new ILoginCallback() {
            @Override
            public void onLoginSuccess() {
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
                                    intent = new Intent(StartActivity.this, AddDeviceActivity.class);
                                } else {
                                    intent = new Intent(StartActivity.this, Main2Activity.class);
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

            @Override
            public void onLoginFailed(int i, String s) {

            }
        });
    }


}
