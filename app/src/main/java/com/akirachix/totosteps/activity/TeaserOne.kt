package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.OneTeaserBinding

class TeaserOne : AppCompatActivity() {
    lateinit var binding: OneTeaserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OneTeaserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, TeaserTwoActivity::class.java)
            startActivity(intent)
        }
    }
}