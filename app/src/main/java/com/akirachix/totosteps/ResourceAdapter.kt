    package com.akirachix.totosteps


    import android.content.Intent
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.akirachix.totosteps.activity.ResourceDetailsActivity
    import com.akirachix.totosteps.databinding.ActivityResourceListBinding
    import com.google.gson.Gson

    class ResourceAdapter(private var originalResources: List<resources>) :
        RecyclerView.Adapter<ResourcesViewHolder>() {

        private var filteredResources: List<resources> = originalResources

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
            val binding = ActivityResourceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ResourcesViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
            val resource = filteredResources[position]
            holder.binding.tvAge.text = resource.title

            val gson = Gson()
            val tipsJson = gson.toJson(resource.tips)
            val activitiesJson = gson.toJson(resource.activities)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ResourceDetailsActivity::class.java)
                intent.putExtra("RESOURCE_TITLE", resource.title)
                intent.putExtra("RESOURCE_TIPS", tipsJson)
                intent.putExtra("RESOURCE_ACTIVITIES", activitiesJson)
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = filteredResources.size

        fun updateResources(newResources: List<resources>) {
            originalResources = newResources
            filteredResources = newResources
            notifyDataSetChanged()
        }

        fun filter(query: String) {
            filteredResources = if (query.isEmpty()) {
                originalResources
            } else {
                originalResources.filter { resource ->
                    resource.title.contains(query, ignoreCase = true)
                }
            }
            notifyDataSetChanged()
        }

        // Optional: Add this function if you want to get the filtered list size
        fun getFilteredItemCount(): Int = filteredResources.size
    }

    class ResourcesViewHolder(var binding: ActivityResourceListBinding) :
        RecyclerView.ViewHolder(binding.root)


