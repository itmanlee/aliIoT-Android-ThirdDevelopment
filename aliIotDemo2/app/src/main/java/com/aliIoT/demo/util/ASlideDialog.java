package com.aliIoT.demo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.aliIoT.demo.R;


/**
 * Created by wuwang.djw on 2015/6/2.
 * <p/>
 * - 侧滑窗口: 左、右、上、下、居中
 * <p/>
 * - 例：
 * ASlideDialog dialog = ASlideDialog.newInstance(getActivity(), ASlideDialog.Gravity.Center, R.layout.xxx);
 * dialog.show();
 */
public class ASlideDialog extends Dialog {

    /* fields */

    private Object customContext = null;

    /* methods: Dialog */

    private ASlideDialog(Context context, int theme) {
        super(context, theme);
    }

    /* methods: I/F */

    static public ASlideDialog newInstance(Context context, Gravity gravity, int layoutResId) {

        ASlideDialog ret = new ASlideDialog(context, getStyle(gravity));
        ret.setContentView(layoutResId);
        configure(ret, gravity);

        return ret;
    }

    public void setCustomContext(Object customContext) {
        this.customContext = customContext;
    }

    public Object getCustomContext() {
        return this.customContext;
    }

    /* methods: helper */

    static private void configure(ASlideDialog dialog, Gravity gravity) {
        if (null == dialog)
            return;

        Window window = dialog.getWindow();
        window.setGravity(gravity.value);
        window.setAttributes(getLayoutParams(window.getAttributes(), gravity));
    }

    static private int getStyle(Gravity gravity) {
        int ret = R.style.ASlideDialog;

        return ret;
    }

    static private WindowManager.LayoutParams getLayoutParams(WindowManager.LayoutParams lp, Gravity gravity) {
        if (null == lp)
            return lp;

        switch (gravity) {
            case Left:
            case Right:
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                break;

            case Top:
            case Bottom:
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                break;

            case Center:
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                break;
        }

        return lp;
    }

    /* inner type */

    public enum Gravity {
        Left(android.view.Gravity.LEFT),
        Right(android.view.Gravity.RIGHT),
        Top(android.view.Gravity.TOP),
        Bottom(android.view.Gravity.BOTTOM),
        Center(android.view.Gravity.CENTER);

        public final int value;

        Gravity(int gravity) {
            this.value = gravity;
        }


    }

    public void setWindowAnimations(int resId) {
        Window window = this.getWindow();
        window.setWindowAnimations(resId);
    }
}
