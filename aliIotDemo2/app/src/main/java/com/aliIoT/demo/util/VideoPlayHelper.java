package com.aliIoT.demo.util;

import android.content.Context;
import android.media.MediaCodec;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aliyun.iotx.linkvisual.media.C;
import com.aliyun.iotx.linkvisual.media.video.HardwareDecoderable;
import com.aliyun.iotx.linkvisual.media.video.PlayerException;
import com.aliyun.iotx.linkvisual.media.video.beans.PlayerStoppedDrawingMode;
import com.aliyun.iotx.linkvisual.media.video.beans.SeiInfoBuffer;
import com.aliyun.iotx.linkvisual.media.video.listener.OnErrorListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnExternalRenderListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnPlayerStateChangedListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnPreparedListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnRenderedFirstFrameListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnSeiInfoListener;
import com.aliyun.iotx.linkvisual.media.video.listener.OnVideoSizeChangedListener;
import com.aliyun.iotx.linkvisual.media.video.player.LivePlayer;
import com.google.android.exoplayer2.Player;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.aliIoT.demo.R;

import static com.aliIoT.demo.util.VideoPlayHelper.playStatus.BUFFING_PLAY;
import static com.aliIoT.demo.util.VideoPlayHelper.playStatus.NO_PALY;
import static com.aliIoT.demo.util.VideoPlayHelper.playStatus.PREPARE;
import static com.aliIoT.demo.util.VideoPlayHelper.playStatus.PREPARE_PLAY;


/**
 *
 * 预览播放辅助类
 */
public class VideoPlayHelper implements PlayLayout.PlayLayoutListener {
    private static final String TAG = "VideoPlayHelper";
    private float starY = -1;
    private float starX = -1;
    boolean isShowToast = true;

    public void setShowToast(boolean showToast) {
        isShowToast = showToast;
    }

    @Override
    public void PlayLayoutRefreshVideo() {
        refreshVideo();
    }


    public interface PlayStatusListener {
        void changePlayStatus(String iotid, playStatus status);

        void moveVideo(boolean flag);
    }

    public void setPlayStatusListener(PlayStatusListener mPlayStatusListener) {
        this.mPlayStatusListener = mPlayStatusListener;
    }

    PlayStatusListener mPlayStatusListener = null;

    public String getIotid() {
        return path;
    }

    public void setIotid(String iotid) {
        path = iotid;
    }

    private String path;

    public PlayLayout getPlayLayout() {
        return mTextureView;
    }

    private PlayLayout mTextureView;
    private playStatus playStatus = NO_PALY;

    private int stream = C.STREAM_TYPE_MINOR;
    private LivePlayer mLivePlayer = null;
    Context mContext;
    private int[] videoResolution = new int[]{0, 0};

    public int getStream() {
        return stream;
    }

    /**
     * 释放播放器方法，退出预览界面前调用一次即可，否则会造成内存泄漏
     */
    public void release() {
        mContext = null;
        if (mLivePlayer != null) {
            mLivePlayer.setOnPreparedListener(null);
            mLivePlayer.setOnErrorListener(null);
            mLivePlayer.setOnPlayerStateChangedListener(null);
            mLivePlayer.setOnExternalRenderListener(null);
            mLivePlayer.setOnRenderedFirstFrameListener(null);
            mLivePlayer.release();
        }
        mLivePlayer = null;

        Log.e(TAG, "release end=" + System.currentTimeMillis());
    }

    public float getVolume() {
        if (mLivePlayer != null) {
            return mLivePlayer.getVolume();
        }
        return -1.0f;
    }

    public void setVolume(float f) {
        if (mLivePlayer != null) {
            mLivePlayer.setVolume(f);
        }

    }

    public int[] getVideoResolution() {
        return videoResolution;
    }

    public void setStream(boolean b) {
        if (b) {
            stream = C.STREAM_TYPE_MAJOR;
        } else {
            stream = C.STREAM_TYPE_MINOR;
        }
    }

    public void setStream(int i) {
        if (i == 0 || i == 1) {
            stream = i;
        }
    }

    public enum playStatus {NO_PALY, PREPARE, PREPARE_PLAY, BUFFING_PLAY, STOP_PLAY}

    public VideoPlayHelper(Context mContext) {
        starX = -1;
        starY = -1;
        this.mContext = mContext;
    }

    public void setTextureView(PlayLayout view) {
        mTextureView = view;
        mTextureView.setClick(this);
//        mTextureView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//
//                        starY = event.getY();
//                        starX = event.getX();
//                        break;
//
//                    }
//                    case MotionEvent.ACTION_UP: {
//
//                        float y = event.getY();
//                        float x = event.getX();
//                        calculateDistanceTraveled(starX, starY, x, y);
//                        starX = -1;
//                        starY = -1;
//                        break;
//                    }
//                    case MotionEvent.ACTION_MOVE: {
//
//
//                        break;
//                    }
//                }
//                return false;
//            }
//        });
    }


