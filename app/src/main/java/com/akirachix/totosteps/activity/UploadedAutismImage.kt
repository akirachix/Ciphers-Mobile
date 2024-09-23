package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityUploadedAutismImageBinding

class UploadedAutismImage : AppCompatActivity() {
    lateinit var binding: ActivityUploadedAutismImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadedAutismImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSubmit.setOnClickListener {
            var intent = Intent(this, SuccessfulSubmissionActivity::class.java)
            startActivity(intent)
        }

        binding.backArrow.setOnClickListener {
            finish()
        }



    }
}