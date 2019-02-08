package com.aequilibrium.transformersbattle.general.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aequilibrium.transformersbattle.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlertDialogFragment extends DialogFragment {

    public static final String ALERTDIALOGTAG = "dialog";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String BTNTEXT = "btnText";

    CharSequence mTitle, mContent, mBtnText;

    TextView tvTitle;
    TextView tvContent;
    Button btn;
    LinearLayout llTitleNContent;

    AlertDialogButtonClickListener mOnClickListener;

    //SaveInstanceState TAG
    private static final String TITLE_TAG = "TITLE_TAG";
    private static final String CONTENT_TAG = "CONTENT_TAG";
    private static final String BTN_TEXT_TAG = "BTN_TEXT_TAG";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(TITLE_TAG, mTitle);
        outState.putCharSequence(CONTENT_TAG, mContent);
        outState.putCharSequence(BTN_TEXT_TAG, mBtnText);
        super.onSaveInstanceState(outState);
    }

    public static AlertDialogFragment newInstance(String title, String content, String btnText) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        args.putString(BTNTEXT, btnText);
        frag.setArguments(args);
        return frag;
    }

    public AlertDialogFragment setTitle(String title) {
        mTitle = title;
        return this;
    }

    public AlertDialogFragment setContent(String content) {
        mContent = content;
        return this;
    }

    public AlertDialogFragment setBtnText(String btnText) {
        mBtnText = btnText;
        return this;
    }

    public AlertDialogFragment setBtnOnclickListener(AlertDialogButtonClickListener onclickListener) {
        mOnClickListener = onclickListener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TITLE_TAG)) {
                mTitle = savedInstanceState.getCharSequence(TITLE_TAG);
            }
            if (savedInstanceState.containsKey(CONTENT_TAG)) {
                mContent = savedInstanceState.getCharSequence(CONTENT_TAG);
            }
            if (savedInstanceState.containsKey(BTN_TEXT_TAG)) {
                mBtnText = savedInstanceState.getCharSequence(BTN_TEXT_TAG);
            }
            setShowsDialog(false);
            dismiss();
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_alert, container, false);
        bindView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle.setText(mTitle);

        Pattern pattern = Pattern.compile("<.+>");
        Matcher matcher = pattern.matcher(mContent);
        if (matcher.find()) {
            tvContent.setText(Html.fromHtml(mContent.toString()));
        } else {
            tvContent.setText(mContent);
        }
        btn.setText(mBtnText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(view);
                }
                dismiss();
            }
        });
    }

    public void bindView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        btn = (Button) view.findViewById(R.id.btn);
        llTitleNContent = (LinearLayout) view.findViewById(R.id.llTitleNContent);
    }

    public interface AlertDialogButtonClickListener {
        void onClick(View view);
    }
}
