package com.example.lily.baseframeapplication.view.widget.refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aspsine.swipetoloadlayout.SwipeLoadMoreFooterLayout;
import com.example.lily.baseframeapplication.R;

/**
 * Created by lily on 2017/11/14.
 */

public class LoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ImageView ivSuccess;
    private ProgressBar progressBar;

    private int mFooterHeight;


    public LoadMoreFooterView(Context context) {
        super(context);
    }


    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }


    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(
                R.dimen.load_more_footer_height);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override public void onPrepare() {
        ivSuccess.setVisibility(GONE);
    }
    @Override public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivSuccess.setVisibility(GONE);
            progressBar.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                tvLoadMore.setText("松开加载更多");
            }
            else {
                //tvLoadMore.setText("SWIPE TO LOAD MORE");
            }
        }
    }

    @Override public void onLoadMore() {
        tvLoadMore.setText("加载更多中...");
        progressBar.setVisibility(VISIBLE);
    }


    @Override public void onRelease() {

    }


    @Override public void onComplete() {
        progressBar.setVisibility(GONE);
        ivSuccess.setVisibility(VISIBLE);
    }


    @Override public void onReset() {
        ivSuccess.setVisibility(GONE);
    }



}
