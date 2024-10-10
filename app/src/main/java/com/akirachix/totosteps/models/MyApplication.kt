package com.akirachix.totosteps.models

import android.app.Application
import android.content.Context
import com.akirachix.totosteps.api.ApiClient

class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        ApiClient.initialize(this)
    }
}
