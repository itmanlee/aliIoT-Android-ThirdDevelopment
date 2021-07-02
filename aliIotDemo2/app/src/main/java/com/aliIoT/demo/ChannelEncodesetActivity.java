package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.model.AliyunChannelEncodeBean;
import com.aliIoT.demo.model.ChannelEcondeAbilityBean;
import com.aliIoT.demo.model.ChannelEncodeBean;
import com.aliIoT.demo.model.TitleItemBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.DeviceSetAdapter;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.ToastUtils;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 编码配置界面
 */
public class ChannelEncodesetActivity extends AppCompatActivity implements View.OnClickListener, DeviceSetAdapter.OnItemClick {

    public static final String TAG = "ChannelEncodeSet";
    ImageView mBackView;
    TextView mSaveView;
    RecyclerView channelChannelEncodeSetLayoutRl;
    private DeviceSetAdapter deviceSetAdapter;
    //    private Map<String, ChannelEncodeBean> map;
    private ChannelEncodeBean nowChannelEncodeBean;
    //private ChannelEncodeSetDialogFragment mChannelEncodeSetDialogFragment;
    //private CustomBiteDialogFragment customBiteDialogFragment;

    private ChannelEcondeAbilityBean mChannelEcondeAbilityBeanMain;
    private ChannelEcondeAbilityBean mChannelEcondeAbilityBeanChild;

    Gson gson = new Gson();
    String iotID = MyApplication.getInstance().getIotID();

