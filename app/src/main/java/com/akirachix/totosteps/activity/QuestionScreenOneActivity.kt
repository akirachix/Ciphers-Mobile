package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityQuestionScreenOneBinding

class QuestionScreenOneActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuestionScreenOneBinding

    var question1RadioGroup: RadioGroup? = null
    var ageSpinner: Spinner? = null
    var question3RadioGroup: RadioGroup? = null
    var continueButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionScreenOneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        question1RadioGroup = binding.question1RadioGroup
        ageSpinner = binding.ageSpinner
        question3RadioGroup = binding.question3RadioGroup
        continueButton = binding.continueButton


        val ageRanges = arrayOf("please choose the age range of your child", "0-6 months","6-12 months", "1-2 years", "2-3 years", "3-4 years", "4-5 years", "5+ years")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageRanges)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner?.adapter = adapter

        continueButton?.setOnClickListener {
            if (validateResponses()) {
                binding.continueButton.setOnClickListener {
                    var intent = Intent(this, QuestionScreenTwoActivity::class.java)
                    startActivity(intent)
                }

            } else {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun validateResponses(): Boolean {
        val question1Answered = question1RadioGroup?.checkedRadioButtonId != -1
        val ageSelected = ageSpinner?.selectedItemPosition != 0
        val question3Answered = question3RadioGroup?.checkedRadioButtonId != -1

        return question1Answered && ageSelected && question3Answered
    }
}







