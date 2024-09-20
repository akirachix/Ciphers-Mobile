package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akirachix.totosteps.databinding.ActivityViewMilestoneAssessmentResultsBinding

class ViewMilestoneAssessmentResults : AppCompatActivity() {
    lateinit var binding: ActivityViewMilestoneAssessmentResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMilestoneAssessmentResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewMilestonesButton.setOnClickListener {
            var intent = Intent(this, ViewMilestoneAssessmentResults::class.java)
            startActivity(intent)
        }

        binding.backArrow.setOnClickListener {
            finish()
        }


    }
}