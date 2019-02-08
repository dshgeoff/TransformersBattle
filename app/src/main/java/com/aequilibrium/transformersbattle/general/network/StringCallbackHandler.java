package com.aequilibrium.transformersbattle.general.network;

public abstract class StringCallbackHandler<RESPONSE, ERROR extends ITransformersErrorResponse> extends StringCallback<RESPONSE, ERROR> {

    private Class<RESPONSE> mSuccessClazz;
    private Class<ERROR> mErrorClazz;

    public StringCallbackHandler(Class<RESPONSE> successClazz, Class<ERROR> errorClazz) {
        mSuccessClazz = successClazz;
        mErrorClazz = errorClazz;
    }

    @Override
    public String[] getErrorKey() {
        return ErrorResponse.ERROR_KEY;
    }

    @Override
    public Class<RESPONSE> getSuccessClazz() {
        return mSuccessClazz;
    }

    @Override
    public Class<ERROR> getErrorClazz() {
        return mErrorClazz;
    }

    @Override
    public ITransformersObjectParser getApiObjectParser() {
        return new GsonObjectParser();
    }

    @Override
    public String[] getSpecialErrorCode() {
        return new String[0];
    }
}
