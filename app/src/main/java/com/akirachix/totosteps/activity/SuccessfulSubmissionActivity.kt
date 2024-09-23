package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivitySuccessfulSubmissionBinding

class SuccessfulSubmissionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuccessfulSubmissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulSubmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, ViewAutismResultsActivity::class.java)
            startActivity(intent)

            finish()
        }, 2000)

    }
}


