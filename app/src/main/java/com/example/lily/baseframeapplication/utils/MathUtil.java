package com.example.lily.baseframeapplication.utils;

import android.util.Log;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <请描述这个类是干什么的>
 *
 * @author chenml@cncn.com
 * @data: 2015/11/26 13:16
 * @version: V1.0
 */
public class MathUtil {

    //保留2位小数
    public static String formatFloat2String(float price) {
        NumberFormat df = DecimalFormat.getNumberInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String s = df.format(price);
        Log.d("MathUtil", "money:" + s);
        return s;

//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String s = decimalFormat.format(price);//format 返回的是字符串
//        if(s.startsWith(".")) {
//            s = "0" + s;
//        }
//        return s;
    }


    //保留2位小数
    public static String formatFloat2String(double price) {
        NumberFormat df = DecimalFormat.getNumberInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String s = df.format(price);
        Log.d("MathUtil", "money:" + s);
        return s;

//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String s = decimalFormat.format(price);//format 返回的是字符串
//        if(s.startsWith(".")) {
//            s = "0" + s;
//        }
//        return s;
    }


}
