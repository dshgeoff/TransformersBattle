package com.aequilibrium.transformersbattle.homepage.interfaces;

import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.general.interfaces.IBaseView;

public interface IUpdateTransformerContract {

    interface View extends IBaseView<Presenter> {

        void initTransformerData(Transformers.Transformer transformer);

        void showCreationSuccess();

        void handleUpdateSuccess();

    }

    interface Presenter extends IBasePresenter {

        void requestUpdateOrCreate(String name, String team, String strength, String intelligence, String speed, String endurance, String rank, String courage, String firepower, String skill);

        void requestInitData();

    }
}
