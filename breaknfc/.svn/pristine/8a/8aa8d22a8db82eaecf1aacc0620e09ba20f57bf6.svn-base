package com.kosmos.brushbreakfast.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kosmos.brushbreakfast.R;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 17:35
 * Describe: statusBar tools
 */
public final class StatusBarUtil {
    public static final int MIN_API = 19;

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     */
    public static void setPaddingSmart(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4以上
            //1.先拿到状态栏高度
            int statusHeight = getStatusBarHeight(context);
            ViewGroup.LayoutParams topParams = view.getLayoutParams();
            topParams.height += statusHeight;
            view.setLayoutParams(topParams);
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusHeight, view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }

    /**
     * 设置沉浸式
     *
     * @param context
     */
    public static void setTitleStatusBarColor(Activity context) {
        RelativeLayout statusbarTitle = context.findViewById(R.id.status_bar);
        if (statusbarTitle != null) {
            ColorDrawable colordDrawable = (ColorDrawable) statusbarTitle.getBackground();
            int color = colordDrawable.getColor();
            statusbarTitle.setBackgroundColor(color);
            setPaddingSmart(context.getBaseContext(), statusbarTitle);
            return;
        }
    }

    public static void setFragmentTitleStatusBarColor(Activity context, View view) {
        RelativeLayout statusbarTitle = view.findViewById(R.id.status_bar);
        if (statusbarTitle != null) {
            ColorDrawable colordDrawable = (ColorDrawable) statusbarTitle.getBackground();
            int color = colordDrawable.getColor();
            statusbarTitle.setBackgroundColor(color);
            setPaddingSmart(context.getBaseContext(), statusbarTitle);
            return;
        }
    }
}
