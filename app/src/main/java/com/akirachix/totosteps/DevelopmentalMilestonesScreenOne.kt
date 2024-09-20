package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenOneBinding

class DevelopmentalMilestonesScreenOne : AppCompatActivity() {
    lateinit var binding: ActivityDevelopmentalMilestonesScreenOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentalMilestonesScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            var intent = Intent(this, DevelopmentalMilestonesScreenTwo::class.java)
            startActivity(intent)
        }


        }
    }
