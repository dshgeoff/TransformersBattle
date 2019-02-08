package com.aequilibrium.transformersbattle.data;

import android.support.annotation.NonNull;

import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;

public class TransformersRepository implements ITransformersDataSource {

    private static TransformersRepository INSTANCE = null;

    private final ITransformersDataSource mTransformersDataSource;

    private TransformersRepository(ITransformersDataSource transformersDataSource) {
        mTransformersDataSource = transformersDataSource;
    }

    public static TransformersRepository getInstance(ITransformersDataSource transformersDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TransformersRepository(transformersDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllSpark(@NonNull final RetrieveAllSparkCallback callback) {
        mTransformersDataSource.getAllSpark(callback);
    }

    @Override
    public void createTransformer(@NonNull String name, @NonNull String team, @NonNull String strength, @NonNull String intelligence, @NonNull String speed, @NonNull String endurance, @NonNull String rank, @NonNull String courage, @NonNull String firepower, @NonNull String skill, CreateTransformerCallback callback) {
        mTransformersDataSource.createTransformer(name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, callback);
    }

    @Override
    public void getTransformers(@NonNull GetTransformersCallback callback) {
        mTransformersDataSource.getTransformers(callback);
    }

    @Override
    public void updateTransformer(@NonNull String id, @NonNull String name, @NonNull String team, @NonNull String strength, @NonNull String intelligence, @NonNull String speed, @NonNull String endurance, @NonNull String rank, @NonNull String courage, @NonNull String firepower, @NonNull String skill, UpdateTransformerCallback callback) {
        mTransformersDataSource.updateTransformer(id, name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, callback);
    }

    @Override
    public void getSingleTransformer(@NonNull String id, @NonNull GetSingleTransformerCallback callback) {
        mTransformersDataSource.getSingleTransformer(id, callback);
    }

    @Override
    public void deleteTransformer(@NonNull String id, DeleteTransformerCallback callback) {
        mTransformersDataSource.deleteTransformer(id, callback);
    }
}
