package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import com.akirachix.totosteps.R
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenTwoBinding

class DevelopmentalMilestonesScreenTwo : AppCompatActivity() {
    lateinit var binding: ActivityDevelopmentalMilestonesScreenTwoBinding
    lateinit var progressBar: ProgressBar
    lateinit var btnBack: Button
    lateinit var btnNext: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentalMilestonesScreenTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = findViewById(R.id.progressBar)
        btnBack = findViewById(R.id.btnBackFour)
        btnNext = findViewById(R.id.btnNextFour)

        // Set up click listeners
        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            // Handle next navigation or submission
            if (allQuestionsAnswered()) {
                var intent = Intent(this, DevelopmentalMilestonesScreenThree::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
            }
        }
        setupRadioGroupListeners()
        // Update progress bar as questions are answered
        updateProgressBar()
    }

    fun setupRadioGroupListeners() {
        val radioGroups = listOf(
            findViewById<RadioGroup>(R.id.radioGroup1),
            findViewById<RadioGroup>(R.id.radioGroup2),
            findViewById<RadioGroup>(R.id.radioGroup3),
            findViewById<RadioGroup>(R.id.radioGroup4)
        )

        for (radioGroup in radioGroups) {
            radioGroup.setOnCheckedChangeListener { _, _ ->
                updateProgressBar()
            }
        }
    }



        fun allQuestionsAnswered(): Boolean {
        val radioGroup1: RadioGroup = findViewById(R.id.radioGroup1)
        val radioGroup2: RadioGroup = findViewById(R.id.radioGroup2)
        val radioGroup3: RadioGroup = findViewById(R.id.radioGroup3)
        val radioGroup4: RadioGroup = findViewById(R.id.radioGroup4)

        return radioGroup1.checkedRadioButtonId != -1 &&
                radioGroup2.checkedRadioButtonId != -1 &&
                radioGroup3.checkedRadioButtonId != -1 &&
                radioGroup4.checkedRadioButtonId != -1
    }

    fun updateProgressBar() {
        // Update progress based on answered questions
        val totalQuestions = 4
        val answeredQuestions = listOf(
            R.id.radioGroup1,
            R.id.radioGroup2,
            R.id.radioGroup3,
            R.id.radioGroup4
        )
            .count { findViewById<RadioGroup>(it).checkedRadioButtonId != -1 }

        val progress = (answeredQuestions.toFloat() / totalQuestions * 100).toInt()
        progressBar.progress = progress
    }
}