    public void calculateDistanceTraveled(float starX, float starY, float x, float y) {
        if (starX == -1 && starY == -1) return;
        float absX = x - starX;
        if (mPlayStatusListener != null) {
            if (absX >= 100) {
                mPlayStatusListener.moveVideo(false);
            } else if (absX <= -100) {
                mPlayStatusListener.moveVideo(true);
            }
        }
    }

    public boolean videoPrepare() {
        if (mTextureView == null) {
            return false;
        }
        if (playStatus != NO_PALY && mLivePlayer != null) {
            mLivePlayer.stop();
            mLivePlayer.reset();
            mLivePlayer.clearTextureView();
        }
        if (mLivePlayer == null) {
            mLivePlayer = new LivePlayer(mContext);
        }
        mLivePlayer.setVolume(0.0f);
        mLivePlayer.setTextureView(mTextureView.getTextureView());
        playerListener();
        return true;
    }

    private boolean softDecode() {
        //boolean sharedPreferencesDataBool = SharedPreferencesUtils.getSharedPreferencesDataBoolDefultReturnTrue(StringConstantResource.SHAREDPREFERENCES_NAME, StringConstantResource.SHAREDPREFERENCES_DATA_SOFT_DECOD);
        //return sharedPreferencesDataBool;
        return false;
    }

    public void showHidePlayVideoRootPadding(boolean b) {
        if (mTextureView != null) {
            mTextureView.showHidePlayVideoRootPadding(b);
        }
    }


    public boolean startVideo() {
        if (mLivePlayer == null || TextUtils.isEmpty(path)) {
            return false;
        }
        mLivePlayer.setIPCLiveDataSource(path, stream, true, C.ENCRYPTE_AES_128, true, 2000);
        playStatus = PREPARE;
        mLivePlayer.prepare();
        mTextureView.showProgressbar();

        return true;
    }

    public boolean startVideoForOnResume() {
        if (mLivePlayer == null || TextUtils.isEmpty(path)) {
            return false;
        }
        if (playStatus != NO_PALY) {
            return false;
        }
        mLivePlayer.setIPCLiveDataSource(path, stream, true, C.ENCRYPTE_AES_128, true, 2000);
        playStatus = PREPARE;
        mLivePlayer.prepare();
        mTextureView.showProgressbar();

        return true;
    }


    public VideoPlayHelper.playStatus getPlayStatus() {
        return playStatus;
    }

    /********************************拉流失败重连****************************************/
    private int resetConnationCount = 0;

