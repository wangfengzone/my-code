package com.kosmos.brushbreakfast.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:23
 * Describe: basic MVPFragment to be achieved
 */
public abstract class BaseMVPFragment<T extends IPresenter> extends Fragment implements IView {

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(createView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (null == mPresenter) {
            throw new IllegalStateException("Please call mPresenter in BaseMVPFragment(createPresenter) to create!");
        } else {
            mPresenter.attach(this);
        }
        viewCreated(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mPresenter) {
            mPresenter.detach();
        }
    }


    protected abstract int createView();

    protected abstract T createPresenter();

    protected abstract void viewCreated(View view);
}
