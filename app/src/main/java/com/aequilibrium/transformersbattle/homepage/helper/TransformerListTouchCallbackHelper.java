package com.aequilibrium.transformersbattle.homepage.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.aequilibrium.transformersbattle.homepage.adapter.TransformersListAdapter;

public class TransformerListTouchCallbackHelper extends ItemTouchHelper.Callback {

    private TransformersListAdapter mAdapter;
    private static boolean mEnableSwipe;

    public TransformerListTouchCallbackHelper(TransformersListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mEnableSwipe;
    }

    public void allowSwipe(boolean isAllow) {
        mEnableSwipe = isAllow;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.requestDeleteTransformer(viewHolder.getAdapterPosition());
    }
}
