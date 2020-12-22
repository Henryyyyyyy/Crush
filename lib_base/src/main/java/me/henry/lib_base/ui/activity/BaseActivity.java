package me.henry.lib_base.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.noober.background.BackgroundLibrary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.disposables.Disposable;
import me.henry.lib_base.R;
import me.henry.lib_base.others.util.InputMethodUtil;
import me.henry.lib_base.rxlifecycle.RxLifeCycleActivity;
import me.henry.lib_base.swipeback.SwipeBackLayout;
import me.henry.lib_base.ui.IActivity;

/**
 * 基类Activity
 */
public abstract class BaseActivity extends RxLifeCycleActivity implements IActivity {

    protected Activity mActivity;
    private LocalBroadcastManager mLocalBroadcastManager;
    private ExitBroadcastReceiver mExitBroadcastReceiver;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isForbidBroughtToFront() && ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)) {
            finish();
            return;
        }
        mSwipeBackLayout = getSwipeBackLayout();
        mActivity = this;
        try {
            int layoutResID = setContentViewLayout();
            if (layoutResID != 0) {
                setContentView(layoutResID); // 设置根布局
                initView(savedInstanceState); // 初始化布局
                if (useEventBus()) {
                    //todo 注册eventbus
                 //   BaseEventBusManager.register(this);
                    // 必须放在initView之后，防止布局初始化之前就收到EventBus事件，导致操作控件报空指针
                }
            }
        } catch (Exception e) {
            //todo log
            e.printStackTrace();
        }

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        //todo 弄一个exit的key
        // IntentFilter intentFilter = new IntentFilter(ExtraKey.ACTION_EXIT);
        IntentFilter intentFilter = new IntentFilter("abccc");
        mExitBroadcastReceiver = new ExitBroadcastReceiver();
        mLocalBroadcastManager.registerReceiver(mExitBroadcastReceiver, intentFilter);

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setSwipeBackEnable(supportSlideBack());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // if (useEventBus()) BaseEventBusManager.unregister(this);
    }

    public boolean supportSlideBack() {
        return true;
    }

    /**
     * 是否禁止从后台带到前台
     *
     * @return
     */
    public boolean isForbidBroughtToFront() {
        return true;
    }

    /**
     * 是否使用eventBus,默认为不使用
     */
    @Override
    public boolean useEventBus() {
        return false;
    }


    private class ExitBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }


}