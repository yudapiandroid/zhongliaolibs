package com.dapi.suse.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr.LeeTong on 2017/3/30.
 */
public class RegularUtils {

    /**
     * 验证身份证
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        String regEx = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(idCard);
        boolean rs = matcher.find();
        return rs;
    }


    /**
     * 验证手机号
     *
     * @param mobilePhone
     * @return
     */
    public static boolean isMobilePhone(String mobilePhone) {
        String regEx = "^[0-9]{11}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(mobilePhone);
        boolean rs = matcher.find();
        return rs;
    }

    /**
     * 删除所有HTML
     *
     * @param content
     * @return
     */
    public static String removeHtml(String content) {
        String regEx_html = "<[^>]+>";
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(content);
        String htmlStr = m_html.replaceAll("");
        return htmlStr;
    }

    public static boolean valiadateVIN(String number) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[0-9a-zA-Z]{17}$");
        m = p.matcher(number);
        b = m.matches();
        return b;
    }


    public static boolean isVerifyCode(String code) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[0-9]{6}$");
        m = p.matcher(code);
        b = m.matches();
        return b;
    }


    public static boolean validatePassword(String password) {
        //^[0-9a-zA-Z]{6,16}$
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[_0-9a-zA-Z]{6,16}$"); // 验证密码
        m = p.matcher(password);
        b = m.matches();
        return b;
    }

}
