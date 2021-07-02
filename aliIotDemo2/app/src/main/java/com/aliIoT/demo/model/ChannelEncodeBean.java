package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjt on 2020/6/20
 */
public class ChannelEncodeBean implements Parcelable {
    private String iotid;
    private String fratherIotId;
    private String channelName;
    private AliyunChannelEncodeBean mAliyunChannelEncodeBean;

    public String getIotid() {
        return iotid;
    }

    public void setIotid(String iotid) {
        this.iotid = iotid;
    }

    public String getFratherIotId() {
        return fratherIotId;
    }

    public void setFratherIotId(String fratherIotId) {
        this.fratherIotId = fratherIotId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public AliyunChannelEncodeBean getAliyunChannelEncodeBean() {
        return mAliyunChannelEncodeBean;
    }

    public void setmAliyunChannelEncodeBean(AliyunChannelEncodeBean mAliyunChannelEncodeBean) {
        this.mAliyunChannelEncodeBean = mAliyunChannelEncodeBean;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iotid);
        dest.writeString(this.fratherIotId);
        dest.writeString(this.channelName);
        dest.writeParcelable(this.mAliyunChannelEncodeBean, flags);
    }

    public ChannelEncodeBean() {
    }

    protected ChannelEncodeBean(Parcel in) {
        this.iotid = in.readString();
        this.fratherIotId = in.readString();
        this.channelName = in.readString();
        this.mAliyunChannelEncodeBean = in.readParcelable(AliyunChannelEncodeBean.class.getClassLoader());
    }

    public static final Creator<ChannelEncodeBean> CREATOR = new Creator<ChannelEncodeBean>() {
        @Override
        public ChannelEncodeBean createFromParcel(Parcel source) {
            return new ChannelEncodeBean(source);
        }

        @Override
        public ChannelEncodeBean[] newArray(int size) {
            return new ChannelEncodeBean[size];
        }
    };

    public  ChannelEncodeBean copyChannelEncodeBean() {
        ChannelEncodeBean channelEncodeBean = new ChannelEncodeBean();
        channelEncodeBean.setChannelName(channelName);
        channelEncodeBean.setIotid(iotid);
        channelEncodeBean.setFratherIotId(fratherIotId);
        channelEncodeBean.setmAliyunChannelEncodeBean(mAliyunChannelEncodeBean.copyAliyunChannelEncodeBean());
        return channelEncodeBean;
    }
}
