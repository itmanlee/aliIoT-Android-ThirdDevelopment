package com.aliIoT.demo.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    static String LOCAL_FILE_ADDRESS = "aliiotdemo";

    /**
     * @Description 判断是否插入SD卡
     */
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取文件存储的文件夹，若该文件夹未创建则顺便创建该文件夹
     *
     * @return
     */
    public static String getFileStoragePath() {
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static String getFileStoragePathUserImage() {
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/userImage/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/userImage/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static String getFileStoragePathUid(String uid) {
        if (TextUtils.isEmpty(uid)) {
            uid = "";
        }
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/" + uid + "/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/" + uid + "/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static String getFileStoragePathCover() {
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/Cover/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/Cover/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static String getFileStoragePathAlarm() {
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/Alarm/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/Alarm/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static String getFileStoragePathTemp() {
        String APK_dir = "";
        if (isHasSdcard()) {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/" + "temp/"; // 保存到SD卡路径下
        } else {
            APK_dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LOCAL_FILE_ADDRESS + "/" + "temp/"; // 保存到app的包名路径下
        }
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        return APK_dir;
    }

    public static File getTestFile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aa/aa.txt";
        File file = new File(path);
        if (!file.exists()) {
            file = null;
        }
        return file;
    }

    public static void readFile() {
        File file = getTestFile();
        try {
            byte[] bytes = new byte[1024];
            FileInputStream fileStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileStream);
            bufferedInputStream.read(bytes);

            String res = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File creatFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }


}
