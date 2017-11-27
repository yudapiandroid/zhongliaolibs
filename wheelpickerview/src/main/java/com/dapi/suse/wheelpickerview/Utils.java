package com.dapi.suse.wheelpickerview;

/**
 * <pre>
 *  -_-!
 *
 *      Nothing is certain in this life.
 *      The only thing i know for sure is that.
 *      I love you and my life.
 *      That is the only thing i know.
 *      have a good day   :)
 *
 *                                              2017/6/27
 *
 *      (*≧▽≦)ツ┏━┓⌒ 〓▇3:) 睡什么睡，起来嗨！
 *
 * </pre>
 */

public class Utils {

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



}
