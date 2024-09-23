package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setupListeners()

    }
    fun initViews() {

        var etEmail = binding.etEmail
        var etPassword = binding.etPassword
        var btnSignUp = binding.btnSignUp
        var tvLogin = binding.tvLogin
    }

    fun setupListeners() {
        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                var intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)

            }
        }

        binding.tvLogin.setOnClickListener {
            binding.tvLogin.setOnClickListener {
                var intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun validateInputs(): Boolean {

        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        else {

            return true

        }
    }
}



