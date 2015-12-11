package com.cjj.mousepaint.contral.control;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller
{
    private int mDuration;

    public ViewPagerScroller(Context paramContext)
    {
        super(paramContext);
    }

    public ViewPagerScroller(Context paramContext, Interpolator paramInterpolator)
    {
        super(paramContext, paramInterpolator);
    }

    public ViewPagerScroller(Context paramContext, Interpolator paramInterpolator, boolean paramBoolean)
    {
        super(paramContext, paramInterpolator, paramBoolean);
    }

    public void setDuration(int paramInt)
    {
        this.mDuration = paramInt;
    }

    public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        super.startScroll(paramInt1, paramInt2, paramInt3, paramInt4, this.mDuration);
    }

    public void startScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
        super.startScroll(paramInt1, paramInt2, paramInt3, paramInt4, this.mDuration);
    }
}