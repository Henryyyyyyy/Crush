package me.henry.lib_base.ui;


import android.os.Bundle;

;


/** 基类Activity接口 */
public interface IActivity {

    /** 是否使用EventBus */
    boolean useEventBus();
    /** 设置根布局 */
    int setContentViewLayout();
    /** 初始化View */
    void initView(Bundle savedInstanceState);

}
