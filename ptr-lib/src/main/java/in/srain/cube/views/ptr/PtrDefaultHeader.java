package in.srain.cube.views.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by dapi on 2016/11/23.
 */
public class PtrDefaultHeader extends FrameLayout implements PtrUIHandler{

    private View rootView;
    private View pullRefreshView,toRefreshView,refreshingView;
    //private ImageView refreshingImageView;

    private Animation refreshAnimation;

    public PtrDefaultHeader(Context context) {
        super(context);
        initView(context);
    }

    public PtrDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PtrDefaultHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_default_refresh_header,this);
        pullRefreshView = rootView.findViewById(R.id.pull_refresh_root);
        toRefreshView = rootView.findViewById(R.id.to_refresh_root);
        refreshingView = rootView.findViewById(R.id.refreshing_root);
        //refreshingImageView = (ImageView) rootView.findViewById(R.id.refreshing);
        refreshAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        refreshAnimation.setDuration(700);
        refreshAnimation.setRepeatCount(Animation.INFINITE);
        refreshAnimation.setInterpolator(new LinearInterpolator());
        refreshAnimation.setRepeatMode(Animation.INFINITE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        //refreshingImageView.clearAnimation();
        refreshAnimation.cancel();
        pullRefreshView.setVisibility(VISIBLE);
        toRefreshView.setVisibility(GONE);
        refreshingView.setVisibility(GONE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        refreshAnimation.reset();
        //refreshingImageView.startAnimation(refreshAnimation);
        refreshingView.setVisibility(VISIBLE);
        toRefreshView.setVisibility(GONE);
        pullRefreshView.setVisibility(GONE);
    }
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        //刷新完成

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int total = frame.getOffsetToRefresh();
        int now = ptrIndicator.getCurrentPosY();

        if(status == PtrFrameLayout.PTR_STATUS_PREPARE){
            if(now >= total){
                toRefreshView.setVisibility(VISIBLE);
                pullRefreshView.setVisibility(GONE);
                refreshingView.setVisibility(GONE);
            }else{
                pullRefreshView.setVisibility(VISIBLE);
                toRefreshView.setVisibility(GONE);
                refreshingView.setVisibility(GONE);
            }
        }
    }

}
