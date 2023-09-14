package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by hjt on 2020/7/23
 */
public class DevicePropertyBean implements Parcelable {
    public static final Creator<DevicePropertyBean> CREATOR = new Creator<DevicePropertyBean>() {
        @Override
        public DevicePropertyBean createFromParcel(Parcel in) {
            return new DevicePropertyBean(in);
        }

        @Override
        public DevicePropertyBean[] newArray(int size) {
            return new DevicePropertyBean[size];
        }
    };
    private int CloudStorageBean;
    private String SerialNo;
    private String P2PVersion;
    private int ChanNum;
    private String DevType;
    private String DevVersion;
    private String HardVersion;
    private String Vendor;
    private String BuildDate;
    private int AlarmFrequencyLevel;//发送频率
    private int AlarmSwitch;//报警开关
    private boolean UpgradeStatus;//true：有新版本；false：已经最新；
    private boolean CounterWire;
    private boolean DetectWire;
    private boolean DetectRegion;
    private boolean ObjectRegion;
    private boolean DetectFire;
    private boolean VideoDiagnose;
    private boolean DetectFace;
    private boolean RecognitionFace;
    private boolean SoundDetect;
    private boolean Retrograde;
    private boolean HighDensity;
    private boolean Humanoid;
    private boolean MaxHeight;
    private boolean Run;
    private boolean ViolentMotion;
    private boolean DetectPerson;
    private boolean EBike;
    private NetLteConfigBean mNetLteConfigBean;
    private NetDefaultConfigBean mNetDefaultConfigBean;
    private String LastOnlineTime;
    private int AlarmInCount;
    private int AlarmOutCount;
    private List<String> SmartAbilityList;
    private int AINum;
    private int AONum;

    protected DevicePropertyBean(Parcel in) {
        CloudStorageBean = in.readInt();
        SerialNo = in.readString();
        P2PVersion = in.readString();
        ChanNum = in.readInt();
        DevType = in.readString();
        DevVersion = in.readString();
        HardVersion = in.readString();
        Vendor = in.readString();
        BuildDate = in.readString();
        AlarmFrequencyLevel = in.readInt();
        AlarmSwitch = in.readInt();
        UpgradeStatus = in.readByte() != 0;
        CounterWire = in.readByte() != 0;
        DetectWire = in.readByte() != 0;
        DetectRegion = in.readByte() != 0;
        ObjectRegion = in.readByte() != 0;
        DetectFire = in.readByte() != 0;
        VideoDiagnose = in.readByte() != 0;
        DetectFace = in.readByte() != 0;
        RecognitionFace = in.readByte() != 0;
        SoundDetect = in.readByte() != 0;
        Retrograde = in.readByte() != 0;
        HighDensity = in.readByte() != 0;
        Humanoid = in.readByte() != 0;
        MaxHeight = in.readByte() != 0;
        Run = in.readByte() != 0;
        ViolentMotion = in.readByte() != 0;
        DetectPerson = in.readByte() != 0;
        EBike = in.readByte() != 0;
        mNetLteConfigBean = in.readParcelable(NetLteConfigBean.class.getClassLoader());
        mNetDefaultConfigBean = in.readParcelable(NetDefaultConfigBean.class.getClassLoader());
        LastOnlineTime = in.readString();
        AlarmInCount = in.readInt();
        AlarmOutCount = in.readInt();
        SmartAbilityList = in.createStringArrayList();
        AINum = in.readInt();
        AONum = in.readInt();
    }

