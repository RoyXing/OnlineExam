package com.onlineexamination.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by 庞品 on 2017/2/14.
 * 控制viewpager滑动速度
 */

public class FixedSpeedScroller extends Scroller {
    private int mDuration =400;
    public FixedSpeedScroller(Context context) {
        super(context);
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }


}
