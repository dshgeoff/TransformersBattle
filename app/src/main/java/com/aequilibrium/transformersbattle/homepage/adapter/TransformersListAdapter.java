package com.aequilibrium.transformersbattle.homepage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aequilibrium.transformersbattle.GlideApp;
import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.data.entity.Transformers;

import java.util.ArrayList;
import java.util.List;

public class TransformersListAdapter extends RecyclerView.Adapter<TransformersListAdapter.ViewHolder> {

    private final List<Transformers.Transformer> mList = new ArrayList<>();
    private IRecyclerOnItemClickListener mRecyclerOnItemClickListener;
    private IItemDeleteListener mItemDeleteListener;

    @NonNull
    @Override
    public TransformersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new TransformerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_transformers_list_item, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TransformersListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setupView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(Transformers transformers) {
        if (transformers == null) return;
        this.mList.clear();
        this.mList.addAll(transformers.transformers);
        notifyDataSetChanged();
    }

    public void requestDeleteTransformer(int position) {
        if (mItemDeleteListener != null) {
            mItemDeleteListener.onItemDelete(position, mList.get(position).id);
        }
    }

    public void deleteTransformer(int position) {
        this.mList.remove(position);
        notifyItemRemoved(position);
    }
    public boolean isLastItem(int position) {
        return position == mList.size() - 1;
    }

    private class TransformerViewHolder extends ViewHolder {
        View itemView;
        ImageView ivTeam;
        TextView tvName, tvOverall, tvStrength, tvIntelligence, tvSpeed, tvEndurance, tvRank, tvCourage, tvFirepower, tvSkill;

        public TransformerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(View view) {
            this.itemView = view;
            ivTeam = (ImageView) itemView.findViewById(R.id.ivTeam);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvOverall = (TextView) itemView.findViewById(R.id.tvOverall);
            tvStrength = (TextView) itemView.findViewById(R.id.tvStrength);
            tvIntelligence = (TextView) itemView.findViewById(R.id.tvIntelligence);
            tvSpeed = (TextView) itemView.findViewById(R.id.tvSpeed);
            tvEndurance = (TextView) itemView.findViewById(R.id.tvEndurance);
            tvRank = (TextView) itemView.findViewById(R.id.tvRank);
            tvCourage = (TextView) itemView.findViewById(R.id.tvCourage);
            tvFirepower = (TextView) itemView.findViewById(R.id.tvFirepower);
            tvSkill = (TextView) itemView.findViewById(R.id.tvSkill);
        }

        @Override
        protected void setupView(final int position) {
            if (mList.get(position) == null) {
                return;
            }
            GlideApp.with(itemView.getContext()).load(mList.get(position).team_icon).into(ivTeam);
            tvName.setText(mList.get(position).name);
            tvOverall.setText(mList.get(position).getOverall());
            tvStrength.setText(mList.get(position).strength);
            tvIntelligence.setText(mList.get(position).intelligence);
            tvSpeed.setText(mList.get(position).speed);
            tvEndurance.setText(mList.get(position).endurance);
            tvRank.setText(mList.get(position).rank);
            tvCourage.setText(mList.get(position).courage);
            tvFirepower.setText(mList.get(position).firepower);
            tvSkill.setText(mList.get(position).skill);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerOnItemClickListener != null) {
                        mRecyclerOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            bindView(itemView);
        }

        protected abstract void bindView(View view);

        protected abstract void setupView(int position);
    }

    public void setRecyclerItemClickListener(IRecyclerOnItemClickListener recyclerOnItemClickListener) {
        this.mRecyclerOnItemClickListener = recyclerOnItemClickListener;
    }

    public void setItemDeleteListener(IItemDeleteListener itemDeleteListener) {
        this.mItemDeleteListener = itemDeleteListener;
    }

    public interface IRecyclerOnItemClickListener {
        void onItemClick(int position);
    }

    public interface IItemDeleteListener {
        void onItemDelete(int position, String transformerId);
    }
}
