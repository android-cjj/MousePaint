package com.cjj.mousepaint.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;


public class AnimatorUtils {
    public static Animator animViewFadeIn(View paramView) {
        return animViewFadeIn(paramView, 200L, null);
    }

    public static Animator animViewFadeIn(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{0.0F, 1.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static Animator animViewFadeOut(View paramView) {
        return animViewFadeOut(paramView, 200L, null);
    }

    public static Animator animViewFadeOut(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{1.0F, 0.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }



    public static Animator moveScrollViewToX(View view, int toX, int time, int delayTime, boolean isStart) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "scrollX", new int[]{toX});
        objectAnimator.setDuration(time);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setStartDelay(delayTime);
        if (isStart)
            objectAnimator.start();
        return objectAnimator;
    }


    public static void showBackgroundColorAnimation(View view, int preColor, int currColor, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{preColor, currColor});
        animator.setDuration(duration);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }


    public static Animator showUpAndDownBounce(View view, int translationY, int animatorTime, boolean isStartAnimator, boolean isStartInterpolator) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", translationY);
        if (isStartInterpolator) {
            objectAnimator.setInterpolator(new OvershootInterpolator());
        }
        objectAnimator.setDuration(animatorTime);
        if (isStartAnimator) {
            objectAnimator.start();
        }
        return objectAnimator;
    }

}