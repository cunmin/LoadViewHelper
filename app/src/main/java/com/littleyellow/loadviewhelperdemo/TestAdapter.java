package com.littleyellow.loadviewhelperdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 小黄 on 2018/2/8.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<String> mdatas;
    public TestAdapter(Context context, ArrayList<String> mdatas) {
        this.context = context;
        this.mdatas = mdatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(
                R.layout.item, null);
        MyViewHolder holder = new MyViewHolder(inflate);
        Log.d("Adapter","onCreateViewHolder======="+viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text.setText(mdatas.get(position));
        Log.d("Adapter","onBindViewHolder======="+position);
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            text= itemView.findViewById(R.id.text);
        }
    }

}
