package com.eclatsol.boundservicekotlin.boundservicdepractice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eclatsol.boundservicekotlin.R

class TimeStampActivity : AppCompatActivity() {

    lateinit var printTimestampButton: Button
    lateinit var stopServiceButon: Button
    lateinit var timestampText: TextView
    lateinit var mBoundService: BoundTimeStampService
    var mServiceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_stamp)

        timestampText = findViewById(R.id.timestamp_text)
        printTimestampButton = findViewById(R.id.print_timestamp)
        stopServiceButon = findViewById(R.id.stop_service)
        printTimestampButton.setOnClickListener {
            if (mServiceBound) {
            timestampText.text = mBoundService.getTimestamp()
            }
        }
        stopServiceButon.setOnClickListener {
            if (mServiceBound) {
                unbindService(mServiceConnection)
                mServiceBound = false
            }
            val intent = Intent(
                this,
                BoundTimeStampService::class.java
            )
            stopService(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundTimeStampService::class.java)
        startService(intent)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mServiceBound) {
            unbindService(mServiceConnection)
            mServiceBound = false
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder: BoundTimeStampService.MyBinder = service as BoundTimeStampService.MyBinder
            mBoundService = myBinder.getService()
            mServiceBound = true
        }
    }
}