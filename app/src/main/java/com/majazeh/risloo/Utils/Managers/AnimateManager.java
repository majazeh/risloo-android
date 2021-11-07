package com.majazeh.risloo.Utils.Managers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

public class AnimateManager {

    /*
    ---------- Funcs ----------
    */

    public static void animateStatusBarColor(Activity activity, int duration, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(fromColor, toColor);
        colorAnimation.setDuration(duration);
        colorAnimation.addUpdateListener(animator -> activity.getWindow().setStatusBarColor((Integer) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    public static void animateAppBarColor(AppBarLayout appBarLayout, int duration, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(fromColor, toColor);
        colorAnimation.setDuration(duration);
        colorAnimation.addUpdateListener(animator -> appBarLayout.setBackgroundColor((Integer) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    public static void animateViewAlpha(View view, int duration, float fromAlpha, float toAlpha) {
        ObjectAnimator fadeAnimation = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha);
        fadeAnimation.setDuration(duration);
        fadeAnimation.start();
    }

}