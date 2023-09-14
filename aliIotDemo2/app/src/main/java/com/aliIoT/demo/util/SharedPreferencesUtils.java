package com.aliIoT.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hjt on 2020/5/7
 */
public class SharedPreferencesUtils {
    public static String getSharedPreferencesDataString(String mSharedPreferences, String dataName) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(dataName, "");
        return string;
    }

    public static String getSharedPreferencesDataString(String mSharedPreferences, String dataName, String defultData) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(dataName, defultData);
        return string;
    }

    public static int getSharedPreferencesDataInt(String mSharedPreferences, String dataName) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        int i = sharedPreferences.getInt(dataName, 0);
        return i;
    }

    public static boolean getSharedPreferencesDataBool(String mSharedPreferences, String dataName) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        boolean bool = sharedPreferences.getBoolean(dataName, false);
        return bool;
    }

    public static boolean getSharedPreferencesDataBoolDefultReturnTrue(String mSharedPreferences, String dataName) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        boolean bool = sharedPreferences.getBoolean(dataName, false);
        return bool;
    }


    public static void setSharedPreferencesDataString(String mSharedPreferences, String dataName, String data) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(dataName, data).commit();

    }

    public static void setSharedPreferencesDataInt(String mSharedPreferences, String dataName, int data) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(dataName, data).commit();
    }

    public static void setSharedPreferencesDataBool(String mSharedPreferences, String dataName, boolean data) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(dataName, data).commit();
    }

    public static void removeSharedPreferencesKey(String mSharedPreferences, String dataName) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(dataName).commit();

    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key, String mSharedPreferences) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mSharedPreferences, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.contains(key);
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public static <K, V> boolean putHashMapData(String key, Map<K, V> map, String mSharedPreferences) {
        boolean result;
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public static HashMap<String, String> getMapData(String key, String mSharedPreferences) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_MULTI_PROCESS);
        String json = sharedPreferences.getString(key, "");
        HashMap<String, String> map = new Gson().fromJson(json, HashMap.class);
        return map;
    }

    /**
     * 用于读取集合
     *
     * @param key key
     * @return HashMap
     */
    public static <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV, String mSharedPreferences) {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences(mSharedPreferences, Context.MODE_MULTI_PROCESS);
        String json = sharedPreferences.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        return map;
    }

}



