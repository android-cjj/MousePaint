package com.cjj.mousepaint.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.DetialActivity;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.adapter.CategoryDetialAdapter;
import com.cjj.mousepaint.customview.NormalListView;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.RefreshEvent;
import com.cjj.mousepaint.events.RefreshFinishEvent;
import com.cjj.mousepaint.events.ViewPagerHeightEvent;
import com.cjj.mousepaint.model.CategoryModel;
import com.cjj.view.ViewSelectorLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/4.
 */
public class SamePeopleFragment extends BaseFragment  implements AdapterView.OnItemClickListener{
    private boolean isInit = false;
    private boolean isHaveLoadData = false;
    private int PageIndex = 0;
    private CategoryDetialAdapter mAdapter;
    private List<CategoryModel.ReturnEntity.ListEntity> mList;
    @Bind(R.id.lv_hot)
    NormalListView lv_hot;
    @Bind(R.id.pb)
    ProgressBar pb;
    private boolean isLoadMore = false;

    public static SamePeopleFragment newInstance() {
        SamePeopleFragment fragment = new SamePeopleFragment();
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
        if (!isInit || !isVisible||isHaveLoadData) {
            return;
        }
        lv_hot.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        lv_hot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("tag", "same=" + lv_hot.getMeasuredHeight());
                EventBus.getDefault().post(new ViewPagerHeightEvent("same", lv_hot.getMeasuredHeight()));
            }
        });
        lv_hot.setOnItemClickListener(this);
        getCategoryData();
    }

    private void getCategoryData() {
        AppDao.getInstance().getCategoryData("2", "30", String.valueOf(PageIndex), new CallbackListener<CategoryModel>() {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                Log.i("tag", "people---->" + result);
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
                        isHaveLoadData = true;
                    } else {
                        if (list.size() <= 0) {
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                        mList.addAll(list);
                        if (mAdapter != null && list != null) {
                            mAdapter.updateData(mList);
                        }
                    }


                }

                reSet();
            }


            @Override
            public void onError(Exception e) {
                super.onError(e);
                reSet();
            }
        });
    }
    public void reSet()
    {
        pb.setVisibility(View.GONE);
        EventBus.getDefault().post(new RefreshFinishEvent());
        isLoadMore = false;
    }

    public void onEvent(RefreshEvent event)
    {
       if(event.category.equals("refresh_same"))
        {
            PageIndex += 1;
            isLoadMore = true;
            getCategoryData();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetialActivity.class);
        intent.putExtra("bookId",String.valueOf(mAdapter.getItem(position).Id));
        startActivity(intent);
    }


}
