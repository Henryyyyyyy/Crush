package me.henry.lib_base.ui.activity

import android.app.Activity
import android.os.Bundle
import me.henry.lib_base.rxlifecycle.RxLifeCycleActivity

abstract class LibActivity :RxLifeCycleActivity(){
    protected var mActivity: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=this
        try {
            layoutResource().apply { if (this!=0)  setContentView(this) }
            initView(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun layoutResource():Int

    abstract fun initView(savedInstanceState: Bundle?):Unit

    open fun observeEvent(){
        //todo 使用livedatabus
    }
}