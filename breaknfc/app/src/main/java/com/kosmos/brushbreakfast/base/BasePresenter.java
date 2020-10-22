package com.kosmos.brushbreakfast.base;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:21
 * Describe: basic presenter to be achieved
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    protected V mView;

    @Override
    public void attach(V view) {
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
