package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2021/6/7 0007.
 */

public class NVRChildDeviceInfoBean implements Parcelable {

    public static final Creator<NVRChildDeviceInfoBean> CREATOR = new Creator<NVRChildDeviceInfoBean>() {
        @Override
        public NVRChildDeviceInfoBean createFromParcel(Parcel in) {
            return new NVRChildDeviceInfoBean(in);
        }

        @Override
        public NVRChildDeviceInfoBean[] newArray(int size) {
            return new NVRChildDeviceInfoBean[size];
        }
    };
    @SerializedName("WebApiVersion")
    private WebApiVersionDTO webApiVersion;
    @SerializedName("FaceDetectNum")
    private Integer faceDetectNum;
    @SerializedName("SoftwareVersion")
    private String softwareVersion;
    @SerializedName("HardwareVersion")
    private String hardwareVersion;
    @SerializedName("SerialNumber")
    private String serialNumber;
    @SerializedName("DVRType")
    private Integer dVRType;
    @SerializedName("CustomerSN")
    private String customerSN;
    @SerializedName("CfgVersion")
    private String cfgVersion;
    @SerializedName("CfgDate")
    private String cfgDate;
    @SerializedName("DVRName")
    private String dVRName;
    @SerializedName("AlarmInPortNum")
    private Integer alarmInPortNum;
    @SerializedName("AlarmOutPortNum")
    private Integer alarmOutPortNum;
    @SerializedName("DeviceTypeString")
    private String deviceTypeString;
    @SerializedName("FunctionInfo")
    private FunctionInfoDTO functionInfo;

