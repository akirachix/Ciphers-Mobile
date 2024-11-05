
package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akirachix.totosteps.activity.utils.Constants
import com.akirachix.totosteps.activity.viewModel.LoginViewModel
import com.akirachix.totosteps.databinding.ActivityLoginBinding
import com.akirachix.totosteps.models.LoginResponse

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        supportActionBar?.hide()

        redirectIfLoggedIn()


        loginViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { loginResponse ->
                Log.d("LoginActivity", "Login successful. User: ${loginResponse.user.first_name}")
                persistLogin(loginResponse)
                val userId = loginResponse.user.user_id
                val email = loginResponse.user.email
                Log.d("LoginActivity", "Login successful. User ID: $userId")
                saveUserIdToSharedPreferences(userId)
                saveEmailToSharedPreferences(email)

                showToast("Login successful")
                navigateToChildAccount()
            }.onFailure { throwable ->
                Log.e("LoginActivity", "Please enter the correct email and password. ${throwable.message}")
                showError(throwable.localizedMessage ?: "Please enter the correct email and password.")
            }
        })


        binding.btnSignUp.setOnClickListener { handleEmailLogin() }
        binding.tvLogin.setOnClickListener { navigateToSignUp() }
    }

    private fun redirectIfLoggedIn() {
        val sharedPreferences = getSharedPreferences(Constants.PREFS, MODE_PRIVATE)
        val firstName = sharedPreferences.getString(Constants.FIRST_NAME, "")
        if (firstName!!.isNotBlank()) {
            navigateToChildAccount()
        }
    }


    private fun handleEmailLogin() {
        val username = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (loginViewModel.validateForm(username, password)) {
            loginViewModel.login(username, password)
        } else {
            showToast("Please enter both username and password")
        }
    }

    private fun persistLogin(loginResponse: LoginResponse) {
        val sharedPreferences = getSharedPreferences(Constants.PREFS, MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(Constants.FIRST_NAME, loginResponse.user.first_name)
            putString(Constants.LAST_NAME, loginResponse.user.last_name)
            putInt(Constants.USER_ID, loginResponse.user.user_id)
            apply()
        }
        Log.d("LoginActivity", "Login information persisted")
    }

    private fun saveUserIdToSharedPreferences(userId: Int) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putInt("USER_ID", userId).apply()
        Log.d("LoginActivity", "User ID saved to SharedPreferences: $userId")
    }
    private fun saveEmailToSharedPreferences(email: String){
        val sharePref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        sharePref.edit().putString("USER_EMAIL", email)
        Log.d("LoginActivity", "User email saved to SharedPreferences: $email")

    }

    private fun navigateToChildAccount() {
        Log.d("LoginActivity", "Navigating to ChildAccountActivity")
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun navigateToSignUp() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    private fun showError(message: String) {
        Log.e("LoginActivity", "Error: $message")
        showToast(message)
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}