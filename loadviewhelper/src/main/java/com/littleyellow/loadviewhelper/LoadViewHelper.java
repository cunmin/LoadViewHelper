package com.littleyellow.loadviewhelper;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by 小黄 on 2018/3/7.
 */

public class LoadViewHelper {

    public static final int STATE_ERROR = 3;

    public static final int STATE_EMPTY = 2;

    public static final int STATE_LOADING = 1;

    public static final int STATE_CONTENT = 0;

    static DefaultSettingCallBack layoutListener;

    private StateChangeListener stateChangeListener;

//    private View emptyView,errorView,loadView;

    private SparseArray<View> views = new SparseArray<>();

    private int loadLayoutRes;

    private int emptyLayoutRes;

    private int errorLayoutRes;



    private int state = STATE_CONTENT;

    private LoadViewHelper(Builder builder) {
        loadLayoutRes = builder.loadLayoutRes;
        emptyLayoutRes = builder.emptyLayoutRes;
        errorLayoutRes = builder.errorLayoutRes;
        stateChangeListener = builder.stateChangeListener;
        if(null==layoutListener){
            layoutListener = new DefaultSettingCallBack() {
                @Override
                public int loadLayoutRes() {
                    return 0;
                }

                @Override
                public int emptyLayoutRes() {
                    return 0;
                }

                @Override
                public int errorLayoutRes() {
                    return 0;
                }

                @Override
                public StateChangeListener stateChangeListener() {
                    return new DefaultStateChangeListener();
                }
            };
        }
        if(0 == loadLayoutRes){
            loadLayoutRes = layoutListener.loadLayoutRes();
        }
        if(0 == emptyLayoutRes){
            emptyLayoutRes = layoutListener.emptyLayoutRes();
        }
        if(0 == errorLayoutRes){
            errorLayoutRes = layoutListener.errorLayoutRes();
        }
        if(null == stateChangeListener){
            stateChangeListener = layoutListener.stateChangeListener();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public LoadViewHelper attach(ViewGroup container){
        Context context = container.getContext();

        int count = container.getChildCount();
        if(count>1){
            throw new IllegalArgumentException("子控件数超过1");
        }

        View emptyView = null;
        if(0==emptyLayoutRes){
             throw new IllegalArgumentException("请设置空布局资源或者设置全局的空布局资源");
        }else{
            emptyView = LayoutInflater.from(context).inflate(emptyLayoutRes, container, false);
        }
        View errorView = null;
        if(0==errorLayoutRes){
            throw new IllegalArgumentException("请设置错误布局资源或者设置全局的错误布局资源");
        }else{
            errorView = LayoutInflater.from(context).inflate(errorLayoutRes, container, false);
        }
        View loadView = null;
        if(0==loadLayoutRes){
            throw new IllegalArgumentException("请设置加载布局资源或者设置全局的加载布局资源");
        }else{
            loadView  =  LayoutInflater.from(context).inflate(loadLayoutRes, container, false);
        }

        if(null==stateChangeListener){
            stateChangeListener = new DefaultStateChangeListener();
        }

        View contentView = container.getChildAt(0);
        if(null!=contentView){
            container.removeView(contentView);
            FrameLayout frameLayout = new FrameLayout(context);
            container.addView(frameLayout,getMatchParentLayoutParams());
            frameLayout.addView(contentView);
            frameLayout.addView(emptyView,getMatchParentLayoutParams());
            frameLayout.addView(errorView,getMatchParentLayoutParams());
            frameLayout.addView(loadView,getMatchParentLayoutParams());

            views.put(STATE_EMPTY,emptyView);
            views.put(STATE_ERROR,errorView);
            views.put(STATE_LOADING,loadView);
            views.put(STATE_CONTENT,contentView);
            hideOtherViews(STATE_CONTENT);
//            showContent();
        }
        return this;
    }

    private ViewGroup.LayoutParams getMatchParentLayoutParams(){
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void showEmpty() {
        View contentView = views.get(STATE_CONTENT);
        if(state==STATE_EMPTY||null==contentView){
            return;
        }
        hideOtherViews(STATE_EMPTY);
        View showView = views.get(state);
        View emptyView = views.get(STATE_EMPTY);
        stateChangeListener.onShowEmpty(state,showView,emptyView);
        state = STATE_EMPTY;
    }

    public void showError() {
        View contentView = views.get(STATE_CONTENT);
        if(state==STATE_ERROR||null==contentView){
            return;
        }
        hideOtherViews(STATE_ERROR);
        View showView = views.get(state);
        View errorView = views.get(STATE_ERROR);
        stateChangeListener.onShowError(state,showView,errorView);
        state = STATE_ERROR;
    }

    public void showLoading() {
        View contentView = views.get(STATE_CONTENT);
        if(state==STATE_LOADING||null==contentView){
            return;
        }
        hideOtherViews(STATE_LOADING);
        View showView = views.get(state);
        View loadView = views.get(STATE_LOADING);
        stateChangeListener.onShowLoad(state,showView,loadView);
        state = STATE_LOADING;
    }

    public void showContent() {
//        for (int i=0;i<views.size();i++) {
//            int state = views.keyAt(i);
//            if(this.state!=state){
//                views.valueAt(i).setVisibility(GONE);
//            }
//        }
        View contentView = views.get(STATE_CONTENT);
        if(state==STATE_CONTENT||null==contentView){
            return;
        }
        hideOtherViews(STATE_CONTENT);
        View showView = views.get(state);
        stateChangeListener.onShowContent(state,showView,contentView);
        state = STATE_CONTENT;
    }


    public static final class Builder {
        private int loadLayoutRes;
        private int emptyLayoutRes;
        private int errorLayoutRes;
        private StateChangeListener stateChangeListener;

        private Builder() {
        }

        public Builder loadLayoutRes(int loadLayoutRes) {
            this.loadLayoutRes = loadLayoutRes;
            return this;
        }

        public Builder emptyLayoutRes(int emptyLayoutRes) {
            this.emptyLayoutRes = emptyLayoutRes;
            return this;
        }

        public Builder errorLayoutRes(int errorLayoutRes) {
            this.errorLayoutRes = errorLayoutRes;
            return this;
        }

        public Builder stateChangeListener(StateChangeListener stateChangeListener) {
            this.stateChangeListener = stateChangeListener;
            return this;
        }

        public LoadViewHelper build() {
            return new LoadViewHelper(this);
        }
    }

    public static void addDefaultLayoutListener(DefaultSettingCallBack listener){
        layoutListener = listener;
    }

    private void hideOtherViews(int exceptState){
        for (int i=0;i<views.size();i++) {
            int state = views.keyAt(i);
            if(STATE_CONTENT!=state && exceptState!=state) {
                views.valueAt(i).setVisibility(View.GONE);
            }else{
                views.valueAt(i).setVisibility(View.VISIBLE);
            }
        }
    }
}
