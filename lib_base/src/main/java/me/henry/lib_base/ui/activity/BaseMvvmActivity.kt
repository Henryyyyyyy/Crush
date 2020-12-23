package me.henry.lib_base.ui.activity

import android.os.Bundle

import me.henry.lib_base.ui.viewmodel.AbstractViewModel

 class BaseMvvmActivity<VM: AbstractViewModel> :LibActivity(){
     override fun layoutResource(): Int {
         TODO("Not yet implemented")
     }

     override fun initView(savedInstanceState: Bundle?) {
         TODO("Not yet implemented")
     }

 }