package com.aequilibrium.transformersbattle.general.network;

import java.util.Map;

public interface NetworkUtil {

    /**
     * Default charset for JSON requestJson.
     */
    String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for requestJson.
     */
    String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    String PROTOCOL_CONTENT_TYPE_JSON =
            String.format("application/json");

    <RESPONSE> void requestJson(int method,
                                Map<String, String> params,
                                byte[] body,
                                String bodyContentType,
                                APIModule apiModule,
                                Class<RESPONSE> responseClass,
                                ITransformersObjectParser IHSBCObjectParser,
                                JSONCallback callback,
                                String tag);

    void requestString(int method,
                       byte[] body,
                       String bodyContentType,
                       APIModule apiModule,
                       StringCallback callback,
                       String tag);

    void requestString(int method,
                       Map<String, String> params,
                       APIModule apiModule,
                       StringCallback callback,
                       String tag);

    void requestString(int method,
                       Map<String, String> params,
                       APIModule apiModule,
                       byte[] body,
                       String bodyContentType,
                       StringCallback callback,
                       String tag);



    void cancel(String tag);
}
