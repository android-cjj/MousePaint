package com.cjj.mousepaint;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.adapter.HomeRecyclerViewAdapter;
import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.model.AllBookModels;
import com.cjj.mousepaint.url.HttpUrl;
import com.cjj.mousepaint.utils.FastOnClickUtils;
import com.cjj.mousepaint.utils.StatusBarCompat;
import com.cjj.view.ReLoadCallbackListener;
import com.cjj.view.ViewSelectorLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cjj on 2015/10/12.
 */
public class RecommendActivity extends BaseActivity implements View.OnClickListener, ReLoadCallbackListener {
    private static final String Tag = RecommendActivity.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refreshlayout)
    MaterialRefreshLayout refreshLayout;

    private HomeRecyclerViewAdapter mAdapter;
    private ArrayList<AllBookModels.ReturnClazz.AllBook> mDataSet;
    private ViewSelectorLayout viewSelectorLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));
        ButterKnife.bind(this);
        init();
        Data();
    }

    public void setContentView() {
        View v = getLayoutInflater().inflate(R.layout.activity_recommend,null);
        viewSelectorLayout = new ViewSelectorLayout(this,v);
        viewSelectorLayout.setReLoadCallbackListener(this);
        setContentView(viewSelectorLayout);
    }

    private void Data() {
        viewSelectorLayout.show_LoadingView();
        getRecommendBookData();

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getRecommendBookData();
            }
        });
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("最新推荐");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Layout Managers:
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);




    }
    public void getRecommendBookData()
    {
        AppDao.getInstance().getRecommendBook("0", "0", new CallbackListener<AllBookModels>() {

            @Override
            public void onSuccess(AllBookModels result) {
                super.onSuccess(result);
                mDataSet =  (ArrayList) result.Return.List;
                if (mAdapter == null) {
                    mAdapter = new HomeRecyclerViewAdapter(RecommendActivity.this,mDataSet);
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
                toastMsg(Constant.NET_ERROR);
                viewSelectorLayout.show_FailView();
                refreshLayout.finishRefresh();
            }
        });
    }



    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Http.cancel(HttpUrl.URL_RECOMMEND);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }


    @Override
    public void onReLoadCallback() {
        if(!FastOnClickUtils.isFastClick800())
        {
            viewSelectorLayout.show_LoadingView();
            getRecommendBookData();
        }
    }
}
