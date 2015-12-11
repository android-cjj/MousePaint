package com.cjj.mousepaint.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.MosuePaintAppication;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.adapter.HomeRecyclerViewAdapter;
import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.UpdateHomeDataEvent;
import com.cjj.mousepaint.model.AllBookModels;
import com.cjj.mousepaint.url.HttpUrl;
import com.cjj.mousepaint.utils.SPUtils;
import com.cjj.view.ReLoadCallbackListener;
import com.cjj.view.ViewSelectorLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by cjj on 2015/10/9.
 */
public class HomeFragment extends Fragment implements ReLoadCallbackListener {
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.refreshlayout) MaterialRefreshLayout refreshLayout;
    private HomeRecyclerViewAdapter mAdapter;
    private ArrayList<AllBookModels.ReturnClazz.AllBook> mDataSet;
    private ViewSelectorLayout viewSelectorLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null!=mAdapter)
        {
            mAdapter.updateData(mDataSet);
        }

        if(refreshLayout!=null)
        {
            refreshLayout.finishRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,null);
        ButterKnife.bind(this,v);
        viewSelectorLayout = new ViewSelectorLayout(getActivity(),v);
        viewSelectorLayout.setReLoadCallbackListener(this);
        return viewSelectorLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Layout Managers:
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Item Decorator:
//        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
//        recyclerView.setItemAnimator(new FadeInLeftAnimator());

//        /* Listeners */
//        recyclerView.setOnScrollListener(onScrollListener);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewSelectorLayout.show_LoadingView();
        getBookData();

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getBookData();
            }
        });
    }

    public void getBookData()
    {
        AppDao.getInstance().getAllBook(new CallbackListener<AllBookModels>() {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                Log.i("cjj", "res----->" + result);
            }

            @Override
            public void onSuccess(AllBookModels result) {
                super.onSuccess(result);
                mDataSet =  (ArrayList) result.Return.List;
                if (mAdapter == null) {


                    mAdapter = new HomeRecyclerViewAdapter(getActivity(),mDataSet);
                    recyclerView.setAdapter(mAdapter);



                } else {
                    mAdapter.updateData(mDataSet);
                }

                viewSelectorLayout.show_ContentView();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
//                toastMsg(Constant.NET_ERROR);
                toastMsg(e+"");
                viewSelectorLayout.show_FailView();
                refreshLayout.finishRefresh();
            }
        });
    }

    private void toastMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void onEvent(UpdateHomeDataEvent event)
    {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();




        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };


    @Override
    public void onReLoadCallback() {
        viewSelectorLayout.show_LoadingView();
        getBookData();
    }
}
