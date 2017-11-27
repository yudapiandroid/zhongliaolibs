package com.dapi.suse.wheelpickerview.prefabs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.dapi.suse.wheelpickerview.KeyBoardUtils;
import com.dapi.suse.wheelpickerview.R;
import com.dapi.suse.wheelpickerview.pickerview.LoopView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 余鑫 on 2017/5/11.
 */

public class NormalOnePickerView {


    private FrameLayout parentFrameLayout;

    private View rootView;
    private View emptyView;
    private View cancelView;
    private View confirmView;
    private View contentView;

    private LoopView loopView;
    private Activity content;

    private boolean      showing = false;
    private boolean animationing = false;
    private List<Object> objects = new ArrayList<>();

    public NormalOnePickerView(Activity context){

        if(context == null){return;}
        if(context.getWindow() == null){return;}
        if(context.getWindow().getDecorView() == null){return;}
        parentFrameLayout = (FrameLayout) context.getWindow().getDecorView().findViewById(android.R.id.content);
        if(parentFrameLayout == null){return;}
        this.content = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_view_normal_one,null);
        emptyView = rootView.findViewById(R.id.dialog_view_normal_one_v_empty);
        cancelView = rootView.findViewById(R.id.dialog_view_normal_one_tv_cancel);
        confirmView = rootView.findViewById(R.id.dialog_view_normal_one_tv_confrim);
        loopView = (LoopView) rootView.findViewById(R.id.dialog_view_normal_one_lv);
        contentView = rootView.findViewById(R.id.dialog_view_normal_one_ll_content);
        contentView.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {}});

        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack != null){
                    callBack.choiseCallBack(objects.get(loopView.getCurrentItem()));
                }
                dismiss();
            }
        });
    }



    public void show(List<Object> objs,NormalOneCallBack callBack,int position){

        if(showing || parentFrameLayout == null || objs == null || objs.size() == 0 || animationing){
            return;
        }

        KeyBoardUtils.hideKeyBoard(content);

        objects.clear();
        objects.addAll(objs);

        this.callBack = callBack;
        removeViewFromParent(rootView);
        List<String> items = new ArrayList<>();
        for(int i = 0;i < objects.size();i++){
            items.add(callBack.transform(objects.get(i)));
        }

        loopView.setNotLoop();
        loopView.setList(items);

        rootView.setVisibility(View.INVISIBLE);
        parentFrameLayout.addView(rootView);
        parentFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f,1.0f);
                TranslateAnimation translateAnimation = new TranslateAnimation(0,0,rootView.getMeasuredHeight(),0);

                AnimationSet set = new AnimationSet(true);
                set.addAnimation(alphaAnimation);
                set.addAnimation(translateAnimation);

                set.setDuration(500);
                set.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        rootView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animationing = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                rootView.startAnimation(set);
                animationing = true;
            }
        });

        showing = true;
        loopView.setCurrentItem(position);
    }


    public void dismiss(){

        if(!showing || animationing){
            return;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,rootView.getMeasuredHeight());
        translateAnimation.setDuration(300);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationing = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationing = false;
                removeViewFromParent(rootView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(translateAnimation);
        showing = false;

    }

    private void removeViewFromParent(View view){
        if(view == null || view.getParent() == null){
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
    }

    private NormalOneCallBack callBack;

    public interface NormalOneCallBack{
        String transform(Object obj);
        void choiseCallBack(Object obj);
    }

}
