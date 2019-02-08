package com.aequilibrium.transformersbattle.general.presenter;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;


public class DialogPresenter {
    private FragmentActivity mActivity;
    private DialogFragment mDialog;

    public DialogPresenter(FragmentActivity activity) {
        mActivity = activity;
    }

    public <D extends DialogFragment> void present(D dialog) {
        present(dialog, true);
    }

    public <D extends DialogFragment> void present(D dialog, boolean cancelable) {
        mDialog = dialog;
        mDialog.setCancelable(cancelable);
        performPresent();
    }

    public <D extends DialogFragment> void softPresent(D dialog, boolean cancelable) throws IllegalStateException {
        mDialog = dialog;
        mDialog.setCancelable(cancelable);
        mDialog.show(mActivity.getSupportFragmentManager(), "dialog " + mDialog.getClass().getSimpleName());
    }

    private void performPresent() {
        if (mActivity.getSupportFragmentManager().findFragmentByTag("dialog " + mDialog.getClass().getSimpleName()) == null)
            try {
                mDialog.show(mActivity.getSupportFragmentManager(), "dialog " + mDialog.getClass().getSimpleName());
            } catch (IllegalStateException e) {

            }
    }
}
