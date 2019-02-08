package com.aequilibrium.transformersbattle.homepage.interfaces;

import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.general.interfaces.IBaseView;

import java.util.List;

public interface IHomepageContract {

    interface View extends IBaseView<Presenter> {

        void updateTransformersList(Transformers transformers);

        void setSwipeRefreshing(boolean isRefresh);

        void setEmptyContainer(boolean isEmpty);

        void proceedUpdateTransformer(Transformers.Transformer transformer);

        void proceedDeleteItem(int position);

        void showEmptyListWarning();

        void showTie();

        void showAutobotsVictory();

        void showDecepticonsVictory();
    }

    interface Presenter extends IBasePresenter {

        void retrieveTransformersList();

        void requestUpdateTransformer(int position);

        void requestDeleteTransformer(int position, String transformerId);

        void requestDeleteBatchTransformers(List<Transformers.Transformer> transformerList);

        void requestStartSimulation();

        void sortTeamsByRank();

        void setupMatchedPairs();

        void applyBattleRules();

        void determineBattleOutcome();

        void requestDisplayResult();

    }
}
