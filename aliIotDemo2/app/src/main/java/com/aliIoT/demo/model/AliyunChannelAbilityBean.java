package com.aliIoT.demo.model;

import java.util.List;

/**
 * Created by hjt on 2020/7/29
 */
public class AliyunChannelAbilityBean {


    /**
     * Ability : [{"Describe":"EncodeConfig","RWMode":3,"Value":201},{"Describe":"RecordConfig","RWMode":3,"Value":202}]
     * ResultCode : 0
     */

    private int ResultCode;
    private List<AbilityBean> Ability;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public List<AbilityBean> getAbility() {
        return Ability;
    }

    public void setAbility(List<AbilityBean> Ability) {
        this.Ability = Ability;
    }

    public static class AbilityBean {
        /**
         * Describe : EncodeConfig
         * RWMode : 3
         * Value : 201
         */

        private String Describe;
        private int RWMode;
        private int Value;

        public String getDescribe() {
            return Describe;
        }

        public void setDescribe(String Describe) {
            this.Describe = Describe;
        }

        public int getRWMode() {
            return RWMode;
        }

        public void setRWMode(int RWMode) {
            this.RWMode = RWMode;
        }

        public int getValue() {
            return Value;
        }

        public void setValue(int Value) {
            this.Value = Value;
        }
    }
}