    protected NVRChildDeviceInfoBean(Parcel in) {
        webApiVersion = in.readParcelable(WebApiVersionDTO.class.getClassLoader());
        if (in.readByte() == 0) {
            faceDetectNum = null;
        } else {
            faceDetectNum = in.readInt();
        }
        softwareVersion = in.readString();
        hardwareVersion = in.readString();
        serialNumber = in.readString();
        if (in.readByte() == 0) {
            dVRType = null;
        } else {
            dVRType = in.readInt();
        }
        customerSN = in.readString();
        cfgVersion = in.readString();
        cfgDate = in.readString();
        dVRName = in.readString();
        if (in.readByte() == 0) {
            alarmInPortNum = null;
        } else {
            alarmInPortNum = in.readInt();
        }
        if (in.readByte() == 0) {
            alarmOutPortNum = null;
        } else {
            alarmOutPortNum = in.readInt();
        }
        deviceTypeString = in.readString();
        functionInfo = in.readParcelable(FunctionInfoDTO.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(webApiVersion, flags);
        if (faceDetectNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(faceDetectNum);
        }
        dest.writeString(softwareVersion);
        dest.writeString(hardwareVersion);
        dest.writeString(serialNumber);
        if (dVRType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dVRType);
        }
        dest.writeString(customerSN);
        dest.writeString(cfgVersion);
        dest.writeString(cfgDate);
        dest.writeString(dVRName);
        if (alarmInPortNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(alarmInPortNum);
        }
        if (alarmOutPortNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(alarmOutPortNum);
        }
        dest.writeString(deviceTypeString);
        dest.writeParcelable(functionInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public WebApiVersionDTO getWebApiVersion() {
        return webApiVersion;
    }

    public void setWebApiVersion(WebApiVersionDTO webApiVersion) {
        this.webApiVersion = webApiVersion;
    }

    public Integer getFaceDetectNum() {
        return faceDetectNum;
    }

    public void setFaceDetectNum(Integer faceDetectNum) {
        this.faceDetectNum = faceDetectNum;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getdVRType() {
        return dVRType;
    }

    public void setdVRType(Integer dVRType) {
        this.dVRType = dVRType;
    }

    public String getCustomerSN() {
        return customerSN;
    }

    public void setCustomerSN(String customerSN) {
        this.customerSN = customerSN;
    }

    public String getCfgVersion() {
        return cfgVersion;
    }

    public void setCfgVersion(String cfgVersion) {
        this.cfgVersion = cfgVersion;
    }

    public String getCfgDate() {
        return cfgDate;
    }

    public void setCfgDate(String cfgDate) {
        this.cfgDate = cfgDate;
    }

    public String getdVRName() {
        return dVRName;
    }

    public void setdVRName(String dVRName) {
        this.dVRName = dVRName;
    }

    public Integer getAlarmInPortNum() {
        return alarmInPortNum;
    }

    public void setAlarmInPortNum(Integer alarmInPortNum) {
        this.alarmInPortNum = alarmInPortNum;
    }

    public Integer getAlarmOutPortNum() {
        return alarmOutPortNum;
    }

    public void setAlarmOutPortNum(Integer alarmOutPortNum) {
        this.alarmOutPortNum = alarmOutPortNum;
    }

    public String getDeviceTypeString() {
        return deviceTypeString;
    }

    public void setDeviceTypeString(String deviceTypeString) {
        this.deviceTypeString = deviceTypeString;
    }

    public FunctionInfoDTO getFunctionInfo() {
        return functionInfo;
    }

    public void setFunctionInfo(FunctionInfoDTO functionInfo) {
        this.functionInfo = functionInfo;
    }

    public static class WebApiVersionDTO implements Parcelable {
        public static final Creator<WebApiVersionDTO> CREATOR = new Creator<WebApiVersionDTO>() {
            @Override
            public WebApiVersionDTO createFromParcel(Parcel in) {
                return new WebApiVersionDTO(in);
            }

            @Override
            public WebApiVersionDTO[] newArray(int size) {
                return new WebApiVersionDTO[size];
            }
        };
        @SerializedName("Standard")
        private String standard;
        @SerializedName("Build")
        private String build;

        protected WebApiVersionDTO(Parcel in) {
            standard = in.readString();
            build = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(standard);
            dest.writeString(build);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }
    }


    public static class FunctionInfoDTO implements Parcelable {
        public static final Creator<FunctionInfoDTO> CREATOR = new Creator<FunctionInfoDTO>() {
            @Override
            public FunctionInfoDTO createFromParcel(Parcel in) {
                return new FunctionInfoDTO(in);
            }

            @Override
            public FunctionInfoDTO[] newArray(int size) {
                return new FunctionInfoDTO[size];
            }
        };
        @SerializedName("SmartInfo")
        private SmartInfoDTO smartInfo;
        @SerializedName("PersonRecord")
        private Integer personRecord;
        @SerializedName("Face")
        private FaceDTO face;

        protected FunctionInfoDTO(Parcel in) {
            smartInfo = in.readParcelable(SmartInfoDTO.class.getClassLoader());
            if (in.readByte() == 0) {
                personRecord = null;
            } else {
                personRecord = in.readInt();
            }
            face = in.readParcelable(FaceDTO.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(smartInfo, flags);
            if (personRecord == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(personRecord);
            }
            dest.writeParcelable(face, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public SmartInfoDTO getSmartInfo() {
            return smartInfo;
        }

        public void setSmartInfo(SmartInfoDTO smartInfo) {
            this.smartInfo = smartInfo;
        }

        public Integer getPersonRecord() {
            return personRecord;
        }

        public void setPersonRecord(Integer personRecord) {
            this.personRecord = personRecord;
        }

        public FaceDTO getFace() {
            return face;
        }

        public void setFace(FaceDTO face) {
            this.face = face;
        }

        public static class SmartInfoDTO implements Parcelable {
            public static final Creator<SmartInfoDTO> CREATOR = new Creator<SmartInfoDTO>() {
                @Override
                public SmartInfoDTO createFromParcel(Parcel in) {
                    return new SmartInfoDTO(in);
                }

                @Override
                public SmartInfoDTO[] newArray(int size) {
                    return new SmartInfoDTO[size];
                }
            };
            @SerializedName("AbilityList")
            private List<String> abilityList;
            @SerializedName("AbilityVersionList")
            private List<Integer> abilityVersionList;

            protected SmartInfoDTO(Parcel in) {
                abilityList = in.createStringArrayList();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeStringList(abilityList);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public List<String> getAbilityList() {
                return abilityList;
            }

            public void setAbilityList(List<String> abilityList) {
                this.abilityList = abilityList;
            }

            public List<Integer> getAbilityVersionList() {
                return abilityVersionList;
            }

            public void setAbilityVersionList(List<Integer> abilityVersionList) {
                this.abilityVersionList = abilityVersionList;
            }
        }


        public static class FaceDTO implements Parcelable {
            public static final Creator<FaceDTO> CREATOR = new Creator<FaceDTO>() {
                @Override
                public FaceDTO createFromParcel(Parcel in) {
                    return new FaceDTO(in);
                }

                @Override
                public FaceDTO[] newArray(int size) {
                    return new FaceDTO[size];
                }
            };
            @SerializedName("EnableFaceTest")
            private Integer enableFaceTest;
            @SerializedName("EnableFaceSnap")
            private Integer enableFaceSnap;
            @SerializedName("EnableFaceCompare")
            private Integer enableFaceCompare;

            protected FaceDTO(Parcel in) {
                if (in.readByte() == 0) {
                    enableFaceTest = null;
                } else {
                    enableFaceTest = in.readInt();
                }
                if (in.readByte() == 0) {
                    enableFaceSnap = null;
                } else {
                    enableFaceSnap = in.readInt();
                }
                if (in.readByte() == 0) {
                    enableFaceCompare = null;
                } else {
                    enableFaceCompare = in.readInt();
                }
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                if (enableFaceTest == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(enableFaceTest);
                }
                if (enableFaceSnap == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(enableFaceSnap);
                }
                if (enableFaceCompare == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(enableFaceCompare);
                }
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public Integer getEnableFaceTest() {
                return enableFaceTest;
            }

            public void setEnableFaceTest(Integer enableFaceTest) {
                this.enableFaceTest = enableFaceTest;
            }

            public Integer getEnableFaceSnap() {
                return enableFaceSnap;
            }

            public void setEnableFaceSnap(Integer enableFaceSnap) {
                this.enableFaceSnap = enableFaceSnap;
            }

            public Integer getEnableFaceCompare() {
                return enableFaceCompare;
            }

            public void setEnableFaceCompare(Integer enableFaceCompare) {
                this.enableFaceCompare = enableFaceCompare;
            }
        }
    }
}
