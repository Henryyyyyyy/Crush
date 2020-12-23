package me.henry.lib_base.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.henry.lib_base.rxlifecycle.RxLifeCycleFragment

abstract class LibFragment : RxLifeCycleFragment() {
    protected var mActivity: Activity? = null

    /** 根布局view  */
    protected var mRootView: View? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView?.let {
            mRootView = inflater.inflate(layoutResource(), null)
        }
        initView(inflater, container, savedInstanceState)
        return mRootView

    }
    abstract fun initView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    abstract fun layoutResource(): Int

}