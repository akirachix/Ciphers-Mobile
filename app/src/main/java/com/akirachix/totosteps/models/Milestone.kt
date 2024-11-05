package com.akirachix.totosteps.models

data class Milestone(
    val milestone_id: Int,
    val name: String,
    val age: Int,
    val description: String,
    val summary: Map<String, List<String>>,

)
