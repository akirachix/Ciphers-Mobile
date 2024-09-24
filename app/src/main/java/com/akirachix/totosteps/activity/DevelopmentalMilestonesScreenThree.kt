package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.R
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenThreeBinding

class DevelopmentalMilestonesScreenThree : AppCompatActivity() {

    lateinit var binding: ActivityDevelopmentalMilestonesScreenThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDevelopmentalMilestonesScreenThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackFour.setOnClickListener {
            finish()
        }

        binding.btnNextFour.setOnClickListener {
            // Handle next navigation or submission
            if (allQuestionsAnswered()) {
                var intent = Intent(this, DevelopmentalMilestonesScreenFour::class.java)
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
            findViewById<RadioGroup>(R.id.radioGroup2)

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

            return radioGroup1.checkedRadioButtonId != -1 &&
                    radioGroup2.checkedRadioButtonId != -1

        }

        fun updateProgressBar() {
            // Update progress based on answered questions
            val totalQuestions = 2
            val answeredQuestions = listOf(R.id.radioGroup1, R.id.radioGroup2)
                .count { findViewById<RadioGroup>(it).checkedRadioButtonId != -1 }

            val progress = (answeredQuestions.toFloat() / totalQuestions * 100).toInt()
            binding.progressBar.progress = progress
        }
    }


