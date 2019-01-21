package com.littleyellow.loadviewhelper;

import android.support.annotation.LayoutRes;

/**
 * Created by 小黄 on 2018/3/15.
 */

public interface DefaultSettingCallBack {


    @LayoutRes
    int loadLayoutRes();
    @LayoutRes
    int emptyLayoutRes();
    @LayoutRes
    int errorLayoutRes();

    StateChangeListener stateChangeListener();

}
