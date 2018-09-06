package com.example.lily.baseframeapplication.dao;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lily on 2017/11/14.
 */

public class SmsObserver extends ContentObserver {
    public static final String SMS_URI_INBOX = "content://sms/inbox";
    private Activity activity = null;
    private String smsContent = "";
    private SmsListener listener;

    public SmsObserver(Handler handler) {
        super(handler);
    }

    public SmsObserver(Activity activity, Handler handler, SmsListener listener) {
        super(handler);
        this.activity = activity;
        this.listener = listener;
    }


    @Override public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        new RxPermissions(activity).request(
                Manifest.permission.READ_SMS)
                                   .subscribe(granted -> {
                                       if (granted) {// 在android 6.0之前会默认返回true 已经获取权限
                                           Cursor cursor = null;
                                           // 读取收件箱中含有某关键词的短信
                                           ContentResolver
                                                   contentResolver = activity.getContentResolver();
                                           cursor = contentResolver.query(Uri.parse(SMS_URI_INBOX), new String[] {
                                                           "_id", "address", "body", "read" }, "body like ? and read=?",
                                                   new String[] { "%腾邦%", "0" }, "date desc");
                                           if (cursor != null) {
                                               cursor.moveToFirst();
                                               if (cursor.moveToFirst()) {
                                                   String smsbody = cursor
                                                           .getString(cursor.getColumnIndex("body"));
                                                   String regEx = "[^0-9]";
                                                   Pattern p = Pattern.compile(regEx);
                                                   Matcher
                                                           m = p.matcher(smsbody.toString());
                                                   smsContent = m.replaceAll("").trim().toString();
                                                   if (!TextUtils.isEmpty(smsContent)) {
                                                       listener.onResult(smsContent);
                                                   }

                                               }
                                           }
                                       }
                                       else {
                                           // 未获取权限
                                       }

                                   });

    }


    /*
        * 短信回调接口
        */
    public interface SmsListener {
        /**
         * 接受sms状态
         *
         * @Title: onResult
         */
        void onResult(String smsContent);
    }
}
