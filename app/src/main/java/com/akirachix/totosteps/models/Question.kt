package com.akirachix.totosteps.models
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question_id") val id: Int,
    @SerializedName("question") val questionJson: String,
    val category: String,
    val question_type: String,
    val milestone: Int,
    var answer: Boolean? = null

)
