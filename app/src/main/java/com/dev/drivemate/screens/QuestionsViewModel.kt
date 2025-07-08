package com.dev.drivemate.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.drivemate.data.DataOrException
import com.dev.drivemate.model.DrivingTheoryQuestionItem
import com.dev.drivemate.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {
    private val _data: MutableState<DataOrException<ArrayList<DrivingTheoryQuestionItem>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception("")))
    val data: MutableState<DataOrException<ArrayList<DrivingTheoryQuestionItem>, Boolean, Exception>>
        get() = _data

    init {
        getAllQuestions()
    }
    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false

        }
    }
}