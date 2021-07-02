package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.model.LightConfigResultBean;
import com.aliIoT.demo.model.OSDBean;
import com.aliIoT.demo.model.TransControlV2Bean;
import com.aliIoT.demo.model.VideoeffectBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.FileNameUtils;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.OSDUtil;
import com.aliIoT.demo.util.PermissionUtils;
import com.aliIoT.demo.util.PlayLayout;
import com.aliIoT.demo.util.RemoteControlView;
import com.aliIoT.demo.util.VideoPlayHelper;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.aliyun.iotx.linkvisual.media.C;
import com.aliyun.iotx.linkvisual.media.audio.AudioParams;
import com.aliyun.iotx.linkvisual.media.audio.LiveIntercomException;
import com.aliyun.iotx.linkvisual.media.audio.LiveIntercomV2;
import com.aliyun.iotx.linkvisual.media.audio.listener.LiveIntercomV2Listener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itheima.wheelpicker.WheelPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 预览主页，下方按钮对应不同的功能
 */
public class RealPlayActivity extends AppCompatActivity implements View.OnClickListener, PermissionUtils.PermissionGrant, RemoteControlView.RemoteListener {

    ImageView mBackView;
    Button mCaptureButton;
    Button mRecordButton;
    Button mAudioButton;
    Button mTalkButton;
    Button mStreamTypeButton;
    Button mColorButton;
    Button mPTZButton;
    Button mOSDButton;
    Button mLightButton;
    Button mImageButton;
    Button mConfigButton;

    PlayLayout mViewLinearLayout;
    VideoPlayHelper videoPlayHelper = new VideoPlayHelper(MyApplication.getInstance());
    String iotID = MyApplication.getInstance().getIotID();

    String permissionRead_Write[] = new String[]{PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE};
    String permissionAudio[] = new String[]{PermissionUtils.PERMISSION_RECORD_AUDIO, PermissionUtils.PERMISSION_RECORD_AUDIO};


    //!镜像
    LinearLayout mImageLinearLayout;
    TextView mImage4CancelTextView;
    TextView mImage4CloseTextView;
    TextView mImage4HorTextView;
    TextView mImage4VerTextView;
    TextView mImage4180TextView;

    //!灯板
    ConstraintLayout mLightLayout;
    TextView mLight4CancelTextView;
    RelativeLayout mLight4DaynightLayout;
    TextView mLight4DaynightTextView;
    RelativeLayout mLight4ModelLayout;
    TextView mLight4ModelTextView;
    RelativeLayout mLight4ControlLayout;
    TextView mLight4ControlTextView;
    TextView mLight4SeekbarTextView;
    SeekBar mLight4Seekbar;
    RelativeLayout mLight4CommitLayout;

    private String lightWorkModel_value = "";//1、灯板工作模式选择值（当前）
    private int infraredWorkCondition_value = 0;//2、红外灯板状态（当前获取）
    private int lightBrightness_Value = 0;//3、亮度
    private List<String> SupportLightsList = new ArrayList<String>();//5、支持的灯板模式（原始值）
    private List<String> SupportLightsListShow = new ArrayList<String>();//用于展示的集合
    private int flag;//0：红外+暖光，1：红外+白光
    private LightConfigResultBean lightConfigResultBean;

    //!osd
    LinearLayout mOsdLayout;
    CheckBox mOsd4CheckBox1;
    CheckBox mOsd4CheckBox2;
    CheckBox mOsd4CheckBox3;
    TextView mOsd4CancelView;
    OSDBean osd;

    //!ptz
    LinearLayout mPTZLayout;
    RelativeLayout mPTZ4PTZLayout;
    TextView mPTZ4PTZTextView;
    RelativeLayout mPTZ4yzdLayout;
    TextView mPTZ4yzdTextView;
    TextView mPTZ4cancelTextView;
    RemoteControlView mPTZ4RemoteControlView;
    FrameLayout mPTZ4ContentFrameLayout;
    LinearLayout mPTZ4yzdly;
    WheelPicker mPTZ4yzdWheelPicker;
    RelativeLayout mPTZ4yzdSetLayout;
    RelativeLayout mPTZ4yzdUseLayout;
    LinearLayout mPTZ4PTZly;
    TextView mPTZ4PTZZoomin;
    TextView mPTZ4PTZZoomout;
    TextView mPTZ4PTZFocusin;
    TextView mPTZ4PTZFocusout;
    TextView mPTZ4PTZIrisin;
    TextView mPTZ4PTZIrisout;
    private ArrayList<Integer> mPresetPoint;

    //!color
    LinearLayout mColorLinearLayout;
    SeekBar mColorSeekBar1;
    TextView mColorTextView1;
    SeekBar mColorSeekBar2;
    TextView mColorTextView2;
    SeekBar mColorSeekBar3;
    TextView mColorTextView3;
    SeekBar mColorSeekBar4;
    TextView mColorTextView4;
    TextView mColorDefault;
    TextView mColorCancel;

