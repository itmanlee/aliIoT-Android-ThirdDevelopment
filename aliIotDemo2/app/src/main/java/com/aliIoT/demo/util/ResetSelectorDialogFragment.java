package com.aliIoT.demo.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aliIoT.demo.R;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;


import java.lang.reflect.Field;

public class ResetSelectorDialogFragment extends DialogFragment implements View.OnClickListener {


    private View.OnClickListener onClickListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.window_background);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ali_sdk_openaccount_dialog, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(ResourceUtils.getString(getActivity(), "account_choose_find_pwd_style"));
        TextView btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setText(ResourceUtils.getString(getActivity(), "account_cancel"));
        btnCancel.setOnClickListener(this);


        TextView btnPhone = view.findViewById(R.id.btn_register_phone);
        btnPhone.setText(ResourceUtils.getString(getActivity(), "account_choose_find_pwd_style_phone"));
        btnPhone.setOnClickListener(onClickListener);
        TextView btnMail = view.findViewById(R.id.btn_register_email);
        btnMail.setText(ResourceUtils.getString(getActivity(), "account_choose_find_pwd_style_email"));
        btnMail.setOnClickListener(onClickListener);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    /**
     * 重写show()
     *
     * @param fragmentManager
     * @param tag
     * @link {@link DialogFragment#show(FragmentManager, String)
     */
    public void showAllowingStateLoss(FragmentManager fragmentManager, String tag) {
        setFiled("mDismissed", this, false);
        setFiled("mShownByMe", this, true);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * @param fragmentTransaction
     * @param tag
     * @link {@link DialogFragment#show(FragmentTransaction, String)
     */
    public void showAllowingStateLoss(FragmentTransaction fragmentTransaction, String tag) {
        setFiled("mDismissed", this, false);
        setFiled("mShownByMe", this, true);
        fragmentTransaction.add(this, tag);
        setFiled("mViewDestroyed", this, false);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void setFiled(String filed, Object object, Object values) {
        try {
            Class cls = object.getClass();
            Field field = cls.getDeclaredField(filed);
            field.setAccessible(true);
            field.set(object, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) {
            dismissAllowingStateLoss();
        }
    }
}
