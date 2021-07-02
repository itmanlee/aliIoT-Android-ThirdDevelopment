package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransControlV2Bean implements Parcelable {
    private int TransType;
    private int Opt;
    private String TransUrl;
    private int PayloadType;
    private int PayloadLen;
    private String Payload;

    public TransControlV2Bean() {
    }

    public int getTransType() {
        return TransType;
    }

    public void setTransType(int transType) {
        TransType = transType;
    }

    public int getOpt() {
        return Opt;
    }

    public void setOpt(int opt) {
        Opt = opt;
    }

    public String getTransUrl() {
        return TransUrl;
    }

    public void setTransUrl(String transUrl) {
        TransUrl = transUrl;
    }

    public int getPayloadType() {
        return PayloadType;
    }

    public void setPayloadType(int payloadType) {
        PayloadType = payloadType;
    }

    public int getPayloadLen() {
        return PayloadLen;
    }

    public void setPayloadLen(int payloadLen) {
        PayloadLen = payloadLen;
    }

    public String getPayload() {
        return Payload;
    }

    public void setPayload(String payload) {
        Payload = payload;
    }

    protected TransControlV2Bean(Parcel in) {
        TransType = in.readInt();
        Opt = in.readInt();
        TransUrl = in.readString();
        PayloadType = in.readInt();
        PayloadLen = in.readInt();
        Payload = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(TransType);
        dest.writeInt(Opt);
        dest.writeString(TransUrl);
        dest.writeInt(PayloadType);
        dest.writeInt(PayloadLen);
        dest.writeString(Payload);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransControlV2Bean> CREATOR = new Creator<TransControlV2Bean>() {
        @Override
        public TransControlV2Bean createFromParcel(Parcel in) {
            return new TransControlV2Bean(in);
        }

        @Override
        public TransControlV2Bean[] newArray(int size) {
            return new TransControlV2Bean[size];
        }
    };


}
