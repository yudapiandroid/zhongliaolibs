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
 *
 *
 * Created by 余鑫 on 2017/5/11.
 *
 *  时间选择
 *
 *
 */
public class TimePickerView {


    private FrameLayout parentFrameLayout;

    private View rootView;
    private View emptyView;
    private View cancelView;
    private View confirmView;

    private LoopView hourLoopView;
    private LoopView minLoopView;


    private boolean      showing = false;
    private boolean animationing = false;
    private List<String> hours = new ArrayList<>();
    private List<String> mins = new ArrayList<>();

    private static final String[] MINS = new String[]{
            "00","10","20","30","40","50"
    };

    public TimePickerView(Activity context){

        if(context == null){return;}
        if(context.getWindow() == null){return;}
        if(context.getWindow().getDecorView() == null){return;}
        parentFrameLayout = (FrameLayout) context.getWindow().getDecorView().findViewById(android.R.id.content);
        if(parentFrameLayout == null){return;}

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_view_time_picker,null);
        emptyView = rootView.findViewById(R.id.dialog_view_time_picker_v_empty);
        cancelView = rootView.findViewById(R.id.dialog_view_time_picker_tv_cancel);
        confirmView = rootView.findViewById(R.id.dialog_view_time_picker_tv_confrim);

        hourLoopView = (LoopView) rootView.findViewById(R.id.dialog_view_time_picker_lv_hour);
        minLoopView = (LoopView) rootView.findViewById(R.id.dialog_view_date_picker_lv_min);

        rootView.findViewById(R.id.dialog_view_date_picker_fl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
                    callBack.resultChoise(
                            hours.get(hourLoopView.getCurrentItem()),
                            mins.get(minLoopView.getCurrentItem())
                    );
                }
                dismiss();
            }
        });

        for(int i=0;i < 24;i++){
            String hour = "";
            if(i < 10){
                hour = "0" + String.valueOf(i);
            }else{
                hour = String.valueOf(i);
            }
            hours.add(hour);
        }

        for(int i=0;i < MINS.length;i++){
            mins.add(MINS[i]);
        }

    }


    public void show(ChoiseTimeCallBack callBack){
        show(hours.size() / 2,mins.size() / 2,callBack);
    }


    public void show(String currentHour,String currentMin,ChoiseTimeCallBack callBack){
        show(getHourPositionByString(currentHour),getMinPositionByString(currentMin),callBack);
    }


    private void show(int currentHour,int currentMin,ChoiseTimeCallBack callBack){

        if(showing || parentFrameLayout == null || animationing){
            return;
        }

        this.callBack = callBack;
        removeViewFromParent(rootView);
        KeyBoardUtils.hideKeyBoard((Activity) parentFrameLayout.getContext());
        hourLoopView.setNotLoop();
        hourLoopView.setList(hours);
        hourLoopView.setCurrentItem(currentHour);

        minLoopView.setNotLoop();
        minLoopView.setList(mins);
        minLoopView.setCurrentItem(currentMin);
        /**
         * 设置启动动画
         */
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

    }


    private int getMinPositionByString(String min){
        for(int i=0;i < mins.size();i++){
            if(min.equals(mins.get(i))){
                return i;
            }
        }
        return 0;
    }

    private int getHourPositionByString(String hour){
        for(int i=0;i < hours.size();i++){
            if(hour.equals(hours.get(i))){
                return i;
            }
        }
        return 0;
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
    private ChoiseTimeCallBack callBack;
    public interface ChoiseTimeCallBack{
        void resultChoise(String hour, String min);
    }


    public boolean isShowing() {
        return showing;
    }
}
