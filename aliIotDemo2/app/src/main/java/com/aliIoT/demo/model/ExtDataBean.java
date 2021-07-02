package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hjt on 2020/9/3
 */
public class ExtDataBean implements Parcelable {
    /*{"iotId":"31nytPc8CdzeSYzmsrw3000000",
    "extParam":"{\"eventId\":\"b3eea6d1a30b47e28cfaf901a5c84940_1600391415744\",
    \"alarmType\":1,\"eventType\":1,\"eventTimeUtc\":1600362615744}",
    "nickName":"憨憨",
    "icon":"http://iotx-paas-admin.oss-cn-shanghai.aliyuncs.com/publish/image/1585736522545.png",
    "productKey":"a11jp5YbdSZ",
    "productName":"云眼",
    "categoryId":967},
    *
    * */
    private String iotId;
    private String icon;
    private String productKey;
    private String productName;
    private int categoryId;
    private String eventId;
    private int alarmType;
    private int eventType;
    private String extParam;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public ExtDataBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iotId);
        dest.writeString(this.icon);
        dest.writeString(this.productKey);
        dest.writeString(this.productName);
        dest.writeInt(this.categoryId);
        dest.writeString(this.eventId);
        dest.writeInt(this.alarmType);
        dest.writeInt(this.eventType);
        dest.writeString(this.extParam);
    }

    protected ExtDataBean(Parcel in) {
        this.iotId = in.readString();
        this.icon = in.readString();
        this.productKey = in.readString();
        this.productName = in.readString();
        this.categoryId = in.readInt();
        this.eventId = in.readString();
        this.alarmType = in.readInt();
        this.eventType = in.readInt();
        this.extParam = in.readString();
    }

    public static final Creator<ExtDataBean> CREATOR = new Creator<ExtDataBean>() {
        @Override
        public ExtDataBean createFromParcel(Parcel source) {
            return new ExtDataBean(source);
        }

        @Override
        public ExtDataBean[] newArray(int size) {
            return new ExtDataBean[size];
        }
    };

    public void initExtParam() {
        if (!TextUtils.isEmpty(getExtParam())) {
            try {
                JSONObject jsonObject = new JSONObject(getExtParam());
                if (jsonObject.has("eventId")) {
                    eventId = jsonObject.getString("eventId");
                }
                if (jsonObject.has("alarmType")) {
                    alarmType = jsonObject.getInt("alarmType");
                }
                if (jsonObject.has("eventType")) {
                    eventType = jsonObject.getInt("eventType");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
