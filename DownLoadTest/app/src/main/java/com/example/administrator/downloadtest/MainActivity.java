package com.example.administrator.downloadtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String url = "http://shouji.360tpcdn.com/170906/0a83321c7168d15f4a5971d03cc72c02/com.sigma_rt.totalcontrol_553.apk";
    /**
     * Button
     */
    private Button mButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                intent=new Intent(this,DownloadService.class);
                intent.putExtra("url",url);
                startService(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LBH","销毁了");
        DownloadUtil.onDestory();
    }
}
