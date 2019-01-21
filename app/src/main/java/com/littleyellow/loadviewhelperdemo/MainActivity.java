package com.littleyellow.loadviewhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.littleyellow.loadviewhelper.DefaultSettingCallBack;
import com.littleyellow.loadviewhelper.LoadViewHelper;
import com.littleyellow.loadviewhelper.StateChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> mdatas = new ArrayList<>();
    LoadViewHelper loadViewHelper;

    public final int LOGIN_LAYOUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadViewHelper.DefaultLayoutListener(new SettingCallBack());
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.ll);
        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadViewHelper.showLoading();
            }
        });
        findViewById(R.id.error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadViewHelper.showError();
            }
        });
        findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadViewHelper.showEmpty();
            }
        });
        findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout= (FrameLayout) viewGroup.getChildAt(0);
                int count = frameLayout.getChildCount();
                loadViewHelper.showContent();
            }
        });

        findViewById(R.id.custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadViewHelper.showCustom(LOGIN_LAYOUT);
            }
        });
        loadViewHelper = LoadViewHelper.newBuilder()
//                .emptyLayoutRes(R.layout.empty_view)
//                .errorLayoutRes(R.layout.error_view)
//                .errorLayoutRes(R.layout.loading_view)
                .addCustomLayout(LOGIN_LAYOUT,R.layout.login_view)
                .stateChangeListener(new ShadeStateChangeListener(){
                    @Override
                    public void onShowContent(int oldState, View fromView, View toView) {
                        super.onShowContent(oldState, fromView, toView);
                    }

                    @Override
                    public void onShowLoad(int oldState, View fromView, View toView) {
                        super.onShowLoad(oldState, fromView, toView);
                    }

                    @Override
                    public void onShowEmpty(int oldState, View fromView, View toView) {
                        super.onShowEmpty(oldState, fromView, toView);
                    }

                    @Override
                    public void onShowError(int oldState, View fromView, View toView) {
                        super.onShowError(oldState, fromView, toView);
                    }

                    @Override
                    public void onCustom(int oldState, int newState, View fromView, View toView) {
                        super.onCustom(oldState, newState, fromView, toView);
                    }
                })
                .build()
                .attach(viewGroup);
    }

    public static class SettingCallBack implements DefaultSettingCallBack {

        @Override
        public int loadLayoutRes() {
            return R.layout.loading_view;
        }

        @Override
        public int emptyLayoutRes() {
            return R.layout.empty_view;
        }

        @Override
        public int errorLayoutRes() {
            return R.layout.error_view;
        }

        @Override
        public StateChangeListener stateChangeListener() {
            return null;
        }
    }
}
