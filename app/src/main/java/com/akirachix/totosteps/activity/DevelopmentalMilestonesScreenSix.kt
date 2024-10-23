package com.akirachix.totosteps.activity

//import android.content.DialogInterface
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.akirachix.totosteps.databinding.ActivityDevelopmentalMilestonesScreenSixBinding
//import com.akirachix.totosteps.models.QuestionsAdapter
//import com.akirachix.totosteps.activity.viewModel.DevelopmentalMilestoneViewModel
//import com.akirachix.totosteps.api.ApiClient
//import com.akirachix.totosteps.models.ResultData
//import com.akirachix.totosteps.models.ResultResponse
//import retrofit2.Call
//
//class DevelopmentalMilestonesScreenSix : AppCompatActivity() {
//    private lateinit var binding: ActivityDevelopmentalMilestonesScreenSixBinding
//    private lateinit var viewModel: DevelopmentalMilestoneViewModel
//    private lateinit var adapter: QuestionsAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDevelopmentalMilestonesScreenSixBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        viewModel = ViewModelProvider(this).get(DevelopmentalMilestoneViewModel::class.java)
//
//
//        adapter = QuestionsAdapter(emptyList())
//
//
//        binding.rvChildren.layoutManager = LinearLayoutManager(this)
//        binding.rvChildren.adapter = adapter
//
//
//        observeViewModel()
//
//        viewModel.fetchQuestions("Movement", 1)
////        val milestoneId = intent.getIntExtra("MILESTONE_ID", -1)
////        if (milestoneId != -1) {
////            viewModel.fetchQuestions("Movement", milestoneId)
////        } else {
////            // Handle the error, milestoneId not found
////            Toast.makeText(this, "Milestone ID not provided", Toast.LENGTH_SHORT).show()
////        }
//
//        setupUi()
//    }
//
//    private fun observeViewModel() {
//        viewModel.questions.observe(this) { questions ->
//            Log.d("DevelopmentalMilestonesScreenSix", "Received ${questions.size} questions")
//
//                     if (questions.isNotEmpty()) {
//                adapter.questions = questions
//                adapter.notifyDataSetChanged()
//                updateProgressBar()
//            } else {
//                Log.e("DevelopmentalMilestonesScreenSix", "No questions received")
//            }
//        }
//
//        viewModel.isLoading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.error.observe(this) { errorMessage ->
//            // Display error message as a toast
//            Log.e("DevelopmentalMilestonesScreenSix", "Error: $errorMessage")
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun setupUi() {
//
//        binding.btnNextFour.setOnClickListener {
//            if (allQuestionsAnswered()) {
//                val userId = getUserIdFromSharedPreferences()
//
//                if (userId != -1) {
//                    Log.d("DevelopmentalMilestonesScreenTwo", "User ID: $userId")
//
//                } else {
//                    Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
//                }
//                val answers = collectAnswers()
//
//                val milestoneId = 1
//
//
//                val resultData = ResultData(
//                    milestone = milestoneId,
//                    answers = answers,
//                    user = userId
//                )
//
//
//                submitResult(resultData)
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
//
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
//    private fun updateProgressBar() {
//        val totalQuestions = viewModel.questions.value?.size ?: 0
//        val answeredQuestions = viewModel.questions.value?.count { it.answer != null } ?: 0
//
//        val progress = if (totalQuestions > 0) {
//            (answeredQuestions.toFloat() / totalQuestions * 100).toInt()
//        } else {
//            0
//        }
//
//        binding.progressBar.progress = progress
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
//                    showAssessmentResultsDialog()
//                } else {
//                    Toast.makeText(
//                        this@DevelopmentalMilestonesScreenSix,
//                        "Failed to submit result",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
//                Toast.makeText(
//                    this@DevelopmentalMilestonesScreenSix,
//                    "Error: ${t.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }
//
//    private fun showAssessmentResultsDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Assessment Results")
//            .setMessage("Please check your email for assessment results.")
//            .setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
//                dialog.dismiss()
//                navigateToHomePage()
//            }
//
//        val dialog = builder.create()
//
//        dialog.window?.setDimAmount(0.5f)
//
//        dialog.show()
//    }
//
//    private fun navigateToHomePage() {
//        val intent = Intent(
//            this,
//            HomeScreenActivity::class.java
//        )
//        startActivity(intent)
//        finish()
//    }
//
//    private fun getUserIdFromSharedPreferences(): Int {
//        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        return sharedPreferences.getInt("USER_ID", -1)
//    }
//}
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akirachix.totosteps.BaseMilestoneScreen
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.models.ResultData
import com.akirachix.totosteps.models.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DevelopmentalMilestonesScreenSix : BaseMilestoneScreen() {
    override val category: String = "Movement"
    private lateinit var userEmail: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
        getUserEmail()
    }

    private fun setupUi() {
        binding.btnBackFour.setOnClickListener {
            finish()
        }

        binding.btnNextFour.text = "Submit"
        binding.btnNextFour.setOnClickListener {
            if (allQuestionsAnswered()) {
                showSubmitDialog()
            } else {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSubmitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Submit Assessment")
            .setMessage("Are you sure you want to submit the assessment?")
            .setPositiveButton("Yes") { _, _ ->
                submitResultAndFinish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun submitResultAndFinish() {
        val milestone = getMilestone(childAgeInMonths)
        val resultData = ResultData(
            milestone = milestone,
            answers = collectAnswers(),
            user = childId,
            email = userEmail
        )

        ApiClient.instance().submitResult(resultData).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(call: Call<ResultResponse>, response: Response<ResultResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        showCompletionDialog(resultResponse)
                    } ?: run {
                        Toast.makeText(this@DevelopmentalMilestonesScreenSix, "Results submitted successfully, but no response data", Toast.LENGTH_SHORT).show()
                        finishAssessment()
                    }
                } else {
                    Toast.makeText(this@DevelopmentalMilestonesScreenSix, "Failed to submit result: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Toast.makeText(this@DevelopmentalMilestonesScreenSix, "Error submitting result: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getUserEmail() {
        val sharedPreferences = getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)
        userEmail = sharedPreferences.getString("USER_EMAIL", "") ?: ""
        if (userEmail.isEmpty()) {
            Log.e("MilestoneScreen", "User email not found")
            Toast.makeText(this, "User email not found. Please log in again.", Toast.LENGTH_LONG).show()
        }
    }


    private fun showCompletionDialog(resultResponse: ResultResponse) {
        AlertDialog.Builder(this)
            .setTitle("Assessment Completed")
            .setMessage("Your results have been submitted successfully. You will receive an email with the detailed results soon.")
            .setPositiveButton("OK") { _, _ ->
                finishAssessment()
            }
            .show()
    }

    override fun finishAssessment() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


}