    public DevicePropertyBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(CloudStorageBean);
        dest.writeString(SerialNo);
        dest.writeString(P2PVersion);
        dest.writeInt(ChanNum);
        dest.writeString(DevType);
        dest.writeString(DevVersion);
        dest.writeString(HardVersion);
        dest.writeString(Vendor);
        dest.writeString(BuildDate);
        dest.writeInt(AlarmFrequencyLevel);
        dest.writeInt(AlarmSwitch);
        dest.writeByte((byte) (UpgradeStatus ? 1 : 0));
        dest.writeByte((byte) (CounterWire ? 1 : 0));
        dest.writeByte((byte) (DetectWire ? 1 : 0));
        dest.writeByte((byte) (DetectRegion ? 1 : 0));
        dest.writeByte((byte) (ObjectRegion ? 1 : 0));
        dest.writeByte((byte) (DetectFire ? 1 : 0));
        dest.writeByte((byte) (VideoDiagnose ? 1 : 0));
        dest.writeByte((byte) (DetectFace ? 1 : 0));
        dest.writeByte((byte) (RecognitionFace ? 1 : 0));
        dest.writeByte((byte) (SoundDetect ? 1 : 0));
        dest.writeByte((byte) (Retrograde ? 1 : 0));
        dest.writeByte((byte) (HighDensity ? 1 : 0));
        dest.writeByte((byte) (Humanoid ? 1 : 0));
        dest.writeByte((byte) (MaxHeight ? 1 : 0));
        dest.writeByte((byte) (Run ? 1 : 0));
        dest.writeByte((byte) (ViolentMotion ? 1 : 0));
        dest.writeByte((byte) (DetectPerson ? 1 : 0));
        dest.writeByte((byte) (EBike ? 1 : 0));
        dest.writeParcelable(mNetLteConfigBean, flags);
        dest.writeParcelable(mNetDefaultConfigBean, flags);
        dest.writeString(LastOnlineTime);
        dest.writeInt(AlarmInCount);
        dest.writeInt(AlarmOutCount);
        dest.writeStringList(SmartAbilityList);
        dest.writeInt(AINum);
        dest.writeInt(AONum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getSmartAbilityList() {
        return SmartAbilityList;
    }

    public void setSmartAbilityList(List<String> smartAbilityList) {
        SmartAbilityList = smartAbilityList;
    }

    public int getAlarmOutCount() {
        return AlarmOutCount;
    }

    public void setAlarmOutCount(int alarmOutCount) {
        AlarmOutCount = alarmOutCount;
    }

    public NetLteConfigBean getNetLteConfigBean() {
        return mNetLteConfigBean;
    }

    public void setNetLteConfigBean(NetLteConfigBean mNetLteConfigBean) {
        this.mNetLteConfigBean = mNetLteConfigBean;
    }

    public NetDefaultConfigBean getNetDefaultConfigBean() {
        return mNetDefaultConfigBean;
    }

    public void setNetDefaultConfigBean(NetDefaultConfigBean mNetDefaultConfigBean) {
        this.mNetDefaultConfigBean = mNetDefaultConfigBean;
    }

    public boolean isEBike() {
        return EBike;
    }

    public void setEBike(boolean EBike) {
        this.EBike = EBike;
    }

    public NetLteConfigBean getmNetLteConfigBean() {
        return mNetLteConfigBean;
    }

    public void setmNetLteConfigBean(NetLteConfigBean mNetLteConfigBean) {
        this.mNetLteConfigBean = mNetLteConfigBean;
    }

    public NetDefaultConfigBean getmNetDefaultConfigBean() {
        return mNetDefaultConfigBean;
    }

    public void setmNetDefaultConfigBean(NetDefaultConfigBean mNetDefaultConfigBean) {
        this.mNetDefaultConfigBean = mNetDefaultConfigBean;
    }

    public String getLastOnlineTime() {
        return LastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        LastOnlineTime = lastOnlineTime;
    }

    public int getAlarmInCount() {
        return AlarmInCount;
    }

    public void setAlarmInCount(int alarmInCount) {
        AlarmInCount = alarmInCount;
    }

    public boolean isDetectPerson() {
        return DetectPerson;
    }

    public void setDetectPerson(boolean detectPerson) {
        DetectPerson = detectPerson;
    }

    public int getAINum() {
        return AINum;
    }

    public void setAINum(int AINum) {
        this.AINum = AINum;
    }

    public int getAONum() {
        return AONum;
    }

    public void setAONum(int AONum) {
        this.AONum = AONum;
    }

    public void AliyunDevicePropertyBeanToDevicePropertyBean(@Nonnull AliyunDevicePropertyBean mbean) {
        if (mbean != null) {
            if (mbean.getNetLteConfig() != null && mbean.getNetLteConfig().getValue() != null) {
                this.mNetLteConfigBean = mbean.getNetLteConfig().getValue();
            }
            if (mbean.getNetDefaultConfig() != null && mbean.getNetDefaultConfig().getValue() != null) {
                this.mNetDefaultConfigBean = mbean.getNetDefaultConfig().getValue();
            }
            if (mbean.getCloudStorage() != null) {
                this.CloudStorageBean = mbean.getCloudStorage().getValue();
            }
            if (mbean.getSerialNo() != null) {
                this.SerialNo = mbean.getSerialNo().getValue();
            }
            if (mbean.getP2PVersion() != null) {
                this.P2PVersion = mbean.getP2PVersion().getValue();
            }
            if (mbean.getChanNum() != null) {
                this.ChanNum = mbean.getChanNum().getValue();
            }
            if (mbean.getDevType() != null) {
                this.DevType = mbean.getDevType().getValue();
            }
            if (mbean.getDevVersion() != null) {
                this.DevVersion = mbean.getDevVersion().getValue();
            }
            if (mbean.getHardVersion() != null) {
                this.HardVersion = mbean.getHardVersion().getValue();
            }
            if (mbean.getVendor() != null) {
                this.Vendor = mbean.getVendor().getValue();
            }
            if (mbean.getBuildDate() != null) {
                this.BuildDate = mbean.getBuildDate().getValue();
            }
            if (mbean.getUpgradeStatus() != null) {
                this.UpgradeStatus = (mbean.getUpgradeStatus().getValue() == 1) ? true : false;
            }
            if (mbean.getAlarmFrequencyLevel() != null) {
                this.AlarmFrequencyLevel = mbean.getAlarmFrequencyLevel().getValue();
            }

            if (mbean.getAlarmSwitch() != null) {
                this.AlarmSwitch = mbean.getAlarmSwitch().getValue();
            }
            if (mbean.getLastOnlineTime() != null) {
                this.LastOnlineTime = mbean.getLastOnlineTime().getValue();
            }
            if (mbean.getAlarmInCount() != null) {
                this.AlarmInCount = mbean.getAlarmInCount().getValue();
            }
            if (mbean.getAlarmOutCount() != null) {
                this.AlarmOutCount = mbean.getAlarmOutCount().getValue();
            }

            if (mbean.getSmartAbility() != null) {
                List<AliyunDevicePropertyBean.SmartAbilityBean.ValueBean> value = mbean.getSmartAbility().getValue();
                List<String> tempList = new ArrayList<>();

                for (AliyunDevicePropertyBean.SmartAbilityBean.ValueBean v : value) {
                    if ("CounterWire".equals(v.getName())) {
                        this.CounterWire = true;
                    } else if ("DetectWire".equals(v.getName())) {
                        this.DetectWire = true;
                    } else if ("DetectRegion".equals(v.getName())) {
                        this.DetectRegion = true;
                    } else if ("ObjectRegion".equals(v.getName())) {
                        this.ObjectRegion = true;
                    } else if ("DetectFire".equals(v.getName())) {
                        this.DetectFire = true;
                    } else if ("VideoDiagnose".equals(v.getName())) {
                        this.VideoDiagnose = true;
                    } else if ("DetectFace".equals(v.getName())) {
                        this.DetectFace = true;
                    } else if ("RecognitionFace".equals(v.getName())) {
                        this.RecognitionFace = true;
                    } else if ("SoundDetect".equals(v.getName())) {
                        this.SoundDetect = true;
                    } else if ("Retrograde".equals(v.getName())) {
                        this.Retrograde = true;
                    } else if ("HighDensity".equals(v.getName())) {
                        this.HighDensity = true;
                    } else if ("Humanoid".equals(v.getName())) {
                        this.Humanoid = true;
                    } else if ("MaxHeight".equals(v.getName())) {
                        this.MaxHeight = true;
                    } else if ("Run".equals(v.getName())) {
                        this.Run = true;
                    } else if ("ViolentMotion".equals(v.getName())) {
                        this.ViolentMotion = true;
                    } else if ("DetectPerson".equals(v.getName())) {
                        this.DetectPerson = true;
                    } else if ("EBike".equals(v.getName())) {
                        this.EBike = true;
                    }
                    tempList.add(v.getName());
                }
                if (tempList != null && tempList.size() > 0) {
                    this.SmartAbilityList = tempList;
                }
            }

            if (mbean.getAlarmOutCount() != null) {
                this.AONum = mbean.getAlarmOutCount().getValue();
            }
        }
    }

    public int getCloudStorageBean() {
        return CloudStorageBean;
    }

    public void setCloudStorageBean(int cloudStorageBean) {
        CloudStorageBean = cloudStorageBean;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getP2PVersion() {
        return P2PVersion;
    }

    public void setP2PVersion(String p2PVersion) {
        P2PVersion = p2PVersion;
    }

    public int getChanNum() {
        return ChanNum;
    }

    public void setChanNum(int chanNum) {
        ChanNum = chanNum;
    }

    public String getDevType() {
        return DevType;
    }

    public void setDevType(String devType) {
        DevType = devType;
    }

    public String getDevVersion() {
        return DevVersion;
    }

    public void setDevVersion(String devVersion) {
        DevVersion = devVersion;
    }

    public String getHardVersion() {
        return HardVersion;
    }

    public void setHardVersion(String hardVersion) {
        HardVersion = hardVersion;
    }

    public boolean isCounterWire() {
        return CounterWire;
    }

    public void setCounterWire(boolean counterWire) {
        CounterWire = counterWire;
    }

    public boolean isDetectWire() {
        return DetectWire;
    }

    public void setDetectWire(boolean detectWire) {
        DetectWire = detectWire;
    }

    public boolean isDetectRegion() {
        return DetectRegion;
    }

    public void setDetectRegion(boolean detectRegion) {
        DetectRegion = detectRegion;
    }

    public boolean isObjectRegion() {
        return ObjectRegion;
    }

    public void setObjectRegion(boolean objectRegion) {
        ObjectRegion = objectRegion;
    }

    public boolean isDetectFire() {
        return DetectFire;
    }

    public void setDetectFire(boolean detectFire) {
        DetectFire = detectFire;
    }

    public boolean isVideoDiagnose() {
        return VideoDiagnose;
    }

    public void setVideoDiagnose(boolean videoDiagnose) {
        VideoDiagnose = videoDiagnose;
    }

    public boolean isDetectFace() {
        return DetectFace;
    }

    public void setDetectFace(boolean detectFace) {
        DetectFace = detectFace;
    }

    public boolean isRecognitionFace() {
        return RecognitionFace;
    }

    public void setRecognitionFace(boolean recognitionFace) {
        RecognitionFace = recognitionFace;
    }

    public boolean isSoundDetect() {
        return SoundDetect;
    }

    public void setSoundDetect(boolean soundDetect) {
        SoundDetect = soundDetect;
    }

    public boolean isRetrograde() {
        return Retrograde;
    }

    public void setRetrograde(boolean retrograde) {
        Retrograde = retrograde;
    }

    public boolean isHighDensity() {
        return HighDensity;
    }

    public void setHighDensity(boolean highDensity) {
        HighDensity = highDensity;
    }

    public boolean isHumanoid() {
        return Humanoid;
    }

    public void setHumanoid(boolean humanoid) {
        Humanoid = humanoid;
    }

    public boolean isMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(boolean maxHeight) {
        MaxHeight = maxHeight;
    }

    public boolean isRun() {
        return Run;
    }

    public void setRun(boolean run) {
        Run = run;
    }

    public boolean isViolentMotion() {
        return ViolentMotion;
    }

    public void setViolentMotion(boolean violentMotion) {
        ViolentMotion = violentMotion;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public boolean isUpgradeStatus() {
        return UpgradeStatus;
    }

    public void setUpgradeStatus(boolean upgradeStatus) {
        UpgradeStatus = upgradeStatus;
    }

    public String getBuildDate() {
        return BuildDate;
    }

    public void setBuildDate(String buildDate) {
        BuildDate = buildDate;
    }

    public int getAlarmFrequencyLevel() {
        return AlarmFrequencyLevel;
    }

    public void setAlarmFrequencyLevel(int alarmFrequencyLevel) {
        AlarmFrequencyLevel = alarmFrequencyLevel;
    }

    public int getAlarmSwitch() {
        return AlarmSwitch;
    }

    public void setAlarmSwitch(int alarmSwitch) {
        AlarmSwitch = alarmSwitch;
    }


}
