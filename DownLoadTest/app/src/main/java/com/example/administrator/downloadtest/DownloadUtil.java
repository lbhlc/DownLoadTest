package com.example.administrator.downloadtest;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;

/**
 * 作者libohan on 2017/10/23.
 * 邮箱:76681287@qq.com
 */

public class DownloadUtil{
    public static UpdateProgress listener;
    private static Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private static final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Log.e("LBH","GOGOGO");
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
            final Cursor c = downloadManager.query(query);
            if (!c.moveToFirst())
            {
                Log.e("LBH","出问题了");
                c.close();
                return;
            }
            long downloadedSoFar = c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int bytes_total = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Log.e("LBH","progress="+(downloadedSoFar*100)/bytes_total);
            long result=(downloadedSoFar*100)/bytes_total;
            listener.getProgress((downloadedSoFar*100)/bytes_total);
            handler.postDelayed(this,500);
            if (result==100)
            {
                handler.removeCallbacks(this);
                c.close();
            }

        }

    };
   private final static BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus(context);//检查下载状态
        }
    };



    private static DownloadManager downloadManager;
    private static long mTaskId;
    private static String downloadPath;

    private static void checkDownloadStatus(Context context) {
        long downloadedSoFar;
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (!c.moveToFirst())
        {
            c.close();
            return;
        }

        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.e("LBH",">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.e("LBH",">>>下载延时");
                case DownloadManager.STATUS_RUNNING:
                    Log.e("LBH",">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.e("LBH",">>>下载完成");
                    //下载完成安装APK
                    downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "downloadtest";

                    installAPK(new File(downloadPath),context);
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.e("LBH",">>>下载失败");
                    break;
                default:
                   break;
            }
        }

    }

    private static void installAPK(File file,Context context) {

        if (!file.exists())
        {
            try {
               boolean result= file.createNewFile();
                Log.e("LBH","downloadPath="+file.getPath());
            } catch (IOException e) {
               Log.e("LBH","创建失败");
            }
        }else
        if (!file.exists())
        {
            return;
        }
        Log.e("LBH","downloadPath="+file.getPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //在服务中开启activity必须设置flag,后面解释
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }

    public static void downloadApk(String versionUrl, String versionName, Context context)
  {

      DownloadManager.Request request=new DownloadManager.Request(Uri.parse(versionUrl));
      request.setAllowedOverRoaming(false);//是否可以漫游下载
      //设置文件类型，可以在下载结束后自动打开该文件
      MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
      String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
      request.setMimeType(mimeString);
//在通知栏中显示，默认就是显示的
      request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
      request.setVisibleInDownloadsUi(true);

      //sdcard的目录下的download文件夹，必须设置
      request.setDestinationInExternalPublicDir("/download/", versionName);
      //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

      //将下载请求加入下载队列
      downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
      //加入下载队列后会给该任务返回一个long型的id，
      //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
      mTaskId = downloadManager.enqueue(request);
   handler.postDelayed(runnable,500);
      //注册广播接收者，监听下载状态
      context.registerReceiver(receiver,
              new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


  }
  public static void onDestory()
  {
      handler.removeCallbacks(runnable);
      handler=null;
  }
  interface UpdateProgress
  {
      public void getProgress(long progress);
  }
}
