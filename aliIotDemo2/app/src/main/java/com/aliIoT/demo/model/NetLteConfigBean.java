package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NetLteConfigBean implements Parcelable {
    private int LinkStatus;
    private String CCID;
    private String IMEI;
    private int ErrorCode;
    private int SignalStrength;

    public NetLteConfigBean() {

    }

    protected NetLteConfigBean(Parcel in) {
        LinkStatus = in.readInt();
        CCID = in.readString();
        IMEI = in.readString();
        ErrorCode = in.readInt();
        SignalStrength = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(LinkStatus);
        dest.writeString(CCID);
        dest.writeString(IMEI);
        dest.writeInt(ErrorCode);
        dest.writeInt(SignalStrength);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetLteConfigBean> CREATOR = new Creator<NetLteConfigBean>() {
        @Override
        public NetLteConfigBean createFromParcel(Parcel in) {
            return new NetLteConfigBean(in);
        }

        @Override
        public NetLteConfigBean[] newArray(int size) {
            return new NetLteConfigBean[size];
        }
    };

    public int getLinkStatus() {
        return LinkStatus;
    }

    public void setLinkStatus(int linkStatus) {
        LinkStatus = linkStatus;
    }

    public String getCCID() {
        return CCID;
    }

    public void setCCID(String CCID) {
        this.CCID = CCID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public int getSignalStrength() {
        return SignalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        SignalStrength = signalStrength;
    }

}
