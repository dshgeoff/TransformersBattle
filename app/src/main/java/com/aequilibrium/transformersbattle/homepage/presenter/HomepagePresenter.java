package com.aequilibrium.transformersbattle.homepage.presenter;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.TransformersConstants;
import com.aequilibrium.transformersbattle.data.TransformersRepository;
import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;
import com.aequilibrium.transformersbattle.data.remote.TransformersDataSource;
import com.aequilibrium.transformersbattle.general.helper.SharedPreferenceHelper;
import com.aequilibrium.transformersbattle.general.network.APIModule;
import com.aequilibrium.transformersbattle.general.presenter.BasePresenter;
import com.aequilibrium.transformersbattle.homepage.interfaces.IHomepageContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomepagePresenter<V extends IHomepageContract.View> extends BasePresenter<V> implements IHomepageContract.Presenter {

    protected transient TransformersRepository mTransformersRepository;

    private final List<Transformers.Transformer> mTransformersList = new ArrayList<>();
    private final List<Transformers.Transformer> mAutobotsList = new ArrayList<>();
    private final List<Transformers.Transformer> mDecepticonsList = new ArrayList<>();
    private final List<Transformers.Transformer> mScrapList = new ArrayList<>();
    private final List<Transformers.Transformer> mSurviorsList = new ArrayList<>();
    private final List<Pair<Transformers.Transformer, Transformers.Transformer>> mBattlePairs = new ArrayList<>();

    private int victoryCount = 0;

    public HomepagePresenter(@NonNull Context context, @NonNull V view) {
        super(context, view);
    }

    @Override
    public void init() {
        mTransformersRepository = TransformersRepository.getInstance(new TransformersDataSource(getContext()));
        String allSpark = SharedPreferenceHelper.getInstance(getContext().getString(R.string.sharedpreference_name), getContext()).getAllSpark();
        if (!allSpark.isEmpty()) {
            APIModule.setAllSpark(allSpark);
        } else {
            getView().setLoadingIndicator(true);
            mTransformersRepository.getAllSpark(new ITransformersDataSource.RetrieveAllSparkCallback() {
                @Override
                public void onRetrieveAllSparkSuccess(String allSpark) {
                    SharedPreferenceHelper.getInstance(getContext().getString(R.string.sharedpreference_name), getContext()).saveAllSpark(allSpark);
                }

                @Override
                public void onCancelled() {

                }

                @Override
                public void onError() {

                }

                @Override
                public void onRespond() {
                    getView().setLoadingIndicator(false);
                }
            });
        }
    }

    @Override
    public void retrieveTransformersList() {
        getView().setLoadingIndicator(true);
        mTransformersRepository.getTransformers(new ITransformersDataSource.GetTransformersCallback() {
            @Override
            public void onGetTransformersSuccess(Transformers transformers) {
                if (transformers != null && transformers.transformers.size() > 0) {
                    getView().setEmptyContainer(false);
                    getView().updateTransformersList(transformers);
                    mTransformersList.clear();
                    mTransformersList.addAll(transformers.transformers);
                } else {
                    getView().setEmptyContainer(true);
                }
            }

            @Override
            public void onCancelled() {

            }

            @Override
            public void onError() {
            }

            @Override
            public void onRespond() {
                getView().setLoadingIndicator(false);
                getView().setSwipeRefreshing(false);
            }
        });
    }

    @Override
    public void requestUpdateTransformer(int position) {
        getView().proceedUpdateTransformer(mTransformersList.get(position));
    }

    @Override
    public void requestDeleteTransformer(final int position, final String transformerId) {
        getView().setLoadingIndicator(true);
        mTransformersRepository.deleteTransformer(transformerId, new ITransformersDataSource.DeleteTransformerCallback() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onRespond() {
                getView().setLoadingIndicator(false);
                getView().proceedDeleteItem(position);
                mTransformersList.remove(position);
                if (mTransformersList.size() > 0) {
                    getView().setEmptyContainer(false);
                } else {
                    getView().setEmptyContainer(true);
                }
            }

            @Override
            public void onDeleteTransformerSuccess(Transformers transformers) {

            }
        });
    }

    @Override
    public void requestDeleteBatchTransformers(List<Transformers.Transformer> transformerList) {
        for (Transformers.Transformer transformer : transformerList) {
            mTransformersRepository.deleteTransformer(transformer.id, null);
            SystemClock.sleep(200); // Backend will not accept quick successive calls
        }
    }

    @Override
    public void requestStartSimulation() {
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();
        mSurviorsList.clear();
        victoryCount = 0;

        if (mTransformersList.isEmpty()) {
            getView().showEmptyListWarning();
        } else {
            sortTeamsByRank();

            setupMatchedPairs();

            applyBattleRules();

            determineBattleOutcome();

            requestDeleteBatchTransformers(mScrapList);

            requestDisplayResult();
        }
    }


    @Override
    public void sortTeamsByRank() {
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new RankComparator());
        Collections.sort(mDecepticonsList, new RankComparator());
    }

    @Override
    public void setupMatchedPairs() {
        // if lengthDifference is > 0, we iterate decepticon list to form pairs and vice-versa, if lengthDifference == 0 it would be the same either way
        int i, j, lengthDifference = mAutobotsList.size() - mDecepticonsList.size();

        if (lengthDifference > 0) {
            for (i = 0; i < mDecepticonsList.size(); i++) {
                mBattlePairs.add(new Pair<Transformers.Transformer, Transformers.Transformer>(mAutobotsList.get(i), mDecepticonsList.get(i)));
            }
            while (i < mAutobotsList.size()) {
                mSurviorsList.add(mAutobotsList.get(i));
                i++;
            }
        } else {
            for (j = 0; j < mAutobotsList.size(); j++) {
                mBattlePairs.add(new Pair<Transformers.Transformer, Transformers.Transformer>(mAutobotsList.get(j), mDecepticonsList.get(j)));
            }
            while (j < mDecepticonsList.size()) {
                mSurviorsList.add(mDecepticonsList.get(j));
                j++;
            }
        }
    }

    @Override
    public void applyBattleRules() {
        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if ((transformerPair.first.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.first.name.equals(TransformersConstants.PREDAKING))
                    && (transformerPair.second.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.second.name.equals(TransformersConstants.PREDAKING))) {
                mScrapList.addAll(mTransformersList);
                break;
            }
            if ((transformerPair.first.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.first.name.equals(TransformersConstants.PREDAKING))) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
            if (transformerPair.second.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.second.name.equals(TransformersConstants.PREDAKING)) {
                mSurviorsList.add(transformerPair.second);
                mScrapList.add(transformerPair.first);
                continue;
            }
            if (Integer.parseInt(transformerPair.first.courage) - Integer.parseInt(transformerPair.second.courage) >= 4
                    && Integer.parseInt(transformerPair.first.strength) - Integer.parseInt(transformerPair.second.strength) >= 3) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
            if (Integer.parseInt(transformerPair.second.courage) - Integer.parseInt(transformerPair.first.courage) >= 4
                    && Integer.parseInt(transformerPair.second.strength) - Integer.parseInt(transformerPair.first.strength) >= 3) {
                mSurviorsList.add(transformerPair.second);
                mScrapList.add(transformerPair.first);
                continue;
            }
            if (Integer.parseInt(transformerPair.first.skill) - Integer.parseInt(transformerPair.second.skill) >= 3) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
            if (Integer.parseInt(transformerPair.second.skill) - Integer.parseInt(transformerPair.first.skill) >= 3) {
                mSurviorsList.add(transformerPair.second);
                mScrapList.add(transformerPair.first);
                continue;
            }
            if (Integer.parseInt(transformerPair.first.getOverall()) == Integer.parseInt(transformerPair.second.getOverall())) {
                mScrapList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
            if (Integer.parseInt(transformerPair.first.getOverall()) > Integer.parseInt(transformerPair.second.getOverall())) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
            if (Integer.parseInt(transformerPair.second.getOverall()) > Integer.parseInt(transformerPair.first.getOverall())) {
                mSurviorsList.add(transformerPair.second);
                mScrapList.add(transformerPair.first);
            }
        }
    }

    @Override
    public void determineBattleOutcome() {
        for (Transformers.Transformer transformer : mScrapList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                victoryCount++;
            } else {
                victoryCount--;
            }
        }
    }

    @Override
    public void requestDisplayResult() {
        if (victoryCount == 0) {
            getView().showTie();
        } else if (victoryCount > 0) {
            getView().showAutobotsVictory();
        } else {
            getView().showDecepticonsVictory();
        }
    }

    // Returns rank descending
    public static class RankComparator implements Comparator<Transformers.Transformer> {
        @Override
        public int compare(Transformers.Transformer transformer1, Transformers.Transformer transformer2) {
            return Integer.compare(Integer.parseInt(transformer2.rank), Integer.parseInt(transformer1.rank));
        }
    }
}
