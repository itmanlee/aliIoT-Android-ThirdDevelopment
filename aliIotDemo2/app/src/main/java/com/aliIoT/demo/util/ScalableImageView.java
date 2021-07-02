package com.aliIoT.demo.util;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;

/**
 * yxy 2020/2/3
 * 实现app图片加载的缩放、拖动功能；
 */
public class ScalableImageView extends android.support.v7.widget.AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "ScalableImageView";

    private int gesture;//yxy用户手势类型，是拖动还是缩放
    private static final int GESTURE_DRAG = 1;//yxy拖动图片
    private static final int GESTURE_ZOOM = 2;//yxy手势缩放图片
    private static final float MAX_SCALE = 4.0f;//  最大的缩放比例
    private float initScale = 1.0f;//初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于1
    private Matrix mMatrix = new Matrix();
    private ScaleGestureDetector mScaleGestureDetector;//yxy缩放实现对象实体类
    private int viewWidth;//yxy当前视图屏幕宽度
    private int viewHeight;//yxy当前屏幕视图高度
    private Drawable mDrawable;

    public ScalableImageView(Context context) {
        this(context, null);
    }

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //这一步很关键
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        //全局缩放比例
        float scale = getScale();
        //上一次缩放事件到当前事件的缩放比例(微分缩放比例)
        float factor = scaleGestureDetector.getScaleFactor();
        //第一次获取的factor偏小，会引起缩放手势触屏的一瞬间图片缩小
        if (resetFactor) {
            factor = 1.0f;
            resetFactor = false;
        }

        //drawable为空或者缩放比例超出范围，拒执行
        if (getDrawable() == null || scale * factor > MAX_SCALE || scale * factor < initScale) {
            return false;
        } else {
            mMatrix.postScale(factor, factor, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mMatrix);
            return true;
        }

    }

    private final float[] matrixValues = new float[9];

    //获取全局缩放比例(相对于未缩放时的缩放比例),和直接用getScaleX()有区别！
    private float getScale() {
        mMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    //缩放开始时：可用于过滤一些手势，比如从有效区以外的区域划进来的手势
    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    //缩放结束时
    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    private float mLastX;
    private float mLastY;
    private boolean resetFactor;
    private boolean onZoomFinished;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                gesture = GESTURE_DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                gesture = GESTURE_ZOOM;
                resetFactor = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                gesture = GESTURE_DRAG;
                onZoomFinished = true;
                break;
            case MotionEvent.ACTION_MOVE:
                switch (gesture) {
                    case GESTURE_DRAG:
                        // 屏蔽缩放转换为拖动时的跳动，此时dx、dy偏大。
                        if (onZoomFinished) {
                            onZoomFinished = false;
                            break;
                        }
                        int dx = (int) (x - mLastX);
                        int dy = (int) (y - mLastY);
                        mMatrix.postTranslate(dx, dy);
                        setImageMatrix(mMatrix);
                        break;
                    case GESTURE_ZOOM:
                        mScaleGestureDetector.onTouchEvent(event);
                        break;
                }
                /**
                 * 不同于scrollTo或scrollBy移动，这两个是通过改变View的mScrollX和mScrollY,然后在onDraw时平移画布的，
                 * 而通过矩阵是对图片的操作，
                 * 这里不曾调用过scrollTo或scrollBy（从View的源码中可以看到这两个方法是用来改变mScrollX和mScrollY的），
                 * 所以打印日志可以看到getX()和getScrollX()一直不变。
                 * Log.e(TAG,"getX() = "+getX()+", getScrollX() = "+getScrollX());
                 */
                break;
            case MotionEvent.ACTION_UP:
                // 消除缩放造成的图片距视图边的空白
                final PointF delta = amendDelta(0, 0);
                int springBackX = Math.round(delta.x);
                int springBackY = Math.round(delta.y);
                if (springBackX != 0 || springBackY != 0) {
                    Message msg = Message.obtain(mHandler,SPRING_BACK,springBackX,springBackY);
                    mHandler.sendMessage(msg);
                }
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private static final int SPRING_BACK = 0;
    private static final int COUNTS = 25;
    private static final int DELAY_MILLIS = 2;

    private int mCount;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SPRING_BACK: {
                    mCount++;
                    if (mCount <= COUNTS) {
                        //使用浮点数，避免回弹后出现间隙
                        mMatrix.postTranslate(msg.arg1*1.0f/COUNTS, msg.arg2*1.0f/COUNTS);
                        setImageMatrix(mMatrix);
                        Message next = Message.obtain(mHandler,SPRING_BACK,msg.arg1,msg.arg2);
                        mHandler.sendMessageDelayed(next, DELAY_MILLIS);
                    } else {
                        mCount = 0;
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();

    }

    private PointF amendDelta(float dx, float dy) {
        RectF rectF = getRectF();

        if (rectF.width() > viewWidth) { //图片宽度超过视图宽度
            if (rectF.left + dx > 0) { //拖动会引起图片左边出现空白
                dx = -rectF.left;
            } else if (rectF.right + dx < viewWidth) { //拖动会引起图片右边出现空白
                dx = viewWidth - rectF.right;
            }
        } else { //图片宽度不及视图宽度,不允许图片宽度方向可视区域离开边界
            if (rectF.left + dx < 0) {
                dx = -rectF.left;
            } else if (rectF.right + dx > viewWidth) {
                dx = viewWidth - rectF.right;
            }
        }

        if (rectF.height() > viewHeight) {
            if (rectF.top + dy > 0) {
                dy = -rectF.top;
            } else if (rectF.bottom + dy < viewHeight) {
                dy = viewHeight - rectF.bottom;
            }
        } else {
            if (rectF.top + dy < 0) {
                dy = -rectF.top;
            } else if (rectF.bottom + dy > viewHeight) {
                dy = viewHeight - rectF.bottom;
            }
        }
        return new PointF(dx, dy);
    }

    private RectF mRectF;

    public RectF getRectF() {
        if (mDrawable == null) {
            mDrawable = getDrawable();
        }
        if (mRectF == null) {
            mRectF = new RectF();
        }
        if (mDrawable != null) {
            mRectF.set(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
            mMatrix.mapRect(mRectF);
        }
        return mRectF;
    }

    //onGlobalLayoutListener可能会多次触发
    private boolean isFirstTime = true;

    //观察布局变化，目的是获取View的尺寸，在测量之前执行onMeasure getWidth()和getHeight()可能为0
    @Override
    public void onGlobalLayout() {
        if (isFirstTime) {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            isFirstTime = false;
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            viewWidth = getWidth();
            viewHeight = getHeight();
            //图片长宽超出View的可见范围的处理
            if (drawableWidth > viewWidth || drawableHeight > viewHeight) {
                initScale = Math.min(viewWidth * 1.0f / drawableWidth, viewHeight * 1.0f / drawableHeight);
            }
            //将图片偏移到中心位置
            mMatrix.postTranslate((viewWidth - drawableWidth) / 2, (viewHeight - drawableHeight) / 2);
            //初始化缩放
            mMatrix.postScale(initScale, initScale, viewWidth / 2, viewHeight / 2);
            setImageMatrix(mMatrix);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

}
