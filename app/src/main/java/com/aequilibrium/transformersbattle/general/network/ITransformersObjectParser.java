package com.aequilibrium.transformersbattle.general.network;

import java.io.Serializable;

public interface ITransformersObjectParser extends Serializable {

    <RESPONSE> RESPONSE parseObject(String json, Class<RESPONSE> responseClass);

    String toJsonString(Object object);
}