    Gson gson = new Gson();
    //!请求type
    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    static final int MIRROR_FLIP = 3;
    static final int LIGHT_CONFIG = 4;
    static final int LIGHT_CONFIG_SET = 5;
    static final int GET_OSD_INFO = 6;
    static final int SET_OSD_INFO = 7;
    static final int ALIYUNSERVICE_GET_VIDEOEFFECT = 8;
    static final int ALIYUNSERVICE_SET_VIDEOEFFECT = 9;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_STOP, 0, 0);
                    break;
                case LIGHT_CONFIG:
                    lightConfigResultBean = gson.fromJson((String) message.obj, LightConfigResultBean.class);
                    if (lightConfigResultBean != null) {
                        lightWorkModel_value = lightConfigResultBean.getLightType();//灯板工作模式选择值
                        infraredWorkCondition_value = lightConfigResultBean.getIcrLightMode();//红外灯板状态
                        lightBrightness_Value = lightConfigResultBean.getIcrLightAue();//亮度

                        SupportLightsList = lightConfigResultBean.getSupportLights();//当前设备支持的灯板模式

                        //再判断取出的属性值是否为空
                        if (lightWorkModel_value != null && !lightWorkModel_value.equals("")) { //灯板工作模式值不为空
                            SupportLightsListShow.clear();
                            if (SupportLightsList.size() > 0) { //不为空
                                for (int i = 0; i < SupportLightsList.size(); i++) {
                                    if (SupportLightsList.get(i).equals("Ir")) { //红外模式
                                        SupportLightsListShow.add(MyApplication.getInstance().getResources().getString(R.string.light_cofig_InfraredMode));
                                    } else if (SupportLightsList.get(i).equals("Warm")) {
                                        SupportLightsListShow.add(MyApplication.getInstance().getResources().getString(R.string.light_cofig_WarmLightMode));
                                    } else if (SupportLightsList.get(i).equals("White")) {
                                        SupportLightsListShow.add(MyApplication.getInstance().getResources().getString(R.string.light_cofig_WhiteLightMode));
                                    } else if (SupportLightsList.get(i).equals("Ir_Warm")) { //红外+暖光
                                        flag = 0;
                                        SupportLightsListShow.add(MyApplication.getInstance().getResources().getString(R.string.light_cofig_Dual_Light_mode));
                                    } else if (SupportLightsList.get(i).equals("Ir_White")) { //红外+白光
                                        flag = 1;
                                        SupportLightsListShow.add(MyApplication.getInstance().getResources().getString(R.string.light_cofig_Dual_Light_mode));
                                    }
                                }

                                //yxy日夜切换赋值
                                initDaynigthModel(lightConfigResultBean);

                                //灯板类型赋值
                                if (lightWorkModel_value.equals("Ir")) { //红外模式
                                    mLight4ModelTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_InfraredMode));
                                } else if (lightWorkModel_value.equals("Warm")) {
                                    mLight4ModelTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_WarmLightMode));
                                } else if (lightWorkModel_value.equals("White")) {
                                    mLight4ModelTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_WhiteLightMode));
                                } else if (lightWorkModel_value.equals("Ir_Warm")) { //红+暖
                                    flag = 0;
                                    mLight4ModelTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_Dual_Light_mode));
                                } else if (lightWorkModel_value.equals("Ir_White")) { //红+白
                                    flag = 1;
                                    mLight4ModelTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_Dual_Light_mode));
                                }

                                //yxy控制方式赋值
                                if (infraredWorkCondition_value >= 0) {
                                    if (infraredWorkCondition_value == 0) {
                                        mLight4ControlTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_close));
                                        setSeekBarClickable(0);
                                    } else if (infraredWorkCondition_value == 1) {
                                        mLight4ControlTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_Manual));
                                        setSeekBarClickable(1);
                                    } else if (infraredWorkCondition_value == 2) {
                                        mLight4ControlTextView.setText(MyApplication.getInstance().getResources().getString(R.string.light_cofig_automatic));
                                        setSeekBarClickable(0);
                                    }

                                    //判断亮度值，亮度赋值
                                    if (lightBrightness_Value >= 0) {
                                        mLight4SeekbarTextView.setText(String.valueOf(lightBrightness_Value));
                                        mLight4Seekbar.setProgress(lightBrightness_Value);//初始化SeekBar进度值
                                    } else {
                                        Toast.makeText(MyApplication.getInstance(), getResources().getString(R.string.light_config_get_light_fail), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MyApplication.getInstance(), getResources().getString(R.string.light_config_get_infraredCondition_fail), Toast.LENGTH_LONG).show();
                                }
                            } else { //关闭已经加载的弹窗，再次刷新
                                Toast.makeText(MyApplication.getInstance(), getResources().getString(R.string.light_config_get_lightWorkModel_fail), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(MyApplication.getInstance(), getResources().getString(R.string.light_config_get_lightWorkModel_fail), Toast.LENGTH_LONG).show();
                        }

                    } else { //对象为空
                        Toast.makeText(MyApplication.getInstance(), getResources().getString(R.string.light_config_dev_erro), Toast.LENGTH_LONG).show();
                    }

                    break;
                case GET_OSD_INFO:
                    JsonObject jsonObject = gson.fromJson((String) message.obj, JsonObject.class);
                    if (jsonObject.has("ResultCode")
                            && jsonObject.get("ResultCode").getAsInt() == 0) {
                        String data = jsonObject.get("Payload").getAsString();
                        try {
                            JSONObject object = new JSONObject(data);
                            String sss = object.getJSONObject("Data").getString("OSD");
                            osd = gson.fromJson(sss, OSDBean.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    initOSD(osd);
                    break;
                case ALIYUNSERVICE_GET_VIDEOEFFECT:
                    VideoeffectBean videoeffectBean = gson.fromJson((String) message.obj, VideoeffectBean.class);
                    initColorData(videoeffectBean);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realplay);

        init();
        initImage();
        initLight();
        initOSD();
        initPTZ();
        initColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void init() {
        mBackView = findViewById(R.id.realplay_back);
        mCaptureButton = findViewById(R.id.realplay_bt1);
        mRecordButton = findViewById(R.id.realplay_bt2);
        mAudioButton = findViewById(R.id.realplay_bt3);
        mTalkButton = findViewById(R.id.realplay_bt4);
        mStreamTypeButton = findViewById(R.id.realplay_bt5);
        mColorButton = findViewById(R.id.realplay_bt6);
        mPTZButton = findViewById(R.id.realplay_bt7);
        mOSDButton = findViewById(R.id.realplay_bt8);
        mLightButton = findViewById(R.id.realplay_bt9);
        mImageButton = findViewById(R.id.realplay_bt10);
        mConfigButton = findViewById(R.id.realplay_bt11);

        mBackView.setOnClickListener(this);
        mCaptureButton.setOnClickListener(this);
        mRecordButton.setOnClickListener(this);
        mAudioButton.setOnClickListener(this);
        mTalkButton.setOnClickListener(this);
        mStreamTypeButton.setOnClickListener(this);
        mColorButton.setOnClickListener(this);
        mPTZButton.setOnClickListener(this);
        mOSDButton.setOnClickListener(this);
        mLightButton.setOnClickListener(this);
        mImageButton.setOnClickListener(this);
        mConfigButton.setOnClickListener(this);

        mViewLinearLayout = findViewById(R.id.realplay_view);

        //videoPlayHelper.setPlayStatusListener(this);
        videoPlayHelper.setTextureView(mViewLinearLayout);
        prepareVideo(0);
        startVideo(0, iotID);
    }

    private void prepareVideo(int i) {
        if (videoPlayHelper != null) {
            videoPlayHelper.videoPrepare();
        } else {
            throw new NullPointerException("videoPlayHelper is null for startPlayVideo");
        }
    }

    private void startVideo(int i, String iotID) {
        if (videoPlayHelper != null) {
            videoPlayHelper.setIotid(iotID);
            videoPlayHelper.startVideo();

        } else {
            throw new NullPointerException("videoPlayHelper is null for startPlayVideo");
        }
    }

    public void initImage() {
        mImageLinearLayout = findViewById(R.id.realplay_flip);
        mImage4CloseTextView = findViewById(R.id.realplay_flip_tv1);
        mImage4HorTextView = findViewById(R.id.realplay_flip_tv2);
        mImage4VerTextView = findViewById(R.id.realplay_flip_tv3);
        mImage4180TextView = findViewById(R.id.realplay_flip_tv4);
        mImage4CancelTextView = findViewById(R.id.realplay_flip_close);

        mImage4CloseTextView.setOnClickListener(this);
        mImage4HorTextView.setOnClickListener(this);
        mImage4VerTextView.setOnClickListener(this);
        mImage4180TextView.setOnClickListener(this);
        mImage4CancelTextView.setOnClickListener(this);
    }

    public void initLight() {
        mLightLayout = findViewById(R.id.realplay_light);
        mLight4CancelTextView = findViewById(R.id.realplay_light_cancel);
        mLight4DaynightLayout = findViewById(R.id.realplay_light_day_night);
        mLight4DaynightTextView = findViewById(R.id.realplay_light_day_night_tv);
        mLight4ModelLayout = findViewById(R.id.realplay_light_mode);
        mLight4ModelTextView = findViewById(R.id.realplay_light_mode_tv);
        mLight4ControlLayout = findViewById(R.id.realplay_light_control);
        mLight4ControlTextView = findViewById(R.id.realplay_light_control_tv);
        mLight4SeekbarTextView = findViewById(R.id.realplay_light_pro_tv);
        mLight4Seekbar = findViewById(R.id.realplay_light_control_sb);
        mLight4CommitLayout = findViewById(R.id.realplay_light_commit);

        mLight4CancelTextView.setOnClickListener(this);
        mLight4DaynightLayout.setOnClickListener(this);
        mLight4ModelLayout.setOnClickListener(this);
        mLight4ControlLayout.setOnClickListener(this);
        mLight4CommitLayout.setOnClickListener(this);

        mLight4Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLight4SeekbarTextView.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void initOSD() {
        mOsdLayout = findViewById(R.id.realplay_osd);
        mOsd4CheckBox1 = findViewById(R.id.realplay_osd_cb1);
        mOsd4CheckBox2 = findViewById(R.id.realplay_osd_cb2);
        mOsd4CheckBox3 = findViewById(R.id.realplay_osd_cb3);
        mOsd4CancelView = findViewById(R.id.realplay_osd_cancel);

        mOsd4CancelView.setOnClickListener(this);
    }

    private void initOSD(OSDBean osd) {
        if (osd == null) {
            mOsd4CheckBox1.setChecked(false);
            mOsd4CheckBox2.setChecked(false);
            mOsd4CheckBox3.setChecked(false);
        } else {
            if (osd.getIsShowChanName() == 0) {
                mOsd4CheckBox1.setChecked(false);
            } else {
                mOsd4CheckBox1.setChecked(true);
            }
            if ((osd.getIsShowOSD() != null && osd.getIsShowOSD() == 1) || (osd.getIsDisplayWeek() != null && osd.getIsDisplayWeek() == 1)) {
                mOsd4CheckBox2.setChecked(true);
            } else {
                mOsd4CheckBox2.setChecked(false);
            }

            mOsd4CheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setOSD(0, b);
                }
            });
            mOsd4CheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setOSD(1, b);
                }
            });

        }
    }

    public void setOSD(int postion, boolean b) {
        try {
            JsonObject jsonObject = new JsonObject();
            if (postion == 0) {
                jsonObject.addProperty("IsShowChanName", (b ? 1 : 0));
            } else if (postion == 1) {
                jsonObject.addProperty("IsShowOSD", (b ? 1 : 0));
                jsonObject.addProperty("IsDisplayWeek", (b ? 1 : 0));
            }
            if (osd == null) {
                return;
            }

            TransControlV2Bean transControlV2Bean = OSDUtil.creatTransControlV2Bean(jsonObject);
            if (transControlV2Bean != null)
                setOSDInfo(iotID, transControlV2Bean, SET_OSD_INFO);

        } catch (Exception e) {

        }
    }

    public void initPTZ() {
        mPTZLayout = findViewById(R.id.realplay_ptz);
        mPTZ4PTZLayout = findViewById(R.id.realplay_ptz_ptz);
        mPTZ4PTZTextView = findViewById(R.id.realplay_ptz_ptz_tv);
        mPTZ4yzdLayout = findViewById(R.id.realplay_ptz_yzd);
        mPTZ4yzdTextView = findViewById(R.id.realplay_ptz_yzd_tv);
        mPTZ4cancelTextView = findViewById(R.id.realplay_ptz_exit);
        mPTZ4RemoteControlView = findViewById(R.id.realplay_ptz_view);
        mPTZ4ContentFrameLayout = findViewById(R.id.realplay_ptz_content);
        mPTZ4yzdly = findViewById(R.id.realplay_ptz_preset);
        mPTZ4yzdWheelPicker = findViewById(R.id.realplay_ptz_wp);
        mPTZ4yzdSetLayout = findViewById(R.id.realplay_ptz_sz);
        mPTZ4yzdUseLayout = findViewById(R.id.realplay_ptz_use);
        mPTZ4PTZly = findViewById(R.id.realplay_ptz_ptz_ly);
        mPTZ4PTZZoomin = findViewById(R.id.realplay_ptz_zoom_in);
        mPTZ4PTZZoomout = findViewById(R.id.realplay_ptz_zoom_out);
        mPTZ4PTZFocusin = findViewById(R.id.realplay_ptz_focus_in);
        mPTZ4PTZFocusout = findViewById(R.id.realplay_ptz_focus_out);
        mPTZ4PTZIrisin = findViewById(R.id.realplay_ptz_iris_in);
        mPTZ4PTZIrisout = findViewById(R.id.realplay_ptz_iris_out);

        mPTZ4PTZLayout.setOnClickListener(this);
        mPTZ4yzdLayout.setOnClickListener(this);
        mPTZ4cancelTextView.setOnClickListener(this);
        mPTZ4yzdly.setOnClickListener(this);
        mPTZ4yzdSetLayout.setOnClickListener(this);
        mPTZ4yzdUseLayout.setOnClickListener(this);
        mPTZ4PTZly.setOnClickListener(this);
        mPTZ4PTZZoomin.setOnClickListener(this);
        mPTZ4PTZZoomout.setOnClickListener(this);
        mPTZ4PTZFocusin.setOnClickListener(this);
        mPTZ4PTZFocusout.setOnClickListener(this);
        mPTZ4PTZIrisin.setOnClickListener(this);
        mPTZ4PTZIrisout.setOnClickListener(this);

        mPresetPoint = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            mPresetPoint.add(i);
        }

        mPTZ4yzdWheelPicker.setData(mPresetPoint);
        mPTZ4yzdWheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {

            }
        });

        mPTZ4RemoteControlView.setRemoteListener(this);

    }

    public void initColor() {
        mColorLinearLayout = findViewById(R.id.realplay_color);
        mColorSeekBar1 = findViewById(R.id.realplay_color_sb1);
        mColorTextView1 = findViewById(R.id.realplay_color_tv1);
        mColorSeekBar2 = findViewById(R.id.realplay_color_sb2);
        mColorTextView2 = findViewById(R.id.realplay_color_tv2);
        mColorSeekBar3 = findViewById(R.id.realplay_color_sb3);
        mColorTextView3 = findViewById(R.id.realplay_color_tv3);
        mColorSeekBar4 = findViewById(R.id.realplay_color_sb4);
        mColorTextView4 = findViewById(R.id.realplay_color_tv4);
        mColorDefault = findViewById(R.id.realplay_color_default);
        mColorCancel = findViewById(R.id.realplay_color_cancel);

        mColorDefault.setOnClickListener(this);
        mColorCancel.setOnClickListener(this);
    }

    public void initColorData(VideoeffectBean bean) {
        if (bean == null) {
            return;
        }
        int temp1 = bean.getBrigthness();
        int temp2 = bean.getContrast();
        int temp3 = bean.getSaturation();
        int temp4 = bean.getHue();

        mColorSeekBar1.setProgress(temp1);
        mColorTextView1.setText(temp1 + "");

        mColorSeekBar2.setProgress(temp2);
        mColorTextView2.setText(temp2 + "");

        mColorSeekBar3.setProgress(temp3);
        mColorTextView3.setText(temp3 + "");

        mColorSeekBar4.setProgress(temp4);
        mColorTextView4.setText(temp4 + "");

        mColorSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mColorTextView1.setText(seekBar.getProgress() + "");
                changeColour(iotID, seekBar.getProgress(), temp2, temp3, temp4);
            }
        });

        mColorSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mColorTextView2.setText(seekBar.getProgress() + "");
                changeColour(iotID, temp1, seekBar.getProgress(), temp3, temp4);
            }
        });

        mColorSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mColorTextView3.setText(seekBar.getProgress() + "");
                changeColour(iotID, temp1, temp2, seekBar.getProgress(), temp4);
            }
        });

        mColorSeekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mColorTextView4.setText(seekBar.getProgress() + "");
                changeColour(iotID, temp1, temp2, temp3, seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.realplay_back:
                finish();
                break;
            case R.id.realplay_bt1:
                if (videoPlayHelper != null) {
                    screenShot(videoPlayHelper);
                }
                break;
            case R.id.realplay_bt2:
                if (!isRecord) {
                    startRecord(videoPlayHelper);
                } else {
                    stopRecord(videoPlayHelper);
                }
                break;
            case R.id.realplay_bt3:
                changMonitorStatus();
                break;
            case R.id.realplay_bt4:
                if ((!ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionAudio[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionAudio[1]))
                        && !PermissionUtils.requestPermission(RealPlayActivity.this, 0, this)) {
                    PermissionUtils.requestMultiResult(RealPlayActivity.this, permissionAudio, new int[]{0, 1}, this);
                }

                if (PermissionUtils.requestPermission(RealPlayActivity.this, 0, this)) {

                    if (mLiveIntercomV2 == null) {
                        mLiveIntercomV2 = initVoiceIntercom(iotID);
                    }
                    if (mLiveIntercomStatus) {
                        closeTalkBack();
                    } else {
                        openTalkBack();
                    }
                }

                break;
            case R.id.realplay_bt5:
                if (videoPlayHelper != null) {

                    if (videoPlayHelper.getStream() != C.STREAM_TYPE_MINOR) {
                        sureDefinition(C.STREAM_TYPE_MINOR);
                        switchStream(false);
                    } else {
                        sureDefinition(C.STREAM_TYPE_MAJOR);
                        switchStream(true);
                    }

                }
                break;
            case R.id.realplay_bt6:
                mColorLinearLayout.setVisibility(View.VISIBLE);
                getColorInfo(iotID);
                break;
            case R.id.realplay_bt7:
                mPTZLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.realplay_bt8:
                mOsdLayout.setVisibility(View.VISIBLE);
                TransControlV2Bean mTransControlV2Bean = OSDUtil.creatTransControlV2Bean(1);
                getOSDInfo(iotID, mTransControlV2Bean, GET_OSD_INFO);
                break;
            case R.id.realplay_osd_cancel:
                mOsdLayout.setVisibility(View.GONE);
                break;
            case R.id.realplay_bt9:
                mLightLayout.setVisibility(View.VISIBLE);
                LightConfig(iotID, 1);
                break;
            case R.id.realplay_bt10:
                mImageLinearLayout.setVisibility(View.VISIBLE);
                getMirrorFlip(iotID, 1);
                break;
            case R.id.realplay_bt11:
                Intent intent = new Intent(this, ChannelConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.realplay_flip_close:
                mImageLinearLayout.setVisibility(View.GONE);
                break;
            case R.id.realplay_flip_tv1:
                setMirrorFlip(iotID, 0, 2);
                break;
            case R.id.realplay_flip_tv2:
                setMirrorFlip(iotID, 1, 2);
                break;
            case R.id.realplay_flip_tv3:
                setMirrorFlip(iotID, 2, 2);
                break;
            case R.id.realplay_flip_tv4:
                setMirrorFlip(iotID, 3, 2);
                break;
            case R.id.realplay_light_cancel:
                mLightLayout.setVisibility(View.GONE);
                break;
            case R.id.realplay_light_mode:
                showDialog(SupportLightsListShow, mLight4ModelTextView, "灯板类型");

                if (!mSerarchWindow.isShowing()) {
                    showWindow(mLight4ModelTextView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            case R.id.realplay_light_day_night:
                List<String> dayNightSwitchList = new ArrayList<String>();
                dayNightSwitchList.add(getResources().getString(R.string.light_cofig_day_ligth_external_trigger));//外部触发
                dayNightSwitchList.add(getResources().getString(R.string.light_cofig_day_ligth_automatic));//自动
                dayNightSwitchList.add(getResources().getString(R.string.light_cofig_day_ligth_colours));//彩色
                dayNightSwitchList.add(getResources().getString(R.string.light_cofig_day_ligth_black_white));//黑白
                showDialog(dayNightSwitchList, mLight4DaynightTextView, "日夜切换");

                if (!mSerarchWindow.isShowing()) {
                    showWindow(mLight4DaynightTextView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            case R.id.realplay_light_control:
                List<String> controlWayList = new ArrayList<String>();
                controlWayList.add(getResources().getString(R.string.light_cofig_close));//关闭
                controlWayList.add(getResources().getString(R.string.light_cofig_Manual));//手动
                controlWayList.add(getResources().getString(R.string.light_cofig_automatic));//自动
                showDialog(controlWayList, mLight4ControlTextView, "控制方式");

                if (!mSerarchWindow.isShowing()) {
                    showWindow(mLight4ControlTextView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;

            case R.id.realplay_light_commit:
                String lightWorkModel_value = mLight4ModelTextView.getText().toString().trim();
                if (lightWorkModel_value.equals(getResources().getString(R.string.light_cofig_InfraredMode))) { //红外模式
                    lightWorkModel_value = "Ir";
                } else if (lightWorkModel_value.equals(getResources().getString(R.string.light_cofig_WarmLightMode))) {
                    lightWorkModel_value = "Warm";
                } else if (lightWorkModel_value.equals(getResources().getString(R.string.light_cofig_WhiteLightMode))) {
                    lightWorkModel_value = "White";
                } else if (lightWorkModel_value.equals(getResources().getString(R.string.light_cofig_Dual_Light_mode)) && flag == 0) {
                    lightWorkModel_value = "Ir_Warm";
                } else if (lightWorkModel_value.equals(getResources().getString(R.string.light_cofig_Dual_Light_mode)) && flag == 1) {
                    lightWorkModel_value = "Ir_White";
                }

                //yxy控制方式
                int infraredWorkCondition_value = 0;  //light_cofig_close
                if (mLight4ControlTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_close))) {
                    infraredWorkCondition_value = 0;
                } else if (mLight4ControlTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_Manual))) {
                    infraredWorkCondition_value = 1;
                } else if (mLight4ControlTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_automatic))) {
                    infraredWorkCondition_value = 2;
                }

                //yxy日夜切换
                int dayNigth_value = 0;  //light_cofig_close
                if (mLight4DaynightTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_day_ligth_external_trigger))) {
                    dayNigth_value = 0;
                } else if (mLight4DaynightTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_day_ligth_automatic))) {
                    dayNigth_value = 1;
                } else if (mLight4DaynightTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_day_ligth_colours))) {
                    dayNigth_value = 2;
                } else if (mLight4DaynightTextView.getText().toString().trim().equals(getResources().getString(R.string.light_cofig_day_ligth_black_white))) {
                    dayNigth_value = 3;
                }
                LightConfigResultBean.DayNightMode dayNightMode = new LightConfigResultBean.DayNightMode();
                dayNightMode.setDayNightMode(dayNigth_value);
                if (lightConfigResultBean != null && lightConfigResultBean.getDayNightMode() != null) {
                    if (lightConfigResultBean.getDayNightMode().getDayToNightThreshold() <= lightConfigResultBean.getDayNightMode().getNightToDayThreshold()
                            && lightConfigResultBean.getDayNightMode().getDayToNightThreshold() >= 0 && lightConfigResultBean.getDayNightMode().getDayToNightThreshold() <= 255
                            && lightConfigResultBean.getDayNightMode().getNightToDayThreshold() >= 0 && lightConfigResultBean.getDayNightMode().getNightToDayThreshold() <= 255) {
                        dayNightMode.setDayToNightThreshold(lightConfigResultBean.getDayNightMode().getDayToNightThreshold());
                        dayNightMode.setNightToDayThreshold(lightConfigResultBean.getDayNightMode().getNightToDayThreshold());
                        dayNightMode.setDelay(lightConfigResultBean.getDayNightMode().getDelay());
                    } else {
                        dayNightMode.setDayToNightThreshold(20);
                        dayNightMode.setNightToDayThreshold(21);
                        dayNightMode.setDelay(2);
                    }
                } else {
                    dayNightMode.setDayToNightThreshold(20);
                    dayNightMode.setNightToDayThreshold(21);
                    dayNightMode.setDelay(2);
                }

                int lightBrightness_Value = mLight4Seekbar.getProgress();
                if (lightBrightness_Value < 0 || lightBrightness_Value > 100) {
                    //ToastUtils.getToastUtils().showToast(mActivity, mActivity.getResources().getString(R.string.light_cofig_Out_of_range));
                } else {
                    LightConfigSet(iotID, 2, lightWorkModel_value, infraredWorkCondition_value, lightBrightness_Value, dayNightMode);
                }
                break;
            case R.id.realplay_ptz_exit: {
                mPTZLayout.setVisibility(View.GONE);
                break;
            }
            case R.id.realplay_ptz_ptz: {
                mPTZ4PTZTextView.setTextColor(getResources().getColor(R.color.hei));
                mPTZ4yzdTextView.setTextColor(getResources().getColor(R.color.hui_s));
                mPTZ4PTZly.setVisibility(View.VISIBLE);
                mPTZ4yzdly.setVisibility(View.GONE);
                break;
            }
            case R.id.realplay_ptz_yzd: {
                mPTZ4PTZTextView.setTextColor(getResources().getColor(R.color.hui_s));
                mPTZ4yzdTextView.setTextColor(getResources().getColor(R.color.hei));
                mPTZ4PTZly.setVisibility(View.GONE);
                mPTZ4yzdly.setVisibility(View.VISIBLE);
                break;
            }

            case R.id.realplay_ptz_iris_in: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_IRIS_IN, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_iris_out: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_IRIS_OUT, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_focus_in: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_FOCUS_IN, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_focus_out: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_FOCUS_OUT, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_zoom_in: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_ZOOM_IN, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_zoom_out: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_ZOOM_OUT, 2, 1);
                sendStopCmd();
                break;
            }
            case R.id.realplay_ptz_sz: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_SET_PRESET, 1, mPresetPoint.get(mPTZ4yzdWheelPicker.getCurrentItemPosition()));
                break;
            }
            case R.id.realplay_ptz_use: {
                onRemoteListener(iotID, ConstUtil.CLOUDEYE_PTZ_CTRL_CALL_PRESET, 1, mPresetPoint.get(mPTZ4yzdWheelPicker.getCurrentItemPosition()));
                break;
            }
            case R.id.realplay_color_default:
                changeColour(iotID, 128, 128, 128, 128);
                break;
            case R.id.realplay_color_cancel:
                mColorLinearLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void screenShot(VideoPlayHelper videoPlayHelper) {
        if ((!ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionRead_Write[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionRead_Write[1]))
                && !PermissionUtils.requestMultiPermissions(RealPlayActivity.this, new int[]{6, 7}, this)) {
            PermissionUtils.requestMultiResult(RealPlayActivity.this, permissionRead_Write, new int[]{6, 7}, this);
        }

        if (PermissionUtils.requestMultiPermissions(RealPlayActivity.this, new int[]{6, 7}, this)) {
            String path = FileNameUtils.creatScreenShotFileName("1222222222", "11111");
            File file = new File(path);
            boolean b = videoPlayHelper.screenShot(file);
            if (b) {
                Toast.makeText(this, "抓图成功", Toast.LENGTH_SHORT).show();
                //ToastUtils.getToastUtils().showToast(RealPlayActivity.this, "抓图成功");
            } else {
                Toast.makeText(this, "抓图失败", Toast.LENGTH_SHORT).show();
                //ToastUtils.getToastUtils().showToast(RealPlayActivity.this, "抓图失败");
            }
        }
    }

    boolean isRecord = false;

    private boolean startRecord(VideoPlayHelper videoPlayHelper) {
        if ((!ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionRead_Write[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(RealPlayActivity.this, permissionRead_Write[1]))
                && !PermissionUtils.requestMultiPermissions(RealPlayActivity.this, new int[]{6, 7}, this)) {
            PermissionUtils.requestMultiResult(RealPlayActivity.this, permissionRead_Write, new int[]{6, 7}, this);
        }

        if (PermissionUtils.requestMultiPermissions(RealPlayActivity.this, new int[]{6, 7}, this)) {

            String path = FileNameUtils.creatRecordFileName("1222222222", "11111");
            File file = new File(path);
            boolean b = videoPlayHelper.startRecord(file);
            isRecord = true;
            if (b) {
                Toast.makeText(this, "开始录像", Toast.LENGTH_SHORT).show();
                //ToastUtils.getToastUtils().showToast(RealPlayActivity.this, "开始录像");
                videoPlayHelper.getPlayLayout().showHideRecord(true);
                return b;
            } else {
                Toast.makeText(this, "正在录像", Toast.LENGTH_SHORT).show();
                //ToastUtils.getToastUtils().showToast(RealPlayActivity.this, "正在录像");
                return b;
            }
        } else {
            return false;
        }
    }


    private void stopRecord(VideoPlayHelper videoPlayHelper) {
        boolean b = videoPlayHelper.stopRecord();
        videoPlayHelper.getPlayLayout().showHideRecord(false);
        isRecord = false;
        if (b) {
            Toast.makeText(this, "停止录像", Toast.LENGTH_SHORT).show();
            //ToastUtils.getToastUtils().showToast(RealPlayActivity.this, "停止录像");
        }
    }

    private void changMonitorStatus() {
        if (videoPlayHelper != null) {
            float volume = videoPlayHelper.getVolume();
            if (volume == 1.0f) {
                videoPlayHelper.setVolume(0.0f);
            } else if (volume == 0.0f) {
                videoPlayHelper.setVolume(1.0f);
            }
            getMonitorStatus();
        }
    }

    private void getMonitorStatus() {
        if (videoPlayHelper != null) {
            float volume = videoPlayHelper.getVolume();
            if (volume == 1.0f) {
            } else if (volume == 0.0f) {
            }
        }
    }

    private LiveIntercomV2 mLiveIntercomV2;
    //对讲状态
    private boolean mLiveIntercomStatus = false;

    private LiveIntercomV2 initVoiceIntercom(String iotId) {
        // 创建语音对讲实例
        LiveIntercomV2 liveIntercomV2 = new LiveIntercomV2(RealPlayActivity.this, iotId, LiveIntercomV2.LiveIntercomMode.DoubleTalk, AudioParams.AUDIOPARAM_MONO_8K_G711A);
        liveIntercomV2.setGainLevel(LiveIntercomV2.GAIN_LEVEL_AGRESSIVE);
        liveIntercomV2.setLiveIntercomV2Listener(new LiveIntercomV2Listener() {
            @Override
            public void onTalkReady() {

            }

            @Override
            public void onError(LiveIntercomException e) {
                if (videoPlayHelper != null) {
                    //videoPlayHelper.setVolume(1.0f);
                    if (!mLiveIntercomStatus) {
                        mLiveIntercomStatus = true;
                    }
                    closeTalkBack();
                }
            }

            @Override
            public void onRecordStart() {
                if (videoPlayHelper != null) {
                    videoPlayHelper.setVolume(0.0f);
                }
            }

            @Override
            public void onRecordEnd() {
                if (videoPlayHelper != null) {
                }
            }

            @Override
            public void onRecordBufferReceived(byte[] bytes, int i, int i1) {
            }
        });
        return liveIntercomV2;
    }

    private void openTalkBack() {
        if (mLiveIntercomV2 != null && !mLiveIntercomStatus) {
            mLiveIntercomV2.start();
            mLiveIntercomStatus = true;

        }
    }

    private void closeTalkBack() {
        if (mLiveIntercomV2 != null && mLiveIntercomStatus) {
            mLiveIntercomV2.stop();
            mLiveIntercomV2.release();
            mLiveIntercomStatus = false;
            mLiveIntercomV2 = null;
        }
    }

    private void sureDefinition(int stream) {
        if (stream == C.STREAM_TYPE_MINOR) {
            mStreamTypeButton.setText("SD");
        } else {
            mStreamTypeButton.setText("HD");
        }
    }

    private void switchStream(boolean b) {
        if (videoPlayHelper.getPlayStatus() != VideoPlayHelper.playStatus.NO_PALY) {
            videoPlayHelper.stop();
            videoPlayHelper.setStream(b);
            videoPlayHelper.startVideo();
        }
    }

    public void getMirrorFlip(String deviceId, int method) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", method);
            boolean b = aliyunService(MIRROR_FLIP, deviceId, "LightConfig", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMirrorFlip(String deviceId, int mode, int method) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", method);
            jsonObject.put("MinorMode", mode);
            boolean b = aliyunService(MIRROR_FLIP, deviceId, "LightConfig", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LightConfig(String deviceId, int Method) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", Method);
            boolean b = aliyunService(LIGHT_CONFIG, deviceId, "LightConfig", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //灯板控制，设置
    public void LightConfigSet(String deviceId,
                               int Method,
                               String LightType,
                               int IcrLightMode,
                               int IcrLightAue,
                               LightConfigResultBean.DayNightMode mDayNightMode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", Method);
            jsonObject.put("LightType", LightType);
            jsonObject.put("IcrLightMode", IcrLightMode);
            jsonObject.put("IcrLightAue", IcrLightAue);
            jsonObject.put("DayNightMode", new JSONObject(JSON.toJSONString(mDayNightMode)));
            boolean b = aliyunService(
                    LIGHT_CONFIG_SET, //本地标识符,灯板控制
                    deviceId, //yxy设备id（编号）
                    "LightConfig", //物模型接口“标识符”
                    jsonObject //yxy物模型参数（有参为JSONObject，无参为null）
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //日夜切换赋值
    private void initDaynigthModel(LightConfigResultBean lightConfigResultBean) {
        if (lightConfigResultBean != null && lightConfigResultBean.getDayNightMode() != null) {
            LightConfigResultBean.DayNightMode dayNightMode = lightConfigResultBean.getDayNightMode();
            switch (dayNightMode.getDayNightMode()) {
                case 0: {
                    mLight4DaynightTextView.setText(getResources().getString(R.string.light_cofig_day_ligth_external_trigger));
                    break;
                }
                case 1: {
                    mLight4DaynightTextView.setText(getResources().getString(R.string.light_cofig_day_ligth_automatic));
                    break;
                }
                case 2: {
                    mLight4DaynightTextView.setText(getResources().getString(R.string.light_cofig_day_ligth_colours));
                    break;
                }
                case 3: {
                    mLight4DaynightTextView.setText(getResources().getString(R.string.light_cofig_day_ligth_black_white));
                    break;
                }
            }
        }
    }

    private void setSeekBarClickable(int i) {
        if (i == 1) {
            //启用状态
            mLight4Seekbar.setClickable(true);
            mLight4Seekbar.setEnabled(true);
            mLight4Seekbar.setSelected(true);
            mLight4Seekbar.setFocusable(true);
        } else {
            //禁用状态
            mLight4Seekbar.setClickable(false);
            mLight4Seekbar.setEnabled(false);
            mLight4Seekbar.setSelected(false);
            mLight4Seekbar.setFocusable(false);
        }
    }

    PopupWindow mSerarchWindow = null;

    public void showDialog(List<String> lists, TextView tv, String title) {

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
                tv.setText(lists.get(i));
                mSerarchWindow.dismiss();
            }
        });
    }

    public void showWindow(View view) {
        mSerarchWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void getOSDInfo(String deviceId, TransControlV2Bean mTransControlV2Bean, int type) {
        try {
            Gson gson = new Gson();
            String s = gson.toJson(mTransControlV2Bean);
            JSONObject jsonObject = new JSONObject(s);
            boolean b = aliyunService(
                    type,
                    deviceId,
                    "TransControlv2",
                    jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setOSDInfo(String deviceId, TransControlV2Bean mTransControlV2Bean, int type) {
        try {
            Gson gson = new Gson();
            String s = gson.toJson(mTransControlV2Bean);
            JSONObject jsonObject = new JSONObject(s);
            boolean b = aliyunService(
                    type,
                    deviceId,
                    "TransControlv2",
                    jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onRemoteListener(String deviceId, int cmd, int Speed, int PersetIndex) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (cmd == ConstUtil.CLOUDEYE_PTZ_CTRL_SET_PRESET || cmd == ConstUtil.CLOUDEYE_PTZ_CTRL_CALL_PRESET) {
                jsonObject.put("Cmd", cmd);
                jsonObject.put("PersetIndex", PersetIndex);
            } else {
                jsonObject.put("Cmd", cmd);
                jsonObject.put("Speed", Speed);
            }
            Log.e("wy", "===jsonObject==" + jsonObject);
            aliyunService(ALIYUNSERVICE_PTZ_CONTROL, deviceId, "PtzControl", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void sendStopCmd() {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(0, 500);
    }

    public void getColorInfo(String deviceId) {
        Gson gson = new Gson();

        String json = gson.toJson(new VideoeffectBean(0, 0, 0, 0));
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.put("Method", 1);
            aliyunService(ALIYUNSERVICE_GET_VIDEOEFFECT, deviceId, "VideoEffect", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void changeColour(String deviceId, int luminance, int contrast, int saturability, int chroma) {
        Gson gson = new Gson();
        String json = gson.toJson(new VideoeffectBean(luminance, contrast, saturability, chroma));
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.put("Method", 2);
            aliyunService(ALIYUNSERVICE_SET_VIDEOEFFECT, deviceId, "VideoEffect", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            case ALIYUNSERVICE_PTZ_CONTROL: {
                Log.e("wy", "==ALIYUNSERVICE_PTZ_CONTROL==" + string);
                break;
            }
            case MIRROR_FLIP: {

                LightConfigResultBean lightConfigResultBean = gson.fromJson(string, LightConfigResultBean.class);
                Log.e("wy", "==MIRROR_FLIP==" + string);
                break;
            }

            case LIGHT_CONFIG:
                Log.e("wy", "==LIGHT_CONFIG==" + string);
                message.what = LIGHT_CONFIG;
                message.obj = string;
                handler.sendMessage(message);

                break;
            case GET_OSD_INFO:
                Log.e("wy", "==GET_OSD_INFO==" + string);
                message.what = GET_OSD_INFO;
                message.obj = string;
                handler.sendMessage(message);

                break;
            case SET_OSD_INFO:
                Log.e("wy", "==SET_OSD_INFO==" + string);

                break;
            case ALIYUNSERVICE_GET_VIDEOEFFECT:
                Log.e("wy", "==ALIYUNSERVICE_GET_VIDEOEFFECT==" + string);
                message.what = ALIYUNSERVICE_GET_VIDEOEFFECT;
                message.obj = string;
                handler.sendMessage(message);
                break;

            case ALIYUNSERVICE_SET_VIDEOEFFECT:
                Log.e("wy", "==ALIYUNSERVICE_SET_VIDEOEFFECT==" + string);

                break;

        }
        return p;
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }

    @Override
    public void onRemoteListener(int cmd, int Speed, int PersetIndex) {
        onRemoteListener(iotID, cmd, 2, 1);
    }


}
