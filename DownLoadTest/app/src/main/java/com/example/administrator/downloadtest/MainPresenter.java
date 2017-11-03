package com.example.administrator.downloadtest;

import android.util.Log;

import com.example.administrator.downloadtest.testmvp.BaseContract;
import com.example.administrator.downloadtest.testmvp.BasePresenter;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/11/3.
 */

public class MainPresenter extends BasePresenter {
    public MainPresenter(BaseContract.TheView mView) {
        super(mView);
    }
    public void testLog()
    {
        Log.e("LBH",",MVP Test");
    }
}
