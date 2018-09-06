package com.example.lily.baseframeapplication.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by lily on 2017/11/14.
 * 引导页viewpager Adapter
 */

public class GuideAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageView> imageViewsList;

    public GuideAdapter(Context context, List<ImageView> imageViewsList) {
        this.mContext = context;
        this.imageViewsList = imageViewsList;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewsList.get(position));
    }


    @Override public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewsList.get(position));
        return imageViewsList.get(position);
    }


    @Override public int getCount() {
        return imageViewsList.size();
    }


    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
