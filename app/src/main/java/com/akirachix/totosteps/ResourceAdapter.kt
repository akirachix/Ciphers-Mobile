package com.akirachix.totosteps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.databinding.ActivityResourceListBinding
import com.akirachix.totosteps.databinding.ChildrenListViewBinding
import com.akirachix.totosteps.databinding.ChildrenMilestoneListBinding

class ResourceAdapter(var milestones: List<resources>):RecyclerView.Adapter<ResourcesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        var binding = ActivityResourceListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResourcesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
        var children = milestones[position]
        holder.binding.tvAge.text = children.age
    }

    override fun getItemCount(): Int {
        return milestones.size
    }
}

class ResourcesViewHolder(var binding: ActivityResourceListBinding):RecyclerView.ViewHolder(binding.root){

}

