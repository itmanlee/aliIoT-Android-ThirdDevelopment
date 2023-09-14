package com.aliIoT.demo.model;

import android.os.Parcel;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * yxy 2002/11/17
 * 灯板控制结果实体类，请求结果、配置结果；
 */
public class LightConfigResultBean {


    /**
     * ResultCode : 0
     * LightType : Ir
     * IcrLightMode : 0
     * IcrLightAue : 10
     * SupportTWDR : 1
     * SupportLights : ["Ir","Ir_Warm","Warm"]
     */

    private int ResultCode;
    private String LightType;
    private int IcrLightMode;
    private int IcrLightAue;
    private int SupportTWDR;
    private int MinorMode;
    private DayNightMode DayNightMode;
    private List<String> SupportLights;

    public LightConfigResultBean() {
    }

    protected LightConfigResultBean(Parcel in) {
        this.ResultCode = in.readInt();
        this.LightType = in.readString();
        this.IcrLightMode = in.readInt();
        this.IcrLightAue = in.readInt();
        this.SupportTWDR = in.readInt();
        this.DayNightMode = in.readParcelable(LightConfigResultBean.DayNightMode.class.getClassLoader());
        this.SupportLights = in.createStringArrayList();
    }

    public int getMinorMode() {
        return MinorMode;
    }

    public void setMinorMode(int minorMode) {
        MinorMode = minorMode;
    }

    public LightConfigResultBean.DayNightMode getDayNightMode() {
        return DayNightMode;
    }

    public void setDayNightMode(LightConfigResultBean.DayNightMode dayNightMode) {
        DayNightMode = dayNightMode;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getLightType() {
        return LightType;
    }

    public void setLightType(String LightType) {
        this.LightType = LightType;
    }

    public int getIcrLightMode() {
        return IcrLightMode;
    }

    public void setIcrLightMode(int IcrLightMode) {
        this.IcrLightMode = IcrLightMode;
    }

    public int getIcrLightAue() {
        return IcrLightAue;
    }

    public void setIcrLightAue(int IcrLightAue) {
        this.IcrLightAue = IcrLightAue;
    }

    public int getSupportTWDR() {
        return SupportTWDR;
    }

    public void setSupportTWDR(int SupportTWDR) {
        this.SupportTWDR = SupportTWDR;
    }

    public List<String> getSupportLights() {
        return SupportLights;
    }

    public void setSupportLights(List<String> SupportLights) {
        this.SupportLights = SupportLights;
    }

    public static class DayNightMode {
        @JSONField(name = "DayNightMode")
        private int DayNightMode;
        @JSONField(name = "Delay")
        private int Delay;
        @JSONField(name = "NightToDayThreshold")
        private int NightToDayThreshold;
        @JSONField(name = "DayToNightThreshold")
        private int DayToNightThreshold;

        public DayNightMode() {
        }

        protected DayNightMode(Parcel in) {
            this.DayNightMode = in.readInt();
            this.Delay = in.readInt();
            this.NightToDayThreshold = in.readInt();
            this.DayToNightThreshold = in.readInt();
        }

        public int getDayNightMode() {
            return DayNightMode;
        }

        public void setDayNightMode(int dayNightMode) {
            DayNightMode = dayNightMode;
        }

        public int getDelay() {
            return Delay;
        }

        public void setDelay(int delay) {
            Delay = delay;
        }

        public int getNightToDayThreshold() {
            return NightToDayThreshold;
        }

        public void setNightToDayThreshold(int nightToDayThreshold) {
            NightToDayThreshold = nightToDayThreshold;
        }

        public int getDayToNightThreshold() {
            return DayToNightThreshold;
        }

        public void setDayToNightThreshold(int dayToNightThreshold) {
            DayToNightThreshold = dayToNightThreshold;
        }

    }

}
