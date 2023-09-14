package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.HttpUtils;
import com.aliIoT.demo.util.SharedPreferencesUtil;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * 添加设备界面，需要传入设备的PK DN
 */
public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mNameEditText;
    EditText mIdEditText;
    EditText mChnumEditText;
    Button mAddButton;
    Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevice);

        mNameEditText = findViewById(R.id.adddevice_name);
        mIdEditText = findViewById(R.id.adddevice_id);
        mChnumEditText = findViewById(R.id.adddevice_chnum);
        mAddButton = findViewById(R.id.adddevice_add);
        mLogoutButton = findViewById(R.id.adddevice_logout);

        mAddButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

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
            case R.id.adddevice_logout:

                break;
            case R.id.adddevice_add:
                mNameEditText.setText("3QEiFFSIPcUXRJkT0XKR");
                mIdEditText.setText("a1pyfAoOlaY");

                String user = mNameEditText.getText().toString().trim();
                String key = mIdEditText.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    break;
                }

                if (TextUtils.isEmpty(key)) {
                    break;
                }

                bindDevice(user, key);

                break;
        }
    }

    public void bindDevice(String name, String key) {
        String path = "/awss/time/window/user/bind";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_08;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("productKey", key);
        params.put("deviceName", name);

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();
        ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params), new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                int code = ioTResponse.getCode();

                // 200 代表成功
                if (200 != code) {
                    //失败示例，参见 "异常数据返回示例"
                    String mesage = ioTResponse.getMessage();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("wy", "====mesage==" + mesage + "==code==" + code);
                    return;
                }
                Object data = ioTResponse.getData();
                //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                Log.e("wy", "====data==" + data.toString());

                /**
                 * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                 * 解析data，data示例参见"正常数据返回示例"
                 * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                 */
                if (data == null) {
                    return;
                }

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data.toString());
                    if (jsonObject.has("iotId")) {
                        String iotid = jsonObject.getString("iotId");

                        SharedPreferencesUtil.put(AddDeviceActivity.this, ConstUtil.KEY_IOTID, iotid);

                        Intent intent = new Intent(AddDeviceActivity.this, Main2Activity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
