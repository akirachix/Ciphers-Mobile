package com.akirachix.totosteps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.FragmentResourcesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResourcesFragment : Fragment() {
    private lateinit var binding: FragmentResourcesBinding
    private lateinit var resourceAdapter: ResourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        fetchResources()
    }

    private fun setupRecyclerView() {
        resourceAdapter = ResourceAdapter(listOf())
        binding.rvChildren.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = resourceAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    resourceAdapter.filter(newText ?: "")
                    updateNoResultsVisibility()
                    return true
                }
            })

            // Optional: Set these properties for better UX
            isSubmitButtonEnabled = false
            setIconifiedByDefault(false)
            queryHint = "Search resources by age in months..."
        }
    }

    private fun updateNoResultsVisibility() {
        binding.tvNoResults.visibility = if (resourceAdapter.getFilteredItemCount() == 0
            && binding.searchView.query.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun fetchResources() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvNoResults.visibility = View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance().getResources()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val resources = response.body() ?: emptyList()
                        resourceAdapter.updateResources(resources)
                        updateNoResultsVisibility()
                    } else {
                        handleError("Failed to fetch resources: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    handleError("Error fetching resources: ${e.message}")
                }
            }
        }
    }

    private fun handleError(errorMessage: String) {
        Log.e("ResourcesFragment", errorMessage)
        // Optional: Show error message to user
        binding.tvNoResults.apply {
            text = "Unable to load resources. Please try again."
            visibility = View.VISIBLE
        }
    }

    // Optional: Add this if you want to clear search when leaving the fragment
    override fun onDestroyView() {
        super.onDestroyView()
        binding.searchView.setQuery("", false)
    }
}