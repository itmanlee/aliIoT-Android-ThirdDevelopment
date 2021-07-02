package com.aliIoT.demo.util;

import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by hjt on 2020/10/14
 */
public class LanguageUtil {
    public static final String LANGUAGE_ZH_CN = "zh-CN";
    public static final String LANGUAGE_EN_US = "en-US";
    public static final String LANGUAGE_FR_FR = "fr-FR";
    public static final String LANGUAGE_DE_DE = "de-DE";
    public static final String LANGUAGE_JA_JP = "ja-JP";
    public static final String LANGUAGE_KO_KR = "ko-KR";
    public static final String LANGUAGE_ES_ES = "es-ES";
    public static final String LANGUAGE_RU_RU = "ru-RU";
    public static final String LANGUAGE_IT_IT = "it-IT";
    public static final String LANGUAGE_PT_PT = "pt-PT";
    public static final String LANGUAGE_PL_PL = "pl-PL";
    public static final String LANGUAGE_NL_BE = "nl-BE";
    public static final String LANGUAGE_ZH = "zh";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_FR = "fr";
    public static final String LANGUAGE_DE = "de";
    public static final String LANGUAGE_JA = "ja";
    public static final String LANGUAGE_KO = "ko";
    public static final String LANGUAGE_ES = "es";
    public static final String LANGUAGE_RU = "ru";
    public static final String LANGUAGE_IT = "it";
    public static final String LANGUAGE_PT = "pt";
    public static final String LANGUAGE_PL = "pl";
    public static final String LANGUAGE_NL = "nl";
    public static final String CN = "CN";
    public static final String TW = "TW";
    public static final String RU = "RU";
    public static final String PT = "PT";
    public static final String ES = "ES";
    public static final String EN = "EN";


    public static String getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String language = locale.getLanguage();
        return language;
    }

    public static String getCountry() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String country = locale.getCountry();
        if (TextUtils.isEmpty(country)) {
            country = CN;
        }
        return country;
    }

    /**
     * 获取中文繁简体判定
     *
     * @return true 简体，false繁体
     */
    public static boolean getSimplifiedChinese() {
        boolean flag = true;
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        String mCountry = locale.getDisplayCountry();
        if ("中國".equals(mCountry)) {
            return false;
        }
        return flag;
    }

    public static String getLanguageToAliyun(String mLanguage) {
        String str = LANGUAGE_EN_US;
        if (LANGUAGE_ZH.equals(mLanguage)) {
            str = LANGUAGE_ZH_CN;
        } else if (LANGUAGE_FR.equals(mLanguage)) {
            str = LANGUAGE_FR_FR;
        } else if (LANGUAGE_DE.equals(mLanguage)) {
            str = LANGUAGE_DE_DE;
        } else if (LANGUAGE_JA.equals(mLanguage)) {
            str = LANGUAGE_JA_JP;
        } else if (LANGUAGE_KO.equals(mLanguage)) {
            str = LANGUAGE_KO_KR;
        } else if (LANGUAGE_ES.equals(mLanguage)) {
            str = LANGUAGE_ES_ES;
        } else if (LANGUAGE_RU.equals(mLanguage)) {
            str = LANGUAGE_RU_RU;
        } else if (LANGUAGE_IT.equals(mLanguage)) {
            str = LANGUAGE_IT_IT;
        } else if (LANGUAGE_PT.equals(mLanguage)) {
            str = LANGUAGE_PT_PT;
        } else if (LANGUAGE_PL.equals(mLanguage)) {
            str = LANGUAGE_PL_PL;
        } else if (LANGUAGE_NL.equals(mLanguage)) {
            str = LANGUAGE_NL_BE;
        }
        return str;
    }

    public static String getLanguageToSystemService() {
        String str = CN;
        String mLanguage = getLanguage();
        if (LANGUAGE_ZH.equals(mLanguage)) {
            if (!getSimplifiedChinese()) {
                str = TW;
            }
        } else if (LANGUAGE_RU.equals(mLanguage)) {
            str = RU;
        } else if (LANGUAGE_PT.equals(mLanguage)) {
            str = PT;
        } else if (LANGUAGE_ES.equals(mLanguage)) {
            str = ES;
        } else if (LANGUAGE_EN.equals(mLanguage)) {
            str = EN;
        }
        return str;
    }

    public static String getLanguageToCN_EN() {
        String str = CN;
        String mLanguage = getLanguage();
        if (!LANGUAGE_ZH.equals(mLanguage)) {
            str = EN;
        }
        return str;
    }
}
