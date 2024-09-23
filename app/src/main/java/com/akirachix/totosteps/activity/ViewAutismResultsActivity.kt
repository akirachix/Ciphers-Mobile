package com.akirachix.totosteps.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.databinding.ActivityViewAutismResultsBinding

class ViewAutismResultsActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewAutismResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAutismResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener {
            finish()
        }



    }
}