package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityTeaserThreeBinding

class TeaserThreeActivity : AppCompatActivity() {
    lateinit var binding: ActivityTeaserThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =  ActivityTeaserThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinueOne.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSkipTwo.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}