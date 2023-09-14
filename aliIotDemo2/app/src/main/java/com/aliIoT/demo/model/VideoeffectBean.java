package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjt on 2020/7/28
 */
public class VideoeffectBean implements Parcelable {

    public static final Creator<VideoeffectBean> CREATOR = new Creator<VideoeffectBean>() {
        @Override
        public VideoeffectBean createFromParcel(Parcel source) {
            return new VideoeffectBean(source);
        }

        @Override
        public VideoeffectBean[] newArray(int size) {
            return new VideoeffectBean[size];
        }
    };
    int Brigthness;
    int Contrast;
    int Saturation;
    int Hue;

    public VideoeffectBean(int Brigthness, int Contrast, int Saturation, int Hue) {

        this.Brigthness = Brigthness;
        this.Contrast = Contrast;
        this.Saturation = Saturation;
        this.Hue = Hue;
    }

    public VideoeffectBean() {
    }

    protected VideoeffectBean(Parcel in) {
        this.Brigthness = in.readInt();
        this.Contrast = in.readInt();
        this.Saturation = in.readInt();
        this.Hue = in.readInt();
    }

    public int getBrigthness() {
        return Brigthness;
    }

    public void setBrigthness(int brigthness) {
        Brigthness = brigthness;
    }

    public int getContrast() {
        return Contrast;
    }

    public void setContrast(int contrast) {
        Contrast = contrast;
    }

    public int getSaturation() {
        return Saturation;
    }

    public void setSaturation(int saturation) {
        Saturation = saturation;
    }

    public int getHue() {
        return Hue;
    }

    public void setHue(int hue) {
        Hue = hue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Brigthness);
        dest.writeInt(this.Contrast);
        dest.writeInt(this.Saturation);
        dest.writeInt(this.Hue);
    }
}
