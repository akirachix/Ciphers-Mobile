package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.ChildrenRepository
import com.akirachix.totosteps.activity.viewModel.SelectChildViewModel
import com.akirachix.totosteps.activity.viewModel.SelectChildViewModelFactory
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.ActivitySelectChildQuestionnaireBinding
import com.akirachix.totosteps.models.Child
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

class SelectChildQuestionnaire : AppCompatActivity() {

    private lateinit var binding: ActivitySelectChildQuestionnaireBinding


    private val viewModel: SelectChildViewModel by viewModels {
        SelectChildViewModelFactory(
            ChildrenRepository(
                apiService = ApiClient.instance(),
                sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            )
        )
    }
    private lateinit var adapter: SelectAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectChildQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            Log.d("SelectChildQuestionnaire", "Initializing API client")
            ApiClient.initialize(this)

            setupRecyclerView()
            observeViewModel()
            Log.d("SelectChildQuestionnaire", "Calling loadChildren()")
            viewModel.loadChildren()
        } catch (e: Exception) {
            Log.e("SelectChildActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error initializing: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = SelectAdapter { child ->
            viewModel.selectChild(child)
            startAssessment(child)
        }
        binding.rvChildren.apply {
            adapter = this@SelectChildQuestionnaire.adapter
            layoutManager = LinearLayoutManager(this@SelectChildQuestionnaire)
        }
    }

    private fun observeViewModel() {
        viewModel.children.observe(this) { children ->
            Log.d("SelectChildActivity", "Received children update. Size: ${children?.size}")
            if (children.isNullOrEmpty()) {
                Log.w("SelectChildActivity", "No children found")
                Toast.makeText(this, "No children found", Toast.LENGTH_LONG).show()
            } else {
                adapter.updateChildren(children)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("SelectChildActivity", "Error received: $errorMessage")
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        viewModel.selectedChild.observe(this) { child ->
            Log.d("SelectChildActivity", "Child selected: ${child.username}")
        }
    }

    private fun startAssessment(child: Child) {
        val ageInMonths = calculateAgeInMonths(child.dateOfBirth)
        val intent = Intent(this, DevelopmentalMilestonesScreenTwo::class.java).apply {
            putExtra("CHILD_ID", child.childId)
            putExtra("CHILD_AGE_MONTHS", ageInMonths)
        }
        startActivity(intent)
        finish()
    }

    private fun calculateAgeInMonths(dateOfBirth: String): Int {
        val dob = LocalDate.parse(dateOfBirth)
        val today = LocalDate.now()
        return Period.between(dob, today).toTotalMonths().toInt()
    }
}



