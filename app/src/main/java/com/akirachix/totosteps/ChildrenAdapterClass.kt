package com.akirachix.totosteps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.databinding.ChildrenListViewBinding

class ChildrenAdapterClass(var childrenList: List<ChildrenDataClass>):RecyclerView.Adapter<ChildrenViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        var binding = ChildrenListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        var children = childrenList[position]
        holder.binding.tvName.text = children.name
        holder.binding.tvAge.text = children.age
    }

    override fun getItemCount(): Int {
        return childrenList.size
    }
}

class ChildrenViewHolder(var binding: ChildrenListViewBinding):RecyclerView.ViewHolder(binding.root){

}