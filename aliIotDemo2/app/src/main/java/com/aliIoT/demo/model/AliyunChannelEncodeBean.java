package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aliIoT.demo.R;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.MyApplication;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by hjt on 2020/8/3
 */
public class AliyunChannelEncodeBean implements Parcelable {

    public static final Creator<AliyunChannelEncodeBean> CREATOR = new Creator<AliyunChannelEncodeBean>() {
        @Override
        public AliyunChannelEncodeBean createFromParcel(Parcel source) {
            return new AliyunChannelEncodeBean(source);
        }

        @Override
        public AliyunChannelEncodeBean[] newArray(int size) {
            return new AliyunChannelEncodeBean[size];
        }
    };
    /**
     * StreamType : 0
     * AVType : 1
     * VideoEncType : 0
     * FrameRate : 0
     * BitType : 1
     * PicQuality : 0
     * Bitrate : 19
     * IFrameInterval : 50
     * ResultCode : 0
     * Resolution : 0
     * H264Profile : 2
     */

    private int StreamType;
    private int AVType;
    private int VideoEncType;
    private int FrameRate;
    private int BitType;
    private int PicQuality;
    @SerializedName("BitRate")
    private int Bitrate;
    private int IFrameInterval;
    //private int ResultCode;
    private int Resolution;
    private int H264Profile;
    private int InBit;

    public AliyunChannelEncodeBean() {
    }

    protected AliyunChannelEncodeBean(Parcel in) {
        this.StreamType = in.readInt();
        this.AVType = in.readInt();
        this.VideoEncType = in.readInt();
        this.FrameRate = in.readInt();
        this.BitType = in.readInt();
        this.PicQuality = in.readInt();
        this.Bitrate = in.readInt();
        this.IFrameInterval = in.readInt();
        // this.ResultCode = in.readInt();
        this.Resolution = in.readInt();
        this.H264Profile = in.readInt();
        this.InBit = in.readInt();
    }

    public int getStreamType() {
        return StreamType;
    }

    public void setStreamType(int StreamType) {
        this.StreamType = StreamType;
    }

    public int getAVType() {
        return AVType;
    }

    public void setAVType(int AVType) {
        this.AVType = AVType;
    }

    public int getVideoEncType() {
        return VideoEncType;
    }

    public void setVideoEncType(int VideoEncType) {
        this.VideoEncType = VideoEncType;
    }

    public int getFrameRate() {
        return FrameRate;
    }

    public void setFrameRate(int FrameRate) {
        this.FrameRate = FrameRate;
    }

    public int getBitType() {
        return BitType;
    }

    public void setBitType(int BitType) {
        this.BitType = BitType;
    }

    public int getPicQuality() {
        return PicQuality;
    }

    public void setPicQuality(int PicQuality) {
        this.PicQuality = PicQuality;
    }

    public int getBitrate() {
        return Bitrate;
    }

    public void setBitrate(int Bitrate) {
        this.Bitrate = Bitrate;
    }

    public int getIFrameInterval() {
        return IFrameInterval;
    }
//    public int getResultCode() {
//        return ResultCode;
//    }
//
//    public void setResultCode(int ResultCode) {
//        this.ResultCode = ResultCode;
//    }

    public void setIFrameInterval(int IFrameInterval) {
        this.IFrameInterval = IFrameInterval;
    }

    public int getInBit() {
        return InBit;
    }

    public void setInBit(int inBit) {
        InBit = inBit;
    }

    public int getResolution() {
        return Resolution;
    }

    public void setResolution(int Resolution) {
        this.Resolution = Resolution;
    }

    public int getH264Profile() {
        return H264Profile;
    }

