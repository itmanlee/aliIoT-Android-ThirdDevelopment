package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class TransControlV2NVRBean implements Parcelable {

    public static final Creator<TransControlV2NVRBean> CREATOR = new Creator<TransControlV2NVRBean>() {
        @Override
        public TransControlV2NVRBean createFromParcel(Parcel in) {
            return new TransControlV2NVRBean(in);
        }

        @Override
        public TransControlV2NVRBean[] newArray(int size) {
            return new TransControlV2NVRBean[size];
        }
    };
    @SerializedName("TransType")
    private Integer transType;
    @SerializedName("Opt")
    private Integer opt;
    @SerializedName("TransUrl")
    private String transUrl;
    @SerializedName("PayloadType")
    private Integer payloadType;
    @SerializedName("PayloadLen")
    private Integer payloadLen;
    @SerializedName("Payload")
    private String payload;

    public TransControlV2NVRBean() {
    }

    protected TransControlV2NVRBean(Parcel in) {
        if (in.readByte() == 0) {
            transType = null;
        } else {
            transType = in.readInt();
        }
        if (in.readByte() == 0) {
            opt = null;
        } else {
            opt = in.readInt();
        }
        transUrl = in.readString();
        if (in.readByte() == 0) {
            payloadType = null;
        } else {
            payloadType = in.readInt();
        }
        if (in.readByte() == 0) {
            payloadLen = null;
        } else {
            payloadLen = in.readInt();
        }
        payload = in.readString();
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getOpt() {
        return opt;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    public String getTransUrl() {
        return transUrl;
    }

    public void setTransUrl(String transUrl) {
        this.transUrl = transUrl;
    }

    public Integer getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(Integer payloadType) {
        this.payloadType = payloadType;
    }

    public Integer getPayloadLen() {
        return payloadLen;
    }

    public void setPayloadLen(Integer payloadLen) {
        this.payloadLen = payloadLen;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (transType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(transType);
        }
        if (opt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(opt);
        }
        dest.writeString(transUrl);
        if (payloadType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(payloadType);
        }
        if (payloadLen == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(payloadLen);
        }
        dest.writeString(payload);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
