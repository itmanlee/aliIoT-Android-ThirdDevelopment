package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.model.AuthCodeBean;
import com.aliIoT.demo.model.ParameterVerifyBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.EncryptionUtil;
import com.aliIoT.demo.util.HttpUtils;
import com.aliIoT.demo.util.LanguageUtil;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.SharedPreferencesUtil;
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
 * 自有账号体系登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBackView;
    EditText mUserEditText;
    EditText mPswEditText;
    TextView mRegistTextView;
    TextView mResetPswTextView;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserEditText = findViewById(R.id.login_user);
        mPswEditText = findViewById(R.id.login_psw);
        mRegistTextView = findViewById(R.id.login_regist);
        mResetPswTextView = findViewById(R.id.login_resetpsw);
        mLoginButton = findViewById(R.id.login_login);

        mRegistTextView.setOnClickListener(this);
        mResetPswTextView.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

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
            case R.id.login_regist:
                intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.login_resetpsw:
                intent = new Intent(this, ResetPswActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login:

                String user = mUserEditText.getText().toString().trim();
                String psw = mPswEditText.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    break;
                }

                if (TextUtils.isEmpty(psw)) {
                    break;
                }

                login(user, psw);

                break;
        }
    }


    /**
     * 自有账号登录方法，获取authcode
     * @param userName
     * @param password
     */
    public void login(final String userName, String password) {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(userName);
            list.add(password);
            list.add(ConstUtil.APP_KEY);
            list.add(LanguageUtil.getCountry());
            list.add("code");
            ParameterVerifyBean parameterVerifyBean = EncryptionUtil.httParameterMd5(list);

            String code = "code";
            String clientid = ConstUtil.APP_KEY;
            String state = parameterVerifyBean.randomIndexString;
            final String encode = parameterVerifyBean.md5VerifyString;
            String isoCode = LanguageUtil.getCountry();

            String url = ConstUtil.mUrl + "/oauth/v1/getCode?response_type=" + code + "&client_id=" + clientid + "&state="
                    + state + "&encode=" + encode + "&account=" + userName
                    + "&password=" + password + "&isoCode=" + isoCode;

            Log.e("wy", "===url==" + url);

            HttpUtils.get(url).connTimeOut(5000).execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int id) throws Exception {
                    String result = response.body().string();
                    Log.e("wy", "===response==" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    int resultcode = jsonObject.getInt("resultCode");
                    if (resultcode == 0) {
                        String userid = jsonObject.getString("userId");
                        String token = jsonObject.getString("token");
                        String identityId = jsonObject.getString("identityId");
                        String serverSite = jsonObject.getString("serverSite");
                        String authCode = jsonObject.getString("authCode");

                        SharedPreferencesUtil.put(LoginActivity.this, ConstUtil.KEY_USERID, userid);
                        SharedPreferencesUtil.put(LoginActivity.this, ConstUtil.KEY_TOKEN, token);
                        SharedPreferencesUtil.put(LoginActivity.this, ConstUtil.KEY_IDENTITYID, identityId);
                        SharedPreferencesUtil.put(LoginActivity.this, ConstUtil.KEY_SERVERSITE, serverSite);
                        SharedPreferencesUtil.put(LoginActivity.this, ConstUtil.KEY_AUTHCODE, authCode);

                        AuthCodeBean authCodeBean = new AuthCodeBean();
                        authCodeBean.setUserId(userid);
                        authCodeBean.setToken(token);
                        authCodeBean.setIdentityId(identityId);
                        authCodeBean.setServerSite(serverSite);
                        authCodeBean.setAuthCode(authCode);

                        MyApplication.getInstance().setAuthCodeBean(authCodeBean);

                        aliyunLogin(authCodeBean, 111);

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

    /**
     * 阿里登录方法，主要对身份进行认证
     * @param oauth
     * @param eventType
     */
    private void aliyunLogin(AuthCodeBean oauth, int eventType) {
        Log.e("wy", "===oauth==" + oauth.getAuthCode());
        LoginBusiness.authCodeLogin(oauth.getAuthCode(), new ILoginCallback() {
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
                                    intent = new Intent(LoginActivity.this, AddDeviceActivity.class);
                                } else {
                                    intent = new Intent(LoginActivity.this, Main2Activity.class);
                                }
                                startActivity(intent);
                                //Toast.makeText(MyApplication.getInstance(),"登录成功",Toast.LENGTH_LONG).show();
                            } else {
                                //UserInfoController.getInstance().queryUserInfo(eventType, oauth, oauth.getIdentityId(), ioTCredentialData.identity, StartActivityPersenter.this);
                            }
                        }

                        @Override
                        public void onRefreshIoTCredentialFailed(IoTCredentialManageError ioTCredentialManageError) {
//                            MessageToView(Message.obtain(null, EventType.USER_LOGIN_FOR_WITHOUT_PASSWORD, HttpTypeSource.REQUEST_RESULT_FAILED, 0));
//                            if (ioTCredentialManageError != null) {
//                                ToastUtils.getToastUtils().showToast(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.aliyun_authentication_error) + ":" + ioTCredentialManageError.errorCode + "(2000)");
//                            }
                            //Toast.makeText(MyApplication.getInstance(),"登录失败,错误"+ioTCredentialManageError.errorCode,Toast.LENGTH_LONG).show();
                        }

                    });
                } else {
                    // MessageToView(Message.obtain(null, EventType.USER_LOGIN_FOR_WITHOUT_PASSWORD, HttpTypeSource.REQUEST_RESULT_FAILED, 0));
                }

            }

            @Override
            public void onLoginFailed(int i, String s) {
                Log.e("wy", "===s==" + s);
//                String ailyunErrorInfo = AliyunErrorInfoUtils.getAilyunErrorInfo(i);
//                ToastUtils.getToastUtils().showToast(MyApplication.getInstance(), (TextUtils.isEmpty(ailyunErrorInfo) ? s : ailyunErrorInfo) + "(2000)");
//                MessageToView(Message.obtain(null, EventType.USER_LOGIN_FOR_WITHOUT_PASSWORD, HttpTypeSource.REQUEST_RESULT_FAILED, 0));
            }
        });
    }


}
