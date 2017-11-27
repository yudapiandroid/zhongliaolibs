package in.srain.cube.views.ptr;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 *
 * Created by dapi on 2016/11/23.
 *
 */
public class PtrRecyclerView extends FrameLayout implements PtrHandler, NotifyRecyclerView.CallAdapterNotifChangeLisenter {

    private PtrDefaultHeader defaultHeader;

    private View rootView;
    private FrameLayout noNetFrameLayout;
    private FrameLayout noDataFrameLayout;
    private PtrFrameLayout refreshView;
    private FrameLayout firstLoadingFrameLayout;
    private RecyclerView contentRecyclerView;


    private LoadMoreAdapter loadMoreAdapter;

    private boolean arrBottom = false;
    private boolean checkNetState = true;//是否检查网络状态
    private boolean canLoadMore = true;
    private boolean loading = false;

    private PtrRecyclerViewInterface loadDataInterface;

    public static final int STATE_NONE = 0x0011;//只显示刷新控件
    public static final int STATE_FIRST = 0x0012;//第一次加载
    public static final int STATE_NO_NET = 0x0013;
    public static final int STATE_NO_DATA = 0x0014;

    private static final String LOG_TAG = "PtrRecyclerView";

    public PtrRecyclerView(Context context) {
        super(context);
        initViews(context);
    }

    public PtrRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PtrRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public void autoRefresh(){
        post(new Runnable() {
            @Override
            public void run() {
                if(refreshView != null){
                    refreshView.autoRefresh(true);
                }
            }
        });
    }

    private void initViews(Context context) {
        defaultHeader = new PtrDefaultHeader(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.ptr_recycler_view,this);
        firstLoadingFrameLayout = (FrameLayout) rootView.findViewById(R.id.first_loading);
        noNetFrameLayout = (FrameLayout) rootView.findViewById(R.id.no_net_root);
        noDataFrameLayout = (FrameLayout) rootView.findViewById(R.id.no_data_root);
        refreshView = (PtrFrameLayout) rootView.findViewById(R.id.refresh);
        contentRecyclerView = (RecyclerView) rootView.findViewById(R.id.ptr_recyclerview);
        refreshView.setHeaderView(defaultHeader);
        refreshView.addPtrUIHandler(defaultHeader);
        refreshView.setPullToRefresh(false);
        showViewByState(STATE_NONE);
        refreshView.setPtrHandler(this);
        contentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(arrBottom && newState == RecyclerView.SCROLL_STATE_IDLE && canLoadMore && !loading){
                    if(loadDataInterface != null && !loading){
                       if(loadDataInterface.haveMoreData()){
                           loadMoreAdapter.changeLoadMoreView(true);
                           loading = true;
                            loadDataInterface.loadData(false);
                       }else{
                           loadMoreAdapter.changeLoadMoreView(false);
                       }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                arrBottom = isSlideToBottom(recyclerView);
            }
        });

        ((NotifyRecyclerView)contentRecyclerView).setChangeLisenter(this);

