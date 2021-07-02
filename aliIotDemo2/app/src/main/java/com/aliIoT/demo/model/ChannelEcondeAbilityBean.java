package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hjt on 2020/8/3
 */
public class ChannelEcondeAbilityBean implements Parcelable {

    /**
     * StreamType : 1
     * AVType : [["Main Stream", 0], ["Aux Stream", 1], ["Third Stream", 3]]
     * FrameRate : [["1", 1], ["2", 2], ["3", 3], ["4", 4], ["5", 5], ["6", 6], ["7", 7],
     * ["8", 8], ["9", 9], ["10", 10], ["11", 11], ["12", 12], ["13", 13], ["14", 14],
     * ["15", 15], ["16", 16], ["17", 17], ["18", 18], ["19", 19], ["20", 20], ["21", 21], ["22", 22], ["23", 23], ["24", 24], ["25", 25], ["FULL", 0]]
     * BitType : [["VBR", 0], ["CBR", 1]]
     * BitRate : [["16Kbps", 1], ["32Kbps", 2], ["48Kbps", 3], ["64Kbps", 4],
     * ["80Kbps", 5], ["96Kbps", 6], ["128Kbps", 7], ["160Kbps", 8], ["192Kbps", 9],
     * ["224Kbps", 10], ["256Kbps", 11], ["320Kbps", 12], ["384Kbps", 13], ["448Kbps", 14],
     * ["512Kbps", 15], ["640Kbps", 16], ["768Kbps", 17], ["896Kbps", 18], ["1024Kbps", 19],
     * ["1280Kbps", 20], ["1536Kbps", 21], ["1792Kbps", 22], ["2 Mbps", 23], ["3 Mbps", 24],
     * ["4 Mbps", 25], ["5 Mbps", 26], ["6 Mbps", 27], ["7 Mbps", 28], ["8 Mbps", 29]]
     * Resolution : [["D1(720*576)", 0, 25], ["VGA(640*480)", 1, 25], ["CIF(352*288)", 2, 25]]
     */

    private int StreamType;
    private String AVType;
    private String FrameRate;
    private String BitType;
    @SerializedName("BitRate")
    private String BitRate;
    private int ResultCode;
    private String Resolution;
    private String VideoEncType;
    private Map<Integer, String> AVTypeMap;
    private Map<Integer, String> FrameRateMap;
    private Map<Integer, String> BitTypeMap;
    private Map<Integer, String> BitRateMap;
    private Map<Integer, String> ResolutionMap;
    private Map<Integer, String> VideoEncTypeMap;
    private String minBitRate;
    private String maxBitRate;

    public String getMinBitRate() {
        return minBitRate;
    }

    public void setMinBitRate(String minBitRate) {
        this.minBitRate = minBitRate;
    }

    public String getMaxBitRate() {
        return maxBitRate;
    }

    public void setMaxBitRate(String maxBitRate) {
        this.maxBitRate = maxBitRate;
    }

    public Map<Integer, String> getAVTypeMap() {
        if (AVTypeMap == null) {
            AVTypeMap = creatAVTypeMap();
        }
        return AVTypeMap;
    }

    public void addCustomBitRate() {
        if (BitRateMap != null) {
            String string = "自定义";
            int count = 0;
            minBitRate = "";
            maxBitRate = "";
            for (Map.Entry<Integer, String> map : BitRateMap.entrySet()) {
                if (count == 0) {
                    minBitRate = map.getValue();
                } else if (count == BitRateMap.size() - 1) {
                    maxBitRate = map.getValue();
                }
                count++;
            }
            string = string + minBitRate + "-" + maxBitRate;
            BitRateMap.put(-1, string);
        }
    }

