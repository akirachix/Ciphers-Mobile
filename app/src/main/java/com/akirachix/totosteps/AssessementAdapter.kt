package com.akirachix.totosteps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.databinding.ActivityResourceListBinding

class AssessementAdapter(var assessmentresources: List<AssessmentDataClass>):RecyclerView.Adapter<AssessmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentViewHolder {
        var binding = ActivityResourceListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AssessmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssessmentViewHolder, position: Int) {
        var children = assessmentresources[position]
        holder.binding.tvAge.text = children.name
    }

    override fun getItemCount(): Int {
       return assessmentresources.size
    }
}

class AssessmentViewHolder(var binding: ActivityResourceListBinding ):RecyclerView.ViewHolder(binding.root){

}