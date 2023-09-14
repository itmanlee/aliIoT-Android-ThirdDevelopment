package com.aliIoT.demo.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.aliIoT.demo.R;
import com.aliyun.iotx.linkvisual.media.video.views.ZoomableTextureView;

/**
 * 自定义播放视图
 */
public class PlayLayout extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "PlayLayout";
    Context context;
    status mNowStatus = status.NO_STATUS;
    PointerModel mPointerModel = PointerModel.SING_POINTRER;
    boolean showResetFlag = false;
    private ZoomableTextureView mTextureView;
    private ProgressBar bar;
    private TextView tv;
    private ConstraintLayout mPlayVideoRoot;
    private View v;
    private int mSmartFrameViewWidth;
    private int mSmartFrameViewHeight;
    private ImageView record;
    private ImageView mVideoReset;
    private PlayLayoutListener mListener;
    private float starY = -1;
    private float starX = -1;
    private ImageView ptz_direction_up;//上
    private ImageView ptz_direction_down;//下
    private ImageView ptz_direction_left;//左
    private ImageView ptz_direction_right;//右
    private ImageView ptz_direction_left_up;//左上
    private ImageView ptz_direction_right_up;//右上
    private ImageView ptz_direction_left_down;//左下
    private ImageView ptz_direction_right_down;//右下
    private TextView mChannelName;

    public PlayLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PlayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setChannelName(String s) {
        if (mChannelName != null) {
            if (mChannelName.getVisibility() == GONE)
                mChannelName.setVisibility(VISIBLE);
            mChannelName.setText(s);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_reset: {
                if (mListener != null) {
                    mListener.PlayLayoutRefreshVideo();
                }
                hideReset();
                break;
            }
        }
    }

    public void setClick(PlayLayoutListener mListener) {
        this.mListener = mListener;

    }

    public TextureView getTextureView() {
        return mTextureView;
    }

    private void init() {
        v = LayoutInflater.from(context).inflate(R.layout.play_video_layout, this);
        mPlayVideoRoot = v.findViewById(R.id.play_video_root);
        mVideoReset = v.findViewById(R.id.video_reset);
        mTextureView = v.findViewById(R.id.textureview);
        mChannelName = v.findViewById(R.id.channel_name);
        record = v.findViewById(R.id.record);
        bar = v.findViewById(R.id.progressbar);
        tv = v.findViewById(R.id.tv);
        mVideoReset.setOnClickListener(this);
        ptz_direction_up = v.findViewById(R.id.ptz_direction_up);
        ptz_direction_down = v.findViewById(R.id.ptz_direction_down);
        ptz_direction_left = v.findViewById(R.id.ptz_direction_left);
        ptz_direction_right = v.findViewById(R.id.ptz_direction_right);
        ptz_direction_left_up = v.findViewById(R.id.ptz_direction_left_up);
        ptz_direction_right_up = v.findViewById(R.id.ptz_direction_right_up);
        ptz_direction_left_down = v.findViewById(R.id.ptz_direction_left_down);
        ptz_direction_right_down = v.findViewById(R.id.ptz_direction_right_down);
    }

    //yxy 上 显示与隐藏
    public void showHideDirectionUp(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_up.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);//yxy加载闪烁动画
            ptz_direction_up.startAnimation(animation);
        } else {
            ptz_direction_up.setVisibility(View.GONE);
            ptz_direction_up.clearAnimation();
        }
    }

    //yxy 下 显示与隐藏
    public void showHideDirectionDown(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_down.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_down.startAnimation(animation);
        } else {
            ptz_direction_down.setVisibility(View.GONE);
            ptz_direction_down.clearAnimation();
        }
    }

    //yxy 左 显示与隐藏
    public void showHideDirectionLeft(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_left.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_left.startAnimation(animation);
        } else {
            ptz_direction_left.setVisibility(View.GONE);
            ptz_direction_left.clearAnimation();
        }
    }

    //yxy 右 显示与隐藏
    public void showHideDirectionRight(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_right.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_right.startAnimation(animation);
        } else {
            ptz_direction_right.setVisibility(View.GONE);
            ptz_direction_right.clearAnimation();
        }
    }

    //yxy 左上 显示与隐藏
    public void showHideDirectionLeftUp(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_left_up.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_left_up.startAnimation(animation);
        } else {
            ptz_direction_left_up.setVisibility(View.GONE);
            ptz_direction_left_up.clearAnimation();
        }
    }

    //yxy 右上 显示与隐藏
    public void showHideDirectionRightUp(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_right_up.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_right_up.startAnimation(animation);
        } else {
            ptz_direction_right_up.setVisibility(View.GONE);
            ptz_direction_right_up.clearAnimation();
        }
    }

    //yxy 左下 显示与隐藏
    public void showHideDirectionLeftDown(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_left_down.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_left_down.startAnimation(animation);
        } else {
            ptz_direction_left_down.setVisibility(View.GONE);
            ptz_direction_left_down.clearAnimation();
        }
    }

    //yxy 右下 显示与隐藏
    public void showHideDirectionRightDown(boolean b) {
        Animation animation;
        if (b) {
            ptz_direction_right_down.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(context, R.anim.flicker_anim);
            ptz_direction_right_down.startAnimation(animation);
        } else {
            ptz_direction_right_down.setVisibility(View.GONE);
            ptz_direction_right_down.clearAnimation();
        }
    }

    public void showHidePlayVideoRootPadding(boolean b) {
        if (b) {
            mPlayVideoRoot.setPadding(2, 2, 2, 2);
            mPlayVideoRoot.setBackgroundResource(R.drawable.back_arrow);
        } else {
            mPlayVideoRoot.setPadding(0, 0, 0, 0);
            mPlayVideoRoot.setBackgroundResource(0);
        }
    }

    public void showHideRecord(boolean b) {
        if (b) {
            record.setVisibility(VISIBLE);
        } else {
            record.setVisibility(GONE);
        }
    }

    public void showProgressbar() {
        bar.setVisibility(VISIBLE);
        tv.setVisibility(GONE);
        mVideoReset.setVisibility(GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "event.getActionMasked()=" + event.getActionMasked());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mPointerModel = PointerModel.SING_POINTRER;
                if (checkIsClick(event.getRawX(), event.getRawY())) {
                    onClick(mVideoReset);
                } else if (mTextureView.getScale() > 1.0) {
//                    if ((context instanceof HomeAcitivty) && ((HomeAcitivty) context).getMediaPlayFragmentNowScreenSpil() == MediaPlayFragment.SCREEN_SPIL_ONE) {
//                        mNowStatus = status.MOVE_STATE;
//                        mTextureView.onTouchEvent(event);
//                    }
                } else {
                    starY = event.getY();
                    starX = event.getX();
                }
                break;

            }
            case MotionEvent.ACTION_UP: {
                if (mPointerModel != PointerModel.DOUBLE_PONTRER_END) {
                    if (starX != -1 && starY != -1) {
                        float y = event.getY();
                        float x = event.getX();
                        if (mListener != null) {
                            mListener.calculateDistanceTraveled(starX, starY, x, y);
                        }
                    }
                }
                starX = -1;
                starY = -1;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                if ((context instanceof HomeAcitivty) && ((HomeAcitivty) context).getMediaPlayFragmentNowScreenSpil() == MediaPlayFragment.SCREEN_SPIL_ONE) {
//                    if (mNowStatus != status.NO_STATUS && mPointerModel != PointerModel.DOUBLE_PONTRER_END) {
//                        mTextureView.onTouchEvent(event);
//                    }
//                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
//                if ((context instanceof HomeAcitivty) && ((HomeAcitivty) context).getMediaPlayFragmentNowScreenSpil() == MediaPlayFragment.SCREEN_SPIL_ONE) {
//                    mPointerModel = PointerModel.DOUBLE_PONTRER_START;
//                    starX = -1;
//                    starY = -1;
//                    mNowStatus = status.ZOOM_STATE;
//                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
//                if ((context instanceof HomeAcitivty) && ((HomeAcitivty) context).getMediaPlayFragmentNowScreenSpil() == MediaPlayFragment.SCREEN_SPIL_ONE) {
//                    mPointerModel = PointerModel.DOUBLE_PONTRER_END;
//                    if (mTextureView.getScale() > 1.0) {
//                        mNowStatus = status.MOVE_STATE;
//                    } else {
//                        mNowStatus = status.NO_STATUS;
//                    }
//                }
                break;
            }
        }
        return super.onTouchEvent(event);

    }

    public void clearScale() {
        if (mTextureView != null && mTextureView.getScale() > 1.0) {
            mTextureView.setZoomScale(false, 1.0f);
            mTextureView.zoomOut(false);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
//            if (checkIsClick(ev.getRawX(), ev.getRawY())) {
//                onClick(mVideoReset);
//                return true;
//            } else if (mTextureView.getScale() > 1.0) {
//                mNowStatus = status.MOVE_STATE;
//            } else {
//                return true;
//            }
//
//        } else if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
//            mNowStatus = status.ZOOM_STATE;
//            return true;
//        } else if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
//            if (mTextureView.getScale() > 1.0) {
//                mNowStatus = status.MOVE_STATE;
//            } else {
//                mNowStatus = status.NO_STATUS;
//            }
//            return true;
//        } else if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
//            if (mNowStatus != status.NO_STATUS) {
//                mTextureView.onTouchEvent(ev);
//                return true;
//            }
//            return true;
//        } else {
//            return true;
//        }
        return true;
    }

    public void hideProgressbar() {
        bar.setVisibility(GONE);
    }

    public void hideError() {
        tv.setVisibility(GONE);
    }

    public void showError() {
        showError("");
    }

    public void showError(String info) {
        if (TextUtils.isEmpty(info)) {
            tv.setText("error");
        } else {
            tv.setText(info);
        }
        tv.setVisibility(VISIBLE);
        bar.setVisibility(GONE);
    }

    public boolean isShowResetFlag() {
        return showResetFlag;
    }

    public void setShowResetFlag(boolean showResetFlag) {
        this.showResetFlag = showResetFlag;
    }

    public void showReset() {
        if (mVideoReset != null && mVideoReset.getVisibility() == GONE) {
            mVideoReset.setVisibility(VISIBLE);
        }
    }

    public void hideReset() {
        if (mVideoReset != null && mVideoReset.getVisibility() == VISIBLE) {
            mVideoReset.setVisibility(GONE);
        }
    }

    public boolean checkIsClick(float x, float y) {
        if (mVideoReset.getVisibility() == GONE) {
            return false;
        }
        return isTouchPointInView(mVideoReset, x, y);
    }

    private boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    enum status {NO_STATUS, ZOOM_STATE, MOVE_STATE}

    enum PointerModel {SING_POINTRER, DOUBLE_PONTRER_START, DOUBLE_PONTRER_END}

    public interface PlayLayoutListener {
        void PlayLayoutRefreshVideo();

        void calculateDistanceTraveled(float starX, float starY, float x, float y);
    }
}
