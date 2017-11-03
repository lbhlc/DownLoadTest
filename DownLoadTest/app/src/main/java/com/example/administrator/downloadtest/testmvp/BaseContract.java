package com.example.administrator.downloadtest.testmvp;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/11/3.
 */

public interface BaseContract {
    interface Presenter
    {
        TheView getView();
    }
    interface TheView<Presenter>
    {
        void setPresenter(Presenter presenter);
    }
}
