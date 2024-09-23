package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.databinding.ActivityParentsProfileBinding

class ParentsProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityParentsProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutTextView.setOnClickListener {
            var intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }


    }
}