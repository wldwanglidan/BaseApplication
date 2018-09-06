package com.example.lily.baseframeapplication.view.widget.loading;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by lily on 2017/11/9.
 */

public class VaryViewHelperX implements IVaryViewHelper {
    private IVaryViewHelper helper;
    private View view;

    //支持FrameLayout
    public VaryViewHelperX(View view) {
        super();
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        group.removeView(view);
        group.addView(frameLayout, layoutParams);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View floatView = new View(view.getContext());
        frameLayout.addView(view, params);
        frameLayout.addView(floatView, params);
        helper = new VaryViewHelper(floatView);

    }

    @Override public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }


    @Override public void restoreView() {
        helper.restoreView();

    }


    @Override public void showLayout(View view) {
        helper.showLayout(view);
    }


    @Override public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }


    @Override public Context getContext() {
        return helper.getContext();
    }


    @Override public View getView() {
        return view;
    }
}
