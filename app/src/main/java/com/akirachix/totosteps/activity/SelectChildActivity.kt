package com.akirachix.totosteps.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.databinding.ActivitySelectChildBinding
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.ChildrenRepository
import android.content.Intent
import com.akirachix.totosteps.activity.viewModel.SelectChildViewModel
import com.akirachix.totosteps.activity.viewModel.SelectChildViewModelFactory
import com.akirachix.totosteps.models.Child

class SelectChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectChildBinding

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
        binding = ActivitySelectChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            Log.d("SelectChildActivity", "Initializing API client")
            ApiClient.initialize(this)

            setupRecyclerView()
            observeViewModel()
            Log.d("SelectChildActivity", "Calling loadChildren()")
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
            adapter = this@SelectChildActivity.adapter
            layoutManager = LinearLayoutManager(this@SelectChildActivity)
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
        val intent = Intent(this, AutismUploadPhoto::class.java).apply {
            putExtra("CHILD_ID", child.childId)
        }
        startActivity(intent)
        finish()
    }
}
