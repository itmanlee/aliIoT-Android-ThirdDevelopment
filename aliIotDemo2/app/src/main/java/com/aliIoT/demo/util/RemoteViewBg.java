package com.aliIoT.demo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by wy on 2019/7/25.
 */

public class RemoteViewBg {
    private Bitmap bitmapBg;

    public RemoteViewBg(Bitmap bitmap) {
        bitmapBg = bitmap;
    }

    //!游戏背景的绘图函数
    public void draw(Canvas canvas, Paint paint, Rect src0, Rect dst0) {
        canvas.drawBitmap(bitmapBg, src0, dst0, paint);
    }
}
