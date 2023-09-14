package com.aliIoT.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliIoT.demo.model.ChannelEncodeBean;
import com.aliIoT.demo.model.NVRChildDeviceInfoBean;
import com.aliIoT.demo.model.TitleItemBean;
import com.aliIoT.demo.model.TransControlV2NVRBean;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.DeviceSetAdapter;
import com.aliIoT.demo.util.MyApplication;
import com.aliIoT.demo.util.OSDUtil;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.tmp.device.panel.PanelDevice;
import com.aliyun.alink.linksdk.tmp.device.panel.listener.IPanelCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 子通道信息界面，只支持nvr
 */

public class ChannelChildActivity extends AppCompatActivity implements View.OnClickListener, DeviceSetAdapter.OnItemClick {

    public static final String TAG = "ChannelChildInfoNVRFragment";
    static final int ALIYUNSERVICE_PTZ_CONTROL = 1;
    static final int ALIYUNSERVICE_PTZ_CONTROL_STOP = 2;
    static final int ALIYUNSERVICE_GET_CHANNELABILITY = 3;
    static final int ALIYUNSERVICE_DEVICE_RECORDING_PLAN = 5;
    static final int GET_NVR_CHILD_DEVICE_INFO = 6;
    ImageView mBackView;
    RecyclerView channelChannelEncodeSetLayoutRl;
    int nowSelect = 0;
    String iotID = MyApplication.getInstance().getIotID();
    Gson gson = new Gson();
    PopupWindow mSerarchWindow = null;
    private DeviceSetAdapter deviceSetAdapter;
    private ChannelEncodeBean nowChannelEncodeBean;
    private NVRChildDeviceInfoBean nvrChildDeviceInfo;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_NVR_CHILD_DEVICE_INFO:
                    JsonObject jsonObject = gson.fromJson((String) message.obj, JsonObject.class);
                    if (jsonObject.has("ResultCode")
                            && jsonObject.get("ResultCode").getAsInt() == 0) {
                        String data = jsonObject.get("Payload").getAsString();
                        try {
                            JSONObject object = new JSONObject(data);
                            String osd = object.getJSONObject("Data").toString();

                            if (!TextUtils.isEmpty(osd)) {
                                nvrChildDeviceInfo = gson.fromJson(osd, NVRChildDeviceInfoBean.class);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (nvrChildDeviceInfo != null) {
                        initItemData(nvrChildDeviceInfo);
                    } else {
                        initItemDataFail();
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_child);
        initCreat();
    }

    public void initCreat() {
        mBackView = findViewById(R.id.channel_child_back);
        channelChannelEncodeSetLayoutRl = findViewById(R.id.channel_child_rl);
        mBackView.setOnClickListener(this);

        nowSelect = 0;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        deviceSetAdapter = new DeviceSetAdapter();
        channelChannelEncodeSetLayoutRl.setLayoutManager(linearLayoutManager);
        channelChannelEncodeSetLayoutRl.setAdapter(deviceSetAdapter);
        getNVRChildDeviceInfo(iotID, nowSelect + 1);
        if (nvrChildDeviceInfo != null) {
            initItemData(nvrChildDeviceInfo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nowChannelEncodeBean = null;
    }

    private void initItemData(NVRChildDeviceInfoBean nvrChildDeviceInfo) {
        String[] stringArray = this.getResources().getStringArray(R.array.child_device_info_array);
        ArrayList<TitleItemBean> list = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            TitleItemBean m = creatTitleBean(stringArray[i], i, nvrChildDeviceInfo);
            list.add(m);
        }
        deviceSetAdapter.setData(list);
        deviceSetAdapter.setListener(this);
    }

    private void initItemDataFail() {
        ArrayList<TitleItemBean> list = new ArrayList<>();
        TitleItemBean m = creatTitleBean(this.getResources().getStringArray(R.array.child_device_info_array)[0], 0, nvrChildDeviceInfo);
        list.add(m);
        deviceSetAdapter.setData(list);
        deviceSetAdapter.setListener(this);
    }

    private TitleItemBean creatTitleBean(String s, int i, NVRChildDeviceInfoBean nvrChildDeviceInfo) {
        TitleItemBean<Object> itemBean = new TitleItemBean<>();
        if (this.getResources().getStringArray(R.array.child_device_info_array)[0].equals(s)) {
            itemBean.init(s, R.mipmap.arrow_left_white, selectToName(nowSelect), ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME);
        } else if (this.getResources().getStringArray(R.array.child_device_info_array)[1].equals(s)) {
            itemBean.init(s, 0, nvrChildDeviceInfo.getSerialNumber(), 0);
        } else if (this.getResources().getStringArray(R.array.child_device_info_array)[2].equals(s)) {
            itemBean.init(s, 0, nvrChildDeviceInfo.getHardwareVersion(), 0);
        } else if (this.getResources().getStringArray(R.array.child_device_info_array)[3].equals(s)) {
            itemBean.init(s, 0, nvrChildDeviceInfo.getSoftwareVersion(), 0);
        } else if (this.getResources().getStringArray(R.array.child_device_info_array)[4].equals(s)) {
            itemBean.init(s, 0, nvrChildDeviceInfo.getAlarmInPortNum() + "", 0);
        } else if (this.getResources().getStringArray(R.array.child_device_info_array)[5].equals(s)) {
            itemBean.init(s, 0, nvrChildDeviceInfo.getAlarmOutPortNum() + "", 0);
        } else {
            itemBean.init();
        }

        return itemBean;
    }

    @Override
    public void OnItemViewClick(TitleItemBean t, int postion) {
        switch (t.getItemType()) {
            case ConstUtil.TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME: {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < 16; i++) {
                    list.add("CH" + (i + 1));
                }

                showDialog(t.getItemType(), this.getResources().getStringArray(R.array.child_device_info_array)[0], list);
                if (!mSerarchWindow.isShowing()) {
                    showWindow(mBackView);
                } else {
                    mSerarchWindow.dismiss();
                }
                break;
            }
        }
    }

    public void editChange(String str, int mType) {
        nowSelect = nameToselect(str);
        getNVRChildDeviceInfo(iotID, nowSelect + 1);
    }

    String selectToName(int i) {
        String str = "";
        i++;
        if (i < 10) {
            str = "CH0" + i;
        } else {
            str = "CH" + i;
        }
        return str;
    }

    int nameToselect(String str) {
        String ch = str.replace("CH", "");
        return Integer.parseInt(ch) - 1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.channel_child_back:
                finish();
                break;
        }

    }

    public void getNVRChildDeviceInfo(String iotid, int childId) {
        TransControlV2NVRBean transControlV2NVRBean = OSDUtil.creatTransControlV2NVRBeanGetChildDeviceInfo(childId);
        try {
            Gson gson = new Gson();
            String s = gson.toJson(transControlV2NVRBean);
            JSONObject jsonObject = new JSONObject(s);
            aliyunService(
                    GET_NVR_CHILD_DEVICE_INFO,
                    iotid,
                    "TransControlv2",
                    jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
            case GET_NVR_CHILD_DEVICE_INFO:
                Log.e("wy", "==GET_NVR_CHILD_DEVICE_INFO==" + string);
                message.what = GET_NVR_CHILD_DEVICE_INFO;
                message.obj = string;
                handler.sendMessage(message);

                break;

        }
        return p;
    }

}
