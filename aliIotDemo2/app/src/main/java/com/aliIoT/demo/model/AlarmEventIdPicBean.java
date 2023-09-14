package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hjt on 2020/9/23
 */
public class AlarmEventIdPicBean implements Parcelable {
    public static final Creator<AlarmEventIdPicBean> CREATOR = new Creator<AlarmEventIdPicBean>() {
        @Override
        public AlarmEventIdPicBean createFromParcel(Parcel source) {
            return new AlarmEventIdPicBean(source);
        }

        @Override
        public AlarmEventIdPicBean[] newArray(int size) {
            return new AlarmEventIdPicBean[size];
        }
    };
    /**
     * eventId : b8406b5a31c54ba8acdd584f1dcd1123_1600850882071
     * picUrl : https://link-vision-picture-sh.oss-cn-shanghai.aliyuncs.com/BR0PrzsMWr86zbVndKMYs8BlxG3obt1uJXqAiIlG3lQ/c75867ab11b24fbf82d0979a41ccfd76?Expires=1600854483&OSSAccessKeyId=LTAILduaCDAC561K&Signature=pA%2BB9RXyhlAwHg3vqZ2qgVB6sXU%3D
     * iotId : nadmhDvjh81s5W5uxvX3000000
     * alarmPicId : QlIwUHJ6c01Xcjg2emJWbmRLTVlzOEJseEczb2J0MXVKWHFBaUlsRzNsUS9jNzU4NjdhYjExYjI0ZmJmODJkMDk3OWE0MWNjZmQ3Nl8xNjAwODUwODgyMDcx
     * pictureTimeUTC : 2020-09-23T08:48:02Z
     * thumbUrl : https://link-vision-picture-sh.oss-cn-shanghai.aliyuncs.com/BR0PrzsMWr86zbVndKMYs8BlxG3obt1uJXqAiIlG3lQ/c75867ab11b24fbf82d0979a41ccfd76?Expires=1600854483&OSSAccessKeyId=LTAILduaCDAC561K&Signature=qyQOwzNd%2FlVwi9qNA4y72QWogt4%3D&x-oss-process=image%2Fauto-orient%2C1%2Fresize%2Cm_lfit%2Cw_400%2Climit_0%2Fquality%2Cq_90
     * pictureTime : 2020-09-23 16:48:02
     */

    private String eventId;
    private String picUrl;
    private String iotId;
    private String alarmPicId;
    private String pictureTimeUTC;
    private String thumbUrl;
    private String pictureTime;

    public AlarmEventIdPicBean() {
    }

    protected AlarmEventIdPicBean(Parcel in) {
        this.eventId = in.readString();
        this.picUrl = in.readString();
        this.iotId = in.readString();
        this.alarmPicId = in.readString();
        this.pictureTimeUTC = in.readString();
        this.thumbUrl = in.readString();
        this.pictureTime = in.readString();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getAlarmPicId() {
        return alarmPicId;
    }

    public void setAlarmPicId(String alarmPicId) {
        this.alarmPicId = alarmPicId;
    }

    public String getPictureTimeUTC() {
        return pictureTimeUTC;
    }

    public void setPictureTimeUTC(String pictureTimeUTC) {
        this.pictureTimeUTC = pictureTimeUTC;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPictureTime() {
        return pictureTime;
    }

    public void setPictureTime(String pictureTime) {
        this.pictureTime = pictureTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventId);
        dest.writeString(this.picUrl);
        dest.writeString(this.iotId);
        dest.writeString(this.alarmPicId);
        dest.writeString(this.pictureTimeUTC);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.pictureTime);
    }
}
