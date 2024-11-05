package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.R
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.ActivitySignupBinding
import com.akirachix.totosteps.models.RegistrationResponse
import com.akirachix.totosteps.models.UserRegistration
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        etFirstName = binding.etFirstName
        etLastName = binding.etLastName
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        etConfirmPassword = binding.etConfirmPassword
    }

    private fun setupListeners() {
        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                registerUser()  // Call API for registration
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Get input values
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Clear all previous errors
        binding.tilFirstName.error = null
        binding.tilLastName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        // Validate First Name
        if (firstName.isEmpty()) {
            binding.tilFirstName.error = "First name is required"
            isValid = false
        }

        // Validate Last Name
        if (lastName.isEmpty()) {
            binding.tilLastName.error = "Last name is required"
            isValid = false
        }

        // Validate Email
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Please enter a valid email address"
            isValid = false
        }

        // Validate Password
        when {
            password.isEmpty() -> {
                binding.tilPassword.error = "Password is required"
                isValid = false
            }
            password.length < 6 -> {
                binding.tilPassword.error = "Password must be at least 6 characters"
                isValid = false
            }
        }

        // Validate Confirm Password
        when {
            confirmPassword.isEmpty() -> {
                binding.tilConfirmPassword.error = "Please confirm your password"
                isValid = false
            }
            confirmPassword != password -> {
                binding.tilConfirmPassword.error = "Passwords do not match"
                isValid = false
            }
        }

        return isValid
    }


    private fun registerUser() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        val user = UserRegistration(firstName, lastName, email, password)


        ApiClient.instance().registerUser(user).enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                if (response.isSuccessful) {
                    val registrationResponse = response.body()
                    Toast.makeText(
                        this@SignupActivity,
                        registrationResponse?.message ?: "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()


                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    finish() // Optional: finish current activity to prevent going back to it
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "Failed to register. Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}
