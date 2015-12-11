package com.cjj.mousepaint.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.model.CategoryModel;
import com.cjj.mousepaint.model.SearchComicModel;
import com.cjj.mousepaint.utils.ViewHolderUtils;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/11/3.
 */
public class SearchAdapter extends BaseAdapter {

    private Activity context;
    private List<SearchComicModel.ReturnEntity.ListEntity> list;
    private ViewHolder mHolder;

    public SearchAdapter(Activity context, List<SearchComicModel.ReturnEntity.ListEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void  updateData(List<SearchComicModel.ReturnEntity.ListEntity> list)
    {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SearchComicModel.ReturnEntity.ListEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mHolder = new ViewHolder();
        convertView = ViewHolderUtils.loadingConvertView(parent.getContext(), convertView, R.layout.item_category_detial, ViewHolder.class);
        mHolder = (ViewHolder) convertView.getTag();
        mHolder.tv_comic_title.setText(list.get(position).Title);
        mHolder.tv_comic_intro.setText(list.get(position).Explain);
        mHolder.tv_num_last.setText(list.get(position).Author);
        mHolder.tv_comic_status.setText(list.get(position).LastChapter.ChapterNo+" 话");
        Glide.with(context).load(list.get(position).FrontCover).centerCrop().into(mHolder.iv_zone_item);
        return convertView;
    }



    public static class ViewHolder {
        public ImageView iv_zone_item;
        public TextView tv_comic_title,tv_comic_intro,tv_num_last,tv_comic_status;
    }
}
