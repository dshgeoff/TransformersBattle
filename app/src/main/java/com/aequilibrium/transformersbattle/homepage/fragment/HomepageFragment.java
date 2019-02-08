package com.aequilibrium.transformersbattle.homepage.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aequilibrium.transformersbattle.R;
import com.aequilibrium.transformersbattle.TransformersConstants;
import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.general.fragment.BaseFragment;
import com.aequilibrium.transformersbattle.general.interfaces.IBasePresenter;
import com.aequilibrium.transformersbattle.general.presenter.FragmentPresenter;
import com.aequilibrium.transformersbattle.general.view.PagingRecyclerView;
import com.aequilibrium.transformersbattle.homepage.adapter.TransformersListAdapter;
import com.aequilibrium.transformersbattle.homepage.helper.TransformerListTouchCallbackHelper;
import com.aequilibrium.transformersbattle.homepage.interfaces.IHomepageContract;
import com.aequilibrium.transformersbattle.homepage.presenter.HomepagePresenter;
import com.aequilibrium.transformersbattle.homepage.presenter.UpdateTransformerPresenter;

public class HomepageFragment extends BaseFragment implements IHomepageContract.View {

    private IHomepageContract.Presenter mPresenter;
    private TransformersListAdapter mAdapter;
    private ItemTouchHelper mTouchHelper;
    private TransformerListTouchCallbackHelper mTouchCallbackHelper;

    private SwipeRefreshLayout swlTransformersList;
    private PagingRecyclerView rvTransformersList;
    private LinearLayout vEmpty, llStartSimulation, llCreateTransformer;

    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void bindView(View view) {
        rvTransformersList = (PagingRecyclerView) view.findViewById(R.id.rvTransformersList);
        swlTransformersList = (SwipeRefreshLayout) view.findViewById(R.id.swlTransformersList);
        llStartSimulation = (LinearLayout) view.findViewById(R.id.llStartSimulation);
        llCreateTransformer = (LinearLayout) view.findViewById(R.id.llCreateTransformer);
        vEmpty = view.findViewById(R.id.vEmpty);

    }

    @Override
    public void setupView(@Nullable Bundle savedInstanceState) {
        rvTransformersList.setSwipeRefreshLayout(swlTransformersList);

        if (rvTransformersList.getLayoutManager() == null) {
            rvTransformersList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvTransformersList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int padding = (int) getResources().getDimension(R.dimen.general_margin_5px);
                    int position = parent.getChildAdapterPosition(view);

                    boolean isLastItem = mAdapter.isLastItem(position);
                    outRect.set(padding, position == 0 ? padding : 0, padding, isLastItem ? 0 : padding);
                }
            });
        }

        if (rvTransformersList.getAdapter() == null) {
            rvTransformersList.setAdapter(getAdapter());
        }

        swlTransformersList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.retrieveTransformersList();
            }
        });

        rvTransformersList.setPagingListener(new PagingRecyclerView.PagingListener() {
            @Override
            public void onLoadNextPage() {

            }
        });

        mTouchCallbackHelper = new TransformerListTouchCallbackHelper(mAdapter);
        mTouchCallbackHelper.allowSwipe(true);
        mTouchHelper = new ItemTouchHelper(mTouchCallbackHelper);
        mTouchHelper.attachToRecyclerView(rvTransformersList);

        llStartSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestStartSimulation();
            }
        });

        llCreateTransformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTransformerFragment updateTransformerFragment = new UpdateTransformerFragment();
                new UpdateTransformerPresenter(getActivity(), updateTransformerFragment, false);
                new FragmentPresenter(getActivity()).push(updateTransformerFragment).asSubContent();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.retrieveTransformersList();
    }

    @Override
    public void setSwipeRefreshing(boolean isRefresh) {
        swlTransformersList.setRefreshing(isRefresh);
    }

    @Override
    public void setEmptyContainer(boolean isEmpty) {
        if(isEmpty){
            vEmpty.setVisibility(View.VISIBLE);
            rvTransformersList.setVisibility(View.GONE);
        } else {
            vEmpty.setVisibility(View.GONE);
            rvTransformersList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(IHomepageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private TransformersListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TransformersListAdapter();
            mAdapter.setRecyclerItemClickListener(new TransformersListAdapter.IRecyclerOnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.requestUpdateTransformer(position);
                }
            });
            mAdapter.setItemDeleteListener(new TransformersListAdapter.IItemDeleteListener() {
                @Override
                public void onItemDelete(int position, String transformerId) {
                    mPresenter.requestDeleteTransformer(position, transformerId);
                }
            });
        }

        return mAdapter;
    }

    @Override
    public void updateTransformersList(Transformers transformers) {
        mAdapter.updateList(transformers);
    }

    @Override
    public void proceedUpdateTransformer(Transformers.Transformer transformer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TransformersConstants.TRANSFORMER_BUNDLE, transformer);
        UpdateTransformerFragment updateTransformerFragment = new UpdateTransformerFragment();
        new UpdateTransformerPresenter(getActivity(), updateTransformerFragment, true);
        new FragmentPresenter(getActivity()).push(updateTransformerFragment).withArguments(bundle).asSubContent();
    }

    @Override
    public void proceedDeleteItem(int position) {
        mAdapter.deleteTransformer(position);
        Toast.makeText(getActivity(), getActivity().getText(R.string.homepage_delete_transformer_success), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyListWarning() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.homepage_empty_list_warning), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTie() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.homepage_battle_tie), Toast.LENGTH_LONG).show();
        onResume();
    }

    @Override
    public void showAutobotsVictory() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.homepage_battle_autobots_victory), Toast.LENGTH_LONG).show();
        onResume();
    }

    @Override
    public void showDecepticonsVictory() {
        Toast.makeText(getActivity(), getActivity().getText(R.string.homepage_battle_decepticons_victory), Toast.LENGTH_LONG).show();
        onResume();
    }
}
