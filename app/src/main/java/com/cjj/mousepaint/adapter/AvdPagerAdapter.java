package com.cjj.mousepaint.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cjj.mousepaint.model.AdvModel;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class AvdPagerAdapter extends FragmentPagerAdapter{
    private List<AdvModel> mList;

    public AvdPagerAdapter(FragmentManager fm,List<AdvModel> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
