package com.akirachix.totosteps.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UserPreferences
        userPreferences = UserPreferences(this)

        // Show splash screen for 2 seconds then navigate
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToDestination()
        }, 2000)
    }

    private fun navigateToDestination() {
        val intent = when {
            !userPreferences.isLoggedIn -> {
                // User not logged in, go to login screen
                Intent(this, TeaserOne::class.java)
            }
            userPreferences.isFirstTimeUser -> {
                // First time user, show teaser screens
                Intent(this, TeaserOne::class.java)
            }
            else -> {
                // Returning logged-in user, go directly to home
                Intent(this, HomeScreenActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }
}

// UserPreferences class to manage user state
class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_TIME_USER = "first_time_user"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    var isFirstTimeUser: Boolean
        get() = prefs.getBoolean(KEY_FIRST_TIME_USER, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST_TIME_USER, value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()
}