    public void addCustomBitRate2() {
        //{Integer@19169} 0 -> 自定义(16Kbps-8 Mbps)
        if (BitRateMap != null) {
            String string = "自定义";
            int count = 0;
            minBitRate = "";
            maxBitRate = "";
            String s = BitRateMap.get(0);
            s = s.replace(string, "");
            s = s.replace("(", "");
            s = s.replace(")", "");
            String[] split = s.split("-");
            minBitRate = split[0];
            maxBitRate = split[1];
        }
    }

    public Map<Integer, String> creatAVTypeMap() {
        HashMap<Integer, String> mHashMap = new HashMap<>();
        mHashMap.put(0, "视频流");
        mHashMap.put(1, "复合流");

        return mHashMap;
    }

    public Map<Integer, String> getFrameRateMap() {
        return FrameRateMap;
    }

    public Map<Integer, String> getBitTypeMap() {
        if (BitTypeMap == null) {
            BitTypeMap = creatBitTypeMap();
        }
        return BitTypeMap;
    }

    public Map<Integer, String> creatBitTypeMap() {
        HashMap<Integer, String> mHashMap = new HashMap<>();
        mHashMap.put(0, "VBR");
        mHashMap.put(1, "CBR");
        return mHashMap;
    }

    public Map<Integer, String> getBitRateMap() {
        return BitRateMap;
    }

    public Map<Integer, String> getResolutionMap() {
        return ResolutionMap;
    }

    public Map<Integer, String> getVideoEncTypeMap() {
        return VideoEncTypeMap;
    }

    /**
     * 初始化编码能力集合，集合解析未考虑通用性故json字段发生变化时解析虚重写。
     */
    public void initMap() {

        initAVTypeMap(AVType);


        initFrameRateMap(FrameRate);


        initBitTypeMap(BitType);


        initBitRateMap(BitRate);


        initResolutionMap(Resolution);

        initVideoEncTypeMap(VideoEncType);

    }

