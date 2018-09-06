package com.example.lily.baseframeapplication.view.widget.refresh.header;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.example.lily.baseframeapplication.R;

/**
 * Created by lily on 2017/11/14.
 */

public class RefreshHeaderView extends RelativeLayout
        implements SwipeTrigger, SwipeRefreshTrigger {
    private ImageView ivRefresh;

    private AnimationDrawable mAnimDrawable;

    public  RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public  RefreshHeaderView(Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public RefreshHeaderView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        ivRefresh.setImageResource(R.drawable.header_animation);
        mAnimDrawable = (AnimationDrawable) ivRefresh.getDrawable();
    }


    @Override public void onRefresh() {
        mAnimDrawable.start();
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }

    }


    @Override public void onPrepare() {

    }


    @Override public void onMove(int i, boolean b, boolean b1) {

    }


    @Override public void onRelease() {
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }

    }


    @Override public void onComplete() {

    }


    @Override public void onReset() {
        mAnimDrawable.stop();
    }
}
