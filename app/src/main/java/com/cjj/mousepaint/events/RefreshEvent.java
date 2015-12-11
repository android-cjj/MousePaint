package com.cjj.mousepaint.events;

import com.cjj.MaterialRefreshLayout;

/**
 * Created by Administrator on 2015/11/6.
 */
public class RefreshEvent {

    public String category ;
    public MaterialRefreshLayout mMaterialRefreshLayout;

    public RefreshEvent(String category)
    {
        this.category = category;
    }

    public RefreshEvent(MaterialRefreshLayout materialRefreshLayout,String category)
    {
        this.category = category;
        this.mMaterialRefreshLayout = materialRefreshLayout;
    }
}
