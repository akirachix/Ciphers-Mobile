package com.akirachix.totosteps

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akirachix.totosteps.models.QuestionsAdapter
import com.akirachix.totosteps.activity.viewModel.DevelopmentalMilestoneViewModel
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestoneScreensBinding
import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenTwoBinding
import com.akirachix.totosteps.models.ResultData
import com.akirachix.totosteps.models.ResultResponse
import retrofit2.Call

open class BaseMilestoneScreen : AppCompatActivity() {
    lateinit var binding: ActivityDevelopmentalMilestoneScreensBinding
    lateinit var viewModel: DevelopmentalMilestoneViewModel
    lateinit var adapter: QuestionsAdapter
    var childAgeInMonths: Int = -1
    var childId: Int = -1
    private lateinit var userEmail: String


    open val category: String = ""
    open val isLastScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentalMilestoneScreensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childAgeInMonths = intent.getIntExtra("CHILD_AGE_MONTHS", -1)
        childId = intent.getIntExtra("CHILD_ID", -1)

        if (childAgeInMonths == -1 || childId == -1) {
            Toast.makeText(this, "Invalid child data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel = ViewModelProvider(this).get(DevelopmentalMilestoneViewModel::class.java)

        adapter = QuestionsAdapter(emptyList())

        binding.rvChildren.layoutManager = LinearLayoutManager(this)
        binding.rvChildren.adapter = adapter

        observeViewModel()

        val milestone = getMilestone(childAgeInMonths)

        viewModel.fetchQuestions(category, milestone)

        setupUi(milestone)
    }
    protected open fun observeViewModel() {
        viewModel.questions.observe(this) { questions ->
            Log.d("BaseMilestoneScreen", "Received ${questions.size} questions")
            if (questions.isNotEmpty()) {
                adapter.questions = questions
                adapter.notifyDataSetChanged()
            } else {
                Log.e("BaseMilestoneScreen", "No questions received")
                Toast.makeText(this, "No questions available for this category and age group", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("BaseMilestoneScreen", "Error: $errorMessage")
            Toast.makeText(this, "Error fetching questions: $errorMessage", Toast.LENGTH_LONG).show()
        }
    }


    open fun setupUi(milestone: Int) {
        binding.btnBackFour.setOnClickListener {
            finish()
        }

        if (isLastScreen) {
            binding.btnNextFour.text = "Submit"
            binding.btnNextFour.setOnClickListener {
                if (allQuestionsAnswered()) {
                    showSubmitDialog(milestone)
                } else {
                    Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            binding.btnNextFour.text = "Next"
            binding.btnNextFour.setOnClickListener {
                if (allQuestionsAnswered()) {
                    submitResultAndMoveToNextScreen(milestone)
                } else {
                    Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showSubmitDialog(milestone: Int) {
        AlertDialog.Builder(this)
            .setTitle("Submit Assessment")
            .setMessage("Are you sure you want to submit the assessment?")
            .setPositiveButton("Yes") { _, _ ->
                submitResultAndShowConfirmation(milestone)
            }
            .setNegativeButton("No", null)
            .show()
    }

    fun submitResultAndShowConfirmation(milestone: Int) {
        val resultData = ResultData(
            milestone = milestone,
            answers = collectAnswers(),
            user = childId,
            email = userEmail
        )

        submitResult(resultData) { success ->
            if (success) {
                AlertDialog.Builder(this)
                    .setTitle("Assessment Submitted")
                    .setMessage("Please check your email for the assessment results.")
                    .setPositiveButton("OK") { _, _ ->
                        finishAssessment()
                    }
                    .show()
            } else {
                Toast.makeText(this, "Failed to submit assessment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    open fun moveToNextScreen() {
        // To be overridden in subclasses
    }

    open fun finishAssessment() {
        // To be overridden in subclasses
    }

    fun submitResult(resultData: ResultData, callback: (Boolean) -> Unit) {
        val call = ApiClient.instance().submitResult(resultData)

        call.enqueue(object : retrofit2.Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: retrofit2.Response<ResultResponse>
            ) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun submitResultAndMoveToNextScreen(milestone: Int) {
        val resultData = ResultData(
            milestone = milestone,
            answers = collectAnswers(),
            user = childId,
            email = userEmail
        )

        submitResult(resultData) { success ->
            if (success) {
                moveToNextScreen()
            } else {
                Toast.makeText(this, "Failed to submit result", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun allQuestionsAnswered(): Boolean {
        return viewModel.questions.value?.all { it.answer != null } == true
    }

    fun collectAnswers(): Map<String, String> {
        val answersMap = mutableMapOf<String, String>()

        viewModel.questions.value?.forEach { question ->
            question.answer?.let { answer ->
                answersMap[question.questionJson] = answer.toString()
            }
        }

        return answersMap
    }
    protected fun fetchQuestions(category: String, milestone: Int) {
        Log.d("BaseMilestoneScreen", "Fetching questions for category: $category, milestone: $milestone")
        viewModel.fetchQuestions(category, milestone)
    }

    fun getMilestone(ageInMonths: Int): Int {
        return when (ageInMonths) {
            in 0..3 -> 1
            in 4..5 -> 2
            in 6..8 -> 3
            in 9..11 -> 4
            in 12..14 -> 5
            in 15..17 -> 6
            in 18..23 -> 7
            in 24..29 -> 8
            in 30..33 -> 9
            in 34..36 -> 10
            else -> -1
        }
    }
}
