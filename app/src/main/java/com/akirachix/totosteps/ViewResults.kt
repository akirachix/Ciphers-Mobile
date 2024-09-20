package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.databinding.ActivityViewResultsBinding

class ViewResults : AppCompatActivity() {
    lateinit var binding: ActivityViewResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            var intent = Intent(this, QuestionScreenTwoActivity::class.java)
            startActivity(intent)
        }

    }
}


