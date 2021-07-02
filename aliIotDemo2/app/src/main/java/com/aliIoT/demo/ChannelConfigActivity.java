package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aliIoT.demo.model.AliyunChannelAbilityBean;
import com.aliIoT.demo.model.ChannelAbilityBean;
import com.aliIoT.demo.model.ChannelEcondeAbilityBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.ToastUtils;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 通道配置界面
 */
public class ChannelConfigActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBackView;
    LinearLayout mInfoLinearLayout;
    LinearLayout mEcodeSetLinearLayout;
    LinearLayout mVideoSetLayout;

    private ChannelEcondeAbilityBean mChannelEcondeAbilityBeanMain;
    private ChannelEcondeAbilityBean mChannelEcondeAbilityBeanChild;
    private ChannelAbilityBean mChannelAbilityBean;

    String iotID = MyApplication.getInstance().getIotID();
    Gson gson = new Gson();

    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    static final int ALIYUNSERVICE_GET_CHANNELABILITY = 3;
    static final int ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN = 4;
    static final int ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD = 5;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case ALIYUNSERVICE_GET_CHANNELABILITY: {
                    AliyunChannelAbilityBean mBean = gson.fromJson((String) message.obj, AliyunChannelAbilityBean.class);
                    mChannelAbilityBean = new ChannelAbilityBean();
                    mChannelAbilityBean.aliyunChannelAbilityBeanToChannelAbilityBean(mBean);

                    if (mChannelAbilityBean != null && mChannelAbilityBean.isHasEncodeConfig()) {
                        getDeviceEcodeAbility(iotID, ConstUtil.STREAMTYPE_CHILD);
                        getDeviceEcodeAbility(iotID, ConstUtil.STREAMTYPE_MAIN);
                    }
                    break;
                }
                case ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN: {
                    ChannelEcondeAbilityBean mBean = gson.fromJson((String) message.obj, ChannelEcondeAbilityBean.class);
                    if (mBean != null)
                        mBean.initMap();

                    mChannelEcondeAbilityBeanMain = mBean;
                    mChannelEcondeAbilityBeanMain.addCustomBitRate();
                    break;
                }
                case ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD: {
                    ChannelEcondeAbilityBean mBean = gson.fromJson((String) message.obj, ChannelEcondeAbilityBean.class);
                    if (mBean != null)
                        mBean.initMap();

                    mChannelEcondeAbilityBeanChild = mBean;
                    mChannelEcondeAbilityBeanChild.addCustomBitRate();
                    break;
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mBackView = findViewById(R.id.config_back);
        mInfoLinearLayout = findViewById(R.id.config_ly1);
        mEcodeSetLinearLayout = findViewById(R.id.config_ly2);
        mVideoSetLayout = findViewById(R.id.config_ly3);

        mBackView.setOnClickListener(this);
        mInfoLinearLayout.setOnClickListener(this);
        mEcodeSetLinearLayout.setOnClickListener(this);
        mVideoSetLayout.setOnClickListener(this);

        getDeviceSetAbility(iotID);

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
            case R.id.config_back:
                finish();
                break;
            case R.id.config_ly1:
                intent = new Intent(this, ChannelChildActivity.class);
                startActivity(intent);
                break;
            case R.id.config_ly2:
                if (mChannelAbilityBean != null && mChannelAbilityBean.isHasEncodeConfig()) {
                    if (mChannelEcondeAbilityBeanMain != null) {
                        intent = new Intent(this, ChannelEncodesetActivity.class);
                        intent.putExtra("main", mChannelEcondeAbilityBeanMain);
                        intent.putExtra("child", mChannelEcondeAbilityBeanChild);
                        startActivity(intent);
                    } else {
                        ToastUtils.getToastUtils().showToast(this, "设备编码信息还在准备中");
                    }
                } else {
                    ToastUtils.getToastUtils().showToast(this, "设备不支持");
                }

                break;
            case R.id.config_ly3:
                intent = new Intent(this, ChannelVideoSetActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void getDeviceSetAbility(String deviceId) {
        aliyunService(ALIYUNSERVICE_GET_CHANNELABILITY, deviceId
                , "ChannelAbility", null);

    }

    public void getDeviceEcodeAbility(String deviceId, int streamType) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("StreamType", streamType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (streamType == ConstUtil.STREAMTYPE_MAIN) {
            aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN, deviceId
                    , "EncodeConfigAbility", jsonObject);
        } else {
            aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD, deviceId
                    , "EncodeConfigAbility", jsonObject);
        }

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
        Message message = handler.obtainMessage();
        switch (serviceType) {
            case ALIYUNSERVICE_GET_CHANNELABILITY:
                Log.e("wy", "==ALIYUNSERVICE_GET_CHANNELABILITY==" + string);
                message.what = ALIYUNSERVICE_GET_CHANNELABILITY;
                message.obj = string;
                handler.sendMessage(message);

                break;
            case ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN:
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN==" + string);
                message.what = ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_MAIN;
                message.obj = string;
                handler.sendMessage(message);
                break;
            case ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD:
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD==" + string);
                message.what = ALIYUNSERVICE_DEVICE_ENCODE_ABILITY_CHILD;
                message.obj = string;
                handler.sendMessage(message);
                break;

        }
        return p;
    }


}
