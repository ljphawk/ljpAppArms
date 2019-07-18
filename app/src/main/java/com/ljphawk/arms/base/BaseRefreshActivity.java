package com.ljphawk.arms.base;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/18 16:31.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public abstract class BaseRefreshActivity<P extends BasePresenter> extends BaseActivity<P> implements OnRefreshLoadMoreListener,BaseRefreshView {

    protected SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected int resView() {
        initSmartRefreshLayout();
        return 0;
    }

    /**
     * @return 刷新的内容布局
     */
    protected abstract int refreshContentView();

    /**
     * 初始化刷新布局和add内容布局
     */
    private void initSmartRefreshLayout() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View parentView = inflater.inflate(R.layout.layout_base_refresh, null);
        View contentView = inflater.inflate(refreshContentView(), null);
        mSmartRefreshLayout = parentView.findViewById(R.id.smartRefreshLayout);
        //第0个放的是ClassicsHeader
        mSmartRefreshLayout.addView(contentView, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(parentView);

        initRefreshData();
    }

    /**
     * mSmartRefreshLayout的设置
     */
    private void initRefreshData() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    /**
     *刷新的回调
     */
    @Override
    public abstract void onRefresh(@NonNull RefreshLayout refreshLayout);

    /**
     * 加载更多的回调
     */
    @Override
    public abstract void onLoadMore(@NonNull RefreshLayout refreshLayout);

    /**
     * 完成刷新
     */
    @Override
    public void doneRefresh() {
        if (null != mSmartRefreshLayout) {
            mSmartRefreshLayout.finishRefresh();
        }
    }

    /**
     * 完成加载更多
     */
    @Override
    public void doneLoadMore() {
        if (null != mSmartRefreshLayout) {
            mSmartRefreshLayout.finishLoadMore(false);
        }
    }

    /**
     *  完成加载更多
     * @param success 是否成功
     */
    @Override
    public void doneLoadMore(boolean success) {
        if (null != mSmartRefreshLayout) {
            mSmartRefreshLayout.finishLoadMore(success);
        }
    }

    /**
     *  完成加载更多
     * @param success 是否成功
     * @param noMoreData 是否无数据
     */
    @Override
    public void doneLoadMore(boolean success, boolean noMoreData) {
        if (null != mSmartRefreshLayout) {
            mSmartRefreshLayout.finishLoadMore(0, success, noMoreData);
        }
    }


}
