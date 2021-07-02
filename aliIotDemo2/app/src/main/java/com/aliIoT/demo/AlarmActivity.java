package com.aliIoT.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliIoT.demo.model.AlarmEventIdPicBean;
import com.aliIoT.demo.model.PushMessageBean;
import com.aliIoT.demo.util.AlarmDetailActivity;
import com.aliIoT.demo.util.AlarmMessageAdapter;
import com.aliIoT.demo.util.ConstUtil;
import com.aliIoT.demo.util.HttpUtils;
import com.aliIoT.demo.util.MyApplication;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 报警消息界面
 */

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    TextView alarmLayoutTitleLeft;
    TextView alarmLayoutTitleRight;
    TextView alarmLayoutTitle;
    ConstraintLayout alarmLayoutTitleCl;
    TextView alarmLayoutTypeFiltrate;
    ConstraintLayout alarmLayoutTypeFiltrateLy;
    TextView alarmLayoutDeviceFiltrate;
    ConstraintLayout alarmLayoutDeviceFiltrateLy;
    RecyclerView alarmLayoutRv;
    ImageView alarmLayoutOperationRead;
    ImageView alarmLayoutRead;
    ImageView alarmLayoutOperationDelet;
    ImageView alarmLayoutDelete;
    public static final int TYPE_SHOW = 0;
    public static final int TYPE_EDIT = 1;
    public static final int TYPE_EVENT = 0;
    public static final int TYPE_DEVICE = 1;
    int nowType = TYPE_SHOW;
    ConstraintLayout alarmLayoutOperationCl;
    ConstraintLayout alarmLayoutReadDelete;
    SwipeRefreshLayout alarmLayoutSwipeRefresh;
    private AlarmMessageAdapter alarmMessageAdapter;
    private LinkedHashMap<String, String> mDeviceNameMap;
    private List<String> mAlarmEventList;


    List<PushMessageBean> listForDeviceIdAndEvevtType = new ArrayList<>();
    public static Map<String, AlarmEventIdPicBean> mAlarmEventIdPicBeanMap = new HashMap<>();

    public static final int GET_ALARM_MESSAGE_LIST = 1;
    public static final int GET_ALARM_PIC_LIST = 2;
    public static final int GET_ALARM_MESSAGE_LIST_MORE = 3;
    public static final int GET_ALARM_MESSAGE_LIST_DELETE = 4;
    public static final int GET_ALARM_MESSAGE_LIST_READ = 5;


    String iotID = MyApplication.getInstance().getIotID();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_ALARM_MESSAGE_LIST: {

                    if (message.arg1 == 1) {
                        String stringData = (String) message.obj;
                        try {
                            JSONObject jsonObject = new JSONObject(stringData);
                            if (jsonObject.has("nextToken")) {
                                nextToken = jsonObject.getInt("nextToken");
                            }
                            if (jsonObject.has("maxResults")) {
                                maxResults = jsonObject.getInt("maxResults");
                            }
                            if (jsonObject.has("data")) {
                                String string = jsonObject.getString("data");
                                Gson gson = new Gson();
                                JsonElement jsonElement = gson.fromJson(string, JsonElement.class);
                                if (jsonElement.isJsonArray()) {
                                    JsonArray asJsonArray = jsonElement.getAsJsonArray();
                                    Iterator<JsonElement> iterator = asJsonArray.iterator();

                                    // mPushMessageMap.clear();
                                    HashMap<String, Map<String, String>> mHashMap = new HashMap<>();
                                    ArrayList<String> eventLists = new ArrayList<>();
                                    while (iterator.hasNext()) {
                                        PushMessageBean pushMessageBean = gson.fromJson(iterator.next(), PushMessageBean.class);
                                        if (pushMessageBean != null) {
                                            try {
                                                if (!TextUtils.isEmpty(pushMessageBean.getExtData().getExtParam())) {
                                                    pushMessageBean.getExtData().initExtParam();
                                                }
                                            } catch (Exception e) {
                                                continue;
                                            }

                                            listForDeviceIdAndEvevtType.add(pushMessageBean);
                                            eventLists.add(pushMessageBean.getExtData().getEventId());
                                        }
                                    }
                                    queryPic(iotID, eventLists);
                                    setAlarmMessageData();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String res = (String) message.obj;
                        Toast.makeText(AlarmActivity.this, res, Toast.LENGTH_LONG).show();
                    }

                    break;
                }
                case GET_ALARM_PIC_LIST:
                    if (message.arg1 == 1) {
                        String stringData = (String) message.obj;
                        try {
                            JSONObject jsonObject = new JSONObject(stringData);
                            if (jsonObject.has("pictureList")) {
                                JSONArray pictureList = jsonObject.getJSONArray("pictureList");
                                Gson gson = new Gson();
                                for (int i = 0; i < pictureList.length(); i++) {
                                    JSONObject jsonObject1 = pictureList.getJSONObject(i);
                                    AlarmEventIdPicBean alarmEventIdPicBean = gson.fromJson(jsonObject1.toString(), AlarmEventIdPicBean.class);
                                    if (!TextUtils.isEmpty(alarmEventIdPicBean.getPicUrl())) {
                                        mAlarmEventIdPicBeanMap.put(alarmEventIdPicBean.getEventId(), alarmEventIdPicBean);
                                    }
                                }
                                setAlarmMessageData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String res = (String) message.obj;
                        Toast.makeText(AlarmActivity.this, res, Toast.LENGTH_LONG).show();
                    }

                    break;
                case GET_ALARM_MESSAGE_LIST_DELETE:
                    if (message.arg1 == 1) {
                        Toast.makeText(AlarmActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    } else {
                        String res = (String) message.obj;
                        Toast.makeText(AlarmActivity.this, res, Toast.LENGTH_LONG).show();
                    }
                    break;
                case GET_ALARM_MESSAGE_LIST_READ:
                    if (message.arg1 == 1) {
                        Toast.makeText(AlarmActivity.this, "已读成功", Toast.LENGTH_LONG).show();
                    } else {
                        String res = (String) message.obj;
                        Toast.makeText(AlarmActivity.this, res, Toast.LENGTH_LONG).show();
                    }
                    break;
            }

            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initCreat();
    }

    public void initCreat() {
        alarmLayoutTitleLeft = findViewById(R.id.alarm_layout_title_left);
        alarmLayoutTitleRight = findViewById(R.id.alarm_layout_title_right);
        alarmLayoutTitle = findViewById(R.id.alarm_layout_title);
        alarmLayoutTitleCl = findViewById(R.id.alarm_layout_title_cl);
        alarmLayoutTypeFiltrate = findViewById(R.id.alarm_layout_type_filtrate);
        alarmLayoutTypeFiltrateLy = findViewById(R.id.alarm_layout_type_filtrate_ly);
        alarmLayoutDeviceFiltrate = findViewById(R.id.alarm_layout_device_filtrate);
        alarmLayoutDeviceFiltrateLy = findViewById(R.id.alarm_layout_device_filtrate_ly);
        alarmLayoutRv = findViewById(R.id.alarm_layout_rv);
        alarmLayoutSwipeRefresh = findViewById(R.id.alarm_layout_sl);
        alarmLayoutOperationRead = findViewById(R.id.alarm_layout_operation_read);
        alarmLayoutRead = findViewById(R.id.alarm_layout_read);
        alarmLayoutOperationDelet = findViewById(R.id.alarm_layout_operation_delet);
        alarmLayoutDelete = findViewById(R.id.alarm_layout_delete);
        alarmLayoutReadDelete = findViewById(R.id.alarm_layout_read_delete);

        String[] array = getResources().getStringArray(R.array.alarm_event_array);
        mAlarmEventList = Arrays.asList(array);
        alarmLayoutTitleLeft.setOnClickListener(this);
        alarmLayoutTitleRight.setOnClickListener(this);
        alarmLayoutTypeFiltrateLy.setOnClickListener(this);
        alarmLayoutDeviceFiltrateLy.setOnClickListener(this);
        alarmLayoutOperationRead.setOnClickListener(this);
        alarmLayoutOperationDelet.setOnClickListener(this);
        alarmLayoutRead.setOnClickListener(this);
        alarmLayoutDelete.setOnClickListener(this);
        alarmLayoutSwipeRefresh.setOnRefreshListener(this);

        alarmLayoutSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if (nowType == TYPE_SHOW) {
            //alarmLayoutOperationCl.setVisibility(View.GONE);
            alarmLayoutReadDelete.setVisibility(View.GONE);//yxy隐藏已读、删除
            alarmLayoutTitle.setVisibility(View.VISIBLE);//yxy显示标题
        }
        alarmMessageAdapter = new AlarmMessageAdapter();
        alarmMessageAdapter.setType(nowType);
        alarmMessageAdapter.setClick(new AlarmMessageAdapter.AlarmMessageAdapterLongClick() {

            @Override
            public void selsectChange() {
                changeTextSelectOrNo();
            }

            @Override
            public void adapeterClick(PushMessageBean mPushMessageBean) {
                creatAlarmParticularsFragment(mPushMessageBean);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alarmLayoutRv.setLayoutManager(linearLayoutManager);
        alarmLayoutRv.setAdapter(alarmMessageAdapter);
        alarmLayoutRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int mLastVisibleItemPosition = 0;
                if (layoutManager instanceof LinearLayoutManager) {
                    mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (alarmMessageAdapter != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && mLastVisibleItemPosition + 1 == alarmMessageAdapter.getItemCount()
                            && (((LinearLayoutManager) layoutManager).getItemCount() > ((LinearLayoutManager) layoutManager).getChildCount())) {
                        //发送网络请求获取更多数据
                        getMoreAlarmMessageData();
                    }
                }
            }
        });
        onRefresh();
    }

    private void changeTextSelectOrNo() {
        if (nowType == TYPE_SHOW) return;
        boolean b = alarmMessageAdapter.checkIsSelectAll();
        if (b) {
            alarmLayoutTitleLeft.setText(getResources().getString(R.string.all_select_no));
        } else {
            alarmLayoutTitleLeft.setText(getResources().getString(R.string.all_select));
        }
    }

    private void creatAlarmParticularsFragment(PushMessageBean mPushMessageBean) {
        Intent intent = new Intent(this, AlarmDetailActivity.class);
        intent.putExtra("message", mPushMessageBean);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alarm_layout_title_left: {
                if (nowType == TYPE_EDIT) {
                    if (getResources().getString(R.string.all_select_no).equals(alarmLayoutTitleLeft.getText().toString())) {
                        alarmMessageAdapter.setSelectAllNo();
                    } else {
                        alarmMessageAdapter.setSelectAll();
                    }
                    changeTextSelectOrNo();
                }
                break;
            }
            case R.id.alarm_layout_title_right: {
                if (nowType == TYPE_SHOW) {
                    nowType = TYPE_EDIT;
                    alarmLayoutTitleLeft.setText(getResources().getString(R.string.all_select));
                    alarmLayoutTitleRight.setText(getResources().getString(R.string.complete));
                    //alarmLayoutOperationCl.setVisibility(View.VISIBLE);
                    alarmLayoutReadDelete.setVisibility(View.VISIBLE);
                    alarmLayoutTitle.setVisibility(View.GONE);
                } else {
                    nowType = TYPE_SHOW;
                    alarmLayoutTitleLeft.setText("");
                    alarmLayoutTitleRight.setText(getResources().getString(R.string.compile));
                    //alarmLayoutOperationCl.setVisibility(View.GONE);
                    alarmLayoutReadDelete.setVisibility(View.GONE);
                    alarmLayoutTitle.setVisibility(View.VISIBLE);
                }
                if (alarmMessageAdapter != null) {
                    alarmMessageAdapter.setType(nowType);
                }
                break;
            }
            case R.id.alarm_layout_type_filtrate_ly: {

                break;
            }
            case R.id.alarm_layout_device_filtrate_ly: {
                break;
            }
            case R.id.alarm_layout_operation_read:
            case R.id.alarm_layout_read: {  //yxy已读
                if (alarmMessageAdapter != null) {
                    List<String> selectList = alarmMessageAdapter.getSelectList();

                    if (selectList.size() > 0) {
                        if (changeReadMessage(selectList)) {
                            alarmMessageAdapter.setSelectAllNo();
                            changeTextSelectOrNo();
                        }
                    } else {
                        //ToastUtils.getToastUtils().showToast(mActivity, mActivity.getResources().getString(R.string.select_file_you_want_read));
                    }
                }
                break;
            }
            case R.id.alarm_layout_operation_delet:
            case R.id.alarm_layout_delete: {
                List<String> selectList = alarmMessageAdapter.getSelectList();
                if (selectList.size() > 0) {
                    deletMessage(selectList);
                }
                break;
            }

        }
    }

    public void setTypeShow() {
        nowType = TYPE_SHOW;
        try {
            if (alarmMessageAdapter != null) {
                alarmMessageAdapter.setType(nowType);
            }
            alarmLayoutTitleLeft.setText("");
            alarmLayoutTitleRight.setText(getResources().getString(R.string.compile));
            //alarmLayoutOperationCl.setVisibility(View.GONE);
            alarmLayoutReadDelete.setVisibility(View.GONE);
            alarmLayoutTitle.setVisibility(View.VISIBLE);
        } catch (Exception e) {

        }

    }

    private void setAlarmMessageData() {
        alarmMessageAdapter.setData(listForDeviceIdAndEvevtType);
    }

    @Override
    public void onRefresh() {
        getHistoryMessage();
    }

    //还原编辑状态到展示状态
    private boolean changeNowTypeToTYPE_SHOW() {
        if (nowType == TYPE_EDIT) {
            nowType = TYPE_SHOW;
            alarmLayoutTitleLeft.setText("");
            alarmLayoutTitleRight.setText(getResources().getString(R.string.compile));
            //alarmLayoutOperationCl.setVisibility(View.GONE);
            alarmLayoutReadDelete.setVisibility(View.GONE);
            alarmLayoutTitle.setVisibility(View.VISIBLE);

            if (alarmMessageAdapter != null) {
                alarmMessageAdapter.setType(nowType);
            }
            return true;
        } else {
            return false;
        }
    }

    int nextToken = 0;
    int maxResults = 0;

    public void getHistoryMessage() {
        nextToken = 0;
        maxResults = 50;
        String path = "/message/center/query/push/message";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_01;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("nextToken", nextToken);
        params.put("maxResults", maxResults);
        params.put("type", "NOTICE");
        params.put("messageType", "device");

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();

        ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params), new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                int code = ioTResponse.getCode();

                // 200 代表成功
                if (200 != code) {
                    //失败示例，参见 "异常数据返回示例"
                    String mesage = ioTResponse.getMessage();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("wy", "====mesage==" + mesage);

                    Message message = handler.obtainMessage();
                    message.arg1 = 2;
                    message.obj = localizedMsg;
                    message.what = GET_ALARM_MESSAGE_LIST;
                    handler.sendMessage(message);
                    return;
                }
                Object data = ioTResponse.getData();
                //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                Log.e("wy", "====data==" + data.toString());

                /**
                 * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                 * 解析data，data示例参见"正常数据返回示例"
                 * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                 */
                if (data == null) {
                    return;
                }

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.obj = data.toString();
                message.what = GET_ALARM_MESSAGE_LIST;
                handler.sendMessage(message);
            }
        });

    }

    public void getMoreAlarmMessageData() {
        if (maxResults != 0) {
            String path = "/message/center/query/push/message";
            String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_01;
            String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
            Map<String, Object> params = new HashMap<>();
            params.put("nextToken", nextToken);
            params.put("maxResults", maxResults);
            params.put("type", "NOTICE");
            params.put("messageType", "device");

            IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();

            ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params), new IoTCallback() {
                @Override
                public void onFailure(IoTRequest ioTRequest, Exception e) {

                }

                @Override
                public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                    int code = ioTResponse.getCode();

                    // 200 代表成功
                    if (200 != code) {
                        //失败示例，参见 "异常数据返回示例"
                        String mesage = ioTResponse.getMessage();
                        String localizedMsg = ioTResponse.getLocalizedMsg();
                        Log.e("wy", "====mesage==" + mesage);
                        return;
                    }
                    Object data = ioTResponse.getData();
                    //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                    Log.e("wy", "====data==" + data.toString());

                    /**
                     * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                     * 解析data，data示例参见"正常数据返回示例"
                     * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                     */
                    if (data == null) {
                        return;
                    }

                    Message message = handler.obtainMessage();
                    message.arg1 = 1;
                    message.obj = data;
                    message.what = GET_ALARM_MESSAGE_LIST_MORE;
                    handler.sendMessage(message);
                }
            });
        } else {

        }
    }

    public void deletMessage(List<String> selectList) {
        Log.e("wy", "===sll==" + new Gson().toJson(selectList));
        String path = "/message/center/record/delete";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_07;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("keyIds", selectList);
        params.put("type", "NOTICE");


        Map<String, Object> params2 = new HashMap<>();
        params2.put("requestDTO", params);

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();

        ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params2), new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                int code = ioTResponse.getCode();

                // 200 代表成功
                if (200 != code) {
                    //失败示例，参见 "异常数据返回示例"
                    String mesage = ioTResponse.getMessage();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("wy", "====mesage==" + mesage);

                    Message message = handler.obtainMessage();
                    message.arg1 = 2;
                    message.obj = localizedMsg;
                    message.what = GET_ALARM_MESSAGE_LIST_DELETE;
                    handler.sendMessage(message);
                    return;
                }
                Object data = ioTResponse.getData();
                //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                Log.e("wy", "====data==" + data.toString());

                /**
                 * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                 * 解析data，data示例参见"正常数据返回示例"
                 * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                 */
                if (data == null) {
                    return;
                }

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.what = GET_ALARM_MESSAGE_LIST_DELETE;
                handler.sendMessage(message);
            }
        });
    }

    public boolean changeReadMessage(List<String> selectList) {
        String path = "/message/center/record/modify";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_08;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("ids", selectList);
        params.put("type", "NOTICE");
        params.put("isRead", 1);

        Map<String, Object> params2 = new HashMap<>();
        params2.put("requestDTO", params);

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();

        ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params2), new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                int code = ioTResponse.getCode();

                // 200 代表成功
                if (200 != code) {
                    //失败示例，参见 "异常数据返回示例"
                    String mesage = ioTResponse.getMessage();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("wy", "====mesage==" + mesage);

                    Message message = handler.obtainMessage();
                    message.arg1 = 2;
                    message.obj = localizedMsg;
                    message.what = GET_ALARM_MESSAGE_LIST_READ;
                    handler.sendMessage(message);
                    return;
                }
                Object data = ioTResponse.getData();
                //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                Log.e("wy", "====data==" + data.toString());

                /**
                 * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                 * 解析data，data示例参见"正常数据返回示例"
                 * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                 */
                if (data == null) {
                    return;
                }

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.what = GET_ALARM_MESSAGE_LIST_READ;
                handler.sendMessage(message);
            }
        });

        boolean flag = false;
        flag = true;

        return flag;
    }

    private void queryPic(String iotId, ArrayList<String> eventId) {
        Log.e("wy", "===eventId====" + iotId + "=====" + new Gson().toJson(eventId));

        String path = "/vision/customer/pic/getbyevent";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_210;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("iotId", iotId);
        params.put("eventIds", eventId);

        IoTAPIClient ioTAPIClient = new IoTAPIClientFactory().getClient();

        ioTAPIClient.send(HttpUtils.creatIoTRequest(path, apiversion, type, params), new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                int code = ioTResponse.getCode();

                // 200 代表成功
                if (200 != code) {
                    //失败示例，参见 "异常数据返回示例"
                    String mesage = ioTResponse.getMessage();
                    String localizedMsg = ioTResponse.getLocalizedMsg();
                    Log.e("wy", "====mesage==" + mesage);
                    return;
                }
                Object data = ioTResponse.getData();
                //TODO，可以将data转成一个本地的对象或者直接使用JSONObject进行数据解析
                Log.e("wy", "====queryPic==" + data.toString());

                /**
                 * {"iotId":"3JWTCE5ZWGgD8ZOvDaIH000000","categoryKey":"Camera","pageRouterUrl":"page\/lvcamera\/devicepanel"}
                 * 解析data，data示例参见"正常数据返回示例"
                 * 以下解析示例采用fastjson针对"正常数据返回示例"，解析各个数据节点
                 */
                if (data == null) {
                    return;
                }

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.obj = data.toString();
                message.what = GET_ALARM_PIC_LIST;
                handler.sendMessage(message);
            }
        });

    }


    //!可以控制报警消息的开关
    public void setPushSet() {
        String path = "/message/center/device/notice/set";
        String apiversion = ConstUtil.AILYUN_REQUEST_APIVERSION_06;
        String type = ConstUtil.AILYUN_REQUEST_AUTHTYPE;
        Map<String, Object> params = new HashMap<>();
        params.put("iotId", iotID);
        params.put("eventId", "eventId");
        params.put("noticeEnabled", true);

    }

}
