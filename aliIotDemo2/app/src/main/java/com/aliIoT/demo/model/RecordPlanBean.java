package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.aliIoT.demo.R;
import com.aliIoT.demo.util.MyApplication;
import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hjt on 2020/8/4
 */
public class RecordPlanBean implements Parcelable {

    public static final Creator<RecordPlanBean> CREATOR = new Creator<RecordPlanBean>() {
        @Override
        public RecordPlanBean createFromParcel(Parcel source) {
            return new RecordPlanBean(source);
        }

        @Override
        public RecordPlanBean[] newArray(int size) {
            return new RecordPlanBean[size];
        }
    };
    /**
     * RecordTime : 3
     * PreRecordTime : 0
     * RecordMode : 3
     * RecordSched :
     * [[[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]],
     * [[0, 0, 23, 59], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]]
     * ResultCode : 0
     */

    private int RecordTime;
    private int PreRecordTime;
    private int RecordMode;
    private String RecordSched;
    @Expose(serialize = false, deserialize = false)
    private int ResultCode;
    @Expose(serialize = false, deserialize = false)
    private Map<Integer, List<RecordPlanTime>> mRecordSchedMap;
    @Expose(serialize = false, deserialize = false)
    private String iotid;

    public RecordPlanBean() {
    }

    protected RecordPlanBean(Parcel in) {
        this.RecordTime = in.readInt();
        this.PreRecordTime = in.readInt();
        this.RecordMode = in.readInt();
        this.RecordSched = in.readString();
        this.ResultCode = in.readInt();
        int RecordSchedMapSize = in.readInt();
        this.mRecordSchedMap = new HashMap<Integer, List<RecordPlanTime>>(RecordSchedMapSize);
        for (int i = 0; i < RecordSchedMapSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            List<RecordPlanTime> value = in.createTypedArrayList(RecordPlanTime.CREATOR);
            this.mRecordSchedMap.put(key, value);
        }
    }

    public String getIotid() {
        return iotid;
    }

    public void setIotid(String iotid) {
        this.iotid = iotid;
    }

    public void parseRecordSched() {
        try {
            if (mRecordSchedMap == null) {
                mRecordSchedMap = new HashMap<>();
            }
            JSONArray jsonArray = new JSONArray(RecordSched);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray array = jsonArray.getJSONArray(i);
                ArrayList<RecordPlanTime> recordPlanTimes = new ArrayList<>();
                for (int j = 0; j < array.length(); j++) {
                    JSONArray array1 = array.getJSONArray(j);
                    RecordPlanTime recordPlanTime = new RecordPlanTime();
                    recordPlanTime.setStartHour(array1.getInt(0));
                    recordPlanTime.setStartMinute(array1.getInt(1));
                    recordPlanTime.setEndHour(array1.getInt(2));
                    recordPlanTime.setEndMinute(array1.getInt(3));
                    recordPlanTimes.add(recordPlanTime);
                }
                mRecordSchedMap.put(i, recordPlanTimes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, List<RecordPlanTime>> getRecordSchedMap() {
        return mRecordSchedMap;
    }

    public void setRecordSchedMap(Map<Integer, List<RecordPlanTime>> recordSchedMap) {
        mRecordSchedMap = recordSchedMap;
    }

    public int getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(int RecordTime) {
        this.RecordTime = RecordTime;
    }

    public int getPreRecordTime() {
        return PreRecordTime;
    }

    public void setPreRecordTime(int PreRecordTime) {
        this.PreRecordTime = PreRecordTime;
    }

    public int getRecordMode() {
        return RecordMode;
    }

    public void setRecordMode(int RecordMode) {
        this.RecordMode = RecordMode;
    }

    public String getRecordSched() {
        return RecordSched;
    }

    public void setRecordSched(String RecordSched) {
        this.RecordSched = RecordSched;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.RecordTime);
        dest.writeInt(this.PreRecordTime);
        dest.writeInt(this.RecordMode);
        dest.writeString(this.RecordSched);
        dest.writeInt(this.ResultCode);
        dest.writeInt(this.mRecordSchedMap.size());
        for (Map.Entry<Integer, List<RecordPlanTime>> entry : this.mRecordSchedMap.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeTypedList(entry.getValue());
        }
    }

    public String recordTimeToString() {
        String str = "";
        if (RecordTime == 3) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[0];
        } else if (RecordTime == 4) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[1];
        } else if (RecordTime == 5) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[2];
        } else if (RecordTime == 6) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[3];
        }
        return str;
    }

    public int recordTimeToInt(String str) {
        int i = 3;
        if (MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[0].equals(str)) {
            i = 3;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[1].equals(str)) {
            i = 4;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[2].equals(str)) {
            i = 5;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[3].equals(str)) {
            i = 6;
        }
        return i;
    }

    public String recordTimeNvrToString() {
        String str = "";
        if (RecordTime == 0) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_delay_time)[0];
        } else if (RecordTime / 60 == 3) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_delay_time)[1];
        } else if (RecordTime / 60 == 5) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[2];
        } else if (RecordTime / 60 == 10) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_delay_time)[3];
        }
        return str;
    }

    public int recordTimeNvrToInt(String str) {
        int i = 0;
        if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_delay_time)[1].equals(str)) {
            i = 180;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_delay_time)[2].equals(str)) {
            i = 300;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_delay_time)[3].equals(str)) {
            i = 600;
        } else {
            i = 0;
        }
        return i;
    }

    public String preRecordTimeToString() {
        String str = "";
        if (PreRecordTime == 0) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[0];
        } else if (PreRecordTime == 1) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[1];
        } else if (PreRecordTime == 2) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[2];
        }
        return str;
    }

    public int preRecordTimeToInt(String str) {
        int i = 0;
        if (MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[0].equals(str)) {
            i = 0;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[1].equals(str)) {
            i = 1;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_pre_time)[2].equals(str)) {
            i = 2;
        }
        return i;
    }

    public String preRecordTimeNvrToString() {
        String str = "";
        if (PreRecordTime == 0) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[0];
        } else if (PreRecordTime == 5) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[1];
        } else if (PreRecordTime == 10) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[2];
        } else if (PreRecordTime == 15) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[3];
        } else if (PreRecordTime == 20) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[4];
        } else if (PreRecordTime == 25) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[5];
        } else if (PreRecordTime == 30) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[6];
        }
        return str;
    }

    public int preRecordTimeNvrToInt(String str) {
        int i = 0;
        if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[0].equals(str)) {
            i = 0;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[1].equals(str)) {
            i = 5;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[2].equals(str)) {
            i = 10;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[3].equals(str)) {
            i = 15;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[4].equals(str)) {
            i = 20;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[5].equals(str)) {
            i = 25;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.nvr_record_pre_time)[6].equals(str)) {
            i = 30;
        }
        return i;
    }

    public String recordModeToString() {
        String str = "";
        if (RecordMode == 0) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[0];
        } else if (RecordMode == 1) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[1];
        } else if (RecordMode == 2) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[2];
        } else if (RecordMode == 3) {
            str = MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[3];
        }
        return str;
    }

    public int recordModeToInt(String str) {
        int i = 0;
        if (MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[0].equals(str)) {
            i = 0;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[1].equals(str)) {
            i = 1;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[2].equals(str)) {
            i = 2;
        } else if (MyApplication.getInstance().getResources().getStringArray(R.array.record_mode)[3].equals(str)) {
            i = 3;
        }
        return i;
    }
}
