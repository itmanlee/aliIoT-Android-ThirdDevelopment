package com.aliIoT.demo.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by Administrator on 2021/5/31 0031.
 */

public class HttpUtils {

    public static RequestCall post(String url,String content){

        PackageInfo pInfo = null;
        try {
            pInfo = MyApplication.getInstance().getPackageManager().getPackageInfo(
                    "com.aliIoT.demo", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        RequestCall call = OkHttpUtils.postString().addHeader("client-type", "Android").
                addHeader("client-name", "" + Build.MODEL + "&" + Build.VERSION.SDK_INT).
                addHeader("pack-name", pInfo == null ? "" : pInfo.packageName).
                addHeader("version", pInfo == null ? "" : pInfo.versionName).
                addHeader("app-vendor", "TEST-DEMO")
                .url(url).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(content).build();

        return call;

    }

    public static RequestCall get(String url){

        PackageInfo pInfo = null;
        try {
            pInfo = MyApplication.getInstance().getPackageManager().getPackageInfo(
                    "com.aliIoT.demo", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        RequestCall call = OkHttpUtils.get().addHeader("client-type", "Android").
                addHeader("client-name", "" + Build.MODEL + "&" + Build.VERSION.SDK_INT).
                addHeader("pack-name", pInfo == null ? "" : pInfo.packageName).
                addHeader("version", pInfo == null ? "" : pInfo.versionName).
                addHeader("app-vendor", "TEST-DEMO")
                .url(url).build();

        return call;

    }

    public static IoTRequest creatIoTRequest(String path, String apiVersion, String type, Map<String,Object> param){
        IoTRequest request = new IoTRequestBuilder()
                .setScheme(Scheme.HTTPS) // 设置Scheme方式，取值范围：Scheme.HTTP或Scheme.HTTPS，默认为Scheme.HTTPS
                .setPath(path) // 参照API文档，设置API接口描述中的Path，本示例为uc/listBindingByDev
                .setApiVersion(apiVersion)  // 参照API文档，设置API接口的版本号，本示例为1.0.2
                .setAuthType(type) // 当云端接口需要用户身份鉴权时需要设置该参数，反之则不需要设置
                .setParams(param) // 参照API文档，设置API接口的参数，也可以使用.setParams(Map<Strign,Object> params)来设置
                .build();

        return request;
    }

}
