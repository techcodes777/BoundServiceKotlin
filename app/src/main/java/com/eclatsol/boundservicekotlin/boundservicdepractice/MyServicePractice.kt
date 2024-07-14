package com.eclatsol.boundservicekotlin.boundservicdepractice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.eclatsol.boundservicekotlin.boundservicdepractice.BoundActivity.Companion.tvCurrentTime
import java.util.Date
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MyServicePractice : Service() {

    var scheduledExecutorService: ScheduledExecutorService? = null
    val binder: IBinder = Binder()


    override fun onCreate() {
        super.onCreate()
        Log.e("BoundService", "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e("BoundService", "onBind: ")
        scheduledExecutorService = Executors.newScheduledThreadPool(1)
        scheduledExecutorService?.scheduleAtFixedRate(Runnable {
            tvCurrentTime?.text = "Time" + Date()
        }, 1, 1, TimeUnit.SECONDS)
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("BoundService", "onUnbind: ")
        scheduledExecutorService!!.shutdown()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BoundService", "onDestroy: ")
    }

    fun task1() {
        println("Running task1...")
    }

    fun task3() {
        println("Running task3...")
    }
}