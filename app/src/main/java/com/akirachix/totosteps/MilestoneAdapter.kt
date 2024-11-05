package com.akirachix.totosteps
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.models.Milestone

class MilestoneAdapter(
    private val milestones: List<Milestone>,
    private val onClick: (Milestone) -> Unit
) : RecyclerView.Adapter<MilestoneAdapter.MilestoneViewHolder>() {

    inner class MilestoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val ageTextView: TextView = view.findViewById(R.id.age)
        val categoryTextView: TextView = view.findViewById(R.id.category)
        val summaryTextView: TextView = view.findViewById(R.id.tvSummary)

        init {
            // Set Nunito fonts
            val nunitoRegular = ResourcesCompat.getFont(view.context, R.font.nunito_medium)
            val nunitoBold = ResourcesCompat.getFont(view.context, R.font.nunito_black)
            val nunitoSemiBold = ResourcesCompat.getFont(view.context, R.font.nunito)
            val nunitoBlack = ResourcesCompat.getFont(view.context, R.font.nunito_bold)

            nameTextView.typeface = nunitoBold
            ageTextView.typeface = nunitoBlack
            categoryTextView.typeface = nunitoSemiBold
            summaryTextView.typeface = nunitoRegular
        }

        fun bind(milestone: Milestone) {
            nameTextView.text = milestone.name
            ageTextView.text = "Age: ${milestone.age} months"
            categoryTextView.text = milestone.description

            itemView.setOnClickListener {
                Log.d("MilestoneAdapter", "Clicked: ${milestone.name}")
                onClick(milestone)
            }
        }

        private fun formatSummary(summary: Map<String, List<String>>): String {
            return summary.entries.joinToString("\n\n") { (category, items) ->
                "$category:\n${items.joinToString("\n") { "• $it" }}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_milestone_adapter, parent, false)
        Log.d("MilestoneAdapter", "Creating new ViewHolder")
        return MilestoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: MilestoneViewHolder, position: Int) {
        Log.d("MilestoneAdapter", "Binding position: $position")
        val milestone = milestones[position]
        holder.bind(milestone)
    }

    override fun getItemCount(): Int = milestones.size
}