package com.example.administrator.downloadtest.testmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.downloadtest.R;

public abstract class BaseActivity<Presenter extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.TheView<Presenter>{

    protected Presenter mPresenter;
    public abstract Presenter initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initPresenter();
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter=presenter;
    }
}
