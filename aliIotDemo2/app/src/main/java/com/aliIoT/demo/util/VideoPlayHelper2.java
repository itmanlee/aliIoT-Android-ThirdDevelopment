package com.aliIoT.demo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.aliyun.iotx.linkvisual.media.C;
import com.aliyun.iotx.linkvisual.media.video.HardwareDecoderable;
import com.aliyun.iotx.linkvisual.media.video.PlayerException;
import com.aliyun.iotx.linkvisual.media.video.beans.PlayerStoppedDrawingMode;
import com.aliyun.iotx.linkvisual.media.video.listener.OnCompletionListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnErrorListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnPlayerStateChangedListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnPreparedListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnRenderedFirstFrameListener;
import com.aliyun.iotx.linkvisual.media.video.player.VodPlayer;
import com.google.android.exoplayer2.Player;

import java.io.File;

/**
 * 回放播放辅助类
 */
public class VideoPlayHelper2 {
    private static final String TAG = "VideoPlayHelper2";
    final int GET_VIDEO_CURRENT = 1;
    int mRecordReferenceTime;
    VideoCallBack mListen = null;
    float[] mPlaybackSpeed = {1 / 16f, 1 / 8f, 1 / 4f, 1 / 2f, 1, 2, 4, 8, 16};
    int nowSpeedIndex = 4;
    Context mContext;
    private int startTime;
    private int endTime;
    private String iotId;
    private PlayLayout mTextureView;
    private PlayStatus playStatus = PlayStatus.NO_PALY;
    private VodPlayer mLivePlayer = null;
    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_VIDEO_CURRENT: {
                    getVideoCurrent();
                    break;
                }
            }
        }
    };

    public VideoPlayHelper2(Context mContext) {
        this.mContext = mContext;
    }

    public void setRecordReferenceTime(int arg1) {
        mRecordReferenceTime = arg1;
    }

    public void seekTo(int newTimeValue) {
        //Log.e(TAG, "seekTo= " + newTimeValue);
        if (mLivePlayer != null && playStatus != PlayStatus.NO_PALY) {
            if ((newTimeValue >= endTime) || (newTimeValue <= startTime)) {
                mLivePlayer.stop();
            } else {
                mLivePlayer.seekTo((newTimeValue - startTime) * 1000);
            }
        }
    }

    public float getVolume() {
        return mLivePlayer.getVolume();
    }

    public void setVolume(float volume) {
        mLivePlayer.setVolume(volume);
    }

    public void setTextureViewClick(PlayLayout.PlayLayoutListener click) {
        if (mTextureView != null) {
            mTextureView.setClick(click);
        }
    }

    public void checkSpeed() {
        //mLivePlayer.setPlaybackSpeed(mPlaybackSpeed[nowSpeedIndex]);
    }

    public void setVideoCurrentListen(VideoCallBack c) {
        mListen = c;
    }

    public String getIotid() {
        return iotId;
    }

    public void setIotid(String iotid) {
        iotId = iotid;
    }

    private void getVideoCurrent() {
        long videoCurrentPosition = getVideoCurrentPosition();
        if (mListen != null) {
            long l = /*mRecordReferenceTime +*/ (videoCurrentPosition / 1000) + startTime;
            if (l < 86400) {
                mListen.videoCurrent(l);
            }
        }
        hand.sendEmptyMessageDelayed(GET_VIDEO_CURRENT, 1000);
    }

    public int getCurTime() {
        int time = 0;
        long videoCurrentPosition = getVideoCurrentPosition();

        long l = /*mRecordReferenceTime +*/ (videoCurrentPosition / 1000) + startTime;
        if (l < 86400) {
            time = (int) l;
        }

        return time;
    }

    public void setPath(String iotid, int startTime, int endTime) {
        iotId = iotid;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    /**
     * 释放播放器方法，退出预览界面前调用一次即可，否则会造成内存泄漏
     */
    public void release() {
        mContext = null;
        hand.removeCallbacksAndMessages(null);
        if (mLivePlayer != null) {
            mLivePlayer.stop();
            mLivePlayer.setOnPreparedListener(null);
            mLivePlayer.setOnErrorListener(null);
            mLivePlayer.setOnPlayerStateChangedListener(null);
            mLivePlayer.setOnExternalRenderListener(null);
            mLivePlayer.release();

        }
        mLivePlayer = null;
    }

    public boolean playVideo() {
        return playVideo(0);
    }

    public boolean playVideo(int currentTime) {
        if (TextUtils.isEmpty(iotId)) {
            return false;
        }
        showProgressBar();
        Log.e("wy", "==showProgressBar===" + playStatus);
        if (playStatus == PlayStatus.NO_PALY) {
            //yxy播放状态改变时触发播放状态监听
            if (mListen != null) {
                mListen.videoStausCallBack(playStatus);
            }
            return startVideo(currentTime);
        } else if (playStatus == PlayStatus.PAUSE_PLAY) {
            if (mLivePlayer != null) {
                mLivePlayer.start();
                playStatus = PlayStatus.PREPARE_PLAY;
                getVideoCurrent();
                //播放状态改变时触发播放状态监听
                if (mListen != null) {
                    mListen.videoStausCallBack(playStatus);
                }
                return true;
            }
            //播放状态改变时触发播放状态监听
            if (mListen != null) {
                mListen.videoStausCallBack(playStatus);
            }
            return false;
        } else {
            pauseVideo();
            //播放状态改变时触发播放状态监听
            if (mListen != null) {
                mListen.videoStausCallBack(playStatus);
            }
            return false;
        }
    }

    public void showProgressBar() {
        mTextureView.showProgressbar();
    }

    public void hideProgressBar() {
        mTextureView.hideProgressbar();
    }

    public void pauseVideo() {
        mLivePlayer.pause();
        playStatus = PlayStatus.PAUSE_PLAY;
        mTextureView.hideProgressbar();
        hand.removeCallbacksAndMessages(null);
        if (mListen != null) {
            mListen.videoStausCallBack(playStatus);
        }
    }

    public void stopVideo() {
        mLivePlayer.stop();
        nowSpeedIndex = 4;

        playStatus = PlayStatus.PAUSE_PLAY;
        mTextureView.hideProgressbar();
        hand.removeCallbacksAndMessages(null);
        if (mListen != null) {
            mListen.videoStausCallBack(playStatus);
        }
    }

    public String getSpeed() {
        return Utils.speed(mPlaybackSpeed[nowSpeedIndex]);
    }

    public void setTextureView(PlayLayout view) {
        mTextureView = view;
    }

    public boolean videoPrepare() {
        if (mTextureView == null) {
            return false;
        }
        if (playStatus != PlayStatus.NO_PALY /*&& playStatus != PlayStatus.END_PALY */ && mLivePlayer != null) {
            mLivePlayer.stop();
            mLivePlayer.reset();
            mLivePlayer.clearTextureView();
        }
        if (mLivePlayer == null) {
            mLivePlayer = new VodPlayer(mContext);
        }
        mLivePlayer.setTextureView(mTextureView.getTextureView());
        mLivePlayer.setVolume(0.0f);
        playerListener();
        return true;
    }

    public long getVideoCurrentPosition() {
        if (playStatus != PlayStatus.NO_PALY/* && playStatus != PlayStatus.END_PALY*/)
            return mLivePlayer.getCurrentPosition();
        else
            return -1;
    }

    /**
     * @param b true 加速播放 false减速播放
     * @return 设置后的播放背书。
     */
    public String setPlaybackSpeed(boolean b) {
        String str = "";
        if (playStatus == PlayStatus.NO_PALY /*|| playStatus == PlayStatus.END_PALY*/) {
            if (!startVideo()) {
                return PlayStatus.NO_PALY + "";
            }
        }
        if (b) {
            if (nowSpeedIndex < mPlaybackSpeed.length - 2) {
                nowSpeedIndex = ++nowSpeedIndex;
                float v = mPlaybackSpeed[nowSpeedIndex];
                mLivePlayer.setPlaybackSpeed(v);
                str = Utils.speed(v) + "";
            } else {
                str = Utils.speed(mPlaybackSpeed[nowSpeedIndex]) + "";
            }
        } else {
            if (nowSpeedIndex > 0) {
                nowSpeedIndex = --nowSpeedIndex;
                float v = mPlaybackSpeed[nowSpeedIndex];
                mLivePlayer.setPlaybackSpeed(v);
                str = Utils.speed(v) + "";
            } else {
                str = Utils.speed(mPlaybackSpeed[nowSpeedIndex]) + "";
            }
        }
        return str;
    }

    public boolean startVideo() {
        return startVideo(0);
    }

    public boolean startVideo(int currentTime) {
        if (mLivePlayer == null || TextUtils.isEmpty(iotId) || (startTime == 0 && endTime == 0) || (mRecordReferenceTime == 0)) {
            //ToastUtils.getToastUtils().showToast(mContext, mContext.getResources().getString(R.string.play_info_error));
            return false;
        }
        mLivePlayer.setDataSourceByIPCRecordTime(iotId, mRecordReferenceTime + startTime, mRecordReferenceTime + endTime, true, C.ENCRYPTE_AES_128, currentTime * 1000);

        mLivePlayer.prepare();
        playStatus = PlayStatus.PREPARE;
        mTextureView.showProgressbar();
        mTextureView.hideReset();
        Log.e(TAG, "prepare*" + System.currentTimeMillis() + "*");
        return true;
    }

    private boolean softDecode() {
//        boolean sharedPreferencesDataBool = SharedPreferencesUtils.getSharedPreferencesDataBoolDefultReturnTrue(StringConstantResource.SHAREDPREFERENCES_NAME, StringConstantResource.SHAREDPREFERENCES_DATA_SOFT_DECOD);
//        return sharedPreferencesDataBool;
        return false;
    }

    private void playerListener() {
        mLivePlayer.setDecoderStrategy(softDecode() ? HardwareDecoderable.DecoderStrategy.FORCE_SOFTWARE : HardwareDecoderable.DecoderStrategy.HARDWARE_FIRST);
        mLivePlayer.setPlayerStoppedDrawingMode(PlayerStoppedDrawingMode.ALWAYS_BLACK);
        mLivePlayer.setVideoScalingMode(com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        mLivePlayer.setOnRenderedFirstFrameListener(new OnRenderedFirstFrameListener() {
            @Override
            public void onRenderedFirstFrame() {
                mTextureView.hideReset();
                mTextureView.hideProgressbar();
                Log.e("wy", "onRenderedFirstFrame");
            }
        });
        mLivePlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(PlayerException e) {
                //  mTextureView.showError(e.getLocalizedMessage());
                Log.e("wy", "onError" + e);
                mTextureView.showReset();
                mTextureView.hideProgressbar();
                playStatus = PlayStatus.NO_PALY;
                if (mListen != null) {
                    mListen.videoStausCallBack(playStatus);
                }
            }
        });

        mLivePlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.e("wy", "onPrepared");
                Log.e(TAG, "prepare*" + System.currentTimeMillis() + "**");

                if (mLivePlayer != null && mContext != null) {
                    mLivePlayer.start();
                }
            }
        });
        mLivePlayer.setOnPlayerStateChangedListener(new OnPlayerStateChangedListener() {
            @Override
            public void onPlayerStateChange(int playerState) {
                Log.e("wy", "onPlayerStateChange" + playerState);
                hideProgressBar();
                switch (playerState) {
                    case Player.STATE_BUFFERING:
                        playStatus = PlayStatus.BUFFING_PLAY;
                        if (mListen != null) {
                            mListen.videoStausCallBack(playStatus);
                        }
                        mTextureView.showProgressbar();
                        hand.removeCallbacksAndMessages(null);
                        break;
                    case Player.STATE_IDLE:
                        playStatus = PlayStatus.NO_PALY;
                        mTextureView.hideProgressbar();
                        if (mListen != null) {
                            mListen.videoStausCallBack(playStatus);
                        }
                        break;
                    case Player.STATE_READY:
                        mTextureView.hideProgressbar();
                        if (playStatus != PlayStatus.PAUSE_PLAY) {
                            playStatus = PlayStatus.PREPARE_PLAY;
                            if (mListen != null) {
                                mListen.videoStausCallBack(playStatus);
                            }
                            getVideoCurrent();
                        }
                        break;
                    case Player.STATE_ENDED:
                        mTextureView.hideProgressbar();
                        mTextureView.hideError();
                        //playStatus = PlayStatus.END_PALY;
                        playStatus = PlayStatus.NO_PALY;
                        hand.removeCallbacksAndMessages(null);
                        if (mListen != null) {
                            mListen.videoStausCallBack(playStatus);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mLivePlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.e("wy", "onCompletion");
            }
        });
    }

    public void stop() {
        if (mLivePlayer != null && playStatus != PlayStatus.NO_PALY /*&& playStatus != PlayStatus.END_PALY*/) {
            nowSpeedIndex = 4;
            mLivePlayer.setPlaybackSpeed(mPlaybackSpeed[nowSpeedIndex]);
            mLivePlayer.stop();
            mLivePlayer.reset();
            playStatus = PlayStatus.NO_PALY;
            mTextureView.hideProgressbar();
            if (mListen != null) {
                mListen.videoStausCallBack(playStatus);
            }
        }
    }

    public boolean screenShot(File file) {
        return mLivePlayer.snapShotToFile(file);
    }

    public boolean startRecord(File file) {
        return mLivePlayer.startRecordingContent(file);
    }

    public boolean stopRecord() {
        return mLivePlayer.stopRecordingContent();
    }

    public enum PlayStatus {NO_PALY, PREPARE, PREPARE_PLAY, BUFFING_PLAY, PAUSE_PLAY/*, END_PALY*/}

    public interface VideoCallBack {
        void videoCurrent(long l);

        void videoStausCallBack(PlayStatus status);

        void playNextTimePart(int startTime, int endTime);
    }
}
