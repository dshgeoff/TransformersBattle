package com.aequilibrium.transformersbattle.general.interfaces;

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    boolean isActive();

    void displayAlertMessage(String msg);

    void setLoadingIndicator(boolean active);

}
