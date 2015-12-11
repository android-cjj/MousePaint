package com.cjj.mousepaint.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.adapter.CategoryDetialAdapter;
import com.cjj.mousepaint.customview.NormalListView;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.RefreshEvent;
import com.cjj.mousepaint.events.RefreshFinishEvent;
import com.cjj.mousepaint.model.CategoryModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/4.
 */
public class ChinaComicFragment extends BaseFragment{
    private boolean isInit = false;
    private int PageIndex = 0;
    private CategoryDetialAdapter mAdapter;
    private List<CategoryModel.ReturnEntity.ListEntity> mList;
    @Bind(R.id.lv_hot)
    NormalListView lv_hot;

    private boolean isLoadMore = false;

    public static ChinaComicFragment newInstance() {
        ChinaComicFragment fragment = new ChinaComicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_hot_book, null);
        ButterKnife.bind(this, v);
        isInit = true;
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("hei","hei----->");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isInit || !isVisible) {
            return;
        }

        getCategoryData();
    }

    private void getCategoryData() {
        AppDao.getInstance().getCategoryData("1", "30", String.valueOf(PageIndex), new CallbackListener<CategoryModel>() {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
            }

            @Override
            public void onSuccess(CategoryModel result) {
                super.onSuccess(result);
                if (result != null) {
                    List<CategoryModel.ReturnEntity.ListEntity> list = result.Return.List;
                    if (!isLoadMore) {
                        mList = list;
                        mAdapter = new CategoryDetialAdapter(getActivity(), mList);
                        lv_hot.setAdapter(mAdapter);
                        lv_hot.setFocusable(false);
                    } else {
                        if(list.size()<=0)
                        {
                            Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                        }
                        mList.addAll(list);
                        if (mAdapter != null && list != null) {
                            mAdapter.updateData(mList);
                        }
                    }


                }

                EventBus.getDefault().post(new RefreshFinishEvent());
                isLoadMore = false;
            }


            @Override
            public void onError(Exception e) {
                super.onError(e);
                EventBus.getDefault().post(new RefreshFinishEvent());
                isLoadMore = false;
            }
        });
    }


    public void onEvent(RefreshEvent event)
    {
        if(event.category.equals("hot_refresh"))
        {
            PageIndex = 0;
            getCategoryData();

        }else if(event.category.equals("hot_refresh_more"))
        {
            PageIndex += 1;
            isLoadMore = true;
            getCategoryData();
        }
    }


}
