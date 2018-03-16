package com.littleyellow.loadviewhelper;

import android.animation.Animator;
import android.view.View;

import static android.view.View.GONE;

/**
 * Created by 小黄 on 2018/3/15.
 */

public class ShadeStateChangeListener implements StateChangeListener {


    @Override
    public void onShowContent(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
        finishAnimate(fromView);
    }

    @Override
    public void onShowLoad(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
        finishAnimate(fromView);
    }

    @Override
    public void onShowEmpty(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
        finishAnimate(fromView);
    }

    @Override
    public void onShowError(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
        finishAnimate(fromView);
    }

    private void finishAnimate(final View view){
//        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(GONE);
                        view.setAlpha(1f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }
}
