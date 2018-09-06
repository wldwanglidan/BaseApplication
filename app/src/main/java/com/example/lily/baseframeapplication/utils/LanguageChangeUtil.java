package com.example.lily.baseframeapplication.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

/**
 * Created by lily on 2017/12/12.
 * 语言切换工具类
 */

public class LanguageChangeUtil {
    public Context mContext;


    public LanguageChangeUtil(Context context) {
        mContext = context;
    }

    @SuppressWarnings("deprecation")
    public static LanguageChangeUtil wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        }
        else {
            sysLocale = getSystemLocaleLegacy(config);
        }
        if (!language.equals("") && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale);
            }
            else {
                setSystemLocaleLegacy(config, locale);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config);
            }
            else {
                context.getResources()
                       .updateConfiguration(config,
                               context.getResources().getDisplayMetrics());
            }
        }
        return new LanguageChangeUtil(context);
    }


    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }


    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }


    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }


    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }


    public static void changeLanguage(Context newBase, String language) {
        //language为 对应的资源格式后缀，比如"zh"
        LanguageChangeUtil.wrap(newBase, language);
    }
}
