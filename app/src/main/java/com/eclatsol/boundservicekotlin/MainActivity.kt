package com.eclatsol.boundservicekotlin

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eclatsol.boundservicekotlin.boundservicdepractice.MyServicePractice
import java.util.Date

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var tvTime: TextView
    }

    var bound = false
    var btnStartService: Button? = null
    var btnStopService: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tvTime)
        btnStartService = findViewById(R.id.btnStartService)
        btnStopService = findViewById(R.id.btnStopService)

        tvTime.text = Date().toString()


//        scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                tvCurrentTime.setText("Time" + new Date());
//            }
//        },1,1, TimeUnit.SECONDS);
        btnStartService?.setOnClickListener(View.OnClickListener {
            bindService(
                Intent(
                    this,
                    MyServicePractice::class.java
                ), serviceConnection, BIND_AUTO_CREATE
            )
        })

        btnStopService?.setOnClickListener(View.OnClickListener {
            if (bound) {
                unbindService(serviceConnection)
                bound = false
            }
        })
    }

    var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

    }
}