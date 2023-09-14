package com.aliIoT.demo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.aliIoT.demo.R;


/**
 * Created by wy on 2019/7/25.
 */

public class RemoteControlView extends View {
    public int mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_STOP;
    public int mlastCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_STOP;
    private float scale = this.getResources().getDisplayMetrics().density;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float posX;
    private float posY;
    private float mDefaultposX;
    private float mDefaultposY;
    private int minRadius = 0;
    private Bitmap bitmap;
    private RemoteViewBg remoteViewBg;
    private Rect src;
    private Matrix matrix;
    private Bitmap bitmap1;
    private int nBitmapWidth;
    private int nBitmapHeight;
    private int RockerCircleX;
    private int RockerCircleY;
    private float tempRad = 0;
    private Rect dst;
    private Bitmap bitmapInner;
    private Rect srcInner;
    private RemoteViewBg innerBg;
    private Rect dstInner;
    private boolean isStart = false;
    private Bitmap bitmap2;
    private Rect srcNoActive;
    private Rect dstNoActive;
    /**
     * 设置控件移动方向回调接口
     */

    private RemoteListener remoteListener;

    public RemoteControlView(Context context) {
        super(context);
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.control_rocker_arrow1);
        bitmapInner = BitmapFactory.decodeResource(getResources(), R.mipmap.control_rocker_paws1);
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.control_rocker_bg1);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.control_rocker_not_active);

        //!  获取图片高度和宽度
        nBitmapWidth = bitmap1.getWidth();
        nBitmapHeight = bitmap1.getHeight();
        remoteViewBg = new RemoteViewBg(bitmap);
        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        srcNoActive = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        srcInner = new Rect(0, 0, bitmapInner.getWidth(), bitmapInner.getHeight());
        innerBg = new RemoteViewBg(bitmapInner);
        matrix = new Matrix();

    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        posX = mWidth / 2;
        posY = mHeight / 2;
        minRadius = mWidth / 8;
        mDefaultposX = mWidth / 2;
        mDefaultposY = mHeight / 2;

        dst = new Rect((int) mWidth / 2 - bitmap.getWidth() / 2, (int) mHeight / 2 - bitmap.getHeight() / 2, (int) mWidth / 2 + bitmap.getWidth() / 2, (int) mHeight / 2 + bitmap.getHeight() / 2);
        dstNoActive = new Rect((int) mWidth / 2 - bitmap2.getWidth() / 2, (int) mHeight / 2 - bitmap2.getHeight() / 2, (int) mWidth / 2 + bitmap2.getWidth() / 2, (int) mHeight / 2 + bitmap2.getHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        remoteViewBg.draw(canvas, mPaint, src, dst);

        dstInner = new Rect((int) posX - minRadius, (int) posY - minRadius, (int) posX + minRadius, (int) posY + minRadius);
        innerBg.draw(canvas, mPaint, srcInner, dstInner);

        matrix.reset();

        matrix.setTranslate(mWidth / 2 - bitmap1.getWidth() / 2, mHeight / 2 - bitmap1.getHeight() / 2);

        if (tempRad != 0) {
            matrix.preRotate(tempRad + 90, (float) bitmap1.getWidth() / 2, (float) bitmap1.getHeight() / 2);  //要旋转的角度

        } else {
            matrix.preRotate(tempRad);
        }

        if (isStart) {
            canvas.drawBitmap(bitmap1, matrix, null);
        } else {
            canvas.drawBitmap(bitmap2, srcNoActive, dstNoActive, null);
        }

        matrix.reset();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isStart = true;
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_STOP;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //! 在范围外触摸
            if (Math.sqrt(Math.pow((mWidth / 2 - (int) event.getX()), 2) + Math.pow((mWidth / 2 - (int) event.getY()), 2)) >= (mWidth / 2 - 50 * scale)) {
                tempRad = getRad(mWidth / 2, mHeight / 2, event.getX(), event.getY());
                getXY(mWidth / 2, mHeight / 2, bitmap.getWidth() / 2 - minRadius, tempRad);
                tempRad = getAngle(event.getX(), event.getY());

            } else {
                //! 范围内触摸
                posX = event.getX();
                posY = event.getY();
                tempRad = getAngle(event.getX(), event.getY());

            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isStart = false;
            posX = mWidth / 2;
            posY = mHeight / 2;
            tempRad = 0;

            if (remoteListener != null) {
                remoteListener.onRemoteListener(ConstUtil.CLOUDEYE_PTZ_CTRL_STOP, 0, 0);
            }
        }
        invalidate();
        return true;
    }

    private float getAngle(float xTouch, float yTouch) {
        RockerCircleX = mWidth / 2;
        RockerCircleY = mHeight / 2;
        return (float) (getRad(RockerCircleX, RockerCircleY, xTouch, yTouch) * 180f / Math.PI);
    }

    public void getXY(float x, float y, float R, double rad) {
        //!获取圆周运动的X坐标
        posX = (float) (R * Math.cos(rad)) + x;
        //!获取圆周运动的Y坐标
        posY = (float) (R * Math.sin(rad)) + y;

        getDirection(posX, posY, R);
    }

    /**
     * 计算角度来获取方向
     *
     * @param x
     * @param y
     * @param R
     */
    public void getDirection(float x, float y, float R) {
        if (x < mDefaultposX + R && x > mDefaultposX && y < mDefaultposY + R / 5 && y > mDefaultposY - R / 5) {
            //!右
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_RIGHT;
        } else if (x < mDefaultposX && x > mDefaultposX - R && y < mDefaultposY + R / 5 && y > mDefaultposY - R / 5) {
            //!左
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_LEFT;
        } else if (x < mDefaultposX + R / 5 && x > mDefaultposX - R / 5 && y < mDefaultposY && y > mDefaultposY - R) {
            //!上
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_UP;
        } else if (x < mDefaultposX + R / 5 && x > mDefaultposX - R / 5 && y < mDefaultposY + R && y > mDefaultposY) {
            //!下
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_DOWN;
        } else if (x < mDefaultposX - R / 2 && x > mDefaultposX - R * 4 / 5 && y < mDefaultposY - R / 2 && y > mDefaultposY - R * 4 / 5) {
            //!左上
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_UPLEFT;
        } else if (x < mDefaultposX - R / 2 && x > mDefaultposX - R * 4 / 5 && y > mDefaultposY + R / 2 && y < mDefaultposY + R * 4 / 5) {
            //!左下
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_DOWNLEFT;
        } else if (x > mDefaultposX + R / 2 && x < mDefaultposX + R * 4 / 5 && y < mDefaultposY - R / 2 && y > mDefaultposY - R * 4 / 5) {
            //!右上
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_UPRIGHT;
        } else if (x > mDefaultposX + R / 2 && x < mDefaultposX + R * 4 / 5 && y > mDefaultposY + R / 2 && y < mDefaultposY + R * 4 / 5) {
            //!右下
            mCmd = ConstUtil.CLOUDEYE_PTZ_CTRL_MOVE_DOWNRIGHT;
        }

        if (mlastCmd != mCmd) {
            if (remoteListener != null) {
                remoteListener.onRemoteListener(mCmd, 0, 0);
            }
        }

        mlastCmd = mCmd;

    }

    /***
     * 得到两点之间的弧度
     */
    public float getRad(float px1, float py1, float px2, float py2) {
        float x = px2 - px1;

        float y = py1 - py2;
        //!斜边的长
        float z = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float cosAngle = x / z;
        float rad = (float) Math.acos(cosAngle);

        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    public void onMove(RemoteListener remoteListener) {
        this.remoteListener = remoteListener;
    }

    public void setRemoteListener(RemoteListener remoteListener) {
        this.remoteListener = remoteListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public interface RemoteListener {
        void onRemoteListener(int cmd, int Speed, int PersetIndex);
    }

}

