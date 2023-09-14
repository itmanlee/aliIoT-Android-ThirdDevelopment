package com.aliIoT.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aliIoT.demo.model.AliyunRecordFileList;
import com.aliIoT.demo.model.AliyunServiceResultBean;
import com.aliIoT.demo.util.FileNameUtils;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.PermissionUtils;
import com.aliIoT.demo.util.PlayLayout;
import com.aliIoT.demo.util.TimeUtils;
import com.aliIoT.demo.util.VideoPlayHelper2;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;


/**
 * 回放界面
 */
public class RePlayActivity extends AppCompatActivity implements View.OnClickListener, PermissionUtils.PermissionGrant {

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
    static final int GET_RECORD_LIST = 10;
    ImageView mBackView;
    Button mCaptureButton;
    Button mRecordButton;
    Button mAudioButton;
    Button mFindFileButton;
    Button mStopButton;
    Button mStartButton;
    Button mSlowButton;
    Button mTextButton;
    Button mFastButton;
    PlayLayout mViewLinearLayout;
    VideoPlayHelper2 videoPlayHelper = new VideoPlayHelper2(MyApplication.getInstance());
    String iotID = MyApplication.getInstance().getIotID();
    String permissionRead_Write[] = new String[]{PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE, PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE};
    String permissionAudio[] = new String[]{PermissionUtils.PERMISSION_RECORD_AUDIO, PermissionUtils.PERMISSION_RECORD_AUDIO};
    List<AliyunRecordFileList.TimeListBean> lists = null;
    Gson gson = new Gson();
    boolean isFirstEnter = false;
    boolean recordVideo = false;
    int startTime = 0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_RECORD_LIST:
                    int code = message.arg1;
                    if (code == 1) {
                        AliyunServiceResultBean aliyunServiceResultBean = (AliyunServiceResultBean) message.obj;
                        AliyunRecordFileList data = (AliyunRecordFileList) aliyunServiceResultBean.getData();
                        lists = data.getTimeList();
                        if (lists != null && lists.size() > 0) {
                            videoPlayHelper.setRecordReferenceTime(startTime);
                            videoPlayHelper.setPath(iotID, 0, 60 * 60 * 24 - 1);

                            int currtime = lists.get(0).getBeginTime() - startTime;
                            Log.e("wy", "==currtime==" + currtime);
                            boolean b = videoPlayHelper.playVideo(currtime);
                            if (b == true) { //开启播放成功
                                String speed = videoPlayHelper.getSpeed();
                                mTextButton.setText(speed + "X");
                            } else { //开启播放失败
                                Toast.makeText(RePlayActivity.this, "播放失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(RePlayActivity.this, "文件查询失败", Toast.LENGTH_LONG).show();
                    }

                    Log.e("wy", "==GET_RECORD_LIST==" + gson.toJson(message.obj));
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        init();
        isFirstEnter = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstEnter) {
            if (videoPlayHelper != null) {
                videoPlayHelper.playVideo(videoPlayHelper.getCurTime());
            }
            isFirstEnter = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFirstEnter = false;
        if (videoPlayHelper != null) {
            videoPlayHelper.stopVideo();
            if (mTextButton != null)
                mTextButton.setText("1X");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayHelper.release();
    }

    public void init() {
        mBackView = findViewById(R.id.replay_back);
        mCaptureButton = findViewById(R.id.replay_bt1);
        mRecordButton = findViewById(R.id.replay_bt2);
        mAudioButton = findViewById(R.id.replay_bt3);
        mFindFileButton = findViewById(R.id.replay_bt4);
        mStopButton = findViewById(R.id.replay_bt5);
        mStartButton = findViewById(R.id.replay_bt6);
        mSlowButton = findViewById(R.id.replay_bt7);
        mTextButton = findViewById(R.id.replay_bt8);
        mFastButton = findViewById(R.id.replay_bt9);

        mBackView.setOnClickListener(this);
        mCaptureButton.setOnClickListener(this);
        mRecordButton.setOnClickListener(this);
        mAudioButton.setOnClickListener(this);
        mFindFileButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mSlowButton.setOnClickListener(this);
        mTextButton.setOnClickListener(this);
        mFastButton.setOnClickListener(this);

        mViewLinearLayout = findViewById(R.id.replay_view);

        //videoPlayHelper.setPlayStatusListener(this);
        videoPlayHelper.setTextureView(mViewLinearLayout);
        videoPlayHelper.videoPrepare();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.replay_back:
                finish();
                break;
            case R.id.replay_bt1:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                if (videoPlayHelper != null) {
                    screenShot(videoPlayHelper);
                }
                break;
            case R.id.replay_bt2:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                if (!recordVideo) {
                    startRecord(videoPlayHelper);
                } else {
                    stopRecord(videoPlayHelper);
                }
                break;
            case R.id.replay_bt3:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                changMonitorStatus();
                break;
            case R.id.replay_bt4:
                queryRecordList(iotID);
                break;
            case R.id.replay_bt5:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                if (videoPlayHelper != null) {
                    if (videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.NO_PALY) {
                        //ToastUtils.getToastUtils().showToast(mActivity, mActivity.getResources().getString(R.string.please_confirm_whether_there_any_video_file_on_that_day));
                        break;
                    }
                    boolean b = videoPlayHelper.playVideo(videoPlayHelper.getCurTime());
                    if (b) {
                        mStopButton.setText("播放");
                        //mediaPlayVideoLayoutVideoPlay.setBackgroundResource(R.mipmap.video_stop);
                    } else {
                        mStopButton.setText("暂停");
                        //mediaPlayVideoLayoutVideoPlay.setBackgroundResource(R.mipmap.video_play);
                    }
                    String speed = videoPlayHelper.getSpeed();
                    mTextButton.setText(speed + "X");
                }
                break;
            case R.id.replay_bt6:

                break;
            case R.id.replay_bt7:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                if (videoPlayHelper != null) {
                    if ((videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.BUFFING_PLAY || videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.PREPARE_PLAY)) {
                        String str = videoPlayHelper.setPlaybackSpeed(false);
                        if (!TextUtils.isEmpty(str)) {
                            mTextButton.setText(str + "X");
                        }
                    } else {
                        // ToastUtils.getToastUtils().showToast(mActivity, mActivity.getResources().getString(R.string.only_adjusted_during_playback));
                    }
                }
                break;
            case R.id.replay_bt8:

                break;
            case R.id.replay_bt9:
                if (lists == null) {
                    Toast.makeText(RePlayActivity.this, "请先执行文件查询", Toast.LENGTH_LONG).show();
                    break;
                }
                if (videoPlayHelper != null) {
                    if ((videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.BUFFING_PLAY || videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.PREPARE_PLAY)) {
                        String str = videoPlayHelper.setPlaybackSpeed(true);
                        if (!TextUtils.isEmpty(str)) {
                            mTextButton.setText(str + "X");
                        }
                    } else {
                        // ToastUtils.getToastUtils().showToast(mActivity, mActivity.getResources().getString(R.string.only_adjusted_during_playback));
                    }
                }
                break;
        }
    }

    private void screenShot(VideoPlayHelper2 videoPlayHelper) {
        if ((!ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRead_Write[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRead_Write[1]))
                && !PermissionUtils.requestMultiPermissions(this, new int[]{6, 7}, this)) {
            PermissionUtils.requestMultiResult(this, permissionRead_Write, new int[]{6, 7}, this);
        }

        if (PermissionUtils.requestMultiPermissions(this, new int[]{6, 7}, this)) {
            String path = FileNameUtils.creatScreenShotFileName(iotID, "rrrrr");
            File file = new File(path);
            boolean b = videoPlayHelper.screenShot(file);
            if (b) {
                Toast.makeText(this, "抓图成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "抓图失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startRecord(VideoPlayHelper2 videoPlayHelper) {
        if ((!ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRead_Write[0]) || !ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRead_Write[1]))
                && !PermissionUtils.requestMultiPermissions(this, new int[]{6, 7}, this)) {
            PermissionUtils.requestMultiResult(this, permissionRead_Write, new int[]{6, 7}, this);
        }

        if (PermissionUtils.requestMultiPermissions(this, new int[]{6, 7}, this)) {

            String path = FileNameUtils.creatRecordFileName(iotID, "rrrrr");
            File file = new File(path);
            if (videoPlayHelper.getPlayStatus() == VideoPlayHelper2.PlayStatus.PREPARE_PLAY) {
                boolean b = videoPlayHelper.startRecord(file);
                recordVideo = true;
                if (b) {
                    Toast.makeText(this, "开始录像", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "正在录像", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "正在录像", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopRecord(VideoPlayHelper2 videoPlayHelper) {
        boolean b = videoPlayHelper.stopRecord();
        recordVideo = false;

        Toast.makeText(this, "停止录像", Toast.LENGTH_SHORT).show();
        if (b) { //关闭了
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

    public void queryRecordList(String mDeviceId) {
        String nowTimeDay = TimeUtils.getNowTimeDay();

        long l = TimeUtils.dateToMillisecond(nowTimeDay);
        startTime = (int) ((l / 1000));
        int endTime = startTime + 24 * 60 * 60;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("BeginTime", startTime);
            jsonObject.put("EndTime", endTime);
            jsonObject.put("QuerySize", 128);
            jsonObject.put("Type", 99);

            PanelDevice panelDevice = new PanelDevice(mDeviceId);
            JSONObject object = new JSONObject();
            object.put("iotId", mDeviceId);
            object.put("identifier", "QueryRecordTimeList");
            object.put("args", jsonObject);

            panelDevice.invokeService(object.toString(), new IPanelCallback() {
                @Override
                public void onComplete(boolean b, @Nullable Object o) {
                    Message message = handler.obtainMessage();
                    if (b) {
                        String string = o.toString();
                        Log.e("wy", "==llst=" + string);
                        AliyunServiceResultBean aliyunRecordFileListAliyunServiceResultBean = new AliyunServiceResultBean();
                        aliyunRecordFileListAliyunServiceResultBean.parseJSON(string, mDeviceId);
                        if (aliyunRecordFileListAliyunServiceResultBean.getCode() == 200) {
                            message.what = GET_RECORD_LIST;
                            message.arg1 = 1;
                            message.obj = aliyunRecordFileListAliyunServiceResultBean;
                            handler.sendMessage(message);
                        } else {
                            message.what = GET_RECORD_LIST;
                            message.arg1 = 2;
                            handler.sendMessage(message);
                        }
                    } else {
                        //!失败
                        message.what = GET_RECORD_LIST;
                        message.arg1 = 3;
                        handler.sendMessage(message);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }

}
