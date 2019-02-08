package com.aequilibrium.transformersbattle.homepage.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.aequilibrium.transformersbattle.TransformersConstants;
import com.aequilibrium.transformersbattle.data.TransformersRepository;
import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;
import com.aequilibrium.transformersbattle.data.remote.TransformersDataSource;
import com.aequilibrium.transformersbattle.general.presenter.BasePresenter;
import com.aequilibrium.transformersbattle.homepage.fragment.UpdateTransformerFragment;
import com.aequilibrium.transformersbattle.homepage.interfaces.IUpdateTransformerContract;

public class UpdateTransformerPresenter<V extends IUpdateTransformerContract.View> extends BasePresenter<V> implements IUpdateTransformerContract.Presenter {

    protected transient TransformersRepository mTransformersRepository;

    private Boolean mIsUpdate = false;
    private String mTransformerId = "";

    public UpdateTransformerPresenter(@NonNull Context context, @NonNull V view, Boolean isUpdate) {
        super(context, view);
        mIsUpdate = isUpdate;
    }

    @Override
    public void init() {
        mTransformersRepository = TransformersRepository.getInstance(new TransformersDataSource(getContext()));
    }

    @Override
    public void requestUpdateOrCreate(final String name, final String team, final String strength, final String intelligence, final String speed, final String endurance, final String rank, final String courage, final String firepower, final String skill) {
        if(mIsUpdate) {
            getView().setLoadingIndicator(true);
            mTransformersRepository.deleteTransformer(mTransformerId, new ITransformersDataSource.DeleteTransformerCallback() {
                @Override
                public void onCancelled() {

                }

                @Override
                public void onError() {

                }

                @Override
                public void onRespond() {
                    mTransformersRepository.createTransformer(name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, new ITransformersDataSource.CreateTransformerCallback() {
                        @Override
                        public void onCancelled() {

                        }

                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onRespond() {
                            getView().setLoadingIndicator(false);
                        }

                        @Override
                        public void onCreateTransformerSuccess(Transformers.Transformer transformer) {
                            getView().handleUpdateSuccess();
                        }
                    });
                }

                @Override
                public void onDeleteTransformerSuccess(Transformers transformers) {

                }
            });

            //FIXME: PUT updateTransformer API returns status 400 even with valid request, will use DELETE and CREATE until fixed
//            mTransformersRepository.updateTransformer(mTransformerId, name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, new ITransformersDataSource.UpdateTransformerCallback() {
//                @Override
//                public void onCancelled() {
//
//                }
//
//                @Override
//                public void onError() {
//
//                }
//
//                @Override
//                public void onRespond() {
//                    getView().setLoadingIndicator(false);
//                }
//
//                @Override
//                public void onUpdateTransformerSuccess(Transformers.Transformer transformer) {
//                    getView().handleUpdateSuccess();
//                }
//            });
        } else {
            getView().setLoadingIndicator(true);
            mTransformersRepository.createTransformer(name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, new ITransformersDataSource.CreateTransformerCallback() {
                @Override
                public void onCancelled() {

                }

                @Override
                public void onError() {

                }

                @Override
                public void onRespond() {
                    getView().setLoadingIndicator(false);
                }

                @Override
                public void onCreateTransformerSuccess(Transformers.Transformer transformer) {
                    getView().showCreationSuccess();
                }
            });
        }
    }

    @Override
    public void requestInitData() {
        if (mIsUpdate) {
            Bundle bundle = ((UpdateTransformerFragment) getView()).getArguments();
            if (bundle != null) {
                Transformers.Transformer transformer = bundle.getParcelable(TransformersConstants.TRANSFORMER_BUNDLE);
                if (transformer != null) {
                    getView().initTransformerData(transformer);
                    mTransformerId = transformer.id;
                }
            }
        }
    }
}