    int method = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    static final int ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN = 3;
    static final int ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD = 4;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN: {
                    AliyunChannelEncodeBean mAliyunChannelEncodeBean = null;
                    if (method == 2) {
                        Toast.makeText(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.set_encode_success), Toast.LENGTH_LONG).show();
                        //ToastUtils.getToastUtils().showToast(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.set_encode_success));
                    } else {
                        //  mAliyunChannelEncodeBean = data.getParcelable(HttpTypeSource.REQUEST_RESULT_SUCCESS_MSG);

                        mAliyunChannelEncodeBean = gson.fromJson((String) message.obj, AliyunChannelEncodeBean.class);
                        ChannelEncodeBean channelEncodeBean = new ChannelEncodeBean();
                        channelEncodeBean.setIotid(iotID);
                        channelEncodeBean.setmAliyunChannelEncodeBean(mAliyunChannelEncodeBean);

                        nowChannelEncodeBean = channelEncodeBean;
                        update(channelEncodeBean);
                    }
                    break;
                }
                case ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD: {
                    AliyunChannelEncodeBean mAliyunChannelEncodeBean = null;
                    if (method == 2) {
                        ToastUtils.getToastUtils().showToast(MyApplication.getInstance(), MyApplication.getInstance().getResources().getString(R.string.set_encode_success));
                    } else {
                        // mAliyunChannelEncodeBean = data.getParcelable(HttpTypeSource.REQUEST_RESULT_SUCCESS_MSG);

                        mAliyunChannelEncodeBean = gson.fromJson((String) message.obj, AliyunChannelEncodeBean.class);
                        ChannelEncodeBean channelEncodeBean = new ChannelEncodeBean();
                        channelEncodeBean.setIotid(iotID);
                        channelEncodeBean.setmAliyunChannelEncodeBean(mAliyunChannelEncodeBean);
                        nowChannelEncodeBean = channelEncodeBean;
                        update(channelEncodeBean);
                    }
                    break;
                }
            }
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_encode);
        Intent intent = getIntent();
        if (intent != null) {
            mChannelEcondeAbilityBeanMain = intent.getParcelableExtra("main");
            mChannelEcondeAbilityBeanChild = intent.getParcelableExtra("child");

            Log.e("wy", "==mChannelEcondeAbilityBeanMain=" + gson.toJson(mChannelEcondeAbilityBeanMain));
            Log.e("wy", "==mChannelEcondeAbilityBeanChild=" + gson.toJson(mChannelEcondeAbilityBeanChild));
        }

        mBackView = findViewById(R.id.channel_channel_encode_back);
        mSaveView = findViewById(R.id.channel_channel_encode_save);
        channelChannelEncodeSetLayoutRl = findViewById(R.id.channel_channel_encode_set_layout_rl);

        mBackView.setOnClickListener(this);
        mSaveView.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        deviceSetAdapter = new DeviceSetAdapter();
        channelChannelEncodeSetLayoutRl.setLayoutManager(linearLayoutManager);
        channelChannelEncodeSetLayoutRl.setAdapter(deviceSetAdapter);
        deviceSetAdapter.setListener(this);
        if (nowChannelEncodeBean != null) {
            initItemData(nowChannelEncodeBean);
        } else {
            getDeviceEcode(iotID, ConstUtil.STREAMTYPE_MAIN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nowChannelEncodeBean = null;
    }

    private void initItemData(ChannelEncodeBean mChannelEncodeBean) {
        String[] stringArray = getResources().getStringArray(R.array.encode_set_array);
        ArrayList<TitleItemBean> list = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            TitleItemBean m = creatTitleBean(stringArray[i], i, mChannelEncodeBean);
            list.add(m);
        }
        deviceSetAdapter.setData(list);
    }

    private TitleItemBean creatTitleBean(String s, int i, ChannelEncodeBean nowChannelEncodeBean) {
        TitleItemBean<Object> itemBean = new TitleItemBean<>();
        if (getResources().getStringArray(R.array.encode_set_array)[0].equals(s)) {
            itemBean.init(s, 0, nowChannelEncodeBean.getChannelName(), ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME);
        } else if (getResources().getStringArray(R.array.encode_set_array)[1].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().streamTypeToString(), ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCIDE_SET);
        } else if (getResources().getStringArray(R.array.encode_set_array)[2].equals(s)) { //yxy初始化“码流类型”
//            itemBean.init(s, R.mipmap.arrow_right_gray, nowChannelEncodeBean.getAliyunChannelEncodeBean().avTypeToString(getMyParentFragment().getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()))
//                    , IntegerConstantResource.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CODE_STREAM_TYPE);

            String abilityEN = nowChannelEncodeBean.getAliyunChannelEncodeBean().avTypeToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
            String abilityCN = "";
            if (abilityEN.equals("Main Stream")) { //视频流
                abilityCN = getResources().getString(R.string.av_type_video);
            } else if (abilityEN.equals("Aux Stream")) { //复合流
                abilityCN = getResources().getString(R.string.av_type_media);
            }
            itemBean.init(s, R.mipmap.arrow_left_white, abilityCN, ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CODE_STREAM_TYPE);
        } else if (getResources().getStringArray(R.array.encode_set_array)[3].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().resolutionToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()))
                    , ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_RESOLUTION_RATIO);
        } else if (getResources().getStringArray(R.array.encode_set_array)[4].equals(s)) {
            String abilityEN = nowChannelEncodeBean.getAliyunChannelEncodeBean().bitTypeToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
            String abilityCN = "";
            if (abilityEN.equals("VBR")) { //变码率
                abilityCN = getResources().getString(R.string.str_vbr);
            } else if (abilityEN.equals("CBR")) { //定码率
                abilityCN = getResources().getString(R.string.str_cbr);
            }

            itemBean.init(s, R.mipmap.arrow_left_white, abilityCN, ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_TYPE);
        } else if (getResources().getStringArray(R.array.encode_set_array)[5].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().bitRateToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()))
                    , ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_LIMIT);
        } else if (getResources().getStringArray(R.array.encode_set_array)[6].equals(s)) {
            String abilityEN = nowChannelEncodeBean.getAliyunChannelEncodeBean().frameRateToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
            String abilityCN = "";
            if (abilityEN.equals("FULL")) { //全帧率
                abilityCN = getResources().getString(R.string.str_frame_full);
            } else {
                abilityCN = abilityEN;
            }
            itemBean.init(s, R.mipmap.arrow_left_white, abilityCN, ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_FRAME_RATE);
        } else if (getResources().getStringArray(R.array.encode_set_array)[7].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().picQualityToString()
                    , ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_IMAGE_QUALITY);
        } else if (getResources().getStringArray(R.array.encode_set_array)[8].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().videoEncTypeToString(getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()))
                    , ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCODE_TYPE);
        } else if (getResources().getStringArray(R.array.encode_set_array)[9].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, nowChannelEncodeBean.getAliyunChannelEncodeBean().iFrameIntervalToString()
                    , ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_I_FRAME);
        } else {
            itemBean.init();
        }
        return itemBean;
    }

    public void update(ChannelEncodeBean bean) {
        if (bean != null) {
            initItemData(bean);
        }
    }

    @Override
    public void OnItemViewClick(TitleItemBean t, int postion) {
        if (nowChannelEncodeBean.getAliyunChannelEncodeBean() == null) {
            return;
        }
        try {
            switch (t.getItemType()) {
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME: {
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCIDE_SET: {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(getResources().getString(R.string.stream_type_main));
                    list.add(getResources().getString(R.string.stream_type_child));
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[1], list);

                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CODE_STREAM_TYPE: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getAVTypeMap().values());
                    List<String> listCN = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        String abilityEN = list.get(i);
                        String abilityCN = "";
                        if (abilityEN.equals("Main Stream")) { //视频流
                            abilityCN = getResources().getString(R.string.av_type_video);
                        } else if (abilityEN.equals("Aux Stream")) { //复合流
                            abilityCN = getResources().getString(R.string.av_type_media);
                        }
                        listCN.add(abilityCN);
                    }
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[2], listCN);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_RESOLUTION_RATIO: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getResolutionMap().values());
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[3], list);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_TYPE: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getBitTypeMap().values());

                    List<String> listCN = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        String abilityEN = list.get(i);
                        String abilityCN = "";
                        if (abilityEN.equals("VBR")) { //变码率
                            abilityCN = getResources().getString(R.string.str_vbr);
                        } else if (abilityEN.equals("CBR")) { //定码率
                            abilityCN = getResources().getString(R.string.str_cbr);
                        }
                        listCN.add(abilityCN);
                    }
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[4], listCN);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_LIMIT: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getBitRateMap().values());
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[5], list);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_FRAME_RATE: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getFrameRateMap().values());

                    List<String> listCN = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        String abilityEN = list.get(i);
                        String abilityCN = "";
                        if (abilityEN.equals("FULL")) { //全帧率
                            abilityCN = getResources().getString(R.string.str_frame_full);
                        } else { //定码率
                            abilityCN = list.get(i);
                        }
                        listCN.add(abilityCN);
                    }
                    //chooseChannelFragment(t.getItemType(), mActivity.getResources().getStringArray(R.array.encode_set_array)[6], list);
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[6], listCN);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_IMAGE_QUALITY: {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(getResources().getString(R.string.pic_quality_0));
                    list.add(getResources().getString(R.string.pic_quality_1));
                    list.add(getResources().getString(R.string.pic_quality_2));
                    list.add(getResources().getString(R.string.pic_quality_3));
                    list.add(getResources().getString(R.string.pic_quality_4));
                    list.add(getResources().getString(R.string.pic_quality_5));
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[7], list);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCODE_TYPE: {
                    ArrayList<String> list = new ArrayList<>();
                    ChannelEcondeAbilityBean ecodeAbility = getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType());
                    list.addAll(ecodeAbility.getVideoEncTypeMap().values());
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[8], list);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_I_FRAME: {
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 10; i < 101; i++) {
                        list.add(i + "");
                    }
                    showDialog(t.getItemType(), getResources().getStringArray(R.array.encode_set_array)[9], list);
                    if (!mSerarchWindow.isShowing()) {
                        showWindow(mSaveView);
                    } else {
                        mSerarchWindow.dismiss();
                    }
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    private void chooseChannelFragment(int id, String title, List<String> list) {
//        if (mChannelEncodeSetDialogFragment == null) {
//            mChannelEncodeSetDialogFragment = new ChannelEncodeSetDialogFragment();
//        }
//        if (!FragmentCheckUtil.dialogFragmentIsShow(mChannelEncodeSetDialogFragment) && getChildManager().findFragmentByTag(ChannelEncodeSetDialogFragment.TAG) == null) {
//            mChannelEncodeSetDialogFragment.initData(id, title, list);
//            mChannelEncodeSetDialogFragment.show(getChildManager(), ChannelEncodeSetDialogFragment.TAG);
//        }
    }

    public void editChange(String str, int mType) {
        if (nowChannelEncodeBean != null) {
            switch (mType) {
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME: {
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCIDE_SET: {
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType() != nowChannelEncodeBean.getAliyunChannelEncodeBean().streamTypeToInt(str)) {
                        getDeviceEcode(nowChannelEncodeBean.getIotid(), nowChannelEncodeBean.getAliyunChannelEncodeBean().streamTypeToInt(str));
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CODE_STREAM_TYPE: {
                    String abilityEN = "";
                    if (str.equals(getResources().getString(R.string.av_type_video))) {
                        abilityEN = "Main Stream";
                    } else if (str.equals(getResources().getString(R.string.av_type_media))) {
                        abilityEN = "Aux Stream";
                    }
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .avTypeToInt(abilityEN, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getAVType() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setAVType(i);
                        initItemData(nowChannelEncodeBean);
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_RESOLUTION_RATIO: {
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .resolutionToInt(str, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getResolution() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setResolution(i);
                        initItemData(nowChannelEncodeBean);
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_TYPE: {
                    String abilityEN = "";
                    if (str.equals(getResources().getString(R.string.str_vbr))) {
                        abilityEN = "VBR";
                    } else if (str.equals(getResources().getString(R.string.str_cbr))) {
                        abilityEN = "CBR";
                    }
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .bitTypeToInt(abilityEN, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getBitType() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setBitType(i);
                        initItemData(nowChannelEncodeBean);
                    }

                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_LIMIT: {
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .bitRateToInt(str, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (i == -1) {
                        //creatCustomBiteDialogFragment(getMyParentFragment().getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    } else {
                        if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getBitrate() != i) {
                            nowChannelEncodeBean.getAliyunChannelEncodeBean().setBitrate(i);
                            initItemData(nowChannelEncodeBean);
                        }
                    }

                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_FRAME_RATE: {
                    String abilityEN = "";
                    if (str.equals(getResources().getString(R.string.str_frame_full))) { //全帧率
                        abilityEN = "FULL";
                    } else {
                        abilityEN = str;
                    }
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .frameRateToInt(abilityEN, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getFrameRate() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setFrameRate(i);
                        initItemData(nowChannelEncodeBean);
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_IMAGE_QUALITY: {
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .picQualityToInt(str);
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getPicQuality() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setPicQuality(i);
                        initItemData(nowChannelEncodeBean);
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCODE_TYPE: {
                    int i = nowChannelEncodeBean.getAliyunChannelEncodeBean()
                            .videoEncTypeToInt(str, getEcodeAbility(nowChannelEncodeBean.getAliyunChannelEncodeBean().getStreamType()));
                    if (nowChannelEncodeBean.getAliyunChannelEncodeBean().getVideoEncType() != i) {
                        nowChannelEncodeBean.getAliyunChannelEncodeBean().setVideoEncType(i);
                        initItemData(nowChannelEncodeBean);
                    }
                    break;
                }
                case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_I_FRAME: {
                    nowChannelEncodeBean.getAliyunChannelEncodeBean().setIFrameInterval(Integer.parseInt(str));
                    initItemData(nowChannelEncodeBean);
                }
                break;
            }

        }
    }

    public void customBite(int s) {
        nowChannelEncodeBean.getAliyunChannelEncodeBean().setBitrate(-1);
        nowChannelEncodeBean.getAliyunChannelEncodeBean().setInBit(s);
        initItemData(nowChannelEncodeBean);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.channel_channel_encode_back:
                finish();
                break;
            case R.id.channel_channel_encode_save:
                if (nowChannelEncodeBean != null) {
                    AliyunChannelEncodeBean aliyunChannelEncodeBean = nowChannelEncodeBean.getAliyunChannelEncodeBean();
                    if (aliyunChannelEncodeBean != null && aliyunChannelEncodeBean.getBitrate() < 0 && aliyunChannelEncodeBean.getInBit() <= 0) {
                        aliyunChannelEncodeBean.setInBit(aliyunChannelEncodeBean.bitRateToInbit());
                        aliyunChannelEncodeBean.setBitrate(-1);
                    }
                    Log.e("wy", "setDeviceEncode");
                    setDeviceEncode(iotID, aliyunChannelEncodeBean);

                }
                break;
        }
    }

    public void getDeviceEcode(String deviceId, int streamType) {
        method = 1;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", 1);
            jsonObject.put("StreamType", streamType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (streamType == ConstUtil.STREAMTYPE_MAIN) {
            aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN, deviceId
                    , "EncodeConfig", jsonObject);
        } else {
            aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD, deviceId
                    , "EncodeConfig", jsonObject);
        }

    }

    public void setDeviceEncode(String iotid, AliyunChannelEncodeBean aliyunChannelEncodeBean) {

        try {
            method = 2;
            String s = gson.toJson(aliyunChannelEncodeBean);
            JSONObject jsonObject = new JSONObject(s);
            jsonObject.put("Method", 2);
            if (aliyunChannelEncodeBean.getStreamType() == ConstUtil.STREAMTYPE_MAIN) {
                boolean b = aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN, iotid
                        , "EncodeConfig", jsonObject);
            } else {
                boolean b = aliyunService(ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD, iotid
                        , "EncodeConfig", jsonObject);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ChannelEcondeAbilityBean getEcodeAbility(int streamType) {
        if (streamType == ConstUtil.STREAMTYPE_MAIN) {
            return mChannelEcondeAbilityBeanMain;
        } else {
            return mChannelEcondeAbilityBeanChild;
        }
    }

    PopupWindow mSerarchWindow = null;

    public void showDialog(int mType, String title, List<String> lists) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.realplay_light_pup, null);

        mSerarchWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mSerarchWindow.setFocusable(true);
        mSerarchWindow.setOutsideTouchable(true);

        TextView textView = view.findViewById(R.id.light_config_control_title);
        ListView mListView = view.findViewById(R.id.light_config_control_layout_rv);

        textView.setText(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editChange(lists.get(i), mType);
                mSerarchWindow.dismiss();
            }
        });
    }

    public void showWindow(View view) {
        mSerarchWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public boolean aliyunService(int serviceType, String mDeviceId, String
            identifier, JSONObject json) {
        try {
            // Log.e(TAG, "aliyunService=" + "**identifier=" + identifier + "***json=" + json);
            PanelDevice panelDevice = new PanelDevice(mDeviceId);
            JSONObject object = new JSONObject();
            object.put("iotId", mDeviceId);
            object.put("identifier", identifier);
            if (json != null)
                object.put("args", json);
            else {
                object.put("args", new JSONObject());
            }
            panelDevice.invokeService(object.toString(), new IPanelCallback() {
                @Override
                public void onComplete(boolean b, @Nullable Object o) {

                    if (o instanceof CmpError) {

                    } else {
                        try {
                            //Log.e(TAG, "onComplete=" + ((String) o));
                            if (b) {
                                JSONObject jsonObject = new JSONObject(o.toString());
                                if (jsonObject.has("code") && jsonObject.getInt("code") == 200) {
                                    if (serviceType != ALIYUNSERVICE_PTZ_CONTROL && serviceType != ALIYUNSERVICE_PTZ_CONTROL_STOP) {
                                        String string = jsonObject.getString("data");

                                        int checkResult = checkResultCode(string);
                                        if (checkResult == 0) {
                                            Parcelable p = resultFromat(serviceType, string);
                                        } else {

                                        }
                                    }

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();

            return false;

        }
        return true;
    }

    private int checkResultCode(String string) {
        int i = 0;
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.has("ResultCode")) {
                i = jsonObject.getInt("ResultCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }

    private Parcelable resultFromat(int serviceType, String string) {
        Parcelable p = null;
        Message message = handler.obtainMessage();
        switch (serviceType) {
            case ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN:
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN==" + string);
                message.what = ALIYUNSERVICE_DEVICE_ENCODE_SET_MAIN;
                message.obj = string;
                handler.sendMessage(message);

                break;
            case ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD:
                Log.e("wy", "==ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD==" + string);
                message.what = ALIYUNSERVICE_DEVICE_ENCODE_SET_CHILD;
                message.obj = string;
                handler.sendMessage(message);

                break;

        }
        return p;
    }

}
