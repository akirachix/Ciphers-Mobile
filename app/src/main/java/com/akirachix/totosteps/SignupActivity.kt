package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    lateinit var etFirstName: TextInputEditText
    lateinit var etLastName: TextInputEditText
    lateinit var etEmail: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var etConfirmPassword: TextInputEditText
    lateinit var cbAgreePolicy: CheckBox
    lateinit var btnSignUp: Button
    lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupListeners()

    }
        fun initViews() {
            etFirstName = findViewById(R.id.etFirstName)
            etLastName = findViewById(R.id.etLastName)
            etEmail = findViewById(R.id.etEmail)
            etPassword = findViewById(R.id.etPassword)
            etConfirmPassword = findViewById(R.id.etConfirmPassword)
            cbAgreePolicy = findViewById(R.id.cbAgreePolicy)
            btnSignUp = findViewById(R.id.btnSignUp)
            tvLogin = findViewById(R.id.tvLogin)
        }

        fun setupListeners() {
            btnSignUp.setOnClickListener {
                if (validateInputs()) {
                  var intent = Intent(this, DevelopmentalMilestonesScreenOne::class.java)
                    startActivity(intent)

                }
            }

            tvLogin.setOnClickListener {
                // Navigate to Login screen
                finish()
            }
        }

        fun validateInputs(): Boolean {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return false
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return false
            }

            if (!cbAgreePolicy.isChecked) {
                Toast.makeText(this, "Please agree to the Policy and Privacy", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }



