package com.littleyellow.loadviewhelper;

/**
 * Created by 小黄 on 2018/3/15.
 */

public interface DefaultSettingCallBack {

    int loadLayoutRes();

    int emptyLayoutRes();

    int errorLayoutRes();

    StateChangeListener stateChangeListener();

}
