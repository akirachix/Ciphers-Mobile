package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityAutismUploadPhotoBinding

class AutismUploadPhoto : AppCompatActivity() {
    lateinit var binding: ActivityAutismUploadPhotoBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutismUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
           finish()
        }

        binding.ivUpload.setOnClickListener {
            var intent = Intent(this, UploadedAutismImage::class.java)
            startActivity(intent)


        }



    }
}