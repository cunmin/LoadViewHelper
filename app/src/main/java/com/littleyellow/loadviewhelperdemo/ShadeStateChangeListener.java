package com.littleyellow.loadviewhelperdemo;

import android.animation.Animator;
import android.view.View;

import com.littleyellow.loadviewhelper.StateChangeListener;

import static android.view.View.GONE;

/**
 * Created by 小黄 on 2018/3/15.
 */

public class ShadeStateChangeListener implements StateChangeListener {


    @Override
    public void onShowContent(int oldState,View fromView, View toView) {
        resetState(fromView,toView);
        animate(fromView,toView);
    }

    @Override
    public void onShowLoad(int oldState,View fromView, View toView) {
        resetState(fromView,toView);
    }

    @Override
    public void onShowEmpty(int oldState,View fromView, View toView) {
        resetState(fromView,toView);
    }

    @Override
    public void onShowError(int oldState,View fromView, View toView) {
        resetState(fromView,toView);
    }

    @Override
    public void onCustom(int oldState, int newState, View fromView, View toView) {
        resetState(fromView,toView);
    }

    private void resetState(View fromView, View toView){
        if(null!=fromView){
            fromView.clearAnimation();
            fromView.setVisibility(GONE);
        }
        if(null!=toView) {
            toView.clearAnimation();
            toView.setVisibility(View.VISIBLE);
        }
    }

    private void animate(final View fromView, final View toView){
        if(fromView==toView){
            return;
        }
        if(null!=toView) {
            toView.setAlpha(1f);
            toView.setVisibility(View.VISIBLE);
        }
        if(null!=fromView) {
            fromView.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            fromView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fromView.setVisibility(GONE);
                            fromView.setAlpha(1f);
                            if(null!=toView) {
                                toView.setAlpha(1f);
                                toView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            if(null!=toView) {
                                toView.setAlpha(1f);
                                toView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }
    }
}
