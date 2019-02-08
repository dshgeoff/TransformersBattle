package com.aequilibrium.transformersbattle.homepage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.general.activity.BaseActivity;
import com.aequilibrium.transformersbattle.general.presenter.FragmentPresenter;
import com.aequilibrium.transformersbattle.homepage.fragment.HomepageFragment;
import com.aequilibrium.transformersbattle.homepage.presenter.HomepagePresenter;

public class HomepageActivity extends BaseActivity {

    @Override
    protected int contentView() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomepageFragment homepageFragment = new HomepageFragment();
        new HomepagePresenter(this, homepageFragment);
        new FragmentPresenter(this).push(homepageFragment).asMajorContent();
    }
}