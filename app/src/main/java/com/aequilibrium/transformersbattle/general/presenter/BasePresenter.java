package com.aequilibrium.transformersbattle.general.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.general.interfaces.IBaseView;

public class BasePresenter<BV extends IBaseView> implements IBasePresenter {
    private transient Context mContext;
    private transient BV mView;

    public BasePresenter(@NonNull Context context,
                         @NonNull BV view) {
        mContext = context;
        mView = view;

        mView.setPresenter(this);
        init();
    }

    protected Context getContext() {
        return mContext;
    }

    protected BV getView() {
        return mView;
    }

    public void setView(BV view) {
        mView = view;
        init();
    }

    public void init() {

    }
}
