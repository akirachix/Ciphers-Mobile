package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.R
import com.akirachix.totosteps.databinding.ActivityQuestionScreenTwoBinding

class QuestionScreenTwoActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuestionScreenTwoBinding
    var questionFourRadioGroup: RadioGroup? = null
    var questionFiveRadioGroup: RadioGroup? = null
    var submitButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionScreenTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionFourRadioGroup = findViewById(R.id.questionFourRadioGroup)
        questionFiveRadioGroup = findViewById(R.id.questionFiveRadioGroup)
        submitButton = findViewById(R.id.submitButton)

        submitButton?.setOnClickListener {
            if (validateResponses()) {
                // Here you would typically send the responses to a server or save them locally
                Toast.makeText(this, "Responses submitted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
            }
        }

        binding.submitButton.setOnClickListener {
            var intent = Intent(this, ViewResults::class.java)
            startActivity(intent)
        }
    }

    private fun validateResponses(): Boolean {
        return questionFourRadioGroup?.checkedRadioButtonId != -1 &&
                questionFiveRadioGroup?.checkedRadioButtonId != -1
    }

}



