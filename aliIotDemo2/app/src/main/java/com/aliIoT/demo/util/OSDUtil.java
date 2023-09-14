package com.aliIoT.demo.util;

import com.aliIoT.demo.model.TransControlV2Bean;
import com.aliIoT.demo.model.TransControlV2DataBean;
import com.aliIoT.demo.model.TransControlV2NVRBean;
import com.aliIoT.demo.model.TransControlV2NVRDataBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OSDUtil {
    public static TransControlV2Bean creatTransControlV2Bean(int type) {
        TransControlV2DataBean transControlV2DataBean = new TransControlV2DataBean();
        transControlV2DataBean.setCh(1);
        transControlV2DataBean.setDev(1);
        transControlV2DataBean.setType(type == 1 ? 0 : 1);
        transControlV2DataBean.setData((new JsonObject()));
        TransControlV2Bean transControlV2Bean = new TransControlV2Bean();
        transControlV2Bean.setTransType(1);
        transControlV2Bean.setOpt(1);
        transControlV2Bean.setPayloadType(0);
        Gson gson = new Gson();
        String s = gson.toJson(transControlV2DataBean);
        transControlV2Bean.setPayloadLen(s.length());
        transControlV2Bean.setPayload(s);
        transControlV2Bean.setTransUrl("frmSingleLineOSD");
        return transControlV2Bean;
    }

    public static TransControlV2Bean creatTransControlV2Bean(JsonObject json) {
        try {
            TransControlV2DataBean transControlV2DataBean = new TransControlV2DataBean();
            transControlV2DataBean.setCh(1);
            transControlV2DataBean.setDev(1);
            transControlV2DataBean.setType(1);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("OSD", json);
            transControlV2DataBean.setData(jsonObject);
            TransControlV2Bean transControlV2Bean = new TransControlV2Bean();
            transControlV2Bean.setTransType(1);
            transControlV2Bean.setOpt(0);
            transControlV2Bean.setPayloadType(0);

            Gson gson = new Gson();
            String s = gson.toJson(transControlV2DataBean);
            transControlV2Bean.setPayloadLen(s.length());
            transControlV2Bean.setPayload(s);
            transControlV2Bean.setTransUrl("frmSingleLineOSD");
            return transControlV2Bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TransControlV2Bean creatTransControlV2BeanNVR(int type, int ch) {
        TransControlV2DataBean transControlV2DataBean = new TransControlV2DataBean();
        transControlV2DataBean.setCh(ch);
        transControlV2DataBean.setDev(1);
        transControlV2DataBean.setType(type == 1 ? 0 : 1);
        transControlV2DataBean.setData((new JsonObject()));
        TransControlV2Bean transControlV2Bean = new TransControlV2Bean();
        transControlV2Bean.setTransType(1);
        transControlV2Bean.setOpt(1);
        transControlV2Bean.setPayloadType(0);
        Gson gson = new Gson();
        String s = gson.toJson(transControlV2DataBean);
        transControlV2Bean.setPayloadLen(s.length());
        transControlV2Bean.setPayload(s);
        transControlV2Bean.setTransUrl("frmSingleLineOSD");
        return transControlV2Bean;
    }

    public static TransControlV2Bean creatTransControlV2BeanNVR(JsonObject json, int ch) {
        try {
            TransControlV2DataBean transControlV2DataBean = new TransControlV2DataBean();
            transControlV2DataBean.setCh(ch);
            transControlV2DataBean.setDev(1);
            transControlV2DataBean.setType(1);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("OSD", json);
            transControlV2DataBean.setData(jsonObject);
            TransControlV2Bean transControlV2Bean = new TransControlV2Bean();
            transControlV2Bean.setTransType(1);
            transControlV2Bean.setOpt(0);
            transControlV2Bean.setPayloadType(0);

            Gson gson = new Gson();
            String s = gson.toJson(transControlV2DataBean);
            transControlV2Bean.setPayloadLen(s.length());
            transControlV2Bean.setPayload(s);
            transControlV2Bean.setTransUrl("frmSingleLineOSD");
            return transControlV2Bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TransControlV2NVRBean creatTransControlV2NVRBeanGetChildDeviceInfo(int ch) {
        try {
            TransControlV2NVRDataBean transControlV2DataBean = new TransControlV2NVRDataBean();
            transControlV2DataBean.setCh(ch);
            transControlV2DataBean.setCommand("frmDevicePara");
            transControlV2DataBean.setType(0);
            TransControlV2DataBean t = new TransControlV2DataBean();
            t.setCh(1);
            t.setDev(1);
            t.setType(0);
            t.setData(new JsonObject());
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(t);
            transControlV2DataBean.setData(jsonElement.getAsJsonObject());

            TransControlV2NVRBean transControlV2Bean = new TransControlV2NVRBean();
            transControlV2Bean.setTransType(1);
            transControlV2Bean.setOpt(1);
            transControlV2Bean.setPayloadType(0);

            Gson gson2 = new Gson();
            String s = gson2.toJson(transControlV2DataBean);
            transControlV2Bean.setPayloadLen(s.length());
            transControlV2Bean.setPayload(s);
            transControlV2Bean.setTransUrl("frmTranIPCCmd");
            return transControlV2Bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
