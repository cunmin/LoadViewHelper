package com.littleyellow.loadviewhelper;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by 小黄 on 2018/3/7.
 */

public class LoadViewHelper {

    public static final int STATE_ERROR = -3;

    public static final int STATE_EMPTY = -2;

    public static final int STATE_LOADING = -1;

    public static final int STATE_CONTENT = 0;

    static DefaultSettingCallBack layoutListener;

    private StateChangeListener stateChangeListener;

    private SparseArray<View> views = new SparseArray<>();

    private SparseArray<Integer> layoutRes;

//    private int loadLayoutRes;
//
//    private int emptyLayoutRes;
//
//    private int errorLayoutRes;

    private int state = STATE_CONTENT;

    private ViewGroup container;

    private FrameLayout frameLayout;

    private LoadViewHelper(Builder builder) {
        layoutRes = builder.layoutRes;
        stateChangeListener = builder.stateChangeListener;
        if(null!=layoutListener) {
            if (0 == builder.loadLayoutRes) {
                builder.loadLayoutRes = layoutListener.loadLayoutRes();
            }
            if (0 == builder.emptyLayoutRes) {
                builder.emptyLayoutRes = layoutListener.emptyLayoutRes();
            }
            if (0 == builder.errorLayoutRes) {
                builder.errorLayoutRes = layoutListener.errorLayoutRes();
            }
            if(null == stateChangeListener){
                stateChangeListener = layoutListener.stateChangeListener();
            }
        }
        layoutRes.put(STATE_LOADING,builder.loadLayoutRes);
        layoutRes.put(STATE_EMPTY,builder.emptyLayoutRes);
        layoutRes.put(STATE_ERROR,builder.errorLayoutRes);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public LoadViewHelper attach(final ViewGroup container){
        this.container = container;
        Context context = container.getContext();
        int count = container.getChildCount();
        if(count > 1){
            throw new IllegalArgumentException("子控件数超过1");
        }
        if(null==stateChangeListener){
            stateChangeListener = new DefaultStateChangeListener();
        }

        View contentView = container.getChildAt(0);
        frameLayout = new FrameLayout(context);
        if(null!=contentView){
            container.removeView(contentView);
            container.addView(frameLayout,getMatchParentLayoutParams());
            frameLayout.addView(contentView);
            views.put(STATE_CONTENT,contentView);
        }else{
            container.addView(frameLayout,getMatchParentLayoutParams());
        }
        return this;
    }


    public void showEmpty() {
        int emptyLayoutRes = layoutRes.get(STATE_EMPTY);
        hideOtherViews(STATE_EMPTY,emptyLayoutRes);
        View showView = views.get(state);
        View emptyView = views.get(STATE_EMPTY);
        stateChangeListener.onShowEmpty(state,showView,emptyView);
        state = STATE_EMPTY;
    }

    public void showError() {
        int res = layoutRes.get(STATE_ERROR);
        hideOtherViews(STATE_ERROR,res);
        View showView = views.get(state);
        View errorView = views.get(STATE_ERROR);
        stateChangeListener.onShowError(state,showView,errorView);
        state = STATE_ERROR;
    }

    public void showLoading() {
        int res = layoutRes.get(STATE_LOADING);
        hideOtherViews(STATE_LOADING,res);
        View showView = views.get(state);
        View loadView = views.get(STATE_LOADING);
        stateChangeListener.onShowLoad(state,showView,loadView);
        state = STATE_LOADING;
    }

    public void showContent() {
        hideOtherViews(STATE_CONTENT,0);
        View showView = views.get(state);
        View contentView = views.get(STATE_CONTENT);
        stateChangeListener.onShowContent(state,showView,contentView);
        state = STATE_CONTENT;
    }

    public void showCustom(int state) {
        int res = layoutRes.get(state,-4);
        if(-4 == res){
            throw new IllegalArgumentException("请添加状态为"+state+"对应的布局资源");
        }
        hideOtherViews(state,res);
        View showView = views.get(this.state);
        View customView = views.get(state);
        stateChangeListener.onCustom(state,state,showView,customView);
        this.state = state;
    }


    public static final class Builder {
        private int loadLayoutRes;
        private int emptyLayoutRes;
        private int errorLayoutRes;
        private StateChangeListener stateChangeListener;
        private SparseArray<Integer> layoutRes = new SparseArray<>();

        private Builder() {
        }

        public Builder loadLayoutRes(@LayoutRes int loadLayoutRes) {
            this.loadLayoutRes = loadLayoutRes;
            return this;
        }

        public Builder emptyLayoutRes(@LayoutRes int emptyLayoutRes) {
            this.emptyLayoutRes = emptyLayoutRes;
            return this;
        }

        public Builder errorLayoutRes(@LayoutRes int errorLayoutRes) {
            this.errorLayoutRes = errorLayoutRes;
            return this;
        }

        public Builder stateChangeListener(StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
            return this;
        }

        public Builder addCustomLayout(@IntRange(from = 1) int state, @LayoutRes int LayoutRes) {
            layoutRes.put(state,LayoutRes);
            return this;
        }

        public LoadViewHelper build() {
            return new LoadViewHelper(this);
        }
    }

    public static void DefaultLayoutListener(DefaultSettingCallBack listener){
        layoutListener = listener;
    }

    public int getState(){
        return state;
    }

    private View hideOtherViews(int exceptState,int layoutRes){
        boolean isInflate = true;
        for (int i=0;i<views.size();i++) {
            int state = views.keyAt(i);
            View view = views.valueAt(i);
            if(null != view) {
                if (exceptState==state || STATE_CONTENT==state) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }else if(STATE_CONTENT!=state){
                isInflate = false;
            }
        }
        View exceptView = views.get(exceptState);
        if(!isInflate || null==exceptView) {
            addLayout(exceptState, layoutRes);
        }
        return exceptView;
    }

    private void addLayout(int state,int layoutRes){
        try {
            if(STATE_CONTENT == state){
                return;
            }
            Context context = container.getContext();
            View view  =  LayoutInflater.from(context).inflate(layoutRes, container, false);
            frameLayout.addView(view,getMatchParentLayoutParams());
            views.put(state,view);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("请设置布局资源");
        }
    }

    private ViewGroup.LayoutParams getMatchParentLayoutParams(){
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
