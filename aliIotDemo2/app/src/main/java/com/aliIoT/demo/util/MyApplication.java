package com.aliIoT.demo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.aliIoT.demo.model.AuthCodeBean;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;
import com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import com.aliyun.iot.aep.sdk.credential.listener.IoTTokenInvalidListener;
import com.aliyun.iot.aep.sdk.framework.AApplication;

import java.util.List;

/**
 * 必须继承阿里的AApplication。否则阿里的sdk初始化报错
 */

public class MyApplication extends AApplication {

    private static MyApplication mApplication = null;
    Context mApplicationContext;
    AuthCodeBean mAuthCodeBean = null;

    public static MyApplication getInstance() {
        return mApplication;
    }

    private static String tostring(IoTRequest request) {
        return new StringBuilder("Request:").append("\r\n")
                .append("url:").append(request.getScheme()).append("://").append(null == request.getHost() ? "" : request.getHost()).append(request.getPath()).append("\r\n")
                .append("apiVersion:").append(request.getAPIVersion()).append("\r\n")
                .append("params:").append(null == request.getParams() ? "" : JSON.toJSONString(request.getParams())).append("\r\n").toString();
    }

    private static String tostring(IoTRequestWrapper wrapper) {
        IoTRequest request = wrapper.request;
        return new StringBuilder("Request:").append("\r\n")
                .append("id:").append(wrapper.payload.getId()).append("\r\n")
                .append("apiEnv:").append("").append("\r\n")
                .append("url:").append(request.getScheme()).append("://").append(TextUtils.isEmpty(wrapper.request.getHost()) ? "" : wrapper.request.getHost()).append(request.getPath()).append("\r\n")
                .append("apiVersion:").append(request.getAPIVersion()).append("\r\n")
                .append("params:").append(null == request.getParams() ? "" : JSON.toJSONString(request.getParams())).append("\r\n")
                .append("payload:").append(JSON.toJSONString(wrapper.payload)).append("\r\n").toString();
    }

    private static String tostring(IoTResponse response) {
        return new StringBuilder("Response:").append("\r\n")
                .append("id:").append(response.getId()).append("\r\n")
                .append("code:").append(response.getCode()).append("\r\n")
                .append("message:").append(response.getMessage()).append("\r\n")
                .append("localizedMsg:").append(response.getLocalizedMsg()).append("\r\n")
                .append("data:").append(null == response.getData() ? "" : response.getData().toString()).append("\r\n").toString();
    }

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName(this);
        if (processName != null && processName.equals(getPackageName())) {
            mApplication = this;
            mApplicationContext = this;

            initAliyunSDK();
        }

    }

    public AuthCodeBean getAuthCodeBean() {
        return mAuthCodeBean;
    }

    public void setAuthCodeBean(AuthCodeBean authCodeBean) {
        this.mAuthCodeBean = authCodeBean;
    }

    private void initAliyunSDK() {
        // 初始化参数配置
        IoTSmart.InitConfig initConfig = new IoTSmart.InitConfig()
                .setProductEnv(IoTSmart.PRODUCT_ENV_PROD)
                // REGION_ALL表示连接全球多个接入点；REGION_CHINA_ONLY表示直连中国内地接入点
                .setRegionType(IoTSmart.REGION_CHINA_ONLY)
                // 是否打开日志
                .setDebug(true);
        // 初始化，App须继承自AApplication，否则会报错
        IoTSmart.init((AApplication) mApplicationContext, initConfig);

        /**
         * 设置App配网列表的产品范围，PRODUCT_SCOPE_ALL表示当前项目中已发布和未发布的所有产品，
         * PRODUCT_SCOPE_PUBLISHED表示只包含已发布产品，正式发布的App请选择PRODUCT_SCOPE_PUBLISHED
         */
        IoTSmart.setProductScope(IoTSmart.PRODUCT_SCOPE_ALL);
        String language = LanguageUtil.getLanguageToAliyun(LanguageUtil.getLanguage());
        IoTSmart.setLanguage(language);
        IoTCredentialManageImpl.getInstance((AApplication) mApplicationContext).setIotTokenInvalidListener(new IoTTokenInvalidListener() {
            @Override
            public void onIoTTokenInvalid() {

            }
        });

        IoTSmart.setDebug(true);
        IoTAPIClientImpl.getInstance().clearTracker();
        IoTAPIClientImpl.getInstance().registerTracker(new Tracker() {
            final String TAG = "APIGatewaySDKDelegate$Tracker";

            @Override
            public void onSend(IoTRequest request) {
                ALog.i(TAG, "onSend:\r\n" + tostring(request));
            }

            @Override
            public void onRealSend(IoTRequestWrapper ioTRequest) {
                ALog.d(TAG, "onRealSend:\r\n" + tostring(ioTRequest));
            }

            @Override
            public void onRawFailure(IoTRequestWrapper ioTRequest, Exception e) {
                ALog.d(TAG, "onRawFailure:\r\n" + tostring(ioTRequest) + "ERROR-MESSAGE:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onFailure(IoTRequest request, Exception e) {
                ALog.i(TAG, "onFailure:\r\n" + tostring(request) + "ERROR-MESSAGE:" + e.getMessage());
            }

            @Override
            public void onRawResponse(IoTRequestWrapper request, IoTResponse response) {
                ALog.d(TAG, "onRawResponse:\r\n" + tostring(request) + tostring(response));
            }

            @Override
            public void onResponse(IoTRequest request, IoTResponse response) {
                ALog.i(TAG, "onResponse:\r\n" + tostring(request) + tostring(response));
            }
        });
    }

    public String getIotID() {
        String iotid = "";
        iotid = (String) SharedPreferencesUtil.get(getInstance(), ConstUtil.KEY_IOTID, "");
        return iotid;
    }

}
