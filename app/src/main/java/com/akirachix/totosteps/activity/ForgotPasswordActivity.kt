package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.R
import com.akirachix.totosteps.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (isValidEmail(email)) {
                val intent = Intent(this, SetNewPasswordActivity::class.java)
                intent.putExtra("EMAIL", email)
                startActivity(intent)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Invalid email format"
            false
        } else {
            binding.tilEmail.error = null
            true
        }
    }
}



