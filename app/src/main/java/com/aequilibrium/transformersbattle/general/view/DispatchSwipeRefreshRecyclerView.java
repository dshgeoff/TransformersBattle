package com.aequilibrium.transformersbattle.general.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

public class DispatchSwipeRefreshRecyclerView extends RecyclerView {

    boolean mIsActionUp = true;
    boolean mIsSwipeShowing = false;
    int mSpinnerFinalOffset;
    final int SPINNER_INFINITY_OFFSET = 9999999;
    int mAppBarOffset = 0;
    OnItemTouchListener mOnItemTouchListener;
    int mProgressStartOffset = 0;
    int mProgressEndOffset = 0;
    int DEFAULT_CIRCLE_TARGET = 64;

    String TAG = "Dispatch";
    boolean mIsViewCreated = false;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public DispatchSwipeRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public DispatchSwipeRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DispatchSwipeRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public void init() {
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mSpinnerFinalOffset = (int) (DEFAULT_CIRCLE_TARGET * metrics.density);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                SwipeRefreshLayout refreshLayout = getSwipeRefreshLayout();
                if (refreshLayout != null) {
                    mIsViewCreated = true;
                }

            }
        });


        addOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isRefreshing();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {

                mIsActionUp = false;
                View firstChildView = getChildAt(0);
                int topDecorationHeight = firstChildView != null && getLayoutManager() != null ? getLayoutManager().getTopDecorationHeight(firstChildView) : 0;
                if (firstChildView != null && firstChildView.getTop() - topDecorationHeight != 0) {
                    mIsSwipeShowing = true;
                }
                break;
            }


            case MotionEvent.ACTION_UP: {
                mIsActionUp = true;
                checkIfDispatchParentRefreshLayout();
                break;
            }

        }

        return super.onTouchEvent(event);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        checkIfDispatchParentRefreshLayout();
    }


    public void setProgressViewOffset(int start, int end) {
        mProgressStartOffset = start;
        mProgressEndOffset = end;
    }

    boolean mLastEnable = true;

    public void enableSwipeRefreshLayout(boolean isEnable) {
        if (!mIsViewCreated || mLastEnable == isEnable) {
            return;
        }
        SwipeRefreshLayout refreshLayout = getSwipeRefreshLayout();

        if (refreshLayout == null) {
            return;
        }

        refreshLayout.setDistanceToTriggerSync(isEnable ? mSpinnerFinalOffset : SPINNER_INFINITY_OFFSET);
        refreshLayout.setProgressViewOffset(false, isEnable ? mProgressStartOffset : -2500, isEnable ? mProgressEndOffset + mProgressStartOffset : -1500);
        refreshLayout.setEnabled(isEnable);
        mLastEnable = isEnable;
    }

    boolean mIsResume = false;

    public void checkIfDispatchParentRefreshLayout() {

        View firstChildView = getChildAt(0);
        int topDecorationHeight = firstChildView != null && getLayoutManager() != null ? getLayoutManager().getTopDecorationHeight(firstChildView) : 0;
        boolean isStopAtTop = mIsActionUp && firstChildView != null && (firstChildView.getTop() - topDecorationHeight) == 0;
        enableSwipeRefreshLayout(isStopAtTop && !mIsSwipeShowing);
        if (!mIsResume && isStopAtTop && mIsSwipeShowing) {
            mIsResume = true;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsSwipeShowing = false;
                    mIsResume = false;
                    enableSwipeRefreshLayout(true);
                }
            }, 500);

        }

    }

    int mLastverticalOffset = 0;

    public void setOnOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (verticalOffset != 0) {
            mIsSwipeShowing = true;
        }

        if (mLastverticalOffset != verticalOffset) {
            checkIfDispatchParentRefreshLayout();
        }

        mLastverticalOffset = verticalOffset;
    }

    boolean isRefreshing() {
        SwipeRefreshLayout refreshLayout = getSwipeRefreshLayout();

        if (refreshLayout == null) {
            return false;
        }

        return refreshLayout.isRefreshing();
    }

    private SwipeRefreshLayout getSwipeRefreshLayout() {
        if (getParent() != null && getParent() instanceof SwipeRefreshLayout) {
            return ((SwipeRefreshLayout) getParent());
        }

        return mSwipeRefreshLayout;
    }


    public void setSwipeRefreshLayout(SwipeRefreshLayout refreshLayout) {
        mSwipeRefreshLayout = refreshLayout;
        init();
    }


}