    private void initVideoEncTypeMap(String videoEncType) {
        VideoEncTypeMap = new HashMap<>();
        if (TextUtils.isEmpty(VideoEncType)) {
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(videoEncType);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                if (!o.getString(0).equals("MJPEG")) { //暂时禁止MJPEG
                    VideoEncTypeMap.put(o.getInt(1), o.getString(0));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initAVTypeMap(String avType) {
        AVTypeMap = new HashMap<>();
        if (TextUtils.isEmpty(AVType)) {
            AVTypeMap = creatAVTypeMap();
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(avType);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                //注意此处写死未考虑通用性
                AVTypeMap.put(o.getInt(1), o.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initFrameRateMap(String frameRate) {
        FrameRateMap = new LinkedHashMap<>();
        if (TextUtils.isEmpty(FrameRate)) {
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(frameRate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                //注意此处写死未考虑通用性
                if ("FULL".equals(o.getString(0))) {
                    FrameRateMap.put(o.getInt(1),"全帧率");
                } else {
                    FrameRateMap.put(o.getInt(1), o.getString(0));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initBitTypeMap(String frameRate) {
        BitTypeMap = new HashMap<>();
        if (TextUtils.isEmpty(BitType)) {
            BitTypeMap = creatBitTypeMap();
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(frameRate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                //注意此处写死未考虑通用性
                BitTypeMap.put(o.getInt(1), o.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initBitRateMap(String frameRate) {
        BitRateMap = new LinkedHashMap<>();
        if (TextUtils.isEmpty(BitRate)) {
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(frameRate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                //注意此处写死未考虑通用性
                if (!TextUtils.isEmpty(o.getString(0)) && o.getString(0).contains("self-Define")) {
                    String replace = o.getString(0).replace("self-Define", "自定义");
                    BitRateMap.put(o.getInt(1), replace);
                } else {
                    BitRateMap.put(o.getInt(1), o.getString(0));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initResolutionMap(String frameRate) {
        ResolutionMap = new HashMap<>();
        if (TextUtils.isEmpty(Resolution)) {
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(frameRate);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray o = (JSONArray) jsonArray.get(i);
                //注意此处写死未考虑通用性
                ResolutionMap.put(o.getInt(1), o.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getStreamType() {
        return StreamType;
    }

    public void setStreamType(int StreamType) {
        this.StreamType = StreamType;
    }

    public String getAVType() {
        return AVType;
    }

    public void setAVType(String AVType) {
        this.AVType = AVType;
    }

    public String getFrameRate() {
        return FrameRate;
    }

    public void setFrameRate(String FrameRate) {
        this.FrameRate = FrameRate;
    }

    public String getBitType() {
        return BitType;
    }

    public void setBitType(String BitType) {
        this.BitType = BitType;
    }

    public String getBitRate() {
        return BitRate;
    }

    public void setBitRate(String BitRate) {
        this.BitRate = BitRate;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String Resolution) {
        this.Resolution = Resolution;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.StreamType);
        dest.writeString(this.AVType);
        dest.writeString(this.FrameRate);
        dest.writeString(this.BitType);
        dest.writeString(this.BitRate);
        dest.writeInt(this.ResultCode);
        dest.writeString(this.Resolution);
        dest.writeInt(this.AVTypeMap.size());
        for (Map.Entry<Integer, String> entry : this.AVTypeMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.FrameRateMap.size());
        for (Map.Entry<Integer, String> entry : this.FrameRateMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.BitTypeMap.size());
        for (Map.Entry<Integer, String> entry : this.BitTypeMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.BitRateMap.size());
        for (Map.Entry<Integer, String> entry : this.BitRateMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.ResolutionMap.size());
        for (Map.Entry<Integer, String> entry : this.ResolutionMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.minBitRate);
        dest.writeString(this.maxBitRate);
    }

    public ChannelEcondeAbilityBean() {
    }

    protected ChannelEcondeAbilityBean(Parcel in) {
        this.StreamType = in.readInt();
        this.AVType = in.readString();
        this.FrameRate = in.readString();
        this.BitType = in.readString();
        this.BitRate = in.readString();
        this.ResultCode = in.readInt();
        this.Resolution = in.readString();
        int AVTypeMapSize = in.readInt();
        this.AVTypeMap = new HashMap<Integer, String>(AVTypeMapSize);
        for (int i = 0; i < AVTypeMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            String value = in.readString();
            this.AVTypeMap.put(key, value);
        }
        int FrameRateMapSize = in.readInt();
        this.FrameRateMap = new HashMap<Integer, String>(FrameRateMapSize);
        for (int i = 0; i < FrameRateMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            String value = in.readString();
            this.FrameRateMap.put(key, value);
        }
        int BitTypeMapSize = in.readInt();
        this.BitTypeMap = new HashMap<Integer, String>(BitTypeMapSize);
        for (int i = 0; i < BitTypeMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            String value = in.readString();
            this.BitTypeMap.put(key, value);
        }
        int BitRateMapSize = in.readInt();
        this.BitRateMap = new HashMap<Integer, String>(BitRateMapSize);
        for (int i = 0; i < BitRateMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            String value = in.readString();
            this.BitRateMap.put(key, value);
        }
        int ResolutionMapSize = in.readInt();
        this.ResolutionMap = new HashMap<Integer, String>(ResolutionMapSize);
        for (int i = 0; i < ResolutionMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            String value = in.readString();
            this.ResolutionMap.put(key, value);
        }
        this.minBitRate = in.readString();
        this.maxBitRate = in.readString();
    }

    public static final Creator<ChannelEcondeAbilityBean> CREATOR = new Creator<ChannelEcondeAbilityBean>() {
        @Override
        public ChannelEcondeAbilityBean createFromParcel(Parcel source) {
            return new ChannelEcondeAbilityBean(source);
        }

        @Override
        public ChannelEcondeAbilityBean[] newArray(int size) {
            return new ChannelEcondeAbilityBean[size];
        }
    };
}
