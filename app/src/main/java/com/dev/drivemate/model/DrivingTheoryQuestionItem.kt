package com.dev.drivemate.model

data class DrivingTheoryQuestionItem(
    val answer: String,
    val explanation: String,
    val heading: String,
    val id: Int,
    val image: String,
    val question: String,
    val questions: List<String>
)