package com.cjj.mousepaint.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjj.mousepaint.WebActivity;
import com.cjj.mousepaint.model.AdvModel;


import java.util.ArrayList;
import java.util.List;


/**
 * 首页商家广告适配器
 * @author cjj
 */
public class ViewPagerAdvAdapter extends PagerAdapter {

    private List<AdvModel.ListEntity> mDatas;
    private List<ImageView> mViews;
    private ImageView mImageView;
    private Context mContext;

    public ViewPagerAdvAdapter(Context mContext, List<AdvModel.ListEntity> mDatas) {
        this.mContext = mContext;
        mViews = new ArrayList<ImageView>();
        this.mDatas = mDatas;
        int length = mDatas == null ? 0 : mDatas.size();
        Log.i("cjj","tagcjj1111");
        for (int i = 0; i < length; i++) {
            ImageView mImageView = new ImageView(mContext);
            mViews.add(mImageView);
        }

        length = 0;
    }

    @Override
    public int getCount() {

        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == (arg1);
    }

    @Override
    public Object instantiateItem(View container, final int position) {
        mImageView = mViews.get(position);
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_URL, mDatas.get(position).Link);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(mDatas.get(position).Img).centerCrop().into(mImageView);

        ((ViewPager) container).addView(mImageView, 0);

        return mImageView;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        mImageView = mViews.get(position);
        ((ViewPager) container).removeView(mImageView);
    }

}
