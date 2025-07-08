package com.dev.drivemate.model

sealed class QuestionState {
    class Success(val questions: List<DrivingTheoryQuestionItem>) : QuestionState()
    class Failure(val exception: Exception) : QuestionState()
    object Empty : QuestionState()
    object Loading : QuestionState()
}

