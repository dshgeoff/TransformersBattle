package com.aequilibrium.transformersbattle.general.network;

public interface ITransformersBaseCallback<RESPONSE> {

    void onCancelled();

    void onRespond();

    void onSuccess(RESPONSE response);

    void onError();
}
