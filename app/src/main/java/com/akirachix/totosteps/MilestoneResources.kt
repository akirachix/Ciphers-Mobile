package com.akirachix.totosteps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.ActivityMilestoneResourcesBinding
import com.akirachix.totosteps.models.Milestone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MilestoneResources : AppCompatActivity() {

    private lateinit var binding: ActivityMilestoneResourcesBinding
    private lateinit var milestoneAdapter: MilestoneAdapter
    private val milestoneList = mutableListOf<Milestone>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMilestoneResourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchMilestones()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        milestoneAdapter = MilestoneAdapter(milestoneList) { milestone ->
            navigateToDetail(milestone)
        }
        binding.rvMilestones.layoutManager = LinearLayoutManager(this)
        binding.rvMilestones.adapter = milestoneAdapter
    }

    private fun fetchMilestones() {
        ApiClient.instance().getMilestones().enqueue(object : Callback<List<Milestone>> {
            override fun onResponse(call: Call<List<Milestone>>, response: Response<List<Milestone>>) {
                if (response.isSuccessful) {
                    response.body()?.let { milestones ->
                        milestoneList.clear()
                        milestoneList.addAll(milestones.filter { it.age == 2 }) // Filter by age
                        milestoneAdapter.notifyDataSetChanged()
                    } ?: run {
                        Log.e("API Error", "Response body is null")
                    }
                } else {
                    Log.e("API Error", response.message())
                }
            }

            override fun onFailure(call: Call<List<Milestone>>, t: Throwable) {
                Log.e("Network Error", t.message ?: "Unknown error")
            }
        })
    }

    private fun navigateToDetail(milestone: Milestone) {
        // Convert the summary Map to a formatted string
        val summaryText = milestone.summary.entries.joinToString("\n\n") { (category, items) ->
            "$category:\n${items.joinToString("\n") { "• $it" }}"
        }

        val intent = Intent(this, MilestoneDetailActivity::class.java).apply {
            putExtra("MILESTONE_ID", milestone.milestone_id)
            putExtra("CATEGORY", milestone.description)
            putExtra("SUMMARY", summaryText)  // Now passing a formatted string
            putExtra("MILESTONE_NAME", milestone.name)  // Don't forget to add this if needed
        }
        startActivity(intent)
    }}
