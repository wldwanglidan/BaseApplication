package com.example.lily.baseframeapplication.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.base.BaseFragment;
import com.example.lily.baseframeapplication.manager.UserManager;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.ui.financial.FinancialFragment;

/**
 * Created by lily on 2017/11/16.
 */

public class MeFragment extends BaseFragment {
    Unbinder unbinder;
    private GlobalParams mGlobalParams;


    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override protected int getContentViewID() {
        return R.layout.fragment_me;
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
}
