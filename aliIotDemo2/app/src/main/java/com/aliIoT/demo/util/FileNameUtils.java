package com.aliIoT.demo.util;


import java.io.File;

/**
 * Created by hjt on 2020/7/20
 */
public class FileNameUtils {
    public static String FILE_NAME_INTERVAL = "@_@";


    public static String creatApkWritePath(String str) {
        int i = str.lastIndexOf("/");
        str = str.substring(i + 1);
        return FileUtils.getFileStoragePathTemp() + str;
    }

    public static String creatAlarmPicWritePath(String str) {
        return FileUtils.getFileStoragePathAlarm() + str + ".jpg";
    }


    //yxy最终截图存储路径

    public static String creatScreenShotFileName(String iotid, String deviceName) {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathUid("ali");
        } catch (Exception e) {

        }
        String path = fileStoragePath + iotid + FILE_NAME_INTERVAL + deviceName + FILE_NAME_INTERVAL + System.currentTimeMillis() + ".jpg";
        return path;
    }

    //yxy本地视频播放截图存储路径获取
    public static String creatLocalVideoScreenShotFileName() {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathUid("ali");
        } catch (Exception e) {

        }
        return fileStoragePath;
    }

    public static String creatRecordFileName(String iotid, String deviceName) {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathUid("ali");
        } catch (Exception e) {

        }
        String path = fileStoragePath + iotid + FILE_NAME_INTERVAL + deviceName + FILE_NAME_INTERVAL + System.currentTimeMillis() + ".mp4";
        return path;
    }

    public static String isContainsFile(String iotid) {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathUid("ali");
        } catch (Exception e) {

        }
        String result = "";
        File file = new File(fileStoragePath);
        File[] files = file.listFiles();
        if (files == null)
            return result;
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].isDirectory()) {
                String fileName = files[i].getName();
                if (fileName.trim().contains(iotid)) {
                    result = fileStoragePath + fileName;
                    break;
                }
            }
        }
        return result;
    }

    public static String creatScreenShotCoverFileName(String iotid) {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathCover();
        } catch (Exception e) {

        }
        String path = fileStoragePath + iotid + System.currentTimeMillis() + ".jpg";
        return path;
    }

    public static String containScreenShotCoverFileName(String deviceId) {
        String fileStoragePath = "";
        try {
            File file = new File(FileUtils.getFileStoragePathCover());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.getAbsolutePath().contains(deviceId)) {
                        fileStoragePath = f.getAbsolutePath();
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return fileStoragePath;
    }

    public static String deleteContainScreenShotCoverFileName(String deviceId, String path) {
        String fileStoragePath = "";
        try {
            fileStoragePath = FileUtils.getFileStoragePathCover();
            File file = new File(fileStoragePath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.getAbsolutePath().contains(deviceId) && !f.getAbsolutePath().equals(path)) {
                        //CacheUtil.deleteFile(f.getAbsolutePath());
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return fileStoragePath;
    }
}

