package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by hjt on 2020/9/3
 */
public class PushMessageBean implements Parcelable, Comparable<PushMessageBean> {

    /**
     * deviceType : ALL
     * eventId : 46743
     * gmtModified : 1599120562000
     * isRead : 0
     * keyId : 1000000168840001260
     * messageId : -1
     * gmtCreate : 1599120562000
     * type : MESSAGE
     * title : 移动侦测
     * body : 有动静，请留意
     * target : ACCOUNT
     * iotId : wEwI9SI1CeOnnQBZ3r9K000000
     * messageType : device
     * extData : {"iotId":"wEwI9SI1CeOnnQBZ3r9K000000","
     * icon":"http://iotx-paas-admin.oss-cn-shanghai.aliyuncs.com/publish/image/1585736522545.png",
     * "productKey":"a11jp5YbdSZ","productName":"云眼","categoryId":967}
     * targetValue : 509dopf47625b1f5ae3ceb9aec3c51f4ca405179
     * id : 1000000168840001260
     * tag : 0
     */

    private String deviceType;
    private String eventId;
    private long gmtModified;
    private int isRead;
    private String keyId;
    private String messageId;
    private long gmtCreate;
    private String type;
    private String title;
    private String body;
    private String target;
    private String iotId;
    private String messageType;
    private ExtDataBean extData;
    private String targetValue;
    private long id;
    private int tag;
    private String gatewayId;
    private String nvrDeviceName;//用于展示NVR设备报警消息设备名称

    public String getNvrDeviceName() {
        return nvrDeviceName;
    }

    public void setNvrDeviceName(String nvrDeviceName) {
        Log.e("nvrDeviceName",nvrDeviceName);
        this.nvrDeviceName = nvrDeviceName;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public void nvrDeviceName() {
        setNvrDeviceName("nvr");
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public ExtDataBean getExtData() {
        return extData;
    }

    public void setExtData(ExtDataBean extData) {
        this.extData = extData;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceType);
        dest.writeString(this.eventId);
        dest.writeLong(this.gmtModified);
        dest.writeInt(this.isRead);
        dest.writeString(this.keyId);
        dest.writeString(this.messageId);
        dest.writeLong(this.gmtCreate);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.target);
        dest.writeString(this.iotId);
        dest.writeString(this.messageType);
        dest.writeParcelable(this.extData, flags);
        dest.writeString(this.targetValue);
        dest.writeLong(this.id);
        dest.writeInt(this.tag);
        dest.writeString(this.gatewayId);
        dest.writeString(this.nvrDeviceName);
    }

    public PushMessageBean() {
    }

    protected PushMessageBean(Parcel in) {
        this.deviceType = in.readString();
        this.eventId = in.readString();
        this.gmtModified = in.readLong();
        this.isRead = in.readInt();
        this.keyId = in.readString();
        this.messageId = in.readString();
        this.gmtCreate = in.readLong();
        this.type = in.readString();
        this.title = in.readString();
        this.body = in.readString();
        this.target = in.readString();
        this.iotId = in.readString();
        this.messageType = in.readString();
        this.extData = in.readParcelable(ExtDataBean.class.getClassLoader());
        this.targetValue = in.readString();
        this.id = in.readLong();
        this.tag = in.readInt();
        this.gatewayId = in.readString();
        this.nvrDeviceName = in.readString();
    }

    public static final Creator<PushMessageBean> CREATOR = new Creator<PushMessageBean>() {
        @Override
        public PushMessageBean createFromParcel(Parcel source) {
            return new PushMessageBean(source);
        }

        @Override
        public PushMessageBean[] newArray(int size) {
            return new PushMessageBean[size];
        }
    };

    @Override
    public int compareTo(@NonNull PushMessageBean o) {
        if (o == null) {
            return -1;
        }
        if (o.keyId == keyId) {
            return 0;
        }
        if (o.gmtCreate > gmtCreate) {
            return 1;
        } else if (o.gmtCreate < gmtCreate) {
            return -1;
        } else {
            return 1;
        }
    }
}
