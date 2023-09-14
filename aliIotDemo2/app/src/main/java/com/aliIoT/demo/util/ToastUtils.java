package com.aliIoT.demo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.R;

import java.util.HashMap;
import java.util.Map;

public class ToastUtils {
    private static ToastUtils mToastUtils = null;
    Handler mainHandle = new Handler((Looper.getMainLooper()));
    private Map<Context, String> map = new HashMap<>();
    private Toast toast;

    public static ToastUtils getToastUtils() {
        if (mToastUtils == null)
            mToastUtils = new ToastUtils();
        return mToastUtils;
    }

    public void addContext(Context c) {
        if (c != null)
            map.put(c, "");
    }

    public void removeContext(Context c) {
        if (c != null) {
            map.remove(c);
        }
    }

    public void showToast(Context c, String content) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (c != null && !TextUtils.isEmpty(content)) {
                if (map.get(c) != null) {
                    if (toast != null)
                        toast.cancel();
                    View layout = LayoutInflater.from(c).inflate(R.layout.toast_xml, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText(content);
                    toast = new Toast(c);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        } else {
            mainHandle.post(new Runnable() {
                @Override
                public void run() {
                    if (c != null && !TextUtils.isEmpty(content)) {
                        if (map.get(c) != null) {
                            if (toast != null)
                                toast.cancel();
                            View layout = LayoutInflater.from(c).inflate(R.layout.toast_xml, null);
                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setText(content);
                            toast = new Toast(c);
                            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }
                    }
                }
            });
        }
    }

    public void showToast(Context c, String content, int time) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (c != null && !TextUtils.isEmpty(content)) {
                if (map.get(c) != null) {
                    if (toast != null)
                        toast.cancel();
                    View layout = LayoutInflater.from(c).inflate(R.layout.toast_xml, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText(content);
                    toast = new Toast(c);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.setDuration(time);
                    toast.show();
                }
            }
        }
    }

    /**
     * 解决小米系统提示多包名的问题
     *
     * @param context
     * @param text
     */
    public void makeText(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }
}
