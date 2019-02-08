package com.aequilibrium.transformersbattle.general.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.general.fragment.AlertDialogFragment;
import com.aequilibrium.transformersbattle.general.presenter.DialogPresenter;
import com.aequilibrium.transformersbattle.general.util.ProgressDialogUtil;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int contentView();

    private ProgressDialog mProgressDialog;
    private AtomicInteger mLoadingCount = new AtomicInteger(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(BaseActivity.this, R.color.colorTransparentGrey));
        }
    }

    public void setLoadingIndicator(boolean active) {
        if (active) {
            showLoading();
        } else {
            hideLoading();
        }
    }

    public void showLoading() {
        if (isFinishing()) {
            return;
        }

        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogUtil.createProgressDialog(this);
        }
        mLoadingCount.incrementAndGet();
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingCount.get() == 0) {
            return;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing() && mLoadingCount.decrementAndGet() == 0) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void displayAlertMessage(String msg) {
        displayAlertMessage(getString(R.string.general_error), msg);
    }

    public void displayAlertMessage(String title, String desc) {
        displayAlertMessage(title, desc, getString(R.string.general_confirm));
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

    public void displayAlertMessage(String title, String desc, AlertDialogFragment.AlertDialogButtonClickListener onclickListener) {
        displayAlertMessage(title, desc, getString(R.string.general_confirm), true, false, onclickListener);
    }

    public void displayAlertMessage(String title, String desc, String buttonText, boolean needTrack, boolean cancelable, AlertDialogFragment.AlertDialogButtonClickListener onclickListener) {
        new DialogPresenter(BaseActivity.this)
                .present(new AlertDialogFragment()
                                .setTitle(title)
                                .setContent(desc)
                                .setBtnText(buttonText)
                                .setBtnOnclickListener(onclickListener),
                        cancelable);
    }
}
