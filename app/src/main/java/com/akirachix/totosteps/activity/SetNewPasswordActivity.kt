package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.R
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.api.ResetPasswordRequest
import com.akirachix.totosteps.databinding.ActivitySetNewPasswordBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SetNewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetNewPasswordBinding
    private val apiService = ApiClient.instance()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val email = intent.getStringExtra("EMAIL") ?: ""

        setupUI()
        setupClickListeners()

    }

    private fun setupUI() {
        // Set status bar color

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setupClickListeners() {
        binding.btnResetPassword.setOnClickListener {
            validateAndResetPassword()
        }
    }

    private fun validateAndResetPassword() {
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        // Clear previous errors
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        when {
            password.length < 6 -> {
                binding.tilPassword.error = "Password must be at least 6 characters"
            }
            password.isEmpty() -> {
                binding.tilPassword.error = "Password is required"
            }
            confirmPassword.isEmpty() -> {
                binding.tilConfirmPassword.error = "Please confirm your password"
            }
            password != confirmPassword -> {
                binding.tilConfirmPassword.error = "Passwords do not match"
            }


            else -> {
                // All validations passed
//                resetPassword(email, password)
            }
        }

    }

    private fun resetPassword(email: String, newPassword: String) {
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.resetPassword(ResetPasswordRequest(email, newPassword))
                }

                if (response.isSuccessful) {
                    showSuccessOverlay()
                } else {
                    handleError(response.message())
                }
            } catch (e: Exception) {
                handleError(e.message ?: "An error occurred")
            }
        }
    }



private fun handleError(message: String) {
        binding.tilPassword.error = message
    }

    private fun showSuccessOverlay() {
        // Show overlay with fade in animation
        binding.successOverlay.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(300)
                .start()
        }

        // Hide overlay and navigate after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            binding.successOverlay.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    binding.successOverlay.visibility = View.GONE
                    navigateToLogin()
                }
                .start()
        }, 2000)

    }

    private fun navigateToLogin(){
        // Clear the activity stack and go to login
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

