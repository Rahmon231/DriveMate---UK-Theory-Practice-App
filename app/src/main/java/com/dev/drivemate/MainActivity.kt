package com.dev.drivemate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.drivemate.model.QuestionState
import com.dev.drivemate.screens.DriveMateHome
import com.dev.drivemate.screens.QuestionsViewModel
import com.dev.drivemate.ui.theme.DriveMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveMateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Surface(modifier = Modifier
                       .fillMaxSize()
                       .padding(innerPadding)) {
                       DriveMateHome()
                       //DriveMateApp()
                   }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}