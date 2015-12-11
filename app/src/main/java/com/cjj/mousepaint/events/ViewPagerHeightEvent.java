package com.cjj.mousepaint.events;

/**
 * Created by Administrator on 2015/11/6.
 */
public class ViewPagerHeightEvent  {

    public int heith;
    public String vp;

    public ViewPagerHeightEvent(String vp,int heith) {
        this.vp = vp;
        this.heith = heith;
    }
}
