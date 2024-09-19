package com.lauranyaaga.totosteps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lauranyaaga.totosteps.databinding.OneTeaserBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: OneTeaserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OneTeaserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}