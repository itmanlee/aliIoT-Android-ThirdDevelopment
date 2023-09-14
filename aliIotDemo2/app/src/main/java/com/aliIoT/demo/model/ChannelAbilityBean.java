package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by hjt on 2020/7/29
 */
public class ChannelAbilityBean implements Parcelable {
    public static final Creator<ChannelAbilityBean> CREATOR = new Creator<ChannelAbilityBean>() {
        @Override
        public ChannelAbilityBean createFromParcel(Parcel source) {
            return new ChannelAbilityBean(source);
        }

        @Override
        public ChannelAbilityBean[] newArray(int size) {
            return new ChannelAbilityBean[size];
        }
    };
    private boolean hasEncodeConfig;
    private boolean hasRecordConfig;
    private int EncodeConfigRWMode;
    private int RecordConfigRWMode;

    public ChannelAbilityBean() {
    }

    protected ChannelAbilityBean(Parcel in) {
        this.hasEncodeConfig = in.readByte() != 0;
        this.hasRecordConfig = in.readByte() != 0;
        this.EncodeConfigRWMode = in.readInt();
        this.RecordConfigRWMode = in.readInt();
    }

    public boolean isHasEncodeConfig() {
        return hasEncodeConfig;
    }

    public void setHasEncodeConfig(boolean hasEncodeConfig) {
        this.hasEncodeConfig = hasEncodeConfig;
    }

    public boolean isHasRecordConfig() {
        return hasRecordConfig;
    }

    public void setHasRecordConfig(boolean hasRecordConfig) {
        this.hasRecordConfig = hasRecordConfig;
    }

    public int getEncodeConfigRWMode() {
        return EncodeConfigRWMode;
    }

    public void setEncodeConfigRWMode(int encodeConfigRWMode) {
        EncodeConfigRWMode = encodeConfigRWMode;
    }

    public int getRecordConfigRWMode() {
        return RecordConfigRWMode;
    }

    public void setRecordConfigRWMode(int recordConfigRWMode) {
        RecordConfigRWMode = recordConfigRWMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.hasEncodeConfig ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasRecordConfig ? (byte) 1 : (byte) 0);
        dest.writeInt(this.EncodeConfigRWMode);
        dest.writeInt(this.RecordConfigRWMode);
    }

    public void aliyunChannelAbilityBeanToChannelAbilityBean(@Nonnull AliyunChannelAbilityBean bean) {
        List<AliyunChannelAbilityBean.AbilityBean> ability = bean.getAbility();
        if (ability != null) {
            for (AliyunChannelAbilityBean.AbilityBean b : ability) {
                switch (b.getValue()) {
                    case 201: {
                        hasEncodeConfig = true;
                        EncodeConfigRWMode = b.getRWMode();
                        break;
                    }
                    case 202: {
                        hasRecordConfig = true;
                        RecordConfigRWMode = b.getRWMode();
                        break;
                    }
                }
            }
        }
    }
}
