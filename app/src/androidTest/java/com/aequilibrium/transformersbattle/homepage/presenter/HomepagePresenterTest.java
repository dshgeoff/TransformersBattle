package com.aequilibrium.transformersbattle.homepage.presenter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.aequilibrium.transformersbattle.TransformersConstants;
import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.homepage.fragment.HomepageFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HomepagePresenterTest {

    private Context mContext = InstrumentationRegistry.getTargetContext();
    private HomepagePresenter mPresenter;

    private final List<Transformers.Transformer> mTransformersList = new ArrayList<>();
    private final List<Transformers.Transformer> mAutobotsList = new ArrayList<>();
    private final List<Transformers.Transformer> mDecepticonsList = new ArrayList<>();
    private final List<Transformers.Transformer> mScrapList = new ArrayList<>();
    private final List<Transformers.Transformer> mSurviorsList = new ArrayList<>();
    private final List<Pair<Transformers.Transformer, Transformers.Transformer>> mBattlePairs = new ArrayList<>();

    @Mock
    private HomepageFragment mView;
    private Transformers.Transformer transformer1, transformer2, transformer3, transformer4, transformer5, transformer6;

    @Before
    public void setUp() throws Exception {
        mView = new HomepageFragment();
        mPresenter = new HomepagePresenter(mContext, mView);
    }

    @Test
    public void testSortTeamsByRank() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "1";
        transformer1.team = "A";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer3 = new Transformers.Transformer();
        transformer3.rank = "3";
        transformer3.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "1";
        transformer4.team = "D";

        transformer5 = new Transformers.Transformer();
        transformer5.rank = "2";
        transformer5.team = "D";

        transformer6 = new Transformers.Transformer();
        transformer6.rank = "3";
        transformer6.team = "D";

        mTransformersList.add(transformer3);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer1);
        mTransformersList.add(transformer4);
        mTransformersList.add(transformer6);
        mTransformersList.add(transformer5);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

        // Check if team ranks are descending
        int prevRank = Integer.MAX_VALUE;
        for (Transformers.Transformer transformer : mAutobotsList) {
            int nextRank = Integer.parseInt(transformer.rank);
            assertTrue(nextRank <= prevRank);
            if (nextRank != prevRank) {
                prevRank = nextRank;
            }
        }

        prevRank = Integer.MAX_VALUE;
        for (Transformers.Transformer transformer : mDecepticonsList) {
            int nextRank = Integer.parseInt(transformer.rank);
            assertTrue(nextRank <= prevRank);
            if (nextRank != prevRank) {
                prevRank = nextRank;
            }
        }
    }

    @Test
    public void testSetupMatchedPairs() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "1";
        transformer1.team = "A";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "1";
        transformer4.team = "D";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        assertEquals(mBattlePairs.size(), mDecepticonsList.size());
    }

    @Test
    public void testOptimusPredakingBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.name = "Optimus Prime";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.name = "Predaking";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if ((transformerPair.first.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.first.name.equals(TransformersConstants.PREDAKING))
                    && (transformerPair.second.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.second.name.equals(TransformersConstants.PREDAKING))) {
                mScrapList.addAll(mTransformersList);
                break;
            }
        }

        assertEquals(mTransformersList.size(), mScrapList.size());
    }

    @Test
    public void testOptimusBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.name = "Optimus Prime";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.name = "Starscream";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if ((transformerPair.first.name.equals(TransformersConstants.OPTIMUS_PRIME) || transformerPair.first.name.equals(TransformersConstants.PREDAKING))) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
        }

        assertEquals(1, mScrapList.size());
    }

    @Test
    public void testCourageStrengthDifferenceBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.courage = "10";
        transformer1.strength = "7";
        transformer1.name = "Bumblebee";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.courage = "6";
        transformer4.strength = "4";
        transformer4.name = "Starscream";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if (Integer.parseInt(transformerPair.first.courage) - Integer.parseInt(transformerPair.second.courage) >= 4
                    && Integer.parseInt(transformerPair.first.strength) - Integer.parseInt(transformerPair.second.strength) >= 3) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
        }

        assertEquals(1, mScrapList.size());
    }

    @Test
    public void testSkillDifferenceBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.courage = "7";
        transformer1.strength = "7";
        transformer1.skill = "7";
        transformer1.name = "Bumblebee";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.courage = "7";
        transformer4.strength = "7";
        transformer4.skill = "4";
        transformer4.name = "Starscream";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if (Integer.parseInt(transformerPair.first.skill) - Integer.parseInt(transformerPair.second.skill) >= 3) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
        }

        assertEquals(1, mScrapList.size());
    }

    @Test
    public void testEqualOverallBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.courage = "7";
        transformer1.strength = "7";
        transformer1.skill = "7";
        transformer1.intelligence = "10";
        transformer1.speed = "10";
        transformer1.endurance = "10";
        transformer1.firepower = "10";
        transformer1.name = "Bumblebee";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.courage = "7";
        transformer4.strength = "7";
        transformer4.skill = "7";
        transformer4.intelligence = "10";
        transformer4.speed = "10";
        transformer4.endurance = "10";
        transformer4.firepower = "10";
        transformer4.name = "Starscream";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if (Integer.parseInt(transformerPair.first.getOverall()) == Integer.parseInt(transformerPair.second.getOverall())) {
                mScrapList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
        }

        assertEquals(2, mScrapList.size());
    }

    @Test
    public void testGreaterOverallBattle() throws Exception {
        mTransformersList.clear();
        mAutobotsList.clear();
        mDecepticonsList.clear();
        mBattlePairs.clear();
        mScrapList.clear();

        transformer1 = new Transformers.Transformer();
        transformer1.rank = "10";
        transformer1.team = "A";
        transformer1.courage = "7";
        transformer1.strength = "7";
        transformer1.skill = "7";
        transformer1.intelligence = "10";
        transformer1.speed = "10";
        transformer1.endurance = "10";
        transformer1.firepower = "10";
        transformer1.name = "Bumblebee";

        transformer2 = new Transformers.Transformer();
        transformer2.rank = "2";
        transformer2.team = "A";

        transformer4 = new Transformers.Transformer();
        transformer4.rank = "10";
        transformer4.team = "D";
        transformer4.courage = "7";
        transformer4.strength = "7";
        transformer4.skill = "7";
        transformer4.intelligence = "5";
        transformer4.speed = "5";
        transformer4.endurance = "5";
        transformer4.firepower = "5";
        transformer4.name = "Starscream";

        mTransformersList.add(transformer1);
        mTransformersList.add(transformer2);
        mTransformersList.add(transformer4);

        // Sort teams by rank descending
        for (Transformers.Transformer transformer : mTransformersList) {
            if (transformer.team.equals(TransformersConstants.TRANSFORMER_AUTOBOT)) {
                mAutobotsList.add(transformer);
            } else if (transformer.team.equals(TransformersConstants.TRANSFORMER_DECEPTICON)) {
                mDecepticonsList.add(transformer);
            }
        }
        Collections.sort(mAutobotsList, new HomepagePresenter.RankComparator());
        Collections.sort(mDecepticonsList, new HomepagePresenter.RankComparator());

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

        // Perform battle according to the given rules
        for (Pair<Transformers.Transformer, Transformers.Transformer> transformerPair : mBattlePairs) {
            if (Integer.parseInt(transformerPair.first.getOverall()) > Integer.parseInt(transformerPair.second.getOverall())) {
                mSurviorsList.add(transformerPair.first);
                mScrapList.add(transformerPair.second);
                continue;
            }
        }

        assertEquals(1, mScrapList.size());
    }
}
