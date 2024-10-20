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

        binding.sentence.text = question.questionJson
        binding.questionNumber.text = "${position + 1}"


        binding.radioGroup1.setOnCheckedChangeListener(null)


        binding.radioGroup1.clearCheck()


        when (question.answer) {
            true -> binding.radioGroup1.check(binding.radioGroup1.getChildAt(0).id)
            false -> binding.radioGroup1.check(binding.radioGroup1.getChildAt(1).id)
            null -> binding.radioGroup1.clearCheck()
        }


        binding.radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            question.answer = when (checkedId) {
                binding.radioGroup1.getChildAt(0).id -> true  // "Yes" selected
                binding.radioGroup1.getChildAt(1).id -> false // "No" selected
                else -> null
            }
        }
    }
}



