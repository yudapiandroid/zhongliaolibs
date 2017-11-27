package in.srain.cube.views.ptr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by dapi on 2016/11/26.
 */
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RecyclerView.Adapter adapter;

    private View loadMoreView;//加载更多的布局

    public static final int TYPE_LOAD_MORE = 0x8888;//加载更多

    private boolean haveMoreData;


    public LoadMoreAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_LOAD_MORE){
            //底部的加载更多的布局
            return  new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_load_more_footer_,null));
        }else{
            return adapter.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_LOAD_MORE){
            //加载更多的holder
            if(holder instanceof FooterViewHolder){
                footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.hideLoadMore();
            }
        }else{
            adapter.onBindViewHolder(holder,position);
        }
    }

    private FooterViewHolder footerViewHolder;

    public void changeLoadMoreView(boolean loadMore){
        if(footerViewHolder != null){
            if(loadMore){
                footerViewHolder.showLoadMoreView();
            }else{
                footerViewHolder.showNoDataView();
            }
        }
    }

    public void hideLoadMoreView(){
        if(footerViewHolder != null){
            footerViewHolder.hideLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == adapter.getItemCount()){
            return TYPE_LOAD_MORE;
        }else{
            return adapter.getItemViewType(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }


    public boolean isHaveMoreData() {
        return haveMoreData;
    }

    public void setHaveMoreData(boolean haveMoreData) {
        this.haveMoreData = haveMoreData;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        View rootView;//加载更多的跟布局
        //View loadMoreView;//加载更多
        View noMoreDataView;//没有更多数据

        //ImageView loadMoreIconView;


        Animation loadMoreAnimation;

        public FooterViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            loadMoreView = rootView.findViewById(R.id.fl_load_more);
            noMoreDataView = rootView.findViewById(R.id.no_more_data);
            //loadMoreIconView = (ImageView) rootView.findViewById(R.id.load_more_icon);
            loadMoreAnimation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
            loadMoreAnimation.setDuration(500);
            loadMoreAnimation.setRepeatMode(Animation.INFINITE);
            loadMoreAnimation.setRepeatCount(Animation.INFINITE);
            loadMoreAnimation.setInterpolator(new LinearInterpolator());
        }

        /**
         *
         * 显示加载中的布局
         *
         */
        public void showLoadMoreView(){
            rootView.setVisibility(View.VISIBLE);
            loadMoreView.setVisibility(View.VISIBLE);
            noMoreDataView.setVisibility(View.GONE);
            loadMoreAnimation.reset();
            //loadMoreIconView.startAnimation(loadMoreAnimation);
        }

        /**
         *
         * 显示没有数据的布局
         *
         */
        public void showNoDataView(){
            rootView.setVisibility(View.VISIBLE);
            loadMoreView.setVisibility(View.GONE);
            //loadMoreIconView.clearAnimation();
            loadMoreAnimation.cancel();
            noMoreDataView.setVisibility(View.VISIBLE);
        }

        /**
         *
         * 将整个加载更多的布局隐藏
         *
         */
        public void hideLoadMore(){
            rootView.setVisibility(View.GONE);
            //loadMoreIconView.clearAnimation();
            loadMoreAnimation.cancel();
        }

    }
}
