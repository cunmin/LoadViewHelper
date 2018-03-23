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
        animate(fromView,toView);
    }

    @Override
    public void onShowLoad(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowEmpty(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowError(int oldState,View fromView, View toView) {
        toView.setVisibility(View.VISIBLE);
    }

    private void animate(final View fromView, final View toView){
        toView.setAlpha(1f);
        toView.setVisibility(View.VISIBLE);

        fromView.clearAnimation();
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
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        toView.setAlpha(1f);
                        toView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }
}
