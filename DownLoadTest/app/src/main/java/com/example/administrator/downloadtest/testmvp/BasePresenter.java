package com.example.administrator.downloadtest.testmvp;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/11/3.
 */

public class BasePresenter<T extends BaseContract.TheView> implements BaseContract.Presenter {
    private T mView;

    public BasePresenter(T mView) {
        this.mView = mView;
        this.mView.setPresenter(this);//必须注入presenter否则基类的baseactivity的mPresenter为空;
    }

    @Override
    public BaseContract.TheView getView() {
        return mView;
    }
}
