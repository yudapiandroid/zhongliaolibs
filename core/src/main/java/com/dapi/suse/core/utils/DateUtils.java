package com.dapi.suse.core.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 *
 * Created by dapi on 2017/1/4.
 *
 *
 */
public class DateUtils {


    public static Date getDateByStr(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getYMDHMTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }


    public static int getYearByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonthByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH); // the month startwith 0
    }


    public static int getDayByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     *
     *
     * Long  转化成天数
     *
     * @return
     */
    public static String longTimeToDay(long time){
        time = Math.abs(time);
        long sTime = time / 1000;//  S
        long mTime = sTime / 60; // M
        long hTime = mTime / 60; // H
        long dTime = hTime / 24;//D
        mTime = mTime % 60;
        hTime = hTime % 24;
        sTime = sTime % 60;
        String result = "";
        if(dTime > 0){
            result += dTime + "天";
        }
        if(hTime > 0){
            result += hTime + "小时";
        }
        if(mTime > 0){
            result += mTime + "分";
        }

        if(result.length() == 0 && sTime > 0){
            result += sTime+"秒";
        }
        return result;
    }



    /**
     *
     *
     * @return
     */
    public static long getLongTimeByStr(String str){
        if(TextUtils.isEmpty(str)){
            return System.currentTimeMillis();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String getYMDByDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     *
     * 2017-03-03 12:20:30  ==> 2017-03-03
     *
     * @return
     *
     */
    public static String YMDSMSToYMD(String date){

        return normalFormat("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",date);
    }


    /**
     *
     * 2017-03-03 12:20:30  ==> 2017年03月03日
     *
     * 2017-03-03 12:20:20  ==>  03-03
     *
     *
     * @param date
     * @return
     */
    public static String YMDSMSToMD(String date){
        return normalFormat("yyyy-MM-dd HH:mm:ss","MM-dd",date);
    }


    /**
     *
     *
     * @param date
     * @return
     */
    public static String YMDSMSTOYMDChinese(String date){
        return normalFormat("yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日",date);
    }


    /**
     *
     *
     * 2017-03-03 12:20:30  ==>  12:20
     *
     *
     */
    public static String YMDSMSToMS(String date){
        return normalFormat("yyyy-MM-dd HH:mm:ss","HH:mm",date);
    }


    /**
     *
     * 2017-03-03 12:20:30  ==> 2017-03-03 12:20
     *
     * @return
     */
    public static String YMDSMSToYMDSM(String date){
        return normalFormat("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm",date);
    }


    private static String normalFormat(String before, String after, String date){
        if(TextUtils.isEmpty(date)){
            return "";
        }
        if(date.contains(".")){
            date = date.substring(0,date.indexOf('.'));
        }
        SimpleDateFormat format = new SimpleDateFormat(before);
        SimpleDateFormat resultFormat = new SimpleDateFormat(after);
        try {
            Date parse = format.parse(date);
            return resultFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 浮点数处理
     *
     * @return
     */
    public static String parseDouble(String number){
        boolean isInteger = true;
        if(TextUtils.isEmpty(number)){
            return "";
        }

        if(number.contains(".")){
            //小数
            int pointPosition = number.indexOf('.');//小数点位置
            for(int i = pointPosition + 1; i < number.length();i++){


                if(number.charAt(i) != '0'){
                    isInteger = false;
                }

            }

            if(isInteger){
                number = number.substring(0,number.indexOf('.'));
            }

            return String.valueOf(isInteger ? Integer.valueOf(number) : number);
        }else{
            //整数
            return String.valueOf(Integer.valueOf(number));
        }
    }//end parseDouble



    //判断闰年
    public static boolean isLeap(int year) {

        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }



    public static String getTodayYMD(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

}
