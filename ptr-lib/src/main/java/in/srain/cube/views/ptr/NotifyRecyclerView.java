package in.srain.cube.views.ptr;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by dapi on 2016/11/23.
 */
public class NotifyRecyclerView extends RecyclerView {


    public NotifyRecyclerView(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        //初始化加载更多的布局

    }

    public NotifyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public NotifyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof LoadMoreAdapter){
            super.setAdapter(adapter);
            final LoadMoreAdapter loadMoreAdapter = (LoadMoreAdapter) adapter;
            adapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    //在调用了adapter的notify方法后 将会回调该方法
                    if(changeLisenter != null){
                        changeLisenter.callAdapterNotify();
                    }
                }
            });

            loadMoreAdapter.getAdapter().registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    loadMoreAdapter.notifyDataSetChanged();
                }
            });
        }else{
            final LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(adapter);
            loadMoreAdapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if(changeLisenter != null){
                        changeLisenter.callAdapterNotify();
                    }
                }
            });

            adapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    loadMoreAdapter.notifyDataSetChanged();
                }
            });
            super.setAdapter(loadMoreAdapter);

        }

    }

    private CallAdapterNotifChangeLisenter changeLisenter;


    public CallAdapterNotifChangeLisenter getChangeLisenter() {
        return changeLisenter;
    }

    public void setChangeLisenter(CallAdapterNotifChangeLisenter changeLisenter) {
        this.changeLisenter = changeLisenter;
    }

    public interface CallAdapterNotifChangeLisenter{

        void callAdapterNotify();

    }

}
