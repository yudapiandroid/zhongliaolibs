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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mr.LeeTong on 2017/7/3.
 */
public class YearsMonthPickerView {

    private FrameLayout parentFrameLayout;

    private View rootView;
    private View emptyView;
    private View cancelView;
    private View confirmView;

    private LoopView yearLoopView;
    private LoopView monthLoopView;

    private boolean showing = false;
    private boolean animationing = false;


    private List<String> years = new ArrayList<>();
    private List<String> months = new ArrayList<>();

    public YearsMonthPickerView(Activity context){

        if(context == null){return;}
        if(context.getWindow() == null){return;}
        if(context.getWindow().getDecorView() == null){return;}
        parentFrameLayout = (FrameLayout) context.getWindow().getDecorView().findViewById(android.R.id.content);
        if(parentFrameLayout == null){return;}

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_view_year_month_picker,null);
        emptyView = rootView.findViewById(R.id.dialog_view_date_picker_v_empty);
        cancelView = rootView.findViewById(R.id.dialog_view_date_picker_tv_cancel);
        confirmView = rootView.findViewById(R.id.dialog_view_date_picker_tv_confrim);

        yearLoopView = (LoopView) rootView.findViewById(R.id.dialog_view_date_picker_lv_year);
        monthLoopView = (LoopView) rootView.findViewById(R.id.dialog_view_date_picker_lv_month);

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
                    callBack.resultDate(getChoiseDate());
                }
                dismiss();
            }
        });

        rootView.findViewById(R.id.dialog_view_date_picker_fl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }//end construct


    public void show(int startYear,int endYear,YearMonthCallBack callBack){
        if(showing || parentFrameLayout == null ){
            return;
        }
        this.callBack = callBack;
        KeyBoardUtils.hideKeyBoard((Activity) parentFrameLayout.getContext());
        removeViewFromParent(rootView);
        initDateData(startYear,endYear);

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
    }//end show

    public void setPostion(final String year, final String month, final String day){
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int yearPosition = getPosition(years,year);
                int monthPosition = getPosition(months,month);
                yearLoopView.setCurrentItem(yearPosition >= 0 ? yearPosition : yearLoopView.getCurrentItem());
                monthLoopView.setCurrentItem(monthPosition >= 0 ? monthPosition : monthLoopView.getCurrentItem());

            }
        },500);
    }


    public void setPosition(Date date){
        if(date == null){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        setPostion(String.valueOf(year),String.valueOf(month < 10 ? ( "0" + month ): month),String.valueOf(day < 10 ? ("0" + day) : day));
    }

    private int getPosition(List<String> items,String item){
        for(int i = 0;i < items.size();i++){
            if(item.equals(items.get(i))){
                return i;
            }
        }
        return  -1;
    }

    /**
     *
     * 初始化数据
     *
     * @param startYear
     * @param endYear
     */
    private void initDateData(int startYear, int endYear) {

        initYearView(startYear,endYear);
        initMonthView();
    }




    public boolean isShowing() {
        return showing;
    }

    private void initYearView(int startYear, int endYear){
        years.clear();
        years.add("全部");
        for(int i=startYear;i <= endYear;i++){
            years.add(String.valueOf(i));
        }
        yearLoopView.setList(years);
        yearLoopView.setNotLoop();
        yearLoopView.setCurrentItem(0);

    }


    private void initMonthView(){
        months.add("全部");
        for(int i = 1;i < 13;i++){
            months.add(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
        }
        monthLoopView.setList(months);
        monthLoopView.setNotLoop();
        monthLoopView.setCurrentItem(0);

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
    }//end dismiss

    private String getChoiseDate(){
        String year = String.valueOf(years.get(yearLoopView.getCurrentItem()));
        String month = String.valueOf(months.get(monthLoopView.getCurrentItem()));
        if(year.equals("全部") || month.equals("全部")){
            return "全部";
        }
        return year + "-" + month;
    }

    private void removeViewFromParent(View view){
        if(view == null || view.getParent() == null){
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
    }

    private YearMonthCallBack callBack;
    public interface YearMonthCallBack{
        void resultDate(String date);
    }

}
