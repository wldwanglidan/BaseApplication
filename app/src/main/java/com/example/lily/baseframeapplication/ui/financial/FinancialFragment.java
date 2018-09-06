package com.example.lily.baseframeapplication.ui.financial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.base.BaseFragment;
import com.example.lily.baseframeapplication.manager.UserManager;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.ui.MainActivity;
import com.example.lily.baseframeapplication.utils.LanguageChangeUtil;

/**
 * Created by lily on 2017/11/16.
 */

public class FinancialFragment extends BaseFragment {
    //@BindView(R.id.tvTest) TextView mTvTest;
    //@BindView(R.id.btnTestLanguage) Button mBtnTest;

    Unbinder unbinder;
    private GlobalParams mGlobalParams;


    public static FinancialFragment newInstance() {
        FinancialFragment fragment = new FinancialFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override protected int getContentViewID() {
        return R.layout.fragment_financial;
    }


    @Override protected void initView(View view) {
        UserManager.getInstance().getGlobalParams().compose(bindToLifecycle()).
                subscribe(getNotProSubscribe(s -> mGlobalParams = s));
    }


    @Override protected View getLoadingTargetView() {

        return ButterKnife.findById(getActivity(), android.R.id.content);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container,
                savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @OnClick({ R.id.btnTestLanguage }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTestLanguage:
                LanguageChangeUtil.changeLanguage(mContext, "en");
                ((MainActivity) mContext).recreate();
                Log.d("result", "点击");
                //更新语言后，destroy当前页面，重新绘制
                //重启MainActivity
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
