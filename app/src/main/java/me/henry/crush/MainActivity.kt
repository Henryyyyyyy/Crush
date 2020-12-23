package me.henry.crush

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jeremyliao.liveeventbus.LiveEventBus
import me.henry.crush.databinding.ActivityMainBinding
import me.henry.crush.util.runDelay
import me.henry.crush.util.start

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        LiveEventBus.get("xixi").observe(this, Observer {
            Log.d("zxc", "get nice on main")
        })
        LiveEventBus.get("xixi").post("nice")
        runDelay({
            start(SecondActivity::class.java)
        }, 2000)
    }
}
