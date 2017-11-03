package com.example.administrator.downloadtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.downloadtest.testmvp.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener, DownloadUtil.UpdateProgress {

    String url = "http://shouji.360tpcdn.com/170906/0a83321c7168d15f4a5971d03cc72c02/com.sigma_rt.totalcontrol_553.apk";
    /**
     * Button
     */
    private Button mButton;
    private Intent intent;
    /**
     * Button
     */
    private Button mButton2;


    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        DownloadUtil.listener = this;
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, DownloadService.class);
                intent.putExtra("url", url);
                startService(intent);
                break;
            case R.id.button2:
                mPresenter.testLog();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LBH", "销毁了");
        DownloadUtil.onDestory();
    }

    @Override
    public void getProgress(long progress) {
        Log.e("LBH", "view的progress=" + progress);
    }
}