    public void setH264Profile(int H264Profile) {
        this.H264Profile = H264Profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.StreamType);
        dest.writeInt(this.AVType);
        dest.writeInt(this.VideoEncType);
        dest.writeInt(this.FrameRate);
        dest.writeInt(this.BitType);
        dest.writeInt(this.PicQuality);
        dest.writeInt(this.Bitrate);
        dest.writeInt(this.IFrameInterval);
        //dest.writeInt(this.ResultCode);
        dest.writeInt(this.Resolution);
        dest.writeInt(this.H264Profile);
        dest.writeInt(this.InBit);
    }

    public String streamTypeToString() {
        String str = MyApplication.getInstance().getResources().getString(R.string.stream_type_main);
        if (getStreamType() == ConstUtil.STREAMTYPE_CHILD) {
            str = MyApplication.getInstance().getResources().getString(R.string.stream_type_child);
        }
        return str;
    }

    public int streamTypeToInt(String str) {
        if (MyApplication.getInstance().getResources().getString(R.string.stream_type_child).equals(str)) {
            return ConstUtil.STREAMTYPE_CHILD;
        } else {
            return ConstUtil.STREAMTYPE_MAIN;
        }

    }

    public String avTypeToString(ChannelEcondeAbilityBean mBean) {
        try {
            return mBean.getAVTypeMap().get(getAVType());
        } catch (NullPointerException e) {
            return "";
        }

    }

    public String resolutionToString(ChannelEcondeAbilityBean mBean) {
        try {
            return mBean.getResolutionMap().get(getResolution());
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String bitTypeToString(ChannelEcondeAbilityBean mBean) {
        try {
            return mBean.getBitTypeMap().get(getBitType());
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String bitRateToString(ChannelEcondeAbilityBean mBean) {
        try {
            String s = "";
            if (getBitrate() == -1 || getBitrate() == 0) {
                s = getInBit() + "kbs";
            } else {
                s = mBean.getBitRateMap().get(getBitrate());
                if (TextUtils.isEmpty(s)) {
                    if ((getBitrate() & 0x80000000) < 0) {
                        s = (getBitrate() & 0x7fffffff) + "kbs";
                    }
                }
            }
            return s;
        } catch (NullPointerException e) {
            return "";
        }
    }

    public int bitRateToInbit() {
        try {
            int s = 0;
            if ((getBitrate() & 0x80000000) < 0) {
                s = (getBitrate() & 0x7fffffff);
            }

            return s;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public String frameRateToString(ChannelEcondeAbilityBean mBean) {
        try {
            return mBean.getFrameRateMap().get(getFrameRate());
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String videoEncTypeToString(ChannelEcondeAbilityBean mBean) {
        try {
            return mBean.getVideoEncTypeMap().get(getVideoEncType());
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String picQualityToString() {
        String str = "";
        switch (getPicQuality()) {
            case 0: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_0);
                break;
            }
            case 1: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_1);
                break;
            }
            case 2: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_2);
                break;
            }
            case 3: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_3);
                break;
            }
            case 4: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_4);
                break;
            }
            case 5: {
                str = MyApplication.getInstance().getResources().getString(R.string.pic_quality_5);
                break;
            }
        }
        return str;
    }

    public String iFrameIntervalToString() {
        String str = "";
        try {
            str = getIFrameInterval() + "";
        } catch (Exception e) {
        }

        return str;
    }

    public int avTypeToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getAVTypeMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            }
        }
        return i;
    }

    public int resolutionToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getResolutionMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            }
        }
        return i;
    }

    public int bitRateToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getBitRateMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            } else if (str.contains("自定义")) {
                i = -1;
                break;
            }
        }
        return i;
    }

    public int bitTypeToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getBitTypeMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            }
        }
        return i;
    }

    public int frameRateToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getFrameRateMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            }
        }
        return i;
    }

    public int videoEncTypeToInt(String str, ChannelEcondeAbilityBean ecodeAbility) {
        int i = 0;
        Map<Integer, String> avTypeMap = ecodeAbility.getVideoEncTypeMap();
        for (Map.Entry<Integer, String> bean : avTypeMap.entrySet()) {
            if (str.equals(bean.getValue())) {
                i = bean.getKey();
                break;
            }
        }
        return i;
    }

    public int picQualityToInt(String str) {
        int i = 0;
        if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_0))) {
            i = 0;
        } else if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_1))) {
            i = 1;
        } else if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_2))) {
            i = 2;
        } else if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_3))) {
            i = 3;
        } else if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_4))) {
            i = 4;
        } else if (str.equals(MyApplication.getInstance().getResources().getString(R.string.pic_quality_5))) {
            i = 5;
        }
        return i;
    }

    /*
    *  private int StreamType;
        private int AVType;
        private int VideoEncType;
        private int FrameRate;
        private int BitType;
        private int PicQuality;
        private int Bitrate;
        private int IFrameInterval;
        //private int ResultCode;
        private int Resolution;
        private int H264Profile;*/
    public AliyunChannelEncodeBean copyAliyunChannelEncodeBean() {
        AliyunChannelEncodeBean aliyunChannelEncodeBean = new AliyunChannelEncodeBean();
        aliyunChannelEncodeBean.setIFrameInterval(IFrameInterval);
        aliyunChannelEncodeBean.setVideoEncType(VideoEncType);
        aliyunChannelEncodeBean.setPicQuality(PicQuality);
        aliyunChannelEncodeBean.setStreamType(StreamType);
        aliyunChannelEncodeBean.setFrameRate(FrameRate);
        aliyunChannelEncodeBean.setBitrate(Bitrate);
        aliyunChannelEncodeBean.setResolution(Resolution);
        aliyunChannelEncodeBean.setAVType(AVType);
        aliyunChannelEncodeBean.setBitType(BitType);
        aliyunChannelEncodeBean.setH264Profile(H264Profile);
        return aliyunChannelEncodeBean;
    }
}
