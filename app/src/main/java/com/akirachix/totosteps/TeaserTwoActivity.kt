package com.lauranyaaga.totosteps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lauranyaaga.totosteps.databinding.ActivityTeaserTwoBinding

class TeaserTwoActivity : AppCompatActivity() {
    lateinit var binding: ActivityTeaserTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeaserTwoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_teaser_two)

    }
}