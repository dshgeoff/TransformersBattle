package com.aequilibrium.transformersbattle.general.network;

public interface ITransformersJSONCallback<RESPONSE> extends ITransformersBaseCallback<RESPONSE> {

    void onFail(RESPONSE response, String errorMsg);
}
