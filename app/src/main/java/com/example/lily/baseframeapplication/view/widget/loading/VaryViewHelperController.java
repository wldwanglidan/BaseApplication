package com.example.lily.baseframeapplication.view.widget.loading;

import android.view.View;
import android.widget.TextView;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.utils.EmptyUtils;

/**
 * Created by lily on 2017/11/9.
 */

public class VaryViewHelperController {
    private IVaryViewHelper helper;


    public VaryViewHelperController(View view) {
        this(new VaryViewHelperX(view));
    }


    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.layout_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(helper.getContext()
                               .getResources()
                               .getString(R.string.common_no_network_msg));
        layout.findViewById(R.id.message_info_two).setVisibility(View.VISIBLE);
        //ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        //imageView.setImageResource(R.drawable.ic_exception);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }


    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.layout_message);
        TextView textView = layout.findViewById(R.id.message_info);
        if (!EmptyUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_error_msg));
        }

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.layout_message);
        TextView textView = layout.findViewById(R.id.message_info);
        if (!EmptyUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        else {
            textView.setText(helper.getContext()
                                   .getResources()
                                   .getString(R.string.common_empty_msg));
        }

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.layout_loading);
        if (!EmptyUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(
                    R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }


    public void restore() {
        helper.restoreView();
    }

}
