package me.henry.lib_base.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/** 基类Fragment接口 */
public interface IFragment {

    /** 是否使用EventBus */
    boolean useEventBus();
    /** 初始化 View*/
    void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /** 设置根布局 */
    int setContentViewLayout();
    /** 显示 */
    void onShow();
    /** 隐藏 */
    void onHide();

}
