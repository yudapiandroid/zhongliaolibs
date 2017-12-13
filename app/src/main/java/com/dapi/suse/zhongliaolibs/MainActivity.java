package com.dapi.suse.zhongliaolibs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrRecyclerView;

public class MainActivity extends AppCompatActivity {

    private PtrRecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (PtrRecyclerView) findViewById(R.id.prv_content);
        GridLayoutManager manager = new GridLayoutManager(this,1);
        view.setLayoutManager(manager);
        view.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(parent.getContext());
                return new TestViewHolder(textView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        view.setLoadDataInterface(new PtrRecyclerView.PtrRecyclerViewInterface() {
            @Override
            public void loadData(boolean isFrist) {
                view.getAdapter().notifyDataSetChanged();
            }
            @Override
            public boolean haveMoreData() {
                return false;
            }
        });
        view.fristLoadData();
    } // end m
}


class TestViewHolder extends RecyclerView.ViewHolder{
    public TestViewHolder(View itemView) {
        super(itemView);
    }
}