    /*********************************************************************/
    private void playerListener() {
        mLivePlayer.setDecoderStrategy(softDecode() ? HardwareDecoderable.DecoderStrategy.FORCE_SOFTWARE : HardwareDecoderable.DecoderStrategy.HARDWARE_FIRST);
        mLivePlayer.setPlayerStoppedDrawingMode(PlayerStoppedDrawingMode.ALWAYS_BLACK);
        mLivePlayer.setVideoScalingMode(MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        mLivePlayer.setOnRenderedFirstFrameListener(new OnRenderedFirstFrameListener() {
            @Override
            public void onRenderedFirstFrame() {
                mTextureView.hideReset();
                mTextureView.hideProgressbar();
            }
        });
        mLivePlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(PlayerException e) {
                if (resetConnationCount < 3) {
                    resetConnationCount++;
                    Log.e(TAG, "onError");
                    startVideo();
                } else {
                    playStatus = NO_PALY;

                    if (mPlayStatusListener != null) {
                        mPlayStatusListener.changePlayStatus(path, playStatus);
                    }
                    if (isShowToast) {
                        //ToastUtils.getToastUtils().showToast(MyApplication.getInstance(), playerror(e.getSubCode()));
                    }
                    mTextureView.hideProgressbar();
                    resetConnationCount = 0;
                    if (!mTextureView.isShowResetFlag()) {
                        mTextureView.showReset();
                    }
                }
            }
        });
        mLivePlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                playStatus = BUFFING_PLAY;
                //  Log.e(TAG, "playStatus = BUFFING_PLAY 6");
                Log.e(TAG, "Player.onPrepared");
                if (mLivePlayer != null && mContext != null)
                    mLivePlayer.start();
            }
        });
        mLivePlayer.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int width, int height) {
                videoResolution[0] = width;
                videoResolution[1] = height;
            }
        });
        mLivePlayer.setOnPlayerStateChangedListener(new OnPlayerStateChangedListener() {
            @Override
            public void onPlayerStateChange(int playerState) {
                switch (playerState) {
                    case Player.STATE_BUFFERING:
                        if (playStatus != PREPARE)
                            playStatus = BUFFING_PLAY;
                        mTextureView.showProgressbar();
                        if (mPlayStatusListener != null) {
                            mPlayStatusListener.changePlayStatus(path, BUFFING_PLAY);
                        }
                        break;
                    case Player.STATE_READY:
                        resetConnationCount = 0;
                        mTextureView.hideProgressbar();
                        if (playStatus != PREPARE)
                            playStatus = PREPARE_PLAY;
                        if (mPlayStatusListener != null) {
                            mPlayStatusListener.changePlayStatus(path, PREPARE_PLAY);
                        }
                        break;
                    case Player.STATE_IDLE:
                        Log.e(TAG, "Player.STATE_IDLE");
                        if (playStatus != PREPARE)
                            playStatus = NO_PALY;
                        mTextureView.hideProgressbar();
                        if (mPlayStatusListener != null) {
                            mPlayStatusListener.changePlayStatus(path, NO_PALY);
                        }
                        break;
                    case Player.STATE_ENDED:
                        Log.e(TAG, "Player.STATE_ENDED");
                        mTextureView.hideProgressbar();
                        mTextureView.hideError();
                        if (playStatus != PREPARE)
                            playStatus = NO_PALY;
                        if (mPlayStatusListener != null) {
                            mPlayStatusListener.changePlayStatus(path, NO_PALY);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        //智能帧回调监听
        mLivePlayer.setOnSeiInfoListener(new SeiInfoBuffer(1024 * 512), new OnSeiInfoListener() {
            @Override
            public void onSeiInfoUpdate(SeiInfoBuffer seiInfoBuffer) {
            }
        });
        mLivePlayer.setOnExternalRenderListener(new OnExternalRenderListener() {
            @Override
            public void onVideoFrameUpdate(int i, int i1, long l) {

            }
        });

    }

//    public String playerror(int code) {
//        String res = "";
//        switch (code) {
//            case PlayerException.SUB_CODE_RENDER_DECODE_ERROR:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset2);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_STREAM_CONNECT_ERROR:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset3);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_INVALID_DECRYPTE_KEY:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset4);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_INVALID_RTMP_URL:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset5);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_PARAMETER_ERROR:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset6);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_QUERY_URL_FAILED:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset7);
//                break;
//            case PlayerException.SUB_CODE_SOURCE_INVALID_HLS_URL:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset8);
//                break;
//            default:
//                res = MyApplication.getInstance().getString(R.string.play_failed_reset);
//                break;
//        }
//        return res;
//    }

    boolean mSmartRuleFlag = false;
    boolean mSmartReSultFlag = false;

    public boolean isSmartRuleFlag() {
        return mSmartRuleFlag;
    }

    public void setSmartRuleFlag(boolean mSmartRuleFlag) {
        this.mSmartRuleFlag = mSmartRuleFlag;
    }

    public boolean isSmartReSultFlag() {
        return mSmartReSultFlag;
    }

    public void setSmartReSultFlag(boolean mSmartReSultFlag) {
        this.mSmartReSultFlag = mSmartReSultFlag;
    }

    public ByteBuffer deletSpecialByte(ByteBuffer mByteBuffer, int lenth) {
        byte[] b = new byte[lenth];  //byte[] b = new byte[bb.capacity()]  is OK
        ArrayList<Byte> list = new ArrayList<>();
        mByteBuffer.get(b, 0, b.length);
        list.add(b[0]);
        list.add(b[1]);
        for (int i = 2; i < b.length; i++) {
            if (!((b[i - 2] == 0) && (b[i - 1] == 0) && (b[i] == 3))) {
                list.add(b[i]);
            }
        }

        byte[] array2 = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array2[i] = list.get(i);
        }
        ByteBuffer buf = ByteBuffer.wrap(array2);
        return buf;
    }

    public void refreshVideo() {
        mLivePlayer.stop();
        mLivePlayer.reset();
        Log.e(TAG, "playStatus =" + playStatus + "0");
        mLivePlayer.setIPCLiveDataSource(path, stream, true, C.ENCRYPTE_AES_128, true, 2000);
        playStatus = PREPARE;
        // Log.e(TAG, "playStatus = PREPARE 1");
        mLivePlayer.prepare();
        Log.e(TAG, "refreshVideo");
        mTextureView.showProgressbar();


    }

    public void changePlay(String url) {
        if (mTextureView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        path = url;
    }

    public void stop() {
        if (mLivePlayer != null) {
            mLivePlayer.stop();
            mLivePlayer.reset();
        }
    }

    public void pause() {
        if (mLivePlayer != null && playStatus != PREPARE) {
            mLivePlayer.stop();
            playStatus = NO_PALY;
            // Log.e(TAG, "playStatus = NO_PALY 2");
        }
    }

    public void onResume() {
        if (mLivePlayer != null) {
            startVideoForOnResume();
        }
    }

    public void showHide(boolean bool) {
        showHide(bool, false);
    }

    public void showHide(boolean bool, boolean isOpenVideo) {
        if (bool) {
            mTextureView.setVisibility(View.VISIBLE);
            if (isOpenVideo) {
                mLivePlayer.setIPCLiveDataSource(path, stream, true, C.ENCRYPTE_AES_128, true, 2000);
                playStatus = PREPARE;
                //Log.e(TAG, "playStatus = PREPARE 3");
                mLivePlayer.prepare();
                mTextureView.showProgressbar();

            }
        } else {
            if (isOpenVideo) {
                stop();
            }
            mTextureView.setVisibility(View.GONE);
        }
    }

    public void stopClean() {
        stop();
        setIotid("");
    }

    public void showTextureView() {
        mTextureView.setVisibility(View.VISIBLE);
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
}
