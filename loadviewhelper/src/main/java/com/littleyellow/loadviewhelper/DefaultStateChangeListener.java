package com.littleyellow.loadviewhelper;

import android.view.View;

/**
 * Created by 小黄 on 2018/3/15.
 */

public class DefaultStateChangeListener implements StateChangeListener {


    @Override
    public void onShowContent(int oldState,View from, View to) {
        if(null!=from) {
            from.setVisibility(View.GONE);
        }
        to.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowLoad(int oldState,View from, View to) {
        if(null!=from) {
            from.setVisibility(View.GONE);
        }
        to.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowEmpty(int oldState,View from, View to) {
        if(null!=from) {
            from.setVisibility(View.GONE);
        }
        to.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowError(int oldState,View from, View to) {
        if(null!=from) {
            from.setVisibility(View.GONE);
        }
        to.setVisibility(View.VISIBLE);
    }
}
