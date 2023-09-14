package com.aliIoT.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.aliIoT.demo.model.ParameterVerifyBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.EncryptionUtil;
import com.aliIoT.demo.util.HttpUtils;
import com.aliIoT.demo.util.LanguageUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 自有体系重置密码页
 */
public class ResetPswActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBackView;
    EditText mUserEditText;
    EditText mPswEditText;
    EditText mCodeEditText;
    Button mGetCodeButton;
    Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpsw);

        mBackView = findViewById(R.id.resetpsw_back);
        mUserEditText = findViewById(R.id.resetpsw_user);
        mPswEditText = findViewById(R.id.resetpsw_psw);
        mCodeEditText = findViewById(R.id.resetpsw_code);
        mGetCodeButton = findViewById(R.id.resetpsw_getcode);
        mResetButton = findViewById(R.id.resetpsw_resetpsw);

        mBackView.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.resetpsw_back:
                finish();
                break;
            case R.id.resetpsw_getcode:
                String user1 = mUserEditText.getText().toString().trim();
                getCode(user1);
                break;
            case R.id.resetpsw_resetpsw:

                String user = mUserEditText.getText().toString().trim();
                String psw = mPswEditText.getText().toString().trim();
                String code = mCodeEditText.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    break;
                }

                if (TextUtils.isEmpty(psw)) {
                    break;
                }

                if (TextUtils.isEmpty(code)) {
                    break;
                }

                resetPsw(user, psw, code);

                break;
        }
    }

    public void getCode(String userName) {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(userName);
            list.add(LanguageUtil.getCountry());
            ParameterVerifyBean parameterVerifyBean = EncryptionUtil.httParameterMd5(list);

            String state = parameterVerifyBean.randomIndexString;
            String encode = parameterVerifyBean.md5VerifyString;
            String isoCode = LanguageUtil.getCountry();

            String url = ConstUtil.mUrl1 + "/api/v1/resetPasswordCode?" + "state="
                    + state + "&encode=" + encode + "&account=" + userName
                    + "&isoCode=" + isoCode;

            Log.e("wy", "===url==" + url);

            HttpUtils.get(url).connTimeOut(5000).execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int id) throws Exception {
                    Log.e("wy", "===response==" + response.body().string());
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

    public void resetPsw(String userName, String password, String code) {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add(userName);
            list.add(password);
            list.add(code);
            list.add(LanguageUtil.getCountry());

            ParameterVerifyBean parameterVerifyBean = EncryptionUtil.httParameterMd5(list);

            String state = parameterVerifyBean.randomIndexString;
            String encode = parameterVerifyBean.md5VerifyString;
            String isoCode = LanguageUtil.getCountry();

            String url = ConstUtil.mUrl1 + "/api/v1/resetPassword";

            Map<Object, Object> map = new HashMap<>();
            map.put("isoCode", isoCode);
            map.put("account", userName);
            map.put("password", password);
            map.put("verifyCode", code);
            map.put("state", state);
            map.put("encode", encode);

            Log.e("wy", "===url==" + url);

            HttpUtils.post(url, new Gson().toJson(map)).connTimeOut(5000).execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response, int id) throws Exception {
                    Log.e("wy", "===response==" + response.body().string());
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


}
