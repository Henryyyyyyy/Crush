package me.henry.crush.util

import android.app.Activity
import android.content.Intent
import android.os.Handler

fun Activity.runDelay(block: ()->Unit,delay:Long){
    Handler().postDelayed(Runnable {
        block.invoke()
    },delay)


}
fun <T:Activity>Activity.start(clazz: Class<T>){
    val intent=Intent(this,clazz)
    this.startActivity(intent)


}