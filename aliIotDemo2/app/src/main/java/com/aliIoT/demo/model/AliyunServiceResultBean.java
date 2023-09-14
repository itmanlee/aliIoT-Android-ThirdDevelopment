package com.aliIoT.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hjt on 2020/7/16
 */
public class AliyunServiceResultBean {
    private int code;
    private String id;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void parseJSON(String string, String iotId) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("code")) {
                code = jsonObject.getInt("code");
            }
            if (jsonObject.has("id")) {
                id = jsonObject.getString("id");
            }
            if (jsonObject.has("data")) {
                String data = jsonObject.getString("data");
                AliyunRecordFileList aliyunRecordFileList = com.alibaba.fastjson.JSONObject.parseObject(data, AliyunRecordFileList.class);
                aliyunRecordFileList.setIotid(iotId);
                this.data = aliyunRecordFileList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
