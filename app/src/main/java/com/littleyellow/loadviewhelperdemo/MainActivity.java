package com.littleyellow.loadviewhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.littleyellow.loadviewhelper.DefaultSettingCallBack;
import com.littleyellow.loadviewhelper.LoadViewHelper;
import com.littleyellow.loadviewhelper.ShadeStateChangeListener;
import com.littleyellow.loadviewhelper.StateChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> mdatas = new ArrayList<>();
    LoadViewHelper loadViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LoadViewHelper.addDefaultLayoutListener(new SettingCallBack());
        final RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        for (int i=0;i<100;i++)
            mdatas.add("这是测试的"+i);
        TestAdapter adapter=new TestAdapter(this,mdatas);
        recyclerView.setAdapter(adapter);
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
                loadViewHelper.showContent();
            }
        });
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.ll);
        loadViewHelper = LoadViewHelper.newBuilder()
//                .emptyLayoutRes(R.layout.empty_view)
//                .errorLayoutRes(R.layout.error_view)
                .loadLayoutRes(R.layout.loading_view)
                .stateChangeListener(new ShadeStateChangeListener(){
                    @Override
                    public void onShowError(int oldState, View fromView, View toView) {
                        super.onShowError(oldState, fromView, toView);
                        toView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadViewHelper.showLoading();
                            }
                        });
                    }
                })
                .build()
                .attach(viewGroup);
    }

    public static class SettingCallBack implements DefaultSettingCallBack {

        @Override
        public int loadLayoutRes() {
            return 0;
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
        public StateChangeListener StateChangeListener() {
            return new ShadeStateChangeListener();
        }
    }
}
