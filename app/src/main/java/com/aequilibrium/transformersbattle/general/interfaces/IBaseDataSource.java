package com.aequilibrium.transformersbattle.general.interfaces;

public interface IBaseDataSource {

    interface BaseCallback {

        void onCancelled();

        void onError();

        void onRespond();
    }
}
