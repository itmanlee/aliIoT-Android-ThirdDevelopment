package com.aliIoT.demo.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class TransControlV2NVRDataBean implements Parcelable {

    @SerializedName("Type")
    private Integer type;
    @SerializedName("Ch")
    private Integer ch;
    @SerializedName("Command")
    private String command;
    @SerializedName("Data")
    private JsonObject data;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCh() {
        return ch;
    }

    public void setCh(Integer ch) {
        this.ch = ch;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public TransControlV2NVRDataBean() {
    }


    protected TransControlV2NVRDataBean(Parcel in) {
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        if (in.readByte() == 0) {
            ch = null;
        } else {
            ch = in.readInt();
        }
        command = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        if (ch == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ch);
        }
        dest.writeString(command);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransControlV2NVRDataBean> CREATOR = new Creator<TransControlV2NVRDataBean>() {
        @Override
        public TransControlV2NVRDataBean createFromParcel(Parcel in) {
            return new TransControlV2NVRDataBean(in);
        }

        @Override
        public TransControlV2NVRDataBean[] newArray(int size) {
            return new TransControlV2NVRDataBean[size];
        }
    };
}
