package com.aequilibrium.transformersbattle.general.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.general.activity.BaseActivity;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.general.interfaces.IBaseView;
import com.aequilibrium.transformersbattle.general.presenter.BasePresenter;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    View mRootView = null;
    boolean isFirstInflated = false;

    private static final String PRESENTER_TAG = "Presenter_Instance";

    public abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void bindView(View view);

    public abstract void setupView(@Nullable Bundle savedInstanceState);

    protected boolean isFirstInflated() {
        return isFirstInflated;
    }

    private BaseActivity mBaseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PRESENTER_TAG)) {
                if (this instanceof IBaseView) {
                    IBasePresenter presenter = (IBasePresenter) savedInstanceState.getSerializable(PRESENTER_TAG);
                    if (presenter instanceof BasePresenter && this instanceof IBaseView) {
                        ((BasePresenter) presenter).setView((IBaseView) this);
                    }
                    ((IBaseView) this).setPresenter(presenter);
                }
            }
            setSavedInstanceBundle(savedInstanceState);
        }
        try {
            mBaseActivity = (BaseActivity) getActivity();
        } catch (Exception ex) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            View view = createView(inflater, container, savedInstanceState);
            bindView(view);
            mRootView = view;
            isFirstInflated = true;
        } else {
            ViewParent parent = mRootView.getParent();
            if (parent instanceof ViewGroup) ((ViewGroup) parent).removeView(mRootView);
            isFirstInflated = false;
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isFirstInflated()) {
            setupView(savedInstanceState);
        }
    }

    protected void setSavedInstanceBundle(Bundle bundle) {

    }

    public void showLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().showLoading();
    }

    public void hideLoading() {
        if (getBaseActivity() != null)
            getBaseActivity().hideLoading();
    }

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    public boolean isActive() {
        return isAdded() && !isHidden() && getView() != null
                && getView().getVisibility() == View.VISIBLE;
    }

    public void displayAlertMessage(String msg) {
        displayAlertMessage(getString(R.string.general_error), msg);
    }

    public void displayAlertMessage(String title, String desc) {
        displayAlertMessage(title, desc, getString(R.string.general_confirm));
    }

    public void displayAlertMessage(String title, String desc, AlertDialogFragment.AlertDialogButtonClickListener onclickListener) {
        displayAlertMessage(title, desc, getString(R.string.general_confirm), true, false, onclickListener);
    }

    public void displayAlertMessage(String title, String desc, String buttonText) {
        displayAlertMessage(title, desc, buttonText, true);
    }

    public void displayAlertMessage(String title, String desc, String buttonText, boolean needTrack) {
        displayAlertMessage(title, desc, buttonText, needTrack, true, null);
    }

    public void displayAlertMessage(String title, String desc, String buttonText, boolean needTrack, boolean cancelable) {
        displayAlertMessage(title, desc, buttonText, needTrack, cancelable, null);
    }

    public void displayAlertMessage(String title, String desc, String buttonText, boolean needTrack, boolean cancelable, AlertDialogFragment.AlertDialogButtonClickListener onclickListener) {
        if (getBaseActivity() != null) {
            getBaseActivity().displayAlertMessage(title, desc, buttonText, needTrack, cancelable, onclickListener);
        }
    }

    public void setLoadingIndicator(boolean active) {
        if (active)
            showLoading();
        else
            hideLoading();
    }

    public abstract IBasePresenter getPresenter();

    public BaseFragment getChildCurrentFragment() {
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        if (fragmentList != null) {
            for (int i = fragmentList.size() - 1; i >= 0; i--) {
                BaseFragment targetFragment = (BaseFragment) fragmentList.get(i);
                if (targetFragment != null)
                    return targetFragment;
            }
        }
        return null;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (isBlockDefaultAnimation()) {
            Animation animation = new Animation() {
            };
            animation.setDuration(0);
            return animation;
        } else {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
    }

    public boolean isBlockDefaultAnimation() {
        return true;
    }
}
