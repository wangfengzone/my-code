package com.kosmos.brushbreakfast.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kosmos.brushbreakfast.utils.StatusBarUtil;
import com.kosmos.brushbreakfast.utils.ThemeUtil;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:26
 * Describe: basic BaseMVPActivity to be achieved
 */
public abstract class BaseMVPActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        ThemeUtil.setStatusBar(this);
        StatusBarUtil.setTitleStatusBarColor(this);
        mPresenter = createPresenter();
        if (null == mPresenter) {
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        } else {
            mPresenter.attach(this);
        }
        viewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detach();
        }
    }

    protected abstract int createView();

    protected abstract T createPresenter();

    protected abstract void viewCreated();
}