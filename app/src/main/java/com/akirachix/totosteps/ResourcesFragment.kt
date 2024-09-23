package com.akirachix.totosteps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.databinding.FragmentMilestoneBinding
import com.akirachix.totosteps.databinding.FragmentResourcesBinding

class ResourcesFragment : Fragment() {

    lateinit var binding: FragmentResourcesBinding
    lateinit var ResourceAdapter: ResourceAdapter
    val milestones = mutableListOf<resources>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentResourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        displayChildren()

        // Set a delay to move to the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            moveToNextActivity()
        }, 2000) // 2000 milliseconds = 2 seconds
    }

    fun setupRecyclerView() {
        binding.rvChildren.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ResourceAdapter = ResourceAdapter(milestones)
        binding.rvChildren.adapter = ResourceAdapter
    }

    fun displayChildren() {
        milestones.clear()
        milestones.addAll(listOf(
            resources("","Baby at 2months"),
            resources("","Baby at 4months"),
            resources("","Baby at 6months"),
            resources("","Baby at 8months"),
            resources("","Baby at 10months"),
            resources("","Baby at 12months"),
            resources("","Baby at 14months")
        ))

        ResourceAdapter.notifyDataSetChanged()
    }

    private fun moveToNextActivity() {
        val intent = Intent(requireContext(), MilestoneResources::class.java)
        startActivity(intent)
    }
}