package com.listofreposgithub

import android.annotation.SuppressLint
import android.app.Application
import timber.log.Timber

@SuppressLint("Registered")
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
