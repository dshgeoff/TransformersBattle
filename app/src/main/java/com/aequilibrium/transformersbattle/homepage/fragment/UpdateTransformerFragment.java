package com.aequilibrium.transformersbattle.homepage.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;
import com.aequilibrium.transformersbattle.general.fragment.BaseFragment;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.homepage.interfaces.IUpdateTransformerContract;

public class UpdateTransformerFragment extends BaseFragment implements IUpdateTransformerContract.View {

    private IUpdateTransformerContract.Presenter mPresenter;

    private LinearLayout llTeam, llStrength, llIntelligence, llSpeed, llEndurance, llRank, llCourage, llFirepower, llSkill, llConfirmCreation;
    private TextView tvTeam, tvStrength, tvIntelligence, tvSpeed, tvEndurance, tvRank, tvCourage, tvFirepower, tvSkill, tvConfirmCreation;
    private EditText etName;
    private ImageView ivClose;

    private String[] mTeam = {"Autobots", "Decepticons"};
    private String[] mPoints = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_transformer, container, false);
    }

    @Override
    public void bindView(View view) {
        ivClose = (ImageView) view.findViewById(R.id.ivClose);
        etName = (EditText) view.findViewById(R.id.etName);
        llTeam = (LinearLayout) view.findViewById(R.id.llTeam);
        llStrength = (LinearLayout) view.findViewById(R.id.llStrength);
        llIntelligence = (LinearLayout) view.findViewById(R.id.llIntelligence);
        llSpeed = (LinearLayout) view.findViewById(R.id.llSpeed);
        llEndurance = (LinearLayout) view.findViewById(R.id.llEndurance);
        llRank = (LinearLayout) view.findViewById(R.id.llRank);
        llCourage = (LinearLayout) view.findViewById(R.id.llCourage);
        llFirepower = (LinearLayout) view.findViewById(R.id.llFirepower);
        llSkill = (LinearLayout) view.findViewById(R.id.llSkill);
        llConfirmCreation = (LinearLayout) view.findViewById(R.id.llConfirmCreation);

        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvStrength = (TextView) view.findViewById(R.id.tvStrength);
        tvIntelligence = (TextView) view.findViewById(R.id.tvIntelligence);
        tvSpeed = (TextView) view.findViewById(R.id.tvSpeed);
        tvEndurance = (TextView) view.findViewById(R.id.tvEndurance);
        tvRank = (TextView) view.findViewById(R.id.tvRank);
        tvCourage = (TextView) view.findViewById(R.id.tvCourage);
        tvFirepower = (TextView) view.findViewById(R.id.tvFirepower);
        tvSkill = (TextView) view.findViewById(R.id.tvSkill);
        tvConfirmCreation = (TextView) view.findViewById(R.id.tvConfirmCreation);
    }

    @Override
    public void setupView(@Nullable Bundle savedInstanceState) {
        mPresenter.requestInitData();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_team));
                builder.setItems(mTeam, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvTeam.setText(position == 0 ? "A" : "D");
                    }
                });
                builder.show();
            }
        });

        llStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_strength));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvStrength.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llIntelligence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_intelligence));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvIntelligence.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_speed));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvSpeed.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llEndurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_Endurance));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvEndurance.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_rank));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvRank.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llCourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_courage));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvCourage.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llFirepower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_firepower));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvFirepower.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getString(R.string.transformers_item_skill));
                builder.setItems(mPoints, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        tvSkill.setText(mPoints[position]);
                    }
                });
                builder.show();
            }
        });

        llConfirmCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestUpdateOrCreate(etName.getText().toString(), tvTeam.getText().toString(), tvStrength.getText().toString(), tvIntelligence.getText().toString(), tvSpeed.getText().toString(), tvEndurance.getText().toString(), tvRank.getText().toString(), tvCourage.getText().toString(), tvFirepower.getText().toString(), tvSkill.getText().toString());
            }
        });
    }

    @Override
    public IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(IUpdateTransformerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initTransformerData(Transformers.Transformer transformer) {
        etName.setText(transformer.name != null? transformer.name : "");
        tvTeam.setText(transformer.team);
        tvStrength.setText(transformer.strength);
        tvIntelligence.setText(transformer.intelligence);
        tvSpeed.setText(transformer.speed);
        tvEndurance.setText(transformer.endurance);
        tvRank.setText(transformer.rank);
        tvCourage.setText(transformer.courage);
        tvFirepower.setText(transformer.firepower);
        tvSkill.setText(transformer.skill);
        tvConfirmCreation.setText(getActivity().getText(R.string.update_transformer_confirm));
    }

    @Override
    public void showCreationSuccess() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.update_transformer_creation_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleUpdateSuccess() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.update_transformer_update_success), Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
}
