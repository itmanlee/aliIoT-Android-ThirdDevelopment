package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class OSDBean implements Parcelable {

    public static final Creator<OSDBean> CREATOR = new Creator<OSDBean>() {
        @Override
        public OSDBean createFromParcel(Parcel in) {
            return new OSDBean(in);
        }

        @Override
        public OSDBean[] newArray(int size) {
            return new OSDBean[size];
        }
    };
    /**
     * ChanName : IpCamera
     * IsShowChanName : 1
     * ChanNameTopLeftX : 0
     * ChanNameTopLeftY : 0
     * ChanNameTopLeftW : 100
     * ChanNameTopLeftH : 25
     * OSDTopLeftX : 350
     * OSDTopLeftY : 0
     * OSDTopLeftW : 80
     * OSDTopLeftH : 25
     * OSDType : 1
     * IsDisplayWeek : 1
     * OSDAttribute : 0
     * OSDHourType : 1
     */

    @SerializedName("ChanName")
    private String ChanName;
    @SerializedName("IsShowChanName")
    private Integer IsShowChanName;
    @SerializedName("ChanNameTopLeftX")
    private Integer ChanNameTopLeftX;
    @SerializedName("ChanNameTopLeftY")
    private Integer ChanNameTopLeftY;
    @SerializedName("ChanNameTopLeftW")
    private Integer ChanNameTopLeftW;
    @SerializedName("ChanNameTopLeftH")
    private Integer ChanNameTopLeftH;
    @SerializedName("OSDTopLeftX")
    private Integer OSDTopLeftX;
    @SerializedName("OSDTopLeftY")
    private Integer OSDTopLeftY;
    @SerializedName("OSDTopLeftW")
    private Integer OSDTopLeftW;
    @SerializedName("OSDTopLeftH")
    private Integer OSDTopLeftH;
    @SerializedName("OSDType")
    private Integer OSDType;
    @SerializedName("IsDisplayWeek")
    private Integer IsDisplayWeek;
    @SerializedName("IsShowOSD")
    private Integer IsShowOSD;
    @SerializedName("OSDAttribute")
    private Integer OSDAttribute;
    @SerializedName("OSDHourType")
    private Integer OSDHourType;

    public OSDBean() {
    }

    protected OSDBean(Parcel in) {
        ChanName = in.readString();
        if (in.readByte() == 0) {
            IsShowChanName = null;
        } else {
            IsShowChanName = in.readInt();
        }
        if (in.readByte() == 0) {
            ChanNameTopLeftX = null;
        } else {
            ChanNameTopLeftX = in.readInt();
        }
        if (in.readByte() == 0) {
            ChanNameTopLeftY = null;
        } else {
            ChanNameTopLeftY = in.readInt();
        }
        if (in.readByte() == 0) {
            ChanNameTopLeftW = null;
        } else {
            ChanNameTopLeftW = in.readInt();
        }
        if (in.readByte() == 0) {
            ChanNameTopLeftH = null;
        } else {
            ChanNameTopLeftH = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDTopLeftX = null;
        } else {
            OSDTopLeftX = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDTopLeftY = null;
        } else {
            OSDTopLeftY = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDTopLeftW = null;
        } else {
            OSDTopLeftW = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDTopLeftH = null;
        } else {
            OSDTopLeftH = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDType = null;
        } else {
            OSDType = in.readInt();
        }
        if (in.readByte() == 0) {
            IsDisplayWeek = null;
        } else {
            IsDisplayWeek = in.readInt();
        }
        if (in.readByte() == 0) {
            IsShowOSD = null;
        } else {
            IsShowOSD = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDAttribute = null;
        } else {
            OSDAttribute = in.readInt();
        }
        if (in.readByte() == 0) {
            OSDHourType = null;
        } else {
            OSDHourType = in.readInt();
        }
    }

    public Integer getIsShowOSD() {
        return IsShowOSD;
    }

    public void setIsShowOSD(Integer isShowOSD) {
        IsShowOSD = isShowOSD;
    }

    public String getChanName() {
        return ChanName;
    }

    public void setChanName(String chanName) {
        ChanName = chanName;
    }

    public Integer getIsShowChanName() {
        return IsShowChanName;
    }

    public void setIsShowChanName(Integer isShowChanName) {
        IsShowChanName = isShowChanName;
    }

    public Integer getChanNameTopLeftX() {
        return ChanNameTopLeftX;
    }

    public void setChanNameTopLeftX(Integer chanNameTopLeftX) {
        ChanNameTopLeftX = chanNameTopLeftX;
    }

    public Integer getChanNameTopLeftY() {
        return ChanNameTopLeftY;
    }

    public void setChanNameTopLeftY(Integer chanNameTopLeftY) {
        ChanNameTopLeftY = chanNameTopLeftY;
    }

    public Integer getChanNameTopLeftW() {
        return ChanNameTopLeftW;
    }

    public void setChanNameTopLeftW(Integer chanNameTopLeftW) {
        ChanNameTopLeftW = chanNameTopLeftW;
    }

    public Integer getChanNameTopLeftH() {
        return ChanNameTopLeftH;
    }

    public void setChanNameTopLeftH(Integer chanNameTopLeftH) {
        ChanNameTopLeftH = chanNameTopLeftH;
    }

    public Integer getOSDTopLeftX() {
        return OSDTopLeftX;
    }

    public void setOSDTopLeftX(Integer OSDTopLeftX) {
        this.OSDTopLeftX = OSDTopLeftX;
    }

    public Integer getOSDTopLeftY() {
        return OSDTopLeftY;
    }

    public void setOSDTopLeftY(Integer OSDTopLeftY) {
        this.OSDTopLeftY = OSDTopLeftY;
    }

    public Integer getOSDTopLeftW() {
        return OSDTopLeftW;
    }

    public void setOSDTopLeftW(Integer OSDTopLeftW) {
        this.OSDTopLeftW = OSDTopLeftW;
    }

    public Integer getOSDTopLeftH() {
        return OSDTopLeftH;
    }

    public void setOSDTopLeftH(Integer OSDTopLeftH) {
        this.OSDTopLeftH = OSDTopLeftH;
    }

    public Integer getOSDType() {
        return OSDType;
    }

    public void setOSDType(Integer OSDType) {
        this.OSDType = OSDType;
    }

    public Integer getIsDisplayWeek() {
        return IsDisplayWeek;
    }

    public void setIsDisplayWeek(Integer isDisplayWeek) {
        IsDisplayWeek = isDisplayWeek;
    }

    public Integer getOSDAttribute() {
        return OSDAttribute;
    }

    public void setOSDAttribute(Integer OSDAttribute) {
        this.OSDAttribute = OSDAttribute;
    }

    public Integer getOSDHourType() {
        return OSDHourType;
    }

    public void setOSDHourType(Integer OSDHourType) {
        this.OSDHourType = OSDHourType;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ChanName);
        if (IsShowChanName == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(IsShowChanName);
        }
        if (ChanNameTopLeftX == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ChanNameTopLeftX);
        }
        if (ChanNameTopLeftY == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ChanNameTopLeftY);
        }
        if (ChanNameTopLeftW == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ChanNameTopLeftW);
        }
        if (ChanNameTopLeftH == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ChanNameTopLeftH);
        }
        if (OSDTopLeftX == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDTopLeftX);
        }
        if (OSDTopLeftY == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDTopLeftY);
        }
        if (OSDTopLeftW == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDTopLeftW);
        }
        if (OSDTopLeftH == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDTopLeftH);
        }
        if (OSDType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDType);
        }
        if (IsDisplayWeek == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(IsDisplayWeek);
        }
        if (IsShowOSD == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(IsShowOSD);
        }
        if (OSDAttribute == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDAttribute);
        }
        if (OSDHourType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(OSDHourType);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
