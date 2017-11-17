package com.dapi.suse.core.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
 *                                              2017/6/16
 *
 *      (*≧▽≦)ツ┏━┓⌒ 〓▇3:) 睡什么睡，起来嗨！
 *
 * </pre>
 */

public class KeyBoardUtils {


    public static void showKeyBoard(View view){
        if(view == null || view.getContext() == null){
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void hideKeyBoard(Activity activity){
        if(activity == null){
            return;
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
