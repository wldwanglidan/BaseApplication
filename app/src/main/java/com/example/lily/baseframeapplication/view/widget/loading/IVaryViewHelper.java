package com.example.lily.baseframeapplication.view.widget.loading;

import android.content.Context;
import android.view.View;

/**
 * Created by lily on 2017/11/9.
 */

public interface IVaryViewHelper {
    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