        //点击重新加载
        noDataFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fristLoadData();
            }
        });

        noNetFrameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fristLoadData();
            }
        });

    }


    private RotateAnimation rotateAnimation;

    private void showViewByState(int state){
        firstLoadingFrameLayout.setVisibility(state == STATE_FIRST ? VISIBLE : GONE);
        noNetFrameLayout.setVisibility(state == STATE_NO_NET ? VISIBLE : GONE);
        noDataFrameLayout.setVisibility(state == STATE_NO_DATA ? VISIBLE : GONE);
        refreshView.setVisibility(state == STATE_NONE ? VISIBLE : GONE);
        if(state == STATE_FIRST){
            LinearLayout layout = (LinearLayout) firstLoadingFrameLayout.getChildAt(0);
            /*ImageView loadingImageView = (ImageView) layout.getChildAt(0);
            final TextView loadingTextView = (TextView) layout.getChildAt(1);*/
            /*rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(Animation.INFINITE);
            rotateAnimation.setRepeatMode(Animation.INFINITE);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {
                    String loadingStr = loadingTextView.getText().toString();
                    int pointCount = 0;
                    for(int i=0;i<loadingStr.length();i++){
                        char c = loadingStr.charAt(i);
                        if(c == '.'){
                            pointCount++;
                        }
                    }
                    if(loadingStr.indexOf('.') > 0){
                        loadingStr = loadingStr.substring(0,loadingStr.indexOf('.'));
                    }
                    Log.i(LOG_TAG,"str "+loadingStr);
                    for(int i = 0;i < (pointCount + 1) % 4;i++){
                        loadingStr += ".";
                    }
                    loadingTextView.setText(loadingStr);
                }
            });
            loadingImageView.startAnimation(rotateAnimation);*/
        }else{
            if(rotateAnimation != null){
                rotateAnimation.cancel();
            }
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        RecyclerView.LayoutManager manager = contentRecyclerView.getLayoutManager();
        boolean flag = false;
        if(manager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            flag = linearLayoutManager.findFirstVisibleItemPosition() <= 0;
            Log.i(LOG_TAG,"LinearLayoutManager position "+linearLayoutManager.findFirstVisibleItemPosition());
        }

        if(manager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            flag = gridLayoutManager.findFirstVisibleItemPosition() <= 0;
            Log.i(LOG_TAG,"GridLayoutManager position "+gridLayoutManager.findFirstVisibleItemPosition()); // -1
        }

        if(manager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            int[] vis = null;
            vis = staggeredGridLayoutManager.findFirstVisibleItemPositions(vis);
            for(int i=0; i < vis.length;i++){
                if(vis[i] <= 0){
                    flag = true;
                    Log.i(LOG_TAG," vi "+i+"   "+vis[i]);
                }
            }
        }
        boolean b = manager.findViewByPosition(0) == null || manager.findViewByPosition(0).getTop() == 0;
        Log.i(LOG_TAG,b + "   "+flag);
        return flag && b;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        loadData(true);
    }


    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        loadMoreAdapter = new LoadMoreAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loadMoreAdapter.notifyDataSetChanged();
            }
        });
        contentRecyclerView.setAdapter(loadMoreAdapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout){
        contentRecyclerView.setLayoutManager(layout);
    }

    public void setLayoutManager(GridLayoutManager manager){
        final int spanCount = manager.getSpanCount();
        final GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
        if(lookup != null){
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int spanSize = lookup.getSpanSize(position);
                    return spanSize == 0 ? spanCount : spanSize;
                }
            });
        }
        contentRecyclerView.setLayoutManager(manager);
    }

    public RecyclerView.Adapter getAdapter(){
        return contentRecyclerView.getAdapter();
    }

    public RecyclerView getRecyclerView(){
        return contentRecyclerView;
    }

    /**
     *
     *
     *
     * 第一次加载的时候需要显示
     *
     *
     *
     */
    public void fristLoadData(){
        if(loading){
            return;
        }
        showViewByState(STATE_FIRST);
        loadData(true);
    }

    private void loadData(boolean isFrist){
        if(checkNetState && !isNetworkAvailable() && contentRecyclerView.getAdapter().getItemCount() == 0){
            showViewByState(STATE_NO_NET);
            return;
        }
        if(loadDataInterface != null && !loading){
            loading = true;
            loadDataInterface.loadData(isFrist);
        }
    }

    public PtrRecyclerViewInterface getLoadDataInterface() {
        return loadDataInterface;
    }

    public void setLoadDataInterface(PtrRecyclerViewInterface loadDataInterface) {
        this.loadDataInterface = loadDataInterface;
    }

    @Override
    public void callAdapterNotify() {
        if(!loading){
            return;
        }else{
            loading = false;
        }

        if(refreshView.isRefreshing()){
            refreshView.refreshComplete();
        }
        if(contentRecyclerView.getAdapter().getItemCount() == 1){//没有数据
            if(checkNetState){
                if(isNetworkAvailable()){
                    showViewByState(STATE_NO_DATA);
                }else{
                    showViewByState(STATE_NO_NET);
                }
            }else{
                showViewByState(STATE_NO_DATA);
            }
        }

        if(contentRecyclerView.getAdapter().getItemCount() > 1){//有数据
            showViewByState(STATE_NONE);
        }

        firstLoadingFrameLayout.clearAnimation();
        loadMoreAdapter.hideLoadMoreView();
    }

    public interface PtrRecyclerViewInterface{

        void loadData(boolean isFrist);

        boolean haveMoreData();

    }

    public boolean isNetworkAvailable()
    {
        Context context = getContext().getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
