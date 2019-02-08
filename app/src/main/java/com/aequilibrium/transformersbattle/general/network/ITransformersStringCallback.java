package com.aequilibrium.transformersbattle.general.network;

import com.android.volley.Response;

public interface ITransformersStringCallback<RAWRESPONSE, RESPONSE, ERROR extends ITransformersErrorResponse> extends ITransformersBaseCallback<RESPONSE>, Response.Listener<RAWRESPONSE>, Response.ErrorListener {

    String[] getErrorKey();

    String[] getSpecialErrorCode();

    ITransformersObjectParser getApiObjectParser();

    Class<RESPONSE> getSuccessClazz();

    Class<ERROR> getErrorClazz();
}
