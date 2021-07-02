package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class TransControlV2DataBean implements Parcelable {
    private int Type;
    private int Dev;
    private int Ch;
    private JsonObject Data;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getDev() {
        return Dev;
    }

    public void setDev(int dev) {
        Dev = dev;
    }

    public int getCh() {
        return Ch;
    }

    public void setCh(int ch) {
        Ch = ch;
    }

    public JsonObject getData() {
        return Data;
    }

    public void setData(JsonObject data) {
        Data = data;
    }

    public TransControlV2DataBean() {

    }

    protected TransControlV2DataBean(Parcel in) {
        Type = in.readInt();
        Dev = in.readInt();
        Ch = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Type);
        dest.writeInt(Dev);
        dest.writeInt(Ch);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransControlV2DataBean> CREATOR = new Creator<TransControlV2DataBean>() {
        @Override
        public TransControlV2DataBean createFromParcel(Parcel in) {
            return new TransControlV2DataBean(in);
        }

        @Override
        public TransControlV2DataBean[] newArray(int size) {
            return new TransControlV2DataBean[size];
        }
    };

}
