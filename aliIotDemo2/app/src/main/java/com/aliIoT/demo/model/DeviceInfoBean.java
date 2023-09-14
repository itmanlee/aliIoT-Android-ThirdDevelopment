package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.aliIoT.demo.util.ConstUtil;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by hjt on 2020/5/9
 */
public class DeviceInfoBean implements Parcelable {
    public static final Creator<DeviceInfoBean> CREATOR = new Creator<DeviceInfoBean>() {
        @Override
        public DeviceInfoBean createFromParcel(Parcel source) {
            return new DeviceInfoBean(source);
        }

        @Override
        public DeviceInfoBean[] newArray(int size) {
            return new DeviceInfoBean[size];
        }
    };
    @JSONField(name = "iotId")
    public String deviceId;
    @JSONField(name = "deviceName")
    public String deviceName;
    @JSONField(name = "nickName")
    public String deviceNickName;
    @JSONField(name = "productKey")
    public String productKey;
    @JSONField(name = "productName")
    public String productName;
    @JSONField(name = "productImage")
    public String productImage;
    @JSONField(name = "productModel")
    public String productModel;
    @JSONField(name = "categoryImage")
    public String categoryImage;
    @JSONField(name = "netType")
    public String netType;
    @JSONField(name = "thingType")
    public String thingType;
    @JSONField(name = "identityAlias")
    public String identityAlias;
    @JSONField(name = "status")
    public int status;
    @JSONField(name = "owned")
    public int owned;
    @JSONField(name = "nodeType")
    public String nodeType;
    @JSONField(name = "subDevice")
    public boolean subDevice;
    @JSONField(name = "gmtModified")
    public String gmtModified;
    public boolean deviceHasAI;
    public boolean deviceHasYun;
    @JSONField(name = "isEdgeGateway")
    public boolean isEdgeGateway;
    @JSONField(name = "bindTime")
    public long bindTime;
    @JSONField(name = "identityId")
    public String identityId;
    @JSONField(name = "bound")
    public int childBound;
    private DevicePropertyBean mDevicePropertyBean;

    public DeviceInfoBean() {
    }

    protected DeviceInfoBean(Parcel in) {
        this.deviceId = in.readString();
        this.deviceName = in.readString();
        this.deviceNickName = in.readString();
        this.productKey = in.readString();
        this.productName = in.readString();
        this.productImage = in.readString();
        this.productModel = in.readString();
        this.categoryImage = in.readString();
        this.netType = in.readString();
        this.thingType = in.readString();
        this.identityAlias = in.readString();
        this.status = in.readInt();
        this.owned = in.readInt();
        this.nodeType = in.readString();
        this.subDevice = in.readByte() != 0;
        this.gmtModified = in.readString();
        this.deviceHasAI = in.readByte() != 0;
        this.deviceHasYun = in.readByte() != 0;
        this.isEdgeGateway = in.readByte() != 0;
        this.bindTime = in.readLong();
        this.identityId = in.readString();
        this.childBound = in.readInt();
    }

    public DevicePropertyBean getmDevicePropertyBean() {
        return mDevicePropertyBean;
    }

    public void setmDevicePropertyBean(DevicePropertyBean mDevicePropertyBean) {
        this.mDevicePropertyBean = mDevicePropertyBean;
    }

    public int getChildBound() {
        return childBound;
    }

    public void setChildBound(int childBound) {
        this.childBound = childBound;
    }

    public boolean isEdgeGateway() {
        return isEdgeGateway;
    }

    public void setEdgeGateway(boolean edgeGateway) {
        isEdgeGateway = edgeGateway;
    }

    public long getBindTime() {
        return bindTime;
    }

    public void setBindTime(long bindTime) {
        this.bindTime = bindTime;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNickName() {
        return deviceNickName;
    }

    public void setDeviceNickName(String deviceNickName) {
        this.deviceNickName = deviceNickName;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getThingType() {
        return thingType;
    }

    public void setThingType(String thingType) {
        this.thingType = thingType;
    }

    public String getIdentityAlias() {
        return identityAlias;
    }

    public void setIdentityAlias(String identityAlias) {
        this.identityAlias = identityAlias;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isSubDevice() {
        return subDevice;
    }

    public void setSubDevice(boolean subDevice) {
        this.subDevice = subDevice;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public boolean isDeviceHasAI() {
        return deviceHasAI;
    }



    /* "identityId":"xxx",
             "iotId":"edwB6TqvxxxxxxNa000100",
             "deviceName":"摄像头1",
             "productKey":"产品 pk",
             "productName":"产品名称",
             "productImage":"产品图片",
             "productModel":"产品型号",
             "categoryImage":"xxx",
             "nickName":"设备昵称",
             "netType":"NET_WIFI",
             "thingType":"DEVICE",
             "status":1,
             "owned":1,
             "nodeType":"DEVICE",
             "identityAlias":"133xxx",
             "subDevice":false,
             "gmtModified":"xxx"*/

    public void setDeviceHasAI(boolean deviceHasAI) {
        this.deviceHasAI = deviceHasAI;
    }

    public boolean isDeviceHasYun() {
        return deviceHasYun;
    }

    public void setDeviceHasYun(boolean deviceHasYun) {
        this.deviceHasYun = deviceHasYun;
    }

    public int getDeviceType() {
        if ("GATEWAY".equals(nodeType)) {
            return ConstUtil.DEVICE_TYPE_NVR;
        } else {
            return ConstUtil.DEVICE_TYPE_IPC;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceId);
        dest.writeString(this.deviceName);
        dest.writeString(this.deviceNickName);
        dest.writeString(this.productKey);
        dest.writeString(this.productName);
        dest.writeString(this.productImage);
        dest.writeString(this.productModel);
        dest.writeString(this.categoryImage);
        dest.writeString(this.netType);
        dest.writeString(this.thingType);
        dest.writeString(this.identityAlias);
        dest.writeInt(this.status);
        dest.writeInt(this.owned);
        dest.writeString(this.nodeType);
        dest.writeByte(this.subDevice ? (byte) 1 : (byte) 0);
        dest.writeString(this.gmtModified);
        dest.writeByte(this.deviceHasAI ? (byte) 1 : (byte) 0);
        dest.writeByte(this.deviceHasYun ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEdgeGateway ? (byte) 1 : (byte) 0);
        dest.writeLong(this.bindTime);
        dest.writeString(this.identityId);
        dest.writeInt(this.childBound);
    }
}
