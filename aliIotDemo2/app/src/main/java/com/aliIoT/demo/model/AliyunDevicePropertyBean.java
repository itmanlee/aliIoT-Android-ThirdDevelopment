package com.aliIoT.demo.model;

import java.util.List;

/**
 * Created by hjt on 2020/7/23
 */
public class AliyunDevicePropertyBean {

    /**
     * CloudStorage : {"time":1595469749745,"value":1}
     * SerialNo : {"time":1595469749745,"value":"0bd600028baf0001efac"}
     * P2PVersion : {"time":1595469749745,"value":"V1.1.1.2020050818"}
     * ChanNum : {"time":1595469749745,"value":1}
     * DevType : {"time":1595469749745,"value":"IPC"}
     * DevVersion : {"time":1595469749745,"value":"V4.0.20200718"}
     * SmartAbility : {"time":1595469749745,"value":[{"Version":1,"Name":"CounterWire"},{
     * "Version":1,"Name":"DetectWire"},{"Version":1,"Name":"DetectRegion"},
     * {"Version":1,"Name":"ObjectRegion"},{"Version":1,"Name":"DetectFire"},
     * {"Version":1,"Name":"VideoDiagnose"},{"Version":1,"Name":"DetectFace"},
     * {"Version":1,"Name":"RecognitionFace"},{"Version":1,"Name":"SoundDetect"},
     * {"Version":1,"Name":"Retrograde"},{"Version":1,"Name":"HighDensity"},
     * {"Version":1,"Name":"Humanoid"},{"Version":1,"Name":"MaxHeight"},
     * {"Version":1,"Name":"Run"},{"Version":1,"Name":"ViolentMotion"}]}
     * HardVersion : {"time":1595469749745,"value":"V4.0"}
     */
    /**
     * "UpgradeStatus": {
     * "time": 1604645987188,
     * "value": 1
     * },
     * <p>
     * <p>
     * "Vendor": {
     * "time": 1604645987188,
     * "value": "ENZ"
     * },
     * <p>
     * }
     */
    private LastOnlineTime LastOnlineTime;
    private AlarmInCount AlarmInCount;
    private CloudStorageBean CloudStorage;
    private SerialNoBean SerialNo;
    private P2PVersionBean P2PVersion;
    private ChanNumBean ChanNum;
    private DevTypeBean DevType;
    private DevVersionBean DevVersion;
    private SmartAbilityBean SmartAbility;
    private HardVersionBean HardVersion;
    private Vendor Vendor;
    private UpgradeStatus UpgradeStatus;
    private BuildDate BuildDate;
    private AlarmOutCount AlarmOutCount;
    private AlarmFrequencyLevel AlarmFrequencyLevel;
    private AlarmSwitch AlarmSwitch;
    private NetLteConfig NetLteConfig;
    private NetDefaultConfig DefaultRouteConfig;

    public AliyunDevicePropertyBean.AlarmSwitch getAlarmSwitch() {
        return AlarmSwitch;
    }

    public void setAlarmSwitch(AliyunDevicePropertyBean.AlarmSwitch alarmSwitch) {
        AlarmSwitch = alarmSwitch;
    }

    public AliyunDevicePropertyBean.AlarmFrequencyLevel getAlarmFrequencyLevel() {
        return AlarmFrequencyLevel;
    }

    public void setAlarmFrequencyLevel(AliyunDevicePropertyBean.AlarmFrequencyLevel alarmFrequencyLevel) {
        AlarmFrequencyLevel = alarmFrequencyLevel;
    }

    public AliyunDevicePropertyBean.NetDefaultConfig getNetDefaultConfig() {
        return DefaultRouteConfig;
    }

    public void setNetDefaultConfig(AliyunDevicePropertyBean.NetDefaultConfig netDefaultConfig) {
        DefaultRouteConfig = netDefaultConfig;
    }

    public AliyunDevicePropertyBean.AlarmOutCount getAlarmOutCount() {
        return AlarmOutCount;
    }

    public void setAlarmOutCount(AliyunDevicePropertyBean.AlarmOutCount alarmOutCount) {
        AlarmOutCount = alarmOutCount;
    }

    public AliyunDevicePropertyBean.BuildDate getBuildDate() {
        return BuildDate;
    }

    public void setBuildDate(AliyunDevicePropertyBean.BuildDate buildDate) {
        BuildDate = buildDate;
    }

    public AliyunDevicePropertyBean.Vendor getVendor() {
        return Vendor;
    }

    public void setVendor(AliyunDevicePropertyBean.Vendor vendor) {
        Vendor = vendor;
    }

    public AliyunDevicePropertyBean.UpgradeStatus getUpgradeStatus() {
        return UpgradeStatus;
    }

    public void setUpgradeStatus(AliyunDevicePropertyBean.UpgradeStatus upgradeStatus) {
        UpgradeStatus = upgradeStatus;
    }

    public AliyunDevicePropertyBean.NetLteConfig getNetLteConfig() {
        return NetLteConfig;
    }

    public void setNetLteConfig(AliyunDevicePropertyBean.NetLteConfig netLteConfig) {
        NetLteConfig = netLteConfig;
    }

    public CloudStorageBean getCloudStorage() {
        return CloudStorage;
    }

