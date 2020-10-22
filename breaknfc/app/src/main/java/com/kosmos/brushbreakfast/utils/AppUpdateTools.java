package com.kosmos.brushbreakfast.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.kosmos.brushbreakfast.R;
import com.kosmos.brushbreakfast.activities.AtyInit;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

@SuppressLint("SdCardPath")
public class AppUpdateTools {
    private String url;

    private String addr = "/sdcard/breakfast.apk";

    public static boolean sIsDownloading = false;

    Context context;
    private RemoteViews views;
    private NotificationManager nm;
    private Notification notification;
    private boolean flag = true;

    public AppUpdateTools(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public void notifyAndInstallApp() {
        isDirExist();
        views = new RemoteViews(context.getPackageName(), R.layout.download_view);
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder1 = new Notification.Builder(context);
        //8.0以上必加
        builder1.setSmallIcon(R.mipmap.icon_app);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上加
            builder1.setChannelId(context.getPackageName());

            //9.0以上加
            NotificationChannel channel = new NotificationChannel(
                    context.getPackageName(),
                    "会话类型",
                    //这块Android9.0分类的比较完整，你创建多个这样的东西，你可以在设置里边显示那个或者第几个
                    NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);
        }
        notification = builder1.build();
        notification.contentView = views;
        notification.icon = R.mipmap.icon_app;
        notification.tickerText = "";
        notification.when = System.currentTimeMillis();
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.contentView = views;
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, AtyInit.class), 0);
        notification.contentIntent = pi;
        nm.notify(1, notification); // 通过通知管理器发送通知
        downLoadApp();
    }

    private void downLoadApp() {
        HttpUtils http = new HttpUtils();
        http.download(url, addr, true, false, new RequestCallBack<File>() {
            @Override
            public void onStart() {
                Log.i("onStart", "onStart");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                sIsDownloading = true;
                if (flag) {
                    flag = false;
                    nm.notify(1, notification);
                }
                if (current > 0 && current < total) {
                    views.setTextViewText(R.id.tvProcess, "已下载" + (int) ((double) current / (double) total * 100) + "%");
                    views.setProgressBar(R.id.progressBar, (int) total, (int) current, false);
                    notification.contentView = views;
                    nm.notify(1, notification);
                } else if (total == current) {
                    nm.cancel(1);
                    sIsDownloading = false;
                    install();
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                sIsDownloading = false;
            }

            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                sIsDownloading = false;
            }
        });
    }

    private void install() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//		/*intent.setDataAndType(Uri.fromFile(new File(addr)),
//				"application/vnd.android.package-archive");*/
//        intent.setDataAndType(UT.getFileUri(new File(addr), context),
//                "application/vnd.android.package-archive");
//        context.startActivity(intent);

        UT.installApk(context, addr);
    }


    private void isDirExist() {
        File file = new File(addr);
        if (file.exists()) {
            file.delete();
        }
    }

}
