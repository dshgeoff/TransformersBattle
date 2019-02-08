package com.aequilibrium.transformersbattle.general.network;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public abstract class StringCallback<RESPONSE, ERROR extends ITransformersErrorResponse> implements ITransformersStringCallback<TransformersResponse, RESPONSE, ERROR>, Response.Listener<TransformersResponse>, Response.ErrorListener {

    @Override
    public void onResponse(TransformersResponse response) {
        onRespond();

        if (response != null && response.transformersMessage != null) {
            onError();
        } else {
            if (response == null) {
                onError();
            } else if (TextUtils.isEmpty(response.json) || response.json.startsWith("null")) {
                onError();
            } else {
                try {
                    JSONObject jsonObject = new JSONObject(response.json);
                    String[] errorKeys = getErrorKey();
                    for (String errorKey : errorKeys) {
                        if (jsonObject.has(errorKey)) {
                            String error = jsonObject.get(errorKey).toString();
                            String[] specialErrorCodes = getSpecialErrorCode();

                            if (!TextUtils.isEmpty(error) && !error.equals("[]") && !error.equals("{}") && !error.startsWith("null")) {
                                ERROR errorResponse = getApiObjectParser().parseObject(response.json, getErrorClazz());

                                if (!Arrays.asList(specialErrorCodes).contains(errorResponse.getErrorCode())) {
                                    onError();
                                    return;
                                } else
                                    break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RESPONSE successResponse = getApiObjectParser().parseObject(response.json, getSuccessClazz());
                if (successResponse == null) {
                    onError();
                } else {
                    onSuccess(successResponse);
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onRespond();
        onError();
    }
}
