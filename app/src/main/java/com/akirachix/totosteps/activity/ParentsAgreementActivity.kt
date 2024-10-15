package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import com.akirachix.totosteps.R
import com.akirachix.totosteps.databinding.ActivityParentsAgreementBinding

class ParentsAgreementActivity : AppCompatActivity() {
    lateinit var binding: ActivityParentsAgreementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParentsAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBack: ImageView = findViewById(R.id.backArrow)
        val checkBoxAgree: CheckBox = findViewById(R.id.checkBoxAgree)
        val checkBoxDisagree: CheckBox = findViewById(R.id.checkBoxDisagree)
        val btnNext: Button = findViewById(R.id.btnNext)

        btnNext.isEnabled = false

        btnBack.setOnClickListener {
            onBackPressed()
        }

        checkBoxAgree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxDisagree.isChecked = false
                btnNext.isEnabled = true
            } else if (!checkBoxDisagree.isChecked) {
                btnNext.isEnabled = false
            }
        }

        checkBoxDisagree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBoxAgree.isChecked = false
                btnNext.isEnabled = true
            } else if (!checkBoxAgree.isChecked) {
                btnNext.isEnabled = false
            }
        }

        btnNext.setOnClickListener {
            if (checkBoxAgree.isChecked) {
                Toast.makeText(this, "Proceeding to the next step.", Toast.LENGTH_SHORT).show()
                 val intent = Intent(this, SelectChildActivity::class.java)
                 startActivity(intent)
            } else if (checkBoxDisagree.isChecked) {
                Toast.makeText(this, "You need to agree to proceed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}





