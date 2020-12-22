package me.henry.lib_base.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import me.henry.lib_base.rxlifecycle.RxLifeCycleFragment;
import me.henry.lib_base.ui.IFragment;


/** 基类Activity */
public abstract class BaseFragment extends RxLifeCycleFragment implements IFragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    /** 根布局view */
    protected View mRootView;
    /** Fragment当前状态是否可见 */
    protected boolean mIsVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView == null) {
            mRootView = inflater.inflate(setContentViewLayout(), null);

//            if (useEventBus()) {
//                //todo 注册event bus
//                BaseEventBusManager.register(this);
//            }
            initView(inflater, container, savedInstanceState);
        }
        setUserVisibleHint(getUserVisibleHint());
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mRootView == null) return; // view为空则跳过
        mIsVisible = isVisibleToUser;
        if(isVisibleToUser) {
            onShow();
        } else {
            onHide();
        }
    }

    public View getRootView(){return mRootView;}


    /** 是否使用eventBus,默认为使用 */
    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    //    if (useEventBus()) BaseEventBusManager.unregister(this);
    }

}