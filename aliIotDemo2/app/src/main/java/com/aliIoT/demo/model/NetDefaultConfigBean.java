package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NetDefaultConfigBean implements Parcelable {
    private String DefaultRoute;
    private int NetType;

    protected NetDefaultConfigBean(Parcel in) {
        DefaultRoute = in.readString();
        NetType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DefaultRoute);
        dest.writeInt(NetType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetDefaultConfigBean> CREATOR = new Creator<NetDefaultConfigBean>() {
        @Override
        public NetDefaultConfigBean createFromParcel(Parcel in) {
            return new NetDefaultConfigBean(in);
        }

        @Override
        public NetDefaultConfigBean[] newArray(int size) {
            return new NetDefaultConfigBean[size];
        }
    };

    public String getDefaultRoute() {
        return DefaultRoute;
    }

    public void setDefaultRoute(String defaultRoute) {
        DefaultRoute = defaultRoute;
    }

    public int getNetType() {
        return NetType;
    }

    public void setNetType(int netType) {
        NetType = netType;
    }
}
