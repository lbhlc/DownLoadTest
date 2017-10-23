package com.example.administrator.downloadtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 作者libohan on 2017/10/23.
 * 邮箱:76681287@qq.com
 */

public class DownloadService extends Service {
    String url;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        url=intent.getStringExtra("url");
        DownloadUtil.downloadApk(url,"1.0",this);
        Log.e("LBH","开始下载了");
        return super.onStartCommand(intent, flags, startId);

    }
}
