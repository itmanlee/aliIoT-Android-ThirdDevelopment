package com.aliIoT.demo.util;

/**
 * Created by Administrator on 2021/5/29 0029.
 */

public class ConstUtil {

    public static final String KEY_USERID = "userid";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_IDENTITYID = "identityId";
    public static final String KEY_SERVERSITE  = "serverSite";
    public static final String KEY_AUTHCODE  = "authCode";
    public static final String KEY_IOTID  = "iotid";


    public static final String mUrl = "http://oauth20.p2p-platform.com:10001";
    public static final String mUrl1 = "http://account.p2p-platform.com:10000";
    public static final String APP_KEY = "787CBE6ECFB54B3882A7CA5173BE19F3";

    public static final String AILYUN_REQUEST_APIVERSION_08 = "1.0.8";
    public static final String AILYUN_REQUEST_APIVERSION_11 = "1.1.1";
    public static final String AILYUN_REQUEST_APIVERSION_07 = "1.0.7";
    public static final String AILYUN_REQUEST_APIVERSION_06 = "1.0.6";
    public static final String AILYUN_REQUEST_APIVERSION_05 = "1.0.5";
    public static final String AILYUN_REQUEST_APIVERSION_04 = "1.0.4";
    public static final String AILYUN_REQUEST_APIVERSION_03 = "1.0.3";
    public static final String AILYUN_REQUEST_APIVERSION_02 = "1.0.2";
    public static final String AILYUN_REQUEST_APIVERSION_01 = "1.0.1";
    public static final String AILYUN_REQUEST_APIVERSION_00 = "1.0.0";
    public static final String AILYUN_REQUEST_APIVERSION_210 = "2.1.0";
    public static final String AILYUN_REQUEST_AUTHTYPE = "iotAuth";


    //!PTZ控制命令宏定义
    public static final int CLOUDEYE_PTZ_CTRL_STOP              =100;	  //!控制停止
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_UP	        =101;	//!向上
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_DOWN	        =102;	//!向下
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_LEFT	        =103;	//!向左
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_RIGHT	    =104;	//!向右
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_UPLEFT	    =105;	//!向左上
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_DOWNLEFT	    =106;	//!向右下
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_UPRIGHT	    =107;	//!向右上
    public static final int CLOUDEYE_PTZ_CTRL_MOVE_DOWNRIGHT	=108;	//!向右下
    public static final int CLOUDEYE_PTZ_CTRL_IRIS_IN	        =109;
    public static final int CLOUDEYE_PTZ_CTRL_IRIS_OUT	        =110;
    public static final int CLOUDEYE_PTZ_CTRL_FOCUS_IN	        =111;	//!焦点靠近
    public static final int CLOUDEYE_PTZ_CTRL_FOCUS_OUT	        =112;	//!焦点远离
    public static final int CLOUDEYE_PTZ_CTRL_ZOOM_IN	        =113;	//!放大
    public static final int CLOUDEYE_PTZ_CTRL_ZOOM_OUT	        =114;	//!缩小
    public static final int CLOUDEYE_PTZ_CTRL_SET_PRESET	    =115;	//!设置预置点
    public static final int CLOUDEYE_PTZ_CTRL_CALL_PRESET	    =116;	//!到达预置点
    public static final int CLOUDEYE_PTZ_CTRL_DELETE_PRESET	    =117;	//!删除预置点
    public static final int CLOUDEYE_PTZ_CTRL_BEGIN_CRUISE_SET	=118;
    public static final int CLOUDEYE_PTZ_CTRL_SET_CRUISE	    =119;
    public static final int CLOUDEYE_PTZ_CTRL_END_CRUISE_SET	=120;
    public static final int CLOUDEYE_PTZ_CTRL_CALL_CRUISE	    =121;
    public static final int CLOUDEYE_PTZ_CTRL_DELETE_CRUISE	    =122;
    public static final int CLOUDEYE_PTZ_CTRL_STOP_CRUISE	    =123;
    public static final int CLOUDEYE_PTZ_CTRL_AUTO_SCAN	        =124;
    public static final int CLOUDEYE_PTZ_CTRL_RAINBRUSH_START	=125;	//!开灯
    public static final int CLOUDEYE_PTZ_CTRL_RAINBRUSH_STOP	=126;	//!关灯
    public static final int CLOUDEYE_PTZ_CTRL_LIGHT_ON	        =127;
    public static final int CLOUDEYE_PTZ_CTRL_LIGHT_OFF	        =128;
    public static final int CLOUDEYE_PTZ_CTRL_MAX 	            =129;

    public static final int TITLE_ITEM_TYPE_ITEM_TITLE = 0x200;
    public static final int TITLE_ITEM_TYPE_DEVICE_INFO = 0x201;
    public static final int TITLE_ITEM_TYPE_DEVICE_NICKNAME = 0x202;
    public static final int TITLE_ITEM_TYPE_DEVICE_TIME_SET = 0x203;

    public static final int STREAMTYPE_MAIN = 0;
    public static final int STREAMTYPE_CHILD = 1;


    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_CHANNEL_NAME = 0x226;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCIDE_SET = 0x227;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_CODE_STREAM_TYPE = 0x228;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_RESOLUTION_RATIO = 0x229;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_TYPE = 0x22A;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_BITE_RATE_LIMIT = 0x22B;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_FRAME_RATE = 0x22C;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_IMAGE_QUALITY = 0x22D;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_ENCODE_TYPE = 0x22E;
    public static final int TITLE_ITEM_TYPE_CHANNEL_ENCODE_I_FRAME = 0x22F;

    public static final int DEVICE_TYPE_NVR = 0;
    public static final int DEVICE_TYPE_IPC = 1;

    public static final int TYPE_SHOW = 0;
    public static final int TYPE_EDIT = 1;


}
