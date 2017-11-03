package com.example.administrator.downloadtest.testmvp;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/11/3.
 */

public class BaseView<T extends BaseContract.Presenter> implements BaseContract.TheView<T> {
    private T presenter;
    @Override
    public void setPresenter(T t) {
        this.presenter=t;
    }
}
