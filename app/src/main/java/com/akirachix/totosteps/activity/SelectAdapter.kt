package com.akirachix.totosteps.activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.databinding.ChildrenListViewBinding
import com.akirachix.totosteps.models.Child
import org.threeten.bp.Period
import org.threeten.bp.LocalDate

class SelectAdapter(
    private val onChildSelected: (Child) -> Unit
) : RecyclerView.Adapter<SelectAdapter.ChildViewHolder>() {
    private var children = emptyList<Child>()

    inner class ChildViewHolder(private val binding: ChildrenListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(child: Child) {
            binding.tvName.text = child.username
            binding.tvAge.text = calculateAge(child.dateOfBirth)

            binding.root.setOnClickListener {
                onChildSelected(child)
            }
        }

        private fun calculateAge(dateOfBirth: String): String {
            val dob = LocalDate.parse(dateOfBirth)
            val today = LocalDate.now()

            val period = Period.between(dob, today)
            val years = period.years
            val months = period.months

            return when {
                years > 0 -> "$years years, $months months"
                else -> "${period.toTotalMonths()} months"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ChildrenListViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(children[position])
    }

    override fun getItemCount() = children.size

    fun updateChildren(newChildren: List<Child>) {
        children = newChildren
        notifyDataSetChanged()
    }
}