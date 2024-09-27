package com.akirachix.totosteps.models

data class QuestionResponse(
    var questions : List<Question>
) {
    private var callback: ((QuestionResponse) -> Unit)? = null
}
