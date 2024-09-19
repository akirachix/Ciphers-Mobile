package com.akirachix.totosteps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityTeaserThreeBinding

class TeaserThreeActivity : AppCompatActivity() {
    lateinit var binding: ActivityTeaserThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =  ActivityTeaserThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}