package com.example.lily.baseframeapplication.base.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import com.example.lily.baseframeapplication.model.event.InstallApkEvent;
import java.io.File;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by lily on 2017/11/10.
 */

public class ApkInstallReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction()
                  .equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long localDownloadId = UpdaterUtils.getLocalDownloadId(context);
            if (downloadApkId == localDownloadId) {
                Logger.get()
                      .d("download complete. downloadId is " + downloadApkId);
                installApk(context, downloadApkId);
            }
        }
        else if (intent.getAction()
                       .equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            //处理 如果还未完成下载，用户点击Notification
            Intent viewDownloadIntent = new Intent(
                    DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }


    public static void installApk(Context context, long downloadApkId) {
        try {
            //有可能下载失败，故要捕获异常
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadApkId);
            DownloadManager dm = (DownloadManager) context.getSystemService(
                    Context.DOWNLOAD_SERVICE);
            Cursor downloadResult = dm.query(query);
            if (downloadResult.moveToFirst()) {
                String filePath = null;
                String downloadFileLocalUri = downloadResult.getString(
                        downloadResult.getColumnIndex(
                                DownloadManager.COLUMN_LOCAL_URI));
                if (downloadFileLocalUri != null) {
                    filePath = new File(Uri.parse(downloadFileLocalUri)
                                           .getPath()).getAbsolutePath();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                    Uri apkUri = FileProvider.getUriForFile(context,
                            "com.example.lily.baseframeapplication.fileprovider",
                            new File(filePath));
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri,
                            "application/vnd.android.package-archive");
                }
                else {
                    intent.setDataAndType(Uri.fromFile(new File(filePath)),
                            "application/vnd.android.package-archive");
                }
                context.startActivity(intent);
            }
            EventBus.getDefault().post(new InstallApkEvent(downloadApkId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
