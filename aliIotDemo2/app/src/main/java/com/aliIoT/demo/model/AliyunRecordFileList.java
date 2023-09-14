package com.aliIoT.demo.model;

import java.util.List;

/**
 * Created by hjt on 2020/7/16
 */
public class AliyunRecordFileList {
    private List<TimeListBean> TimeList;
    private String iotid;

    public String getIotid() {
        return iotid;
    }

    public void setIotid(String iotid) {
        this.iotid = iotid;
    }

    public List<TimeListBean> getTimeList() {
        return TimeList;
    }

    public void setTimeList(List<TimeListBean> TimeList) {
        this.TimeList = TimeList;
    }

    public static class TimeListBean {
        /**
         * Type : 0
         * EndTime : 1594827639
         * BeginTime : 1594827557
         */

        private int Type;
        private int EndTime;
        private int BeginTime;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getEndTime() {
            return EndTime;
        }

        public void setEndTime(int EndTime) {
            this.EndTime = EndTime;
        }

        public int getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(int BeginTime) {
            this.BeginTime = BeginTime;
        }
    }
}
