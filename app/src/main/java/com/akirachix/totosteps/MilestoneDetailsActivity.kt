package com.akirachix.totosteps

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MilestoneDetailActivity : AppCompatActivity() {
    private lateinit var tvMilestoneName: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milestone_details)

        tvMilestoneName = findViewById(R.id.tvMilestoneName)
        tvCategory = findViewById(R.id.tvCategory)
        tvSummary = findViewById(R.id.tvSummary)

        val milestoneId = intent.getIntExtra("MILESTONE_ID", -1)
        val summary = intent.getStringExtra("SUMMARY") ?: ""


        val milestoneName = intent.getStringExtra("MILESTONE_NAME") ?: "Unknown Milestone"


        tvMilestoneName.text = milestoneName
        tvSummary.text = summary
    }
}
