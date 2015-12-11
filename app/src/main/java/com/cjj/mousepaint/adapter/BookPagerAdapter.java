package com.cjj.mousepaint.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.cjj.mousepaint.fragment.ChinaComicFragment;
import com.cjj.mousepaint.fragment.HotBookFragment;
import com.cjj.mousepaint.fragment.MouseComicFragment;
import com.cjj.mousepaint.fragment.SamePeopleFragment;

/**
 * Created by Administrator on 2015/11/4.
 */
public class BookPagerAdapter extends FragmentPagerAdapter {

    public BookPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            // 0热血，1国产，2同人，3鼠绘
            case 0:
                return HotBookFragment.newInstance();
//            case 1:
//                return ChinaComicFragment.newInstance();
            case 1:
                return new SamePeopleFragment().newInstance();
            case 2:
                return new MouseComicFragment().newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            // 0热血，1国产，2同人，3鼠绘
            case 0:
                return "热血";
//            case 1:
//                return "国产";
            case 1:
                return "国产";
            case 2:
                return "鼠绘";
        }
        return null;
    }
}
