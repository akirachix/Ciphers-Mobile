package com.akirachix.totosteps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.databinding.FragmentChildrenBinding
import com.akirachix.totosteps.databinding.FragmentMilestoneBinding

class MilestoneFragment : Fragment() {

    lateinit var binding: FragmentMilestoneBinding

    lateinit var childrenAdapter: MilestonesAdapterClass
    val children = mutableListOf<ChildrenDataClass>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMilestoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        displayChildren()
    }

    fun setupRecyclerView() {
        binding.rvChildren.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        childrenAdapter = MilestonesAdapterClass(children)
        binding.rvChildren.adapter = childrenAdapter
    }



    fun displayChildren() {
        children.clear()
        children.addAll(listOf(
            ChildrenDataClass("", "Muthoni_kayaba", "2years 10months old"),
            ChildrenDataClass("", "Zach_arthur", "2years 10months old"),
            ChildrenDataClass("", "Muthoni_kayaba", "2years 10months old")
        ))
        childrenAdapter.notifyDataSetChanged()
    }



}