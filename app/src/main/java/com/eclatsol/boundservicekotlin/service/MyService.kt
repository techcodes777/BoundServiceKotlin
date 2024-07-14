package com.eclatsol.boundservicekotlin.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.eclatsol.boundservicekotlin.MainActivity
import kotlinx.coroutines.Runnable
import java.util.Date
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MyService : Service() {

    val iBinder = Binder()

    lateinit var scheduledExecutorService: ScheduledExecutorService
    override fun onCreate() {
        super.onCreate()
        Log.e("BoundServiceKotlin", "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("BoundServiceKotlin", "onBind: ")
        scheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduledExecutorService.scheduleAtFixedRate(object : Runnable{
            override fun run() {
                MainActivity.tvTime.text = Date().toString()
            }

        },1,1,TimeUnit.SECONDS)
        return iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("BoundServiceKotlin", "onUnbind: ")
        scheduledExecutorService.shutdown()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BoundServiceKotlin", "onDestroy: ")

    }
}