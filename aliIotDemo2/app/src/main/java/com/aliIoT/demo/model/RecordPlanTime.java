package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjt on 2020/8/4
 */
public class RecordPlanTime implements Parcelable {
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.startHour);
        dest.writeInt(this.startMinute);
        dest.writeInt(this.endHour);
        dest.writeInt(this.endMinute);
    }

    public RecordPlanTime() {
    }

    protected RecordPlanTime(Parcel in) {
        this.startHour = in.readInt();
        this.startMinute = in.readInt();
        this.endHour = in.readInt();
        this.endMinute = in.readInt();
    }

    public static final Creator<RecordPlanTime> CREATOR = new Creator<RecordPlanTime>() {
        @Override
        public RecordPlanTime createFromParcel(Parcel source) {
            return new RecordPlanTime(source);
        }

        @Override
        public RecordPlanTime[] newArray(int size) {
            return new RecordPlanTime[size];
        }
    };
}
