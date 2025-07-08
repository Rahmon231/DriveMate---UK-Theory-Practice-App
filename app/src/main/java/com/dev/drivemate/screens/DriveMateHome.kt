package com.dev.drivemate.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.drivemate.components.Questions

@Composable
fun DriveMateHome(viewModel : QuestionsViewModel = hiltViewModel()){
    Questions(viewModel)
}