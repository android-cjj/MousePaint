package com.cjj.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by cjj on 2015/4/2.
 */
public class LoadingView extends FrameLayout {
    private int width;
    private int heigth;
    private Paint paint;
    private int color = 0xff795548;
    private int arcD = 1;
    private int arcO = 0;
    private float rotateAngle = 0;
    private int limite = 0;
    public LoadingView(Context context) {
        super(context);
        initView(context,null);
    }


    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }



    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.ViewSelector);
        color = t.getColor(R.styleable.ViewSelector_pro_color,color);
        t.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        heigth = h;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawProgress(canvas);
        invalidate();
    }


    /**
     * draw progress
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        if (arcO == limite)
            arcD += 6;
        if (arcD >= 290 || arcO > limite) {
            arcO += 6;
            arcD -= 6;
        }
        if (arcO > limite + 290) {
            limite = arcO;
            arcO = limite;
            arcD = 1;
        }
        rotateAngle += 4;
        canvas.rotate(rotateAngle, getWidth() / 2, getHeight() / 2);
        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        paint.setAntiAlias(true);
        paint.setColor(color);
        temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), arcO, arcD, true, paint);
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() / 2) - 8, transparentPaint);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }
}
