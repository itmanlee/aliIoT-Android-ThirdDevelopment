package com.aliIoT.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aliIoT.demo.model.RecordPlanBean;
import com.aliIoT.demo.model.RecordPlanTime;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.TimeUtils;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 录像计划界面
 */
public class ChannelVideoSetActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "ChannelVideoSetFragment";
    ImageView mBackView;
    TextView mSaveView;
    TextView channelVideoSetLayoutTvRight;
    ConstraintLayout channelVideoSetLayoutChooseChannelCl;
    TextView channelVideoSetLayoutVideoModelTvRight;
    ConstraintLayout channelVideoSetLayoutVideoModelCl;
    TextView channelVideoSetLayoutWeekTvRight;
    ConstraintLayout channelVideoSetLayoutWeekCl;
    TextView channelVideoSetLayoutTimeQuantumClStart1TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart1;
    TextView channelVideoSetLayoutTimeQuantumClEnd1TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd1;
    Group channelVideoSetLayoutTimeQuantumCl1;
    TextView channelVideoSetLayoutTimeQuantumClStart2TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart2;
    TextView channelVideoSetLayoutTimeQuantumClEnd2TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd2;
    Group channelVideoSetLayoutTimeQuantumCl2;
    TextView channelVideoSetLayoutTimeQuantumClStart3TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart3;
    TextView channelVideoSetLayoutTimeQuantumClEnd3TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd3;
    Group channelVideoSetLayoutTimeQuantumCl3;
    TextView channelVideoSetLayoutTimeQuantumClStart4TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart4;
    TextView channelVideoSetLayoutTimeQuantumClEnd4TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd4;
    Group channelVideoSetLayoutTimeQuantumCl4;
    TextView channelVideoSetLayoutTimeQuantumClStart5TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart5;
    TextView channelVideoSetLayoutTimeQuantumClEnd5TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd5;
    Group channelVideoSetLayoutTimeQuantumCl5;
    TextView channelVideoSetLayoutTimeQuantumClStart6TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart6;
    TextView channelVideoSetLayoutTimeQuantumClEnd6TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd6;
    Group channelVideoSetLayoutTimeQuantumCl6;
    TextView channelVideoSetLayoutTimeQuantumClStart7TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart7;
    TextView channelVideoSetLayoutTimeQuantumClEnd7TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd7;
    Group channelVideoSetLayoutTimeQuantumCl7;
    TextView channelVideoSetLayoutTimeQuantumClStart8TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClStart8;
    TextView channelVideoSetLayoutTimeQuantumClEnd8TvRight;
    ConstraintLayout channelVideoSetLayoutTimeQuantumClEnd8;
    Group channelVideoSetLayoutTimeQuantumCl8;
    RelativeLayout channelVideoSetLayoutAddTimeQuantum;
    RelativeLayout channelVideoSetLayoutRemoveTimeQuantum;
    TextView channelVideoSetLayoutPrerecordTimeTvRight;
    ConstraintLayout channelVideoSetLayoutPrerecordTimeCl;
    TextView channelVideoSetLayoutVideoDelayTvRight;
    ConstraintLayout channelVideoSetLayoutVideoDelayCl;

    int showTimeQuantum = 0;
    private RecordPlanBean mRecordPlanBean;
    ImageView channelVideoSetLayoutIVRight;

    String iotID = MyApplication.getInstance().getIotID();
    Gson gson = new Gson();

    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    static final int ALIYUNSERVICE_GET_CHANNELABILITY = 3;
    static final int ALIYUNSERVICE_DEVICE_RECORDING_PLAN = 5;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case ALIYUNSERVICE_DEVICE_RECORDING_PLAN:
                    mRecordPlanBean = gson.fromJson((String) message.obj, RecordPlanBean.class);
                    if (mRecordPlanBean != null && !TextUtils.isEmpty(mRecordPlanBean.getRecordSched()))
                        mRecordPlanBean.parseRecordSched();

                    showTimeQuantum = 0;
                    initData(mRecordPlanBean);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecordPlanBean = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_channel_video_set_layout);
        initCreat();
    }

    public void initCreat() {
        mBackView = findViewById(R.id.channel_video_set_layout_back);
        mSaveView = findViewById(R.id.channel_video_set_layout_save);
        channelVideoSetLayoutTvRight = findViewById(R.id.channel_video_set_layout_tv_right);
        channelVideoSetLayoutChooseChannelCl = findViewById(R.id.channel_video_set_layout_choose_channel_cl);
        channelVideoSetLayoutVideoModelTvRight = findViewById(R.id.channel_video_set_layout_video_model_tv_right);
        channelVideoSetLayoutVideoModelCl = findViewById(R.id.channel_video_set_layout_video_model_cl);
        channelVideoSetLayoutWeekTvRight = findViewById(R.id.channel_video_set_layout_week_tv_right);
        channelVideoSetLayoutWeekCl = findViewById(R.id.channel_video_set_layout_week_cl);
        channelVideoSetLayoutTimeQuantumClStart1TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start1_tv_right);
        channelVideoSetLayoutTimeQuantumClStart1 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start1);
        channelVideoSetLayoutTimeQuantumClEnd1TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end1_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd1 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end1);
        channelVideoSetLayoutTimeQuantumCl1 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_1);
        channelVideoSetLayoutTimeQuantumClStart2TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start2_tv_right);
        channelVideoSetLayoutTimeQuantumClStart2 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start2);
        channelVideoSetLayoutTimeQuantumClEnd2TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end2_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd2 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end2);
        channelVideoSetLayoutTimeQuantumCl2 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_2);
        channelVideoSetLayoutTimeQuantumClStart3TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start3_tv_right);
        channelVideoSetLayoutTimeQuantumClStart3 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start3);
        channelVideoSetLayoutTimeQuantumClEnd3TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end3_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd3 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end3);
        channelVideoSetLayoutTimeQuantumCl3 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_3);
        channelVideoSetLayoutTimeQuantumClStart4TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start4_tv_right);
        channelVideoSetLayoutTimeQuantumClStart4 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start4);
        channelVideoSetLayoutTimeQuantumClEnd4TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end4_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd4 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end4);
        channelVideoSetLayoutTimeQuantumCl4 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_4);
        channelVideoSetLayoutTimeQuantumClStart5TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start5_tv_right);
        channelVideoSetLayoutTimeQuantumClStart5 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start5);
        channelVideoSetLayoutTimeQuantumClEnd5TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end5_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd5 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end5);
        channelVideoSetLayoutTimeQuantumCl5 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_5);
        channelVideoSetLayoutTimeQuantumClStart6TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start6_tv_right);
        channelVideoSetLayoutTimeQuantumClStart6 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start6);
        channelVideoSetLayoutTimeQuantumClEnd6TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end6_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd6 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end6);
        channelVideoSetLayoutTimeQuantumCl6 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_6);
        channelVideoSetLayoutTimeQuantumClStart7TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start7_tv_right);
        channelVideoSetLayoutTimeQuantumClStart7 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start7);
        channelVideoSetLayoutTimeQuantumClEnd7TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end7_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd7 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end7);
        channelVideoSetLayoutTimeQuantumCl7 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_7);
        channelVideoSetLayoutTimeQuantumClStart8TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start8_tv_right);
        channelVideoSetLayoutTimeQuantumClStart8 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_start8);
        channelVideoSetLayoutTimeQuantumClEnd8TvRight = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end8_tv_right);
        channelVideoSetLayoutTimeQuantumClEnd8 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_end8);
        channelVideoSetLayoutTimeQuantumCl8 = findViewById(R.id.channel_video_set_layout_time_quantum_cl_8);
        channelVideoSetLayoutAddTimeQuantum = findViewById(R.id.channel_video_set_layout_add_time_quantum);
        channelVideoSetLayoutRemoveTimeQuantum = findViewById(R.id.channel_video_set_layout_remove_time_quantum);
        channelVideoSetLayoutPrerecordTimeTvRight = findViewById(R.id.channel_video_set_layout_prerecord_time_tv_right);
        channelVideoSetLayoutPrerecordTimeCl = findViewById(R.id.channel_video_set_layout_prerecord_time_cl);
        channelVideoSetLayoutVideoDelayTvRight = findViewById(R.id.channel_video_set_layout_video_delay_tv_right);
        channelVideoSetLayoutVideoDelayCl = findViewById(R.id.channel_video_set_layout_video_delay_cl);
        channelVideoSetLayoutIVRight = findViewById(R.id.channel_video_set_layout_select);

        channelVideoSetLayoutIVRight.setVisibility(View.INVISIBLE);

        showHideRemove(false);//默认隐藏
        showTimeQuantum = 0;

        mBackView.setOnClickListener(this);
        mSaveView.setOnClickListener(this);
        channelVideoSetLayoutChooseChannelCl.setOnClickListener(this);
        channelVideoSetLayoutVideoModelCl.setOnClickListener(this);
        channelVideoSetLayoutWeekCl.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart1.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd1.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart2.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd2.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart3.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd3.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart4.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd4.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart5.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd5.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart6.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd6.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart7.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd7.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClStart8.setOnClickListener(this);
        channelVideoSetLayoutTimeQuantumClEnd8.setOnClickListener(this);
        channelVideoSetLayoutAddTimeQuantum.setOnClickListener(this);
        channelVideoSetLayoutRemoveTimeQuantum.setOnClickListener(this);
        channelVideoSetLayoutPrerecordTimeCl.setOnClickListener(this);
        channelVideoSetLayoutVideoDelayCl.setOnClickListener(this);
        formatTimeQuantum(showTimeQuantum);
        deviceRecordingPlan(iotID, 1, false);

    }

    private void clearTimeQuantum() {
        channelVideoSetLayoutTimeQuantumClStart1TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart2TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart3TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart4TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart5TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart6TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart7TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClStart8TvRight.setText(this.getResources().getString(R.string.zero_hour));
        channelVideoSetLayoutTimeQuantumClEnd1TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd2TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd3TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd4TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd5TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd6TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd7TvRight.setText(this.getResources().getString(R.string.end_hour));
        channelVideoSetLayoutTimeQuantumClEnd8TvRight.setText(this.getResources().getString(R.string.end_hour));


    }

    private void formatTimeQuantum(int showTimeQuantum) {
        if (showTimeQuantum == 0) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(false);
        } else if (showTimeQuantum == 1) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(false);//为1个时保留，不再减少
        } else if (showTimeQuantum == 2) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else if (showTimeQuantum == 3) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else if (showTimeQuantum == 4) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else if (showTimeQuantum == 5) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else if (showTimeQuantum == 6) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.GONE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else if (showTimeQuantum == 7) {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.GONE);
            showHideRemove(true);
        } else {
            channelVideoSetLayoutTimeQuantumCl1.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl2.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl3.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl4.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl5.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl6.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl7.setVisibility(View.VISIBLE);
            channelVideoSetLayoutTimeQuantumCl8.setVisibility(View.VISIBLE);
            showHideRemove(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.channel_video_set_layout_back: {
                finish();

                break;
            }
            case R.id.channel_video_set_layout_save: {
                if (mRecordPlanBean != null) {
                    temporaryStorageTimeQuantum(TimeUtils.stringToWeek2(channelVideoSetLayoutWeekTvRight.getText().toString()));
                    parseData(mRecordPlanBean);
                }
                break;
            }
            case R.id.channel_video_set_layout_choose_channel_cl: {

                break;
            }
            case R.id.channel_video_set_layout_week_cl: {
                ArrayList<String> list = new ArrayList<>();
                list.addAll(Arrays
                        .asList(this.getResources().getStringArray(R.array.week_array)));
                showDialog(R.id.channel_video_set_layout_week_cl
                        , this.getResources().getString(R.string.week), list);

                if (!mSerarchWindow.isShowing()) {
                    showWindow(mSaveView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_video_model_cl: {
                ArrayList<String> list = new ArrayList<>();
                list.addAll(Arrays
                        .asList(this.getResources().getStringArray(R.array.record_mode)));
                showDialog(R.id.channel_video_set_layout_video_model_cl
                        , this.getResources().getString(R.string.video_model), list);

                if (!mSerarchWindow.isShowing()) {
                    showWindow(mSaveView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start1: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart1TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);

                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end1: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd1TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start2: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart2TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end2: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd2TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start3: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart3TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end3: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd3TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start4: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart4TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end4: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd4TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start5: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart5TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end5: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd5TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start6: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart6TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end6: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd6TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start7: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart7TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end7: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd7TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }

            case R.id.channel_video_set_layout_time_quantum_cl_start8: {
                String nowTime = channelVideoSetLayoutTimeQuantumClStart8TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end8: {
                String nowTime = channelVideoSetLayoutTimeQuantumClEnd8TvRight.getText().toString().trim();
                showTimeDialog(v.getId(), nowTime);
                if (!mTimeWindow.isShowing()) {
                    showTimeWindow(mSaveView);
                } else {
                    mTimeWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_add_time_quantum: {
                if (showTimeQuantum < 8) {
                    showTimeQuantum += 1;
                    formatTimeQuantum(showTimeQuantum);
                } else {
                    Toast.makeText(this, "最多8个", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.channel_video_set_layout_remove_time_quantum: { //减
                checkIsRemoloveCustomTime();
                break;
            }
            case R.id.channel_video_set_layout_prerecord_time_cl: {
                ArrayList<String> list = new ArrayList<>();
                list.addAll(Arrays
                        .asList(this.getResources().getStringArray(R.array.record_pre_time)));
                showDialog(R.id.channel_video_set_layout_prerecord_time_cl
                        , this.getResources().getString(R.string.prerecord_time), list);
                if (!mSerarchWindow.isShowing()) {
                    showWindow(mSaveView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            }
            case R.id.channel_video_set_layout_video_delay_cl: {
                ArrayList<String> list = new ArrayList<>();
                list.addAll(Arrays
                        .asList(this.getResources().getStringArray(R.array.record_delay_time)));
                showDialog(R.id.channel_video_set_layout_video_delay_cl
                        , this.getResources().getString(R.string.video_delay), list);
                if (!mSerarchWindow.isShowing()) {
                    showWindow(mSaveView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            }
        }
    }

    //yxy减标识的显示和隐藏
    public void showHideRemove(boolean b) {
        if (b) {
            if (channelVideoSetLayoutRemoveTimeQuantum.getVisibility() != View.VISIBLE)
                channelVideoSetLayoutRemoveTimeQuantum.setVisibility(View.VISIBLE);
        } else {
            if (channelVideoSetLayoutRemoveTimeQuantum.getVisibility() == View.VISIBLE)
                channelVideoSetLayoutRemoveTimeQuantum.setVisibility(View.GONE);
        }
    }

    //yxy点击减标识，隐藏对应时间段
    private boolean checkIsRemoloveCustomTime() {
        if (channelVideoSetLayoutTimeQuantumCl8.getVisibility() == View.VISIBLE) { //yxy加到了时间段8
            formatTimeQuantum(7);
            showTimeQuantum = 7;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl7.getVisibility() == View.VISIBLE) { //yxy加到了时间段7
            formatTimeQuantum(6);
            showTimeQuantum = 6;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl6.getVisibility() == View.VISIBLE) { //yxy加到了时间段6
            formatTimeQuantum(5);
            showTimeQuantum = 5;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl5.getVisibility() == View.VISIBLE) { //yxy加到了时间段5
            formatTimeQuantum(4);
            showTimeQuantum = 4;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl4.getVisibility() == View.VISIBLE) { //yxy加到了时间段4
            formatTimeQuantum(3);
            showTimeQuantum = 3;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl3.getVisibility() == View.VISIBLE) { //yxy加到了时间段3
            formatTimeQuantum(2);
            showTimeQuantum = 2;
            showHideRemove(true);
            return true;
        }
        if (channelVideoSetLayoutTimeQuantumCl2.getVisibility() == View.VISIBLE) { //yxy加到了时间段2
            formatTimeQuantum(1);
            showTimeQuantum = 1;
            showHideRemove(false);
            return true;
        }
        return false;
    }

    boolean checkTimeIsError(RecordPlanTime t) {
        boolean flag = true;
        if (t != null) {
            if ((t.getEndHour() - t.getStartHour() > 0
                    || (t.getEndHour() - t.getStartHour() == 0 && t.getEndMinute() - t.getStartMinute() > 0))
                    || (t.getStartMinute() == 0 && t.getEndMinute() == 0 && t.getStartHour() == 0 && t.getEndHour() == 0)) {
                flag = false;
            }
        }
        return flag;
    }

    private void parseData(RecordPlanBean mRecordPlanBean) {
        if (mRecordPlanBean.getRecordSchedMap() == null) {
            return;
        }
        Map<Integer, List<RecordPlanTime>> recordSchedMap = mRecordPlanBean.getRecordSchedMap();
        int[][][] ints = new int[7][8][4];
        for (Map.Entry<Integer, List<RecordPlanTime>> bean : recordSchedMap.entrySet()) {
            List<RecordPlanTime> value = bean.getValue();
            for (int i = 0; i < value.size(); i++) {
                RecordPlanTime r = value.get(i);
                if (checkTimeIsError(r)) {
                    showErrorInfo(bean.getKey(), i);
                    return;
                }
                int[] ints1 = new int[4];
                ints1[0] = r.getStartHour();
                ints1[1] = r.getStartMinute();
                ints1[2] = r.getEndHour();
                ints1[3] = r.getEndMinute();
                ints[bean.getKey()][i] = ints1;
            }
        }
        try {
            JSONArray jsonArray = new JSONArray(ints);
            mRecordPlanBean.setRecordSched(jsonArray.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", 2);
            jsonObject.put("RecordTime", mRecordPlanBean.getRecordTime());
            jsonObject.put("PreRecordTime", mRecordPlanBean.getPreRecordTime());
            jsonObject.put("RecordMode", mRecordPlanBean.getRecordMode());
            jsonObject.put("RecordSched", mRecordPlanBean.getRecordSched());
            setDeviceRecordingPlan(iotID, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showErrorInfo(Integer key, int i) {
        String str = "";
        String[] stringArray = this.getResources().getStringArray(R.array.week_array);
        String string = this.getResources().getString(R.string.wrong_time_setting);
        str = stringArray[key] + "," + string.replace("%d", (i + 1) + "");
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }


    private void initData(RecordPlanBean mRecordPlanBean) {
        channelVideoSetLayoutVideoModelTvRight.setText(mRecordPlanBean.recordModeToString());
        channelVideoSetLayoutWeekTvRight.setText(TimeUtils.intToWeek2(0));
        channelVideoSetLayoutPrerecordTimeTvRight.setText(mRecordPlanBean.preRecordTimeToString());
        channelVideoSetLayoutVideoDelayTvRight.setText(mRecordPlanBean.recordTimeToString());
        initDataTime(mRecordPlanBean.getRecordSchedMap(), 0);
    }

    private void initDataTime(Map<Integer, List<RecordPlanTime>> recordSchedMap, int week) {
        if (recordSchedMap == null) {
            return;
        }
        List<RecordPlanTime> recordPlanTimes = recordSchedMap.get(week);
        clearTimeQuantum();
        if (recordPlanTimes != null) {
            for (RecordPlanTime bean : recordPlanTimes) {
                if (bean.getEndHour() != 0 || bean.getEndMinute() != 0 || bean.getStartHour() != 0 || bean.getStartMinute() != 0) {
                    showTimeQuantum += 1;
                    formatTimeQuantum(showTimeQuantum);
                    switch (showTimeQuantum) {
                        case 1: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start1, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end1, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 2: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start2, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end2, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 3: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start3, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end3, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 4: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start4, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end4, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 5: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start5, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end5, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 6: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start6, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end6, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 7: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start7, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end7, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }
                        case 8: {
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_start8, bean.getStartHour(), bean.getStartMinute());
                            setChioseTime(R.id.channel_video_set_layout_time_quantum_cl_end8, bean.getEndHour(), bean.getEndMinute());
                            break;
                        }

                    }
                }
            }
        }
    }

    public void editChange(String str, int mType) {
        switch (mType) {
            case R.id.channel_video_set_layout_choose_channel_cl: {
                channelVideoSetLayoutTvRight.setText(str);
                break;
            }
            case R.id.channel_video_set_layout_week_cl: {
                int i = TimeUtils.stringToWeek2(str);
                temporaryStorageTimeQuantum(TimeUtils.stringToWeek2(channelVideoSetLayoutWeekTvRight.getText().toString()));
                clearTimeQuantum();
                showTimeQuantum = 0;
                initDataTime(mRecordPlanBean.getRecordSchedMap(), i);
                channelVideoSetLayoutWeekTvRight.setText(str);
                break;
            }
            case R.id.channel_video_set_layout_video_model_cl: {
                int i = mRecordPlanBean.recordModeToInt(str);
                mRecordPlanBean.setRecordMode(i);
                channelVideoSetLayoutVideoModelTvRight.setText(str);
                break;
            }
            case R.id.channel_video_set_layout_prerecord_time_cl: {
                int i = mRecordPlanBean.preRecordTimeToInt(str);
                mRecordPlanBean.setPreRecordTime(i);
                channelVideoSetLayoutPrerecordTimeTvRight.setText(str);
                break;
            }
            case R.id.channel_video_set_layout_video_delay_cl: {
                int i = mRecordPlanBean.recordTimeToInt(str);
                mRecordPlanBean.setRecordTime(i);
                channelVideoSetLayoutVideoDelayTvRight.setText(str);
                break;
            }
        }
    }

    private void temporaryStorageTimeQuantum(int week) {
        if (mRecordPlanBean.getRecordSchedMap() == null) {
            return;
        }
        List<RecordPlanTime> recordPlanTimes = mRecordPlanBean.getRecordSchedMap().get(week);
        if (recordPlanTimes == null) {
            return;
        }
        for (int i = 0; i < recordPlanTimes.size(); i++) {
            RecordPlanTime recordPlanTime = recordPlanTimes.get(i);
            switch (i) {
                case 0: {
                    if (channelVideoSetLayoutTimeQuantumCl1.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart1TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd1TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else { //yxy将减掉的时间还原实现过滤
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 1: {
                    if (channelVideoSetLayoutTimeQuantumCl2.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart2TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd2TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 2: {
                    if (channelVideoSetLayoutTimeQuantumCl3.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart3TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd3TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 3: {
                    if (channelVideoSetLayoutTimeQuantumCl4.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart4TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd4TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 4: {
                    if (channelVideoSetLayoutTimeQuantumCl5.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart5TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd5TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 5: {
                    if (channelVideoSetLayoutTimeQuantumCl6.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart6TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd6TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 6: {
                    if (channelVideoSetLayoutTimeQuantumCl7.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart7TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd7TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
                case 7: {
                    if (channelVideoSetLayoutTimeQuantumCl8.getVisibility() == View.VISIBLE) {
                        int[] ints = formatTime(channelVideoSetLayoutTimeQuantumClStart8TvRight.getText().toString());
                        recordPlanTime.setStartHour(ints[0]);
                        recordPlanTime.setStartMinute(ints[1]);
                        ints = formatTime(channelVideoSetLayoutTimeQuantumClEnd8TvRight.getText().toString());
                        recordPlanTime.setEndHour(ints[0]);
                        recordPlanTime.setEndMinute(ints[1]);
                    } else {
                        recordPlanTime.setStartHour(0);
                        recordPlanTime.setStartMinute(0);
                        recordPlanTime.setEndHour(0);
                        recordPlanTime.setEndMinute(0);
                    }
                    break;
                }
            }
        }
    }

    private String formatTime(int hour, int minute) {
        String time = "";
        if (hour < 10) {
            time = "0" + hour;
        } else {
            time = time + hour;
        }
        if (minute < 10) {
            time = time + ":0" + minute;
        } else {
            time = time + ":" + minute;
        }
        return time;
    }

    private int[] formatTime(String time) {
        int[] ints = new int[2];
        String[] split = time.split(":");
        ints[0] = Integer.parseInt(split[0]);
        ints[1] = Integer.parseInt(split[1]);
        return ints;
    }

    public void setChioseTime(int mFromType, int hour, int minute) {
        String s = formatTime(hour, minute);
        switch (mFromType) {
            case R.id.channel_video_set_layout_time_quantum_cl_start1: {
                channelVideoSetLayoutTimeQuantumClStart1TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end1: {
                channelVideoSetLayoutTimeQuantumClEnd1TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start2: {
                channelVideoSetLayoutTimeQuantumClStart2TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end2: {
                channelVideoSetLayoutTimeQuantumClEnd2TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start3: {
                channelVideoSetLayoutTimeQuantumClStart3TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end3: {
                channelVideoSetLayoutTimeQuantumClEnd3TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start4: {
                channelVideoSetLayoutTimeQuantumClStart4TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end4: {
                channelVideoSetLayoutTimeQuantumClEnd4TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start5: {
                channelVideoSetLayoutTimeQuantumClStart5TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end5: {
                channelVideoSetLayoutTimeQuantumClEnd5TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start6: {
                channelVideoSetLayoutTimeQuantumClStart6TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end6: {
                channelVideoSetLayoutTimeQuantumClEnd6TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start7: {
                channelVideoSetLayoutTimeQuantumClStart7TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end7: {
                channelVideoSetLayoutTimeQuantumClEnd7TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_start8: {
                channelVideoSetLayoutTimeQuantumClStart8TvRight.setText(s);
                break;
            }
            case R.id.channel_video_set_layout_time_quantum_cl_end8: {
                channelVideoSetLayoutTimeQuantumClEnd8TvRight.setText(s);
                break;
            }
        }
    }

    public void deviceRecordingPlan(String deviceId, int method, boolean isReadCache) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", method);
            aliyunService(ALIYUNSERVICE_DEVICE_RECORDING_PLAN, deviceId
                    , "RecordConfig", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setDeviceRecordingPlan(String deviceId, JSONObject jsonObject) {
        boolean b = aliyunService(ALIYUNSERVICE_DEVICE_RECORDING_PLAN, deviceId
                , "RecordConfig", jsonObject);
    }


    PopupWindow mSerarchWindow = null;

    public void showDialog(int mType, String title, List<String> lists) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.realplay_light_pup, null);

        mSerarchWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mSerarchWindow.setFocusable(true);
        mSerarchWindow.setOutsideTouchable(true);

        TextView textView = view.findViewById(R.id.light_config_control_title);
        ListView mListView = view.findViewById(R.id.light_config_control_layout_rv);

        textView.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editChange(lists.get(i), mType);
                mSerarchWindow.dismiss();
            }
        });
    }

    public void showWindow(View view) {
        mSerarchWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    PopupWindow mTimeWindow = null;
    int hour = 0;
    int minute = 0;

    public void showTimeDialog(int mType, String nowTime) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.time_choice_fragment_layout, null);

        mTimeWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mTimeWindow.setFocusable(true);
        mTimeWindow.setOutsideTouchable(true);

        hour = 0;
        minute = 0;

        String[] strArray = nowTime.split(":");//分解节点时间值
        hour = Integer.parseInt(strArray[0]);
        minute = Integer.parseInt(strArray[1]);

        TimePicker timePicker = view.findViewById(R.id.time_picker);

        int hourInt = Integer.parseInt(String.valueOf(hour));
        int minuteInt = Integer.parseInt(String.valueOf(minute));
        timePicker.setCurrentHour(hourInt);
        timePicker.setCurrentMinute(minuteInt);
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });

        TextView dialogSure = view.findViewById(R.id.dialog_sure);
        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChioseTime(mType, hour, minute);
                mTimeWindow.dismiss();
            }
        });

    }

    public void showTimeWindow(View view) {
        mTimeWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public boolean aliyunService(int serviceType, String mDeviceId, String
            identifier, JSONObject json) {
        try {
            // Log.e(TAG, "aliyunService=" + "**identifier=" + identifier + "***json=" + json);
            PanelDevice panelDevice = new PanelDevice(mDeviceId);
            JSONObject object = new JSONObject();
            object.put("iotId", mDeviceId);
            object.put("identifier", identifier);
            if (json != null)
                object.put("args", json);
            else {
                object.put("args", new JSONObject());
            }
            panelDevice.invokeService(object.toString(), new IPanelCallback() {
                @Override
                public void onComplete(boolean b, @Nullable Object o) {

                    if (o instanceof CmpError) {

                    } else {
                        try {
                            //Log.e(TAG, "onComplete=" + ((String) o));
                            if (b) {
                                JSONObject jsonObject = new JSONObject(o.toString());
                                if (jsonObject.has("code") && jsonObject.getInt("code") == 200) {
                                    if (serviceType != ALIYUNSERVICE_PTZ_CONTROL && serviceType != ALIYUNSERVICE_PTZ_CONTROL_STOP) {
                                        String string = jsonObject.getString("data");

                                        int checkResult = checkResultCode(string);
                                        if (checkResult == 0) {
                                            Parcelable p = resultFromat(serviceType, string);
                                        } else {

                                        }
                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();

            return false;

        }
        return true;
    }

    private int checkResultCode(String string) {
        int i = 0;
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("ResultCode")) {
                i = jsonObject.getInt("ResultCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    private Parcelable resultFromat(int serviceType, String string) {
        Parcelable p = null;
        Message message = handler.obtainMessage();
        switch (serviceType) {
            case ALIYUNSERVICE_GET_CHANNELABILITY:
                Log.e("wy", "==ALIYUNSERVICE_GET_CHANNELABILITY==" + string);
                message.what = ALIYUNSERVICE_GET_CHANNELABILITY;
                message.obj = string;
                handler.sendMessage(message);

                break;
            case ALIYUNSERVICE_DEVICE_RECORDING_PLAN:
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_RECORDING_PLAN==" + string);

                message.what = ALIYUNSERVICE_DEVICE_RECORDING_PLAN;
                message.obj = string;
                handler.sendMessage(message);
                break;

        }
        return p;
    }

}
