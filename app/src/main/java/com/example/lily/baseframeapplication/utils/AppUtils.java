package com.example.lily.baseframeapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lily on 2017/11/8.
 *  desc  : App相关工具类
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 安装App(支持6.0)
     *
     * @param context 上下文
     * @param filePath 文件路径
     */
    public static void installApp(Context context, String filePath) {
        installApp(context, FileUtils.getFileByPath(filePath));
    }


    /**
     * 安装App(支持6.0)
     *
     * @param context 上下文
     * @param file 文件
     */
    public static void installApp(Context context, File file) {
        if (!FileUtils.isFileExists(file)) return;
        context.startActivity(IntentUtils.getInstallAppIntent(file));
    }


    /**
     * 安装App(支持6.0)
     *
     * @param activity activity
     * @param filePath 文件路径
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), requestCode);
    }


    /**
     * 安装App(支持6.0)
     *
     * @param activity activity
     * @param file 文件
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, File file, int requestCode) {
        if (!FileUtils.isFileExists(file)) return;
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file),
                requestCode);
    }


    /**
     * 获取App包名
     *
     * @param context 上下文
     * @return App包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }


    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return App名称
     */
    public static String getAppName(Context context) {
        return getAppName(context, context.getPackageName());
    }


    /**
     * 获取App名称
     *
     * @param context 上下文
     * @param packageName 包名
     * @return App名称
     */
    public static String getAppName(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null
                   ? null
                   : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public static String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }


    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本码
     *
     * @param context 上下文
     * @return App版本码
     */
    public static int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }


    /**
     * 获取App版本码
     *
     * @param context 上下文
     * @param packageName 包名
     * @return App版本码
     */
    public static int getAppVersionCode(Context context, String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 清除App所有数据
     *
     * @param context 上下文
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(Context context, String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(context, dirs);
    }


    /**
     * 清除App所有数据
     *
     * @param context 上下文
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(Context context, File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache(context);
        isSuccess &= CleanUtils.cleanInternalDbs(context);
        isSuccess &= CleanUtils.cleanInternalSP(context);
        isSuccess &= CleanUtils.cleanInternalFiles(context);
        isSuccess &= CleanUtils.cleanExternalCache(context);
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }


    public static float getDensity(Context mContext) {
        DisplayMetrics displayMetrics = mContext.getResources()
                                                .getDisplayMetrics();
        return displayMetrics.density;
    }


    /**
     * 执行拨打电话的方法
     */
    public static void callPhone(Context context, String phone) {
        //判断电话号码是否为空，为空则弹出对话框，警告用户
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        new RxPermissions(((BaseActivity) context)).request(
                Manifest.permission.CALL_PHONE).subscribe(granted -> {
            if (granted) {
                //2-调用手机系统里面的拨打电话的拨号盘拨出电话
                Intent intent = new Intent();//意图
                intent.setAction(Intent.ACTION_CALL);//我的意图是想拨打电话
                intent.setData(Uri.parse("tel:" + phone));//设置需要拨打的号码
                //执行意图（拨打电话）
                context.startActivity(intent);//执行一个跳转画面的动作
            }
            else {
                // 未获取权限
            }
        });
    }


    /**
     * 字节转换成相应大小的MB,KB
     */
    public static String bytes2Convert(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal gbyte = new BigDecimal(1024 * 1024 * 1024);
        float returnValue = filesize.divide(gbyte, 2, BigDecimal.ROUND_UP)
                                    .floatValue();
        if (returnValue >= 1) {
            return (returnValue + "GB");
        }
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                              .floatValue();
        if (returnValue >= 1) {
            return (returnValue + "MB");
        }
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte).intValue();
        return (returnValue + "KB");
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                                .getIdentifier("status_bar_height", "dimen",
                                        "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //保留2位小数
    public static String formatFloat2String(double price) {
        //NumberFormat df = DecimalFormat.getNumberInstance();
        //df.setMinimumFractionDigits(2);
        //df.setMaximumFractionDigits(2);
        //String s = df.format(price);
        //return s;
        DecimalFormat decimalFormat = new DecimalFormat(
                ".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String s = decimalFormat.format(price);//format 返回的是字符串
        if (s.startsWith(".")) {
            s = "0" + s;
        }
        return s;
    }


    public static void launchTPayApp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, Constant.T_APP_PACKAGE_NAME)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("launchapp://wallet"));
            context.startActivity(intent);
        }
        else {
            jumpAppMarket(context, Constant.T_APP_PACKAGE_NAME);
        }
    }


    /**
     * 检测某个应用是否安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 去市场下载页面
     */
    public static void jumpAppMarket(Context context, String packageName) {
        //这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
        //intent.setData(Uri.parse(
        //        " market://comments")); //跳转到应用的评论页:
        //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
        if (intent.resolveActivity(context.getPackageManager()) !=
                null) { //可以接收
            context.startActivity(intent);
        }
        else {
            ToastUtils.showLongToast("您的系统中没有安装应用市场");
        }
    }


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager
                = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos
                = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(
                        context.getApplicationInfo().processName)) {
                    Log.d("result", "MainActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d("result", "MainActivity isRunningBackGround");
        return false;
    }


    public static void jumpApp(Context context) {
        //if (!isBackgroundRunning(context)) {
        //    intent.setAction("android.intent.action.MAIN");
        //    intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //}
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
        //        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        //intent.setClassName(Constant.T_APP_PACKAGE_NAME,
        //        Constant.T_APP_CLASS_NAME);
        //intent.setComponent(
        //        new ComponentName(Constant.T_APP_PACKAGE_NAME,
        //                Constant.T_APP_CLASS_NAME));
        //((Activity) context).startActivityForResult(intent,
        //        REQUEST_CODE_T_PAY);
        PackageManager packageManager = context.getPackageManager();
        Intent intent;
        intent = packageManager.getLaunchIntentForPackage(
                Constant.T_APP_PACKAGE_NAME); //这里参数就是你要打开的app的包名
        context.startActivity(intent);
    }



}
