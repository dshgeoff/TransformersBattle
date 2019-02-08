package com.aequilibrium.transformersbattle.general.network;

public enum APIAction {
    AllSpark,
    GetTransformersList,
    CreateTransformer,
    UpdateTransformer,
    GetTransformer,
    DeleteTransformer;

    /**
     * @return String for api action
     */
    @Override
    public String toString() {
        switch (this) {
            case AllSpark:
                return "allspark";
            case GetTransformersList:
            case CreateTransformer:
            case UpdateTransformer:
            case GetTransformer:
            case DeleteTransformer:
                return "transformers/";
        }
        return "";
    }

}