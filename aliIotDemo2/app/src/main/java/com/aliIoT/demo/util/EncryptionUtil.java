package com.aliIoT.demo.util;

import android.text.TextUtils;

import com.aliIoT.demo.model.ParameterVerifyBean;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class EncryptionUtil {
    private static final String KEY = "aliyunandwuhants";
    private static String[] stringList = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"
            , "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"
            , "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String creatRandom() {
        String random = "";
        for (int i = 0; i < 15; i++) {
            int j = (int) (Math.random() * 62);
            random = random + stringList[j];
        }
        random = "ZmCe0BA165XH1RF";
        return random;
    }

    public static int creatIndexRandom(int count) {
        int i = (int) (Math.random() * count);
        i = 2;
        return i;
    }

    public static String creatIndexRandomString(int count, int index) {
        String random = "";
        if (count < 10) {
            random = index + "";
        } else if (10 <= count && count < 100) {
            if (index < 10) {
                random = "00" + index;
            } else {
                random = "0" + index;
            }
        }
        return random;
    }

    public static ParameterVerifyBean httParameterMd5(List<String> list) {
        if (list != null && list.size() > 0) {
            list.add(KEY);
            ParameterVerifyBean parameterVerifyBean = new ParameterVerifyBean();
            parameterVerifyBean.randomString = creatRandom();
            parameterVerifyBean.index = creatIndexRandom(list.size());
            parameterVerifyBean.randomIndexString = parameterVerifyBean.randomString + creatIndexRandomString(list.size(), parameterVerifyBean.index);
            parameterVerifyBean.md5VerifyString = creatMD5(list, parameterVerifyBean.index, parameterVerifyBean.randomString);
            return parameterVerifyBean;
        }
        return null;
    }

    public static String creatMD5(List<String> list, int index, String Random) {
        String md5 = "";
        try {
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (index == i) {
                    if (TextUtils.isEmpty(md5)) {
                        md5 = Random + "&" + str;
                    } else {
                        md5 = md5 + "&" + Random + "&" + str;
                    }
                } else {
                    if (TextUtils.isEmpty(md5)) {
                        md5 = str;
                    } else {
                        md5 = md5 + "&" + str;
                    }
                }
            }
            if (index >= list.size()) {
                md5 = md5 + "&" + Random;
            }
            byte[] mbyte = md5.getBytes("UTF-8");
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(mbyte);
            mbyte = m.digest();
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            for (int offset = 0; offset < mbyte.length; offset++) {
                i = mbyte[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(i));
            }

            md5 = stringBuilder.toString();
        } catch (Exception e) {
        }
        return md5;
    }
}
