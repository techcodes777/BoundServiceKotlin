package com.eclatsol.boundservicekotlin.boundservicdepractice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eclatsol.boundservicekotlin.R
import com.eclatsol.boundservicekotlin.service.MyService
import java.util.Date
import java.util.concurrent.ScheduledExecutorService

class BoundActivity : AppCompatActivity() {

    companion object{
        var tvCurrentTime: TextView? = null
    }
    var btnStartService: Button? = null
    var btnStopService: Button? = null
    var bound = false
    private val scheduledExecutorService: ScheduledExecutorService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound)

        tvCurrentTime = findViewById<TextView>(R.id.tvCurrentTime)
        btnStartService = findViewById(R.id.btnStartService)
        btnStopService = findViewById(R.id.btnStopService)

        tvCurrentTime?.text = "Time" + Date()

        btnStartService?.setOnClickListener {
            val intent = Intent(this, MyServicePractice::class.java)
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        }
        btnStopService?.setOnClickListener {
            if (bound){
                unbindService(serviceConnection)
                bound = false
            }
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("BoundService", "onServiceConnected: ")
            bound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("BoundService", "onServiceDisconnected: ")
            bound = false
        }
    }
}