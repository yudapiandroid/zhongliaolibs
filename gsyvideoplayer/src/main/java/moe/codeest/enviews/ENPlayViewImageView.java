package moe.codeest.enviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.R;

/**
 * Created by codeest on 16/11/7.
 *
 */

public class ENPlayViewImageView extends ImageView {


    public ENPlayViewImageView(Context context) {
        super(context);
    }

    public ENPlayViewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public void play() {
        setImageResource(R.drawable.video_click_pause_selector);
    }


    //video_click_play_selector
    public void pause() {
        setImageResource(R.drawable.video_click_play_selector);
    }

}
