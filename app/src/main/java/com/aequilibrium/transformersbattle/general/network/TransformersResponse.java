package com.aequilibrium.transformersbattle.general.network;

import android.text.TextUtils;

import java.util.Map;

public class TransformersResponse {
    public Map<String, String> header;
    public String json;
    public TransformersMessage transformersMessage;

    public void setTransformersError(String errorString) {
        if (TextUtils.isEmpty(errorString)) {
            return;
        }
        transformersMessage = new TransformersMessage();
        try {
            transformersMessage.transformersError = TransformersError.valueOf(errorString);
        } catch (Exception e) {
            transformersMessage.transformersError = TransformersError.UN;
        }
    }

    public static class TransformersMessage {
        public TransformersError transformersError;
    }

    public enum TransformersError {
        UN("unexpected_error");

        private String mErrorString;

        TransformersError(String errorString) {
            mErrorString = errorString;
        }

        public String toString() {
            return mErrorString;
        }
    }
}
