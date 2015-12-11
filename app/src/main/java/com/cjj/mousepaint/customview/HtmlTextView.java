package com.cjj.mousepaint.customview;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class HtmlTextView extends TextView {
    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;
    private String mEllipsis = "...";
    private boolean mNeedsResize = false;

    public HtmlTextView(Context paramContext) {
        super(paramContext);
    }

    public HtmlTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public HtmlTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if ((paramBoolean) || (this.mNeedsResize))
            resizeText(paramInt3 - paramInt1 - getCompoundPaddingLeft() - getCompoundPaddingRight(), paramInt4 - paramInt2 - getCompoundPaddingBottom() - getCompoundPaddingTop());
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if ((paramInt1 != paramInt3) || (paramInt2 != paramInt4))
            this.mNeedsResize = true;
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        this.linkHit = false;
        boolean bool = super.onTouchEvent(paramMotionEvent);
        if (this.dontConsumeNonUrlClicks)
            bool = this.linkHit;
        return bool;
    }

    public void resizeText() {
        int i = getHeight() - getPaddingBottom() - getPaddingTop();
        resizeText(getWidth() - getPaddingLeft() - getPaddingRight(), i);
    }

    public void resizeText(int paramInt1, int paramInt2) {
        CharSequence localCharSequence = getText();
        if ((localCharSequence == null) || (localCharSequence.length() == 0) || (paramInt2 <= 0) || (paramInt1 <= 0))
            return;
        TextPaint localTextPaint = getPaint();
        StaticLayout localStaticLayout = new StaticLayout(localCharSequence, localTextPaint, paramInt1, Layout.Alignment.ALIGN_NORMAL, 1.25F, 0.0F, true);
        int i = localStaticLayout.getLineCount();
        int j = paramInt2 / (localStaticLayout.getHeight() / i);
        if (i > j) {
            int k = localStaticLayout.getLineStart(j - 1);
            int m = localStaticLayout.getLineEnd(j - 1);
            float f1 = localStaticLayout.getLineWidth(j - 1);
            float f2 = localTextPaint.measureText(this.mEllipsis);
            while (paramInt1 < f1 + f2) {
                m--;
                f1 = localTextPaint.measureText(localCharSequence.subSequence(k, m + 1).toString());
            }
            setText(localCharSequence.subSequence(0, m) + this.mEllipsis);
        }
        setMaxLines(j);
        this.mNeedsResize = false;
    }

    public void setTextViewHtml(String paramString) {
        setText(new SpannableStringBuilder(Html.fromHtml(paramString)));
    }
}