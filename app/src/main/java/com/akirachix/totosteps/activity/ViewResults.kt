package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.backArrow.setOnClickListener {
            var intent = Intent(this, QuestionScreenTwoActivity::class.java)
            startActivity(intent)
        }

    }
}


