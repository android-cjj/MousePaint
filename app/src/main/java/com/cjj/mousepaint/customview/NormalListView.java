package com.cjj.mousepaint.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * create by cjj on 2015/10/30
 */
public class NormalListView extends ListView{
	
	private boolean isAutoSetNumColumns = true;
	private int horizontalSpacing = 0;
    private int columnWidth = 0;
    private boolean isScroll = false;

	public NormalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
  
    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        
        super.onMeasure(widthMeasureSpec,expandSpec); 
    }

//	@Override
//	public void setAdapter(ListAdapter adapter) {
//	
//		if(isScroll)
//		{
//		
//		int gridViewWidth = 0;
//		int adapterCount = adapter.getCount();
//		
//		if(columnWidth == 0)
//		{
//			for(int i = 0 ; i< adapter.getCount();i++)
//		    {
//			View rowView = adapter.getView(i,null,null);
//	        measureView(rowView);
//			gridViewWidth += rowView.getMeasuredWidth();
//		    }
//		}
//		else
//		{
//			gridViewWidth += columnWidth*adapterCount;
//		}
//
//		
//		this.setLayoutParams(new LinearLayout.LayoutParams(gridViewWidth,LayoutParams.WRAP_CONTENT));
//	
//		}
//		
//		super.setAdapter(adapter);
//	} 
//	
//	
//	private void measureView(View child) 
//	{
//		 ViewGroup.LayoutParams p = child.getLayoutParams();
//	       if (p == null) {
//	           p = new ViewGroup.LayoutParams(
//	                   ViewGroup.LayoutParams.FILL_PARENT,
//	                   ViewGroup.LayoutParams.WRAP_CONTENT);
//	       }
//
//	       int childWidthSpec = ViewGroup.getChildMeasureSpec(0,
//	               0 + 0, p.width);
//	       int lpHeight = p.height;
//	       int childHeightSpec;
//	       if (lpHeight > 0) {
//	           childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
//	       } else {
//	           childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//	       }
//	       child.measure(childWidthSpec, childHeightSpec);
//	}
//    

}
