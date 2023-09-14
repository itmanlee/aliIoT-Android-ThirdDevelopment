package com.aliIoT.demo.util;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aliIoT.demo.AlarmActivity;
import com.aliIoT.demo.R;
import com.aliIoT.demo.model.PushMessageBean;

/**
 * Created by Administrator on 2021/6/11 0011.
 */

public class AlarmDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBackView;
    ImageView mImgView;
    TextView mNameTextView;
    TextView mTypeTextView;
    TextView mContentTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        Intent intent = getIntent();
        if (intent != null) {
            PushMessageBean bean = intent.getParcelableExtra("message");
            String url = "";
            if (AlarmActivity.mAlarmEventIdPicBeanMap.get(bean.getExtData().getEventId()) != null) {
                url = AlarmActivity.mAlarmEventIdPicBeanMap.get(bean.getExtData().getEventId()).getPicUrl();
            }

            init(bean, url);
        }

    }

    public void init(PushMessageBean bean, String url) {
        mBackView = findViewById(R.id.alarm_detail_back);
        mImgView = findViewById(R.id.alarm_detail_iv);
        mNameTextView = findViewById(R.id.alarm_detail_name);
        mTypeTextView = findViewById(R.id.alarm_detail_type);
        mContentTextView = findViewById(R.id.alarm_detail_content);

        if (!TextUtils.isEmpty(url)) {
            GlideUtils.loadImage(this, url, GlideUtils.creatRequestOptions(), mImgView);
        } else {
            GlideUtils.loadImage(MyApplication.getInstance(), bean.getExtData().getIcon(), GlideUtils.creatRequestOptions(), mImgView);
        }

        mNameTextView.setText(bean.getExtData().getProductName());
        mTypeTextView.setText(bean.getTitle());
        mContentTextView.setText(bean.getBody());

        mBackView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alarm_detail_back:
                finish();
                break;
        }
    }


}
