package com.aequilibrium.transformersbattle.general.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PagingRecyclerView extends DispatchSwipeRefreshRecyclerView {

    private OnScrollListener mCustomOnScrollListener;
    private PagingListener mPagingListener;
    private boolean mLoading;
    private boolean mEnablePaging;

    public interface PagingListener {
        void onLoadNextPage();
    }

    public PagingRecyclerView(Context context) {
        super(context);
        init();
    }

    public PagingRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagingRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void init() {
        super.init();
        mLoading = false;
        mEnablePaging = false;
        super.setOnScrollListener(mOnScrollListener);
    }

    public void setPagingListener(PagingListener listener) {
        mPagingListener = listener;
    }

    public void completeLoadingPage() {
        mLoading = false;
    }

    public void enablePaging(boolean enable) {
        mEnablePaging = enable;
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        mCustomOnScrollListener = listener;
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mEnablePaging && !mLoading && !canScrollVertically(1)) {
                notifyPagingListener();
            }

            if (mCustomOnScrollListener != null) {
                mCustomOnScrollListener.onScrolled(recyclerView, dx, dy);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (mCustomOnScrollListener != null) {
                mCustomOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }


    };

    public boolean isLoading() {
        return mLoading;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // for resume RecyclerView stay at bottom , onscrollListener does not trigger
        if (mLoading && mEnablePaging && !canScrollVertically(1)) {
            notifyPagingListener();
        }
    }

    private void notifyPagingListener() {
        if (mPagingListener != null) {
            mLoading = true;
            mPagingListener.onLoadNextPage();
        }
    }
}
