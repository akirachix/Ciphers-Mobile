package com.akirachix.totosteps.models

data class ResultData(
    val milestone: Int,
    val answers: Map<String, String>,
    val user: Int
)
