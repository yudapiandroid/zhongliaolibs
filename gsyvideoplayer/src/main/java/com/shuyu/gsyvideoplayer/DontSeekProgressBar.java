package com.shuyu.gsyvideoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * Created by 余鑫 on 2017/4/13.
 */

public class DontSeekProgressBar extends SeekBar {

    public boolean canDrage;

    public DontSeekProgressBar(Context context) {
        super(context);
    }

    public DontSeekProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DontSeekProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(canDrage){
            return super.onTouchEvent(event);
        }
        return false;
    }
}
