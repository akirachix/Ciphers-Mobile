package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.akirachix.totosteps.databinding.ActivityTeaserTwoBinding

class TeaserTwoActivity : AppCompatActivity() {
    lateinit var binding: ActivityTeaserTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeaserTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
   binding.btnContinue.setOnClickListener {
       val intent = Intent(this, TeaserThreeActivity::class.java)
       startActivity(intent)
   }
    }
}