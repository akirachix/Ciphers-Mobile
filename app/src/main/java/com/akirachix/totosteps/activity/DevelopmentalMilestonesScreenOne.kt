package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.SelectChildQuestionnaire
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenOneBinding

class DevelopmentalMilestonesScreenOne : AppCompatActivity() {
    lateinit var binding: ActivityDevelopmentalMilestonesScreenOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentalMilestonesScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            var intent = Intent(this, SelectChildQuestionnaire::class.java)
            startActivity(intent)
        }


        }
    }
