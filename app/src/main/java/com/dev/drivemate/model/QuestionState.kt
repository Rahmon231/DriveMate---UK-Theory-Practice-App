package com.dev.drivemate.model

sealed class QuestionState<out T> {
    data object Idle : QuestionState<Nothing>()
    data object Loading : QuestionState<Nothing>()
    data class Success<T>(val data: T) : QuestionState<T>()
    data class Failure(val throwable: Throwable) : QuestionState<Nothing>()
}


