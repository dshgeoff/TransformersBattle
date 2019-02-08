package com.aequilibrium.transformersbattle.data.interfaces;

import android.support.annotation.NonNull;

import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.general.interfaces.IBaseDataSource;

public interface ITransformersDataSource extends IBaseDataSource {

    void getAllSpark(@NonNull RetrieveAllSparkCallback callback);

    void createTransformer(@NonNull String name, @NonNull String team,
                           @NonNull String strength, @NonNull String intelligence, @NonNull String speed,
                           @NonNull String endurance, @NonNull String rank, @NonNull String courage,
                           @NonNull String firepower, @NonNull String skill, CreateTransformerCallback callback);

    void getTransformers(@NonNull GetTransformersCallback callback);

    void updateTransformer(@NonNull String id, @NonNull String name, @NonNull String team,
                           @NonNull String strength, @NonNull String intelligence, @NonNull String speed,
                           @NonNull String endurance, @NonNull String rank, @NonNull String courage,
                           @NonNull String firepower, @NonNull String skill, UpdateTransformerCallback callback);

    void getSingleTransformer(@NonNull String id, @NonNull GetSingleTransformerCallback callback);

    void deleteTransformer(@NonNull String id, DeleteTransformerCallback callback);

    interface RetrieveAllSparkCallback extends IBaseDataSource.BaseCallback {
        void onRetrieveAllSparkSuccess(String allSpark);
    }

    interface CreateTransformerCallback extends IBaseDataSource.BaseCallback {
        void onCreateTransformerSuccess(Transformers.Transformer transformer);
    }

    interface GetTransformersCallback extends IBaseDataSource.BaseCallback {
        void onGetTransformersSuccess(Transformers transformers);
    }

    interface UpdateTransformerCallback extends IBaseDataSource.BaseCallback {
        void onUpdateTransformerSuccess(Transformers.Transformer transformer);
    }

    interface GetSingleTransformerCallback extends IBaseDataSource.BaseCallback {
        void onGetSingleTransformerSuccess(Transformers.Transformer transformer);
    }

    interface DeleteTransformerCallback extends IBaseDataSource.BaseCallback {
        void onDeleteTransformerSuccess(Transformers transformers);
    }
}
