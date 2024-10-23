package com.akirachix.totosteps.activity

//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenTwoBinding
//import com.akirachix.totosteps.models.QuestionsAdapter
//import com.akirachix.totosteps.activity.viewModel.DevelopmentalMilestoneViewModel
//import com.akirachix.totosteps.api.ApiClient
//import com.akirachix.totosteps.models.ResultData
//import com.akirachix.totosteps.models.ResultResponse
//import retrofit2.Call
//
//class DevelopmentalMilestonesScreenTwo : AppCompatActivity() {
//    private lateinit var binding: ActivityDevelopmentalMilestonesScreenTwoBinding
//    private lateinit var viewModel: DevelopmentalMilestoneViewModel
//    private lateinit var adapter: QuestionsAdapter
//    private var childAgeInMonths: Int = -1
//    private var childId: Int = -1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDevelopmentalMilestonesScreenTwoBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        childAgeInMonths = intent.getIntExtra("CHILD_AGE_MONTHS", -1)
//        childId = intent.getIntExtra("CHILD_ID", -1)
//
//        if (childAgeInMonths == -1 || childId == -1) {
//            Toast.makeText(this, "Invalid child data", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        viewModel = ViewModelProvider(this).get(DevelopmentalMilestoneViewModel::class.java)
//
//        adapter = QuestionsAdapter(emptyList())
//
//        binding.rvChildren.layoutManager = LinearLayoutManager(this)
//        binding.rvChildren.adapter = adapter
//
//        observeViewModel()
//
//
//
//        val (milestone, category) = getMilestoneAndCategory(childAgeInMonths)
//
//        viewModel.fetchQuestions(category, milestone)
//
//        setupUi(milestone)
//    }
//
//    private fun observeViewModel() {
//        viewModel.questions.observe(this) { questions ->
//            Log.d("DevelopmentalMilestonesScreenTwo", "Received ${questions.size} questions")
//
//            if (questions.isNotEmpty()) {
//                adapter.questions = questions
//                adapter.notifyDataSetChanged()
//            } else {
//                Log.e("DevelopmentalMilestonesScreenTwo", "No questions received")
//            }
//        }
//
//        viewModel.isLoading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.error.observe(this) { errorMessage ->
//            Log.e("DevelopmentalMilestonesScreenTwo", "Error: $errorMessage")
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun setupUi(milestone: Int) {
//        binding.btnBackFour.setOnClickListener {
//            finish()
//        }
//
//        binding.btnNextFour.setOnClickListener {
//            if (allQuestionsAnswered()) {
//                val userId = getUserIdFromSharedPreferences()
//
//                if (userId != -1) {
//                    Log.d("DevelopmentalMilestonesScreenTwo", "User ID: $userId")
//
//                    val answers = collectAnswers()
//
//                    if (milestone != -1) {
//                        val resultData = ResultData(
//                            milestone = milestone,
//                            answers = answers,
//                            user = userId
//                        )
//
//                        submitResult(resultData)
//
//                        val intent = Intent(this, DevelopmentalMilestonesScreenThree::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(this, "Milestone ID not provided", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun allQuestionsAnswered(): Boolean {
//        return viewModel.questions.value?.all { it.answer != null } == true
//    }
//
//    private fun collectAnswers(): Map<String, String> {
//        val answersMap = mutableMapOf<String, String>()
//
//        viewModel.questions.value?.forEach { question ->
//            question.answer?.let { answer ->
//                answersMap[question.questionJson] = answer.toString()
//            }
//        }
//
//        return answersMap
//    }
//
//    private fun submitResult(resultData: ResultData) {
//        val call = ApiClient.instance().submitResult(resultData)
//
//        call.enqueue(object : retrofit2.Callback<ResultResponse> {
//            override fun onResponse(
//                call: Call<ResultResponse>,
//                response: retrofit2.Response<ResultResponse>
//            ) {
//                if (response.isSuccessful) {
//                    Toast.makeText(
//                        this@DevelopmentalMilestonesScreenTwo,
//                        "Good job! Continue",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    Toast.makeText(
//                        this@DevelopmentalMilestonesScreenTwo,
//                        "Failed to submit result",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
//                Toast.makeText(
//                    this@DevelopmentalMilestonesScreenTwo,
//                    "Error: ${t.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }
//
//    private fun getUserIdFromSharedPreferences(): Int {
//        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        return sharedPreferences.getInt("USER_ID", -1)
//    }
//
//
//    private fun getMilestoneAndCategory(ageInMonths: Int): Pair<Int, String> {
//        return when (ageInMonths) {
//            in 0..3 -> Pair(1, "Social")
//            in 4..5 -> Pair(2, "Social")
//            in 6..8 -> Pair(3, "Social")
//            in 9..11 -> Pair(4, "Social")
//            in 12..14 -> Pair(5, "Social")
//            in 15..17 -> Pair(6, "Social")
//            in 18..23 -> Pair(7, "Social")
//            in 24..29 -> Pair(8, "Social")
//            in 30..33 -> Pair(9, "Social")
//            in 34..36 -> Pair(10, "Social")
//            else -> Pair(-1, "Unknown")
//        }
//    }
//}

import android.content.Intent
import com.akirachix.totosteps.BaseMilestoneScreen

class DevelopmentalMilestonesScreenTwo : BaseMilestoneScreen() {
    override val category: String = "Social"
    override val isLastScreen: Boolean = false

    override fun moveToNextScreen() {
        val intent = Intent(this, DevelopmentalMilestonesScreenThree::class.java).apply {
            putExtra("CHILD_AGE_MONTHS", childAgeInMonths)
            putExtra("CHILD_ID", childId)
        }
        startActivity(intent)
    }
}

