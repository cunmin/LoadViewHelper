package com.littleyellow.loadviewhelper;

import android.view.View;

/**
 * Created by 小黄 on 2018/3/15.
 */

public interface StateChangeListener {

    void onShowContent(int oldState,View fromView,View contentView);

    void onShowLoad(int oldState,View fromView,View loadView);

    void onShowEmpty(int oldState,View fromView,View emptyView);

    void onShowError(int oldState,View fromView,View errorView);

    void onCustom(int oldState,int newState,View fromView,View customView);

}
