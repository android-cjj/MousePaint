package com.cjj.mousepaint.contral.control;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import com.cjj.mousepaint.R;
import com.cjj.mousepaint.contral.control.RhythmAdapter;
import com.cjj.mousepaint.utils.AnimatorUtils;
import com.cjj.mousepaint.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: shine
 * Date: 2014-12-12
 * Time: 16:48
 * Description:
 */
public class RhythmLayout extends HorizontalScrollView {


    private Context mContext;

    private RhythmAdapter mAdapter;

    private Handler mHandler;

    private ShiftMonitorTimer mTimer;

    private IRhythmItemListener mListener;


    private LinearLayout mLinearLayout;

    private float mItemWidth;

    private int mMaxTranslationHeight;

    private int mIntervalHeight;

    private int mCurrentItemPosition;

    private int mEdgeSizeForShiftRhythm;

    private int mScreenWidth;

    private long mFingerDownTime;

    private int mScrollStartDelayTime;

    private int mLastDisplayItemPosition;

    public RhythmLayout(Context context) {
        this(context, null);
    }

    public RhythmLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mScreenWidth = AppUtils.getScreenDisplayMetrics(mContext).widthPixels;
        mItemWidth = mScreenWidth / 7;
        mMaxTranslationHeight = (int) mItemWidth;
        mIntervalHeight = (mMaxTranslationHeight / 6);
        mEdgeSizeForShiftRhythm = getResources().getDimensionPixelSize(R.dimen.rhythm_edge_size_for_shift);
        mCurrentItemPosition = -1;
        mLastDisplayItemPosition = -1;
        mScrollStartDelayTime = 0;
        mFingerDownTime = 0;
        mHandler = new Handler();
        mTimer = new ShiftMonitorTimer();
        mTimer.startMonitor();
    }

    public void setAdapter(RhythmAdapter adapter) {
        this.mAdapter = adapter;
        if (mLinearLayout == null) {
            mLinearLayout = (LinearLayout) getChildAt(0);
        }
        mAdapter.setItemWidth(mItemWidth);
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            mLinearLayout.addView(mAdapter.getView(i, null, null));
        }
    }


    public void invalidateData() {
        int childCount = this.mLinearLayout.getChildCount();
        if (childCount < this.mAdapter.getCount())
            for (int i = childCount; i < this.mAdapter.getCount(); i++)
                this.mLinearLayout.addView(this.mAdapter.getView(i, null, null));
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mTimer.monitorTouchPosition(ev.getX(), ev.getY());
                updateItemHeight(ev.getX());
                break;
            case MotionEvent.ACTION_DOWN:
                mTimer.monitorTouchPosition(ev.getX(), ev.getY());
                mFingerDownTime = System.currentTimeMillis();
                updateItemHeight(ev.getX());
                if (mListener != null)
                    mListener.onStartSwipe();
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }

        return true;
    }


    private void actionUp() {
        mTimer.monitorTouchPosition(-1.0F, -1.0F);
        if (mCurrentItemPosition < 0) {
            return;
        }
        int firstPosition = getFirstVisibleItemPosition();
        int lastPosition = firstPosition + mCurrentItemPosition;
        final List viewList = getVisibleViews();
        int size = viewList.size();
        if (size > mCurrentItemPosition) {
            viewList.remove(mCurrentItemPosition);
        }
        if (firstPosition - 1 >= 0) {
            viewList.add(mLinearLayout.getChildAt(firstPosition - 1));
        }
        if (lastPosition + 1 <= mLinearLayout.getChildCount()) {
            viewList.add(mLinearLayout.getChildAt(lastPosition + 1));
        }
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < viewList.size(); i++) {
                    View downView = (View) viewList.get(i);
                    shootDownItem(downView, true);
                }
            }
        }, 200L);
        if (mListener != null)
            mListener.onSelected(lastPosition);
        mCurrentItemPosition = -1;
        vibrate(20L);
    }

    private void updateItemHeight(float scrollX) {
        List viewList = getVisibleViews();
        int position = (int) (scrollX / mItemWidth);
        if (position == mCurrentItemPosition || position >= mLinearLayout.getChildCount())
            return;
        mCurrentItemPosition = position;
        makeItems(position, viewList);
    }


    private void makeItems(int fingerPosition, List<View> viewList) {
        if (fingerPosition >= viewList.size()) {
            return;
        }
        int size = viewList.size();
        for (int i = 0; i < size; i++) {

            int translationY = Math.min(Math.max(Math.abs(fingerPosition - i) * mIntervalHeight, 10), mMaxTranslationHeight);
            updateItemHeightAnimator(viewList.get(i), translationY);
        }

    }


    private List<View> getVisibleViews() {
        ArrayList arrayList = new ArrayList();
        if (mLinearLayout == null)
            return arrayList;
        int firstPosition = getFirstVisibleItemPosition();
        int lastPosition = firstPosition + 7;
        if (mLinearLayout.getChildCount() < 7) {
            lastPosition = mLinearLayout.getChildCount();
        }
        for (int i = firstPosition; i < lastPosition; i++)
            arrayList.add(mLinearLayout.getChildAt(i));
        return arrayList;
    }


    private List<View> getVisibleViews(boolean isForward, boolean isBackward) {
        ArrayList viewList = new ArrayList();
        if (this.mLinearLayout == null)
            return viewList;
        int firstPosition = getFirstVisibleItemPosition();
        int lastPosition = firstPosition + 7;
        if (mLinearLayout.getChildCount() < 7) {
            lastPosition = mLinearLayout.getChildCount();
        }
        if ((isForward) && (firstPosition > 0))
            firstPosition--;
        if ((isBackward) && (lastPosition < mLinearLayout.getChildCount()))
            lastPosition++;
        for (int i = firstPosition; i < lastPosition; i++)
            viewList.add(mLinearLayout.getChildAt(i));

        return viewList;
    }


    public int getFirstVisibleItemPosition() {
        if (mLinearLayout == null) {
            return 0;
        }
        int size = mLinearLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = mLinearLayout.getChildAt(i);
            if (getScrollX() < view.getX() + mItemWidth / 2.0F)
                return i;
        }
        return 0;
    }


    class ShiftMonitorTimer extends Timer {
        private TimerTask timerTask;
        private boolean canShift = false;
        private float x;
        private float y;

        void monitorTouchPosition(float x, float y) {
            this.x = x;
            this.y = y;
            if ((x < 0.0F) || ((x > mEdgeSizeForShiftRhythm) && (x < mScreenWidth - mEdgeSizeForShiftRhythm)) || (y < 0.0F)) {
                mFingerDownTime = System.currentTimeMillis();
                this.canShift = false;
            } else {
                this.canShift = true;
            }
        }

        void startMonitor() {
            if (this.timerTask == null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        long duration = System.currentTimeMillis() - mFingerDownTime;
                        if (canShift && duration > 1000) {
                            int firstPosition = getFirstVisibleItemPosition();
                            int toPosition = 0;
                            boolean isForward = false;
                            boolean isBackward = false;
                            final List<View> localList;
                            if (x <= mEdgeSizeForShiftRhythm && x >= 0.0F) {
                                if (firstPosition - 1 >= 0) {
                                    mCurrentItemPosition = 0;
                                    toPosition = firstPosition - 1;
                                    isForward = true;
                                    isBackward = false;

                                }
                            } else if (x > mScreenWidth - mEdgeSizeForShiftRhythm) {
                                if (getSize() >= 1 + (firstPosition + 7)) {
                                    mCurrentItemPosition = 7;
                                    toPosition = firstPosition + 1;
                                    isForward = false;
                                    isBackward = true;
                                }
                            }
                            if (isForward || isBackward) {
                                localList = getVisibleViews(isForward, isBackward);
                                final int finalToPosition = toPosition;
                                mHandler.post(new Runnable() {
                                    public void run() {
                                        makeItems(mCurrentItemPosition, localList);
                                        scrollToPosition(finalToPosition, 200, 0, true);
                                        vibrate(10L);
                                    }
                                });
                            }

                        }

                    }
                };
            }
            schedule(timerTask, 200L, 250L);

        }
    }

    public int getSize() {
        if (mLinearLayout == null) {
            return 0;
        }
        return mLinearLayout.getChildCount();
    }

    public View getItemView(int position) {
        return mLinearLayout.getChildAt(position);
    }


    public Animator scrollToPosition(int position, int duration, int startDelay, boolean isStart) {
        int viewX = (int) getItemView(position).getX();
        return smoothScrollX(viewX, duration, startDelay, isStart);
    }


    public Animator scrollToPosition(int position, int startDelay, boolean isStart) {
        int viewX = (int) getItemView(position).getX();
        return smoothScrollX(viewX, 300, startDelay, isStart);
    }

    private Animator smoothScrollX(int position, int duration, int startDelay, boolean isStart) {
        return AnimatorUtils.moveScrollViewToX(this, position, duration, startDelay, isStart);
    }


    private void updateItemHeightAnimator(View view, int translationY) {
        if (view != null)
            AnimatorUtils.showUpAndDownBounce(view, translationY, 180, true, true);
    }



    public Animator shootDownItem(int viewPosition, boolean isStart) {
        if ((viewPosition >= 0) && (mLinearLayout != null) && (getSize() > viewPosition))
            return shootDownItem(getItemView(viewPosition), isStart);
        return null;
    }

    public Animator shootDownItem(View view, boolean isStart) {
        if (view != null)
            return AnimatorUtils.showUpAndDownBounce(view, mMaxTranslationHeight, 350, isStart, true);
        return null;
    }


    public Animator bounceUpItem(int viewPosition, boolean isStart) {
        if (viewPosition >= 0)
            return bounceUpItem(getItemView(viewPosition), isStart);
        return null;
    }

    public Animator bounceUpItem(View view, boolean isStart) {
        if (view != null)
            return AnimatorUtils.showUpAndDownBounce(view, 10, 350, isStart, true);
        return null;
    }


    private void vibrate(long miss) {
        ((Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0L, miss}, -1);
    }


    public void setRhythmListener(IRhythmItemListener listener) {
        this.mListener = listener;
    }

    public void setScrollRhythmStartDelayTime(int scrollStartDelayTime) {
        this.mScrollStartDelayTime = scrollStartDelayTime;
    }

    public float getRhythmItemWidth() {
        return mItemWidth;
    }


    public void showRhythmAtPosition(int position) {
        if (this.mLastDisplayItemPosition == position)
            return;
        Animator scrollAnimator;
        Animator bounceUpAnimator;
        Animator shootDownAnimator;

        if ((this.mLastDisplayItemPosition < 0) || (mAdapter.getCount() <= 7) || (position <= 3)) {
            scrollAnimator = scrollToPosition(0, mScrollStartDelayTime, false);
        } else if (mAdapter.getCount() - position <= 3) {
            scrollAnimator = scrollToPosition(mAdapter.getCount() - 7, mScrollStartDelayTime, false);
        } else {
            scrollAnimator = scrollToPosition(position - 3, mScrollStartDelayTime, false);
        }
        bounceUpAnimator = bounceUpItem(position, false);
        shootDownAnimator = shootDownItem(mLastDisplayItemPosition, false);
        AnimatorSet animatorSet1 = new AnimatorSet();
        if (bounceUpAnimator != null) {
            animatorSet1.playTogether(bounceUpAnimator);
        }
        if (shootDownAnimator != null) {
            animatorSet1.playTogether(shootDownAnimator);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playSequentially(new Animator[]{scrollAnimator, animatorSet1});
        animatorSet2.start();
        mLastDisplayItemPosition = position;
    }

}
