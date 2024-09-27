package com.akirachix.totosteps.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akirachix.totosteps.databinding.QuestionsListViewBinding

class QuestionsAdapter(var questions: List<Question>):RecyclerView.Adapter<QuestionsViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        var binding = QuestionsListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question, position)
    }

    override fun getItemCount(): Int {
      return questions.size
    }
}

class QuestionsViewHolder(var binding: QuestionsListViewBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(question: Question, position: Int) {
        // Set the question text and number
        binding.sentence.text = question.questionJson
        binding.tvQuestionNumber.text = "${position + 1}"

        // Reset RadioGroup listener to avoid triggering on bind
        binding.radioGroup2.setOnCheckedChangeListener(null)

        // Clear previous selection
        binding.radioGroup2.clearCheck()

        // Pre-select radio button based on the answer (true/false/null)
        when (question.answer) {
            true -> binding.radioGroup2.check(binding.radioGroup2.getChildAt(0).id) // First button for "Yes"
            false -> binding.radioGroup2.check(binding.radioGroup2.getChildAt(1).id) // Second button for "No"
            null -> binding.radioGroup2.clearCheck() // No answer selected
        }

        // Set the listener for radio button selection changes
        binding.radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            question.answer = when (checkedId) {
                binding.radioGroup2.getChildAt(0).id -> true  // "Yes" selected
                binding.radioGroup2.getChildAt(1).id -> false // "No" selected
                else -> null // No selection
            }
        }
    }
}



