package com.aliIoT.demo.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.aliIoT.demo.R;
import com.aliIoT.demo.model.DeviceInfoBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * Created by hjt on 2020/9/16
 */
public class Utils {
    public final static String secretKey = "WuHanEnZhiCoLtd";

    /**
     * 检测字符串是否为纯数字或带有@符
     *
     * @param userName
     * @return false表示是纯数字或带有@符，ture表示否
     */
    public static boolean checkAccountCompliance(String userName) {
        if (userName.contains("@")) {
            return false;
        } else if (userName.matches("[0-9]+")) {
            return false;
        }
        return true;
    }

    public static String devicePasswordRecovery(String str) {
        if (!TextUtils.isEmpty(str)) {
            String replace = str.replace("+", "-");
            replace = replace.replace("/", "_");
            replace = replace.replace("=", "");
            str = replace;
        }
        return str;
    }

    public static boolean deviceName(String str) {
        //[\u4e00-\u9fa5_a-zA-Z0-9_]{4,10}
        if (!TextUtils.isEmpty(str)) {
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,60}");
            Matcher matcher = p.matcher(str);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static void HideKeyboard(Activity activity) {
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null)
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void ShowKeyboard() {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //yxy强制关闭软键盘
    public static void KeyBoardCancle(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isShowKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {   //如果为true,则是键盘正在显示
//            return true;
//        } else
//            return false;
        return isSoftShowing(activity);
    }

    private static boolean isSoftShowing(Activity activity) {
        // 获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        // 获取View可见区域的bottom
        Rect rect = new Rect();
        // DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * sina
     * 判断是否安装新浪微博
     */
    public static boolean isSinaInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String siteToString(int i) {
        String str = null;
        switch (i) {
            case 0:
                str = "CN";
                break;
            case 1:
                str = "SG";
                break;
            case 4:
                str = "DE";
                break;
            case 3:
                str = "US";
                break;
            default:
                str = "CN";
                break;
        }
        return str;
    }

    public static int siteToInt(int str) {
        int i = 0;
        if ("CN".equals(str)) {
            i = 0;
        } else if ("SG".equals(str)) {
            i = 1;
        } else if ("US".equals(str)) {
            i = 3;
        } else if ("DE".equals(str)) {
            i = 4;
        } else {
            i = 0;
        }
        return i;
    }

    public static List<DeviceInfoBean> childDeviceInfoBeanSort(List<DeviceInfoBean> list) {
        if (list != null) {
            Collections.sort(list, new Comparator<DeviceInfoBean>() {
                @Override
                public int compare(DeviceInfoBean o1, DeviceInfoBean o2) {
                    if (o1 != null && o2 != null) {
                        if (o1.getDeviceId().equals(o2.getDeviceId())) {
                            return 0;
                        } else {
                            Integer integer1 = childDeviceInfoBeanNameInt(o1.getDeviceName());
                            Integer integer2 = childDeviceInfoBeanNameInt(o2.getDeviceName());
                            if (integer1.compareTo(integer2) > 0) {
                                return 1;
                            } else if (integer1.compareTo(integer2) < 0) {
                                return -1;
                            }
                        }
                    }
                    return 0;
                }
            });
        }
        return list;
    }

    public static String childDeviceInfoBeanName(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("-");
            if (split != null && split.length == 2) {
                str = split[1];
            }
        }

        return str;
    }

    public static Integer childDeviceInfoBeanNameInt(String str) {
        Integer chNum = -1;
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("-");
                if (split != null && split.length == 2) {
                    str = split[1];
                    String ch = str.replace("CH", "");
                    chNum = Integer.parseInt(ch);
                }
            }
        } catch (Exception e) {

        }
        return chNum;
    }

    public static int streamTypeToInt(String str) {
        if (MyApplication.getInstance().getString(R.string.stream_type_child).equals(str)) {
            return ConstUtil.STREAMTYPE_CHILD;
        } else {
            return ConstUtil.STREAMTYPE_MAIN;
        }
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        return pattern.matcher(str).matches();
    }

    public static String speed(float f) {
        String str = "";
        if (f == 0.0625f) {
            str = "1/16";
        } else if (f == 0.125f) {
            str = "1/8";
        } else if (f == 0.25f) {
            str = "1/4";
        } else if (f == 0.5f) {
            str = "1/2";
        } else if (f == 1.0f) {
            str = "1";
        } else if (f == 2.0f) {
            str = "2";
        } else if (f == 4.0f) {
            str = "4";
        } else if (f == 8.0f) {
            str = "8";
        } else if (f == 16.0f) {
            str = "16";
        }
        return str;
    }

    //!去掉byte中多余的0
    public static String byteToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            if (length == 0) {
                length = buffer.length;
            }
            return new String(buffer, 0, length, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    //!压缩   
    public static String compress(String str) {
        String res = "";

        if (TextUtils.isEmpty(str)) {
            return res;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();

            res = out.toString("ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    //!解压缩   
    public static String uncompress(String str) {
        String res = "";

        if (TextUtils.isEmpty(str)) {
            return res;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            //toString()使用平台默认编码，也可以显式的指定如toString("GBK")   
            res = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 将字符串进行gzip压缩
     *
     * @param data
     * @return
     */
    public static String compress2(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(data.getBytes("utf-8"));
            gzip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(out.toByteArray(), Base64.NO_PADDING);
    }

    public static String uncompress2(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        byte[] decode = Base64.decode(data, Base64.NO_PADDING);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(decode);
        GZIPInputStream gzipStream = null;
        try {
            gzipStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                if (gzipStream != null) {
                    gzipStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(out.toByteArray(), Charset.forName("utf-8"));
    }

    /**
     * 压缩
     */
    public static String compress3(String unzipString) {
        /**
         *     https://www.yiibai.com/javazip/javazip_deflater.html#article-start
         *     0 ~ 9 压缩等级 低到高
         *     public static final int BEST_COMPRESSION = 9;            最佳压缩的压缩级别。
         *     public static final int BEST_SPEED = 1;                  压缩级别最快的压缩。
         *     public static final int DEFAULT_COMPRESSION = -1;        默认压缩级别。
         *     public static final int DEFAULT_STRATEGY = 0;            默认压缩策略。
         *     public static final int DEFLATED = 8;                    压缩算法的压缩方法(目前唯一支持的压缩方法)。
         *     public static final int FILTERED = 1;                    压缩策略最适用于大部分数值较小且数据分布随机分布的数据。
         *     public static final int FULL_FLUSH = 3;                  压缩刷新模式，用于清除所有待处理的输出并重置拆卸器。
         *     public static final int HUFFMAN_ONLY = 2;                仅用于霍夫曼编码的压缩策略。
         *     public static final int NO_COMPRESSION = 0;              不压缩的压缩级别。
         *     public static final int NO_FLUSH = 0;                    用于实现最佳压缩结果的压缩刷新模式。
         *     public static final int SYNC_FLUSH = 2;                  用于清除所有未决输出的压缩刷新模式; 可能会降低某些压缩算法的压缩率。
         */

        //使用指定的压缩级别创建一个新的压缩器。
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        //设置压缩输入数据。
        deflater.setInput(unzipString.getBytes());
        //当被调用时，表示压缩应该以输入缓冲区的当前内容结束。
        deflater.finish();

        final byte[] bytes = new byte[256];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);

        while (!deflater.finished()) {
            //压缩输入数据并用压缩数据填充指定的缓冲区。
            int length = deflater.deflate(bytes);
            outputStream.write(bytes, 0, length);
        }
        //关闭压缩器并丢弃任何未处理的输入。
        deflater.end();
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_PADDING);
    }

    /**
     * 解压缩
     */
    public static String uncompress3(String zipString) {
        byte[] decode = Base64.decode(zipString, Base64.NO_PADDING);
        //创建一个新的解压缩器  https://www.yiibai.com/javazip/javazip_inflater.html

        Inflater inflater = new Inflater();
        //设置解压缩的输入数据。
        inflater.setInput(decode);
        final byte[] bytes = new byte[256];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        try {
            //finished() 如果已到达压缩数据流的末尾，则返回true。
            while (!inflater.finished()) {
                //将字节解压缩到指定的缓冲区中。
                int length = inflater.inflate(bytes);
                outputStream.write(bytes, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭解压缩器并丢弃任何未处理的输入。
            inflater.end();
        }

        return outputStream.toString();
    }

    //!隐藏键盘
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        return baos.toByteArray();
    }

    /**
     * 包括空格判断
     *
     * @param input
     * @return
     */
    public static boolean containSpace(CharSequence input) {
        return Pattern.compile("\\s+").matcher(input).find();
    }

    public static boolean containSpace(String input) {
        return Pattern.compile("\\s+").matcher(input).find();
    }

    public static byte[] readFile(File file) {
        //!需要读取的文件，参数是文件的路径名加文件名
        if (file.isFile()) {
            //!以字节流方法读取文件
            FileInputStream inputStreamFile = null;

            try {
                inputStreamFile = new FileInputStream(file);

                //!设置一个，每次 装载信息的容器
                int len = 0;
                byte[] buffer = new byte[1024];
                byte[] pBufData = null;

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                while ((len = inputStreamFile.read(buffer)) != -1) {
                    //!len值为-1时，表示没有数据了
                    //!append方法往sb对象里面添加数据
                    outputStream.write(buffer, 0, len);
                }

                pBufData = outputStream.toByteArray();

                inputStreamFile.close();

                outputStream.close();

                return pBufData;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static short[] byteArray2ShortArray(byte[] data, int items) {
        short[] retVal = new short[items];
        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = (short) ((data[i * 2] & 0xff) | (data[i * 2 + 1] & 0xff) << 8);
        }
        return retVal;
    }

    public String streamTypeToString(int mStreamType) {
        String str = MyApplication.getInstance().getString(R.string.stream_type_main);
        if (mStreamType == ConstUtil.STREAMTYPE_CHILD) {
            str = MyApplication.getInstance().getString(R.string.stream_type_child);
        }
        return str;
    }

}
