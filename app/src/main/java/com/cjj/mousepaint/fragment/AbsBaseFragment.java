package com.cjj.mousepaint.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: huangruimin
 * Date: 2014-12-13
 * Time: 19:43
 * Description:
 */
public abstract class AbsBaseFragment extends Fragment {
    protected Handler mHandler = new Handler();

    protected abstract void initActions(View paramView);

    protected abstract void initData();

    protected abstract View initViews(LayoutInflater paramLayoutInflater);

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        initData();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
        View localView = initViews(paramLayoutInflater);
        initActions(localView);
        return localView;
    }
}
