package com.akirachix.totosteps.models

import android.app.Application
import android.content.Context
import com.akirachix.totosteps.api.ApiClient
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.AndroidViewModel
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication : Application(), ViewModelStoreOwner {

    override val viewModelStore: ViewModelStore by lazy { ViewModelStore() }


    lateinit var userSession: UserSession
        private set

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        ApiClient.initialize(this)
        userSession = UserSession.getInstance(this)
    }
}

class UserSession private constructor(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    var currentChild: Int
        get() = sharedPreferences.getInt("CURRENT_CHILD", -1)
        set(value) = sharedPreferences.edit().putInt("CURRENT_CHILD", value).apply()

    companion object {
        @Volatile
        private var instance: UserSession? = null

        fun getInstance(application: Application): UserSession {
            return instance ?: synchronized(this) {
                instance ?: UserSession(application).also { instance = it }
            }
        }
    }
}