    public void setCloudStorage(CloudStorageBean CloudStorage) {
        this.CloudStorage = CloudStorage;
    }

    public SerialNoBean getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(SerialNoBean SerialNo) {
        this.SerialNo = SerialNo;
    }

    public P2PVersionBean getP2PVersion() {
        return P2PVersion;
    }

    public void setP2PVersion(P2PVersionBean P2PVersion) {
        this.P2PVersion = P2PVersion;
    }

    public ChanNumBean getChanNum() {
        return ChanNum;
    }

    public void setChanNum(ChanNumBean ChanNum) {
        this.ChanNum = ChanNum;
    }

    public DevTypeBean getDevType() {
        return DevType;
    }

    public void setDevType(DevTypeBean DevType) {
        this.DevType = DevType;
    }

    public DevVersionBean getDevVersion() {
        return DevVersion;
    }

    public void setDevVersion(DevVersionBean DevVersion) {
        this.DevVersion = DevVersion;
    }

    public SmartAbilityBean getSmartAbility() {
        return SmartAbility;
    }

    public void setSmartAbility(SmartAbilityBean SmartAbility) {
        this.SmartAbility = SmartAbility;
    }

    public HardVersionBean getHardVersion() {
        return HardVersion;
    }

    public void setHardVersion(HardVersionBean HardVersion) {
        this.HardVersion = HardVersion;
    }

    public LastOnlineTime getLastOnlineTime() {
        return LastOnlineTime;
    }

    public void setLastOnlineTime(AliyunDevicePropertyBean.LastOnlineTime lastOnlineTime) {
        LastOnlineTime = lastOnlineTime;
    }

    public AliyunDevicePropertyBean.AlarmInCount getAlarmInCount() {
        return AlarmInCount;
    }

    public void setAlarmInCount(AliyunDevicePropertyBean.AlarmInCount alarmInCount) {
        AlarmInCount = alarmInCount;
    }

    public NetDefaultConfig getDefaultRouteConfig() {
        return DefaultRouteConfig;
    }

    public void setDefaultRouteConfig(NetDefaultConfig defaultRouteConfig) {
        DefaultRouteConfig = defaultRouteConfig;
    }

    public static class NetDefaultConfig {
        private long time;
        private NetDefaultConfigBean value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public NetDefaultConfigBean getValue() {
            return value;
        }

        public void setValue(NetDefaultConfigBean value) {
            this.value = value;
        }
    }

    public static class CloudStorageBean {
        /**
         * time : 1595469749745
         * value : 1
         */

        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class LastOnlineTime {
        /**
         * time : 1595469749745
         * value : 1
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class AlarmInCount {
        /**
         * time : 1595469749745
         * value : 1
         */

        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class SerialNoBean {
        /**
         * time : 1595469749745
         * value : 0bd600028baf0001efac
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class P2PVersionBean {
        /**
         * time : 1595469749745
         * value : V1.1.1.2020050818
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ChanNumBean {
        /**
         * time : 1595469749745
         * value : 1
         */

        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class DevTypeBean {
        /**
         * time : 1595469749745
         * value : IPC
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class DevVersionBean {
        /**
         * time : 1595469749745
         * value : V4.0.20200718
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class NetLteConfig {
        private long time;
        private NetLteConfigBean value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public NetLteConfigBean getValue() {
            return value;
        }

        public void setValue(NetLteConfigBean value) {
            this.value = value;
        }

    }

    public static class SmartAbilityBean {
        /**
         * time : 1595469749745
         * value : [{"Version":1,"Name":"CounterWire"},{"Version":1,"Name":"DetectWire"},{"Version":1,"Name":"DetectRegion"},{"Version":1,"Name":"ObjectRegion"},{"Version":1,"Name":"DetectFire"},{"Version":1,"Name":"VideoDiagnose"},{"Version":1,"Name":"DetectFace"},{"Version":1,"Name":"RecognitionFace"},{"Version":1,"Name":"SoundDetect"},{"Version":1,"Name":"Retrograde"},{"Version":1,"Name":"HighDensity"},{"Version":1,"Name":"Humanoid"},{"Version":1,"Name":"MaxHeight"},{"Version":1,"Name":"Run"},{"Version":1,"Name":"ViolentMotion"}]
         */

        private long time;
        private List<ValueBean> value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * Version : 1
             * Name : CounterWire
             */

            private int Version;
            private String Name;

            public int getVersion() {
                return Version;
            }

            public void setVersion(int Version) {
                this.Version = Version;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }

    public static class HardVersionBean {
        /**
         * time : 1595469749745
         * value : V4.0
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class BuildDate {
        /**
         * time : 1595469749745
         * value : V4.0
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Vendor {
        /**
         * time : 1595469749745
         * value : V4.0
         */

        private long time;
        private String value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class UpgradeStatus {
        /**
         * time : 1595469749745
         * value : V4.0
         */

        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class AlarmOutCount {
        /**
         * time : 1595469749745
         * value : V4.0
         */

        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    //yxy 新增推送频率属性对象
    public static class AlarmFrequencyLevel {
        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    //yxy 新增推送频率属性对象
    public static class AlarmSwitch {
        private long time;
        private int value;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
