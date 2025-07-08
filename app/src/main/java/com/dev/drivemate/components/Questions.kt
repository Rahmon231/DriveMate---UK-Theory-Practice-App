package com.dev.drivemate.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.dev.drivemate.R
import com.dev.drivemate.model.DrivingTheoryQuestionItem
import com.dev.drivemate.model.QuestionState
import com.dev.drivemate.screens.QuestionsViewModel
import com.dev.drivemate.utils.AppColors.mDarkPurple
import com.dev.drivemate.utils.AppColors.mLightBlue
import com.dev.drivemate.utils.AppColors.mLightGray
import com.dev.drivemate.utils.AppColors.mLightPurple
import com.dev.drivemate.utils.AppColors.mOffWhite

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val state = viewModel.questionState.value
    val questionIndex = remember { mutableIntStateOf(1) }

    when (state) {
        is QuestionState.Loading -> {
            CircularProgressIndicator()
        }

        is QuestionState.Success -> {
            val questions = state.data
            val question = questions.getOrNull(questionIndex.intValue)

            if (question != null) {
                QuestionDisplay(
                    question = question,
                    questionIndex = questionIndex,
                    viewModel = viewModel,
                    onNextClick = {
                        if (questionIndex.intValue < questions.size - 1) {
                            questionIndex.intValue += 1
                        }
                    }
                )
            } else {
                Text("No more questions.")
            }
        }

        is QuestionState.Failure -> {
            Text("Failed to load: ${state.throwable.localizedMessage}")
        }

        is QuestionState.Idle -> {
            Text("No questions available.")
        }

    }

}


@Composable
fun QuestionDisplay(
    question: DrivingTheoryQuestionItem,
    questionIndex : MutableState<Int> = mutableIntStateOf(1),
    viewModel: QuestionsViewModel,
    onNextClick: (Int) -> Unit
){

    val choices = remember(question) {
        question.questions.toMutableList()
    }

    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val isOptionSelected = remember(question) {
        mutableStateOf(false)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choices[it] == question.answer
            isOptionSelected.value = true
        }
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f), 0f)
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = mDarkPurple) {
        Column(modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            ShowProgress(score = questionIndex.value)
            QuestionTracker(counter = questionIndex.value, questionMax = viewModel.getQuestionCount())
            DrawDottedLine(pathEffect = pathEffect)
            Column {
                Text(modifier = Modifier
                    .padding(6.dp)
                    .align(alignment = Alignment.Start)
                    .fillMaxHeight(0.1f),
                    text = question.question,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp, color = mLightGray
                    )
                )

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(120.dp)
                        .clip(RectangleShape)
                        .align(alignment = Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(question.image.replace("http://", "https://"))
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "question image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)

                    )
                }



                choices.forEachIndexed { index, option ->
                    Row(modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .border(
                            width = 4.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    mLightPurple, mLightPurple
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            shape = RoundedCornerShape(
                                topEndPercent = 50,
                                topStartPercent = 50,
                                bottomEndPercent = 50,
                                bottomStartPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = answerState.value == index,
                            onClick = {
                                if (!isOptionSelected.value) {
                                    updateAnswer(index)
                                }
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            enabled = !(isOptionSelected.value),

                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                    if (correctAnswerState.value == true &&
                                        index == answerState.value){
                                        Color.Green.copy(alpha = 0.2f)
                                    }else{
                                        Color.Red.copy(alpha = 0.2f)
                                    }
                            ))
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color = if (correctAnswerState.value == true &&
                                    index == answerState.value){
                                    Color.Green
                                }else if (correctAnswerState.value == false &&
                                    index == answerState.value){
                                    Color.Red.copy(alpha = 0.2f)
                                }else{
                                    mOffWhite
                                }, fontSize = 17.sp)){

                                append(option)
                            }
                        }
                        Text(text = annotatedString,
                            modifier = Modifier
                                .padding(6.dp)
                                .weight(1f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
                Button(
                    onClick = { onNextClick(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mLightBlue
                    )
                ) {
                    Text(text = "Next", modifier = Modifier.padding(4.dp),
                        color = mOffWhite, fontSize = 17.sp)
                }

            }

        }
    }
}

@Preview
@Composable
fun QuestionTracker(counter: Int = 10, questionMax: Int? = 100){
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = mLightGray,
                    fontWeight = FontWeight.Bold, fontSize = 27.sp
                )
            ){
                append("Question $counter/")
                withStyle(style = SpanStyle(mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp)){
                    append("$questionMax")
                }
            }

        }

    }, modifier = Modifier.padding(20.dp))
}


@Composable
fun DrawDottedLine(pathEffect: PathEffect){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp), onDraw = {
        drawLine(color = mLightGray,
            start = Offset(0f,0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect)
    })
}

@Preview
@Composable
fun ShowProgress(score : Int = 40){
    val progressFactor by remember(score) {
        mutableStateOf(score*0.00112f)
    }

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(mLightPurple, mLightPurple)
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically){

        Button(
            onClick = { },
            modifier = Modifier
                .background(brush = gradient)
                .fillMaxWidth(progressFactor),
            enabled = false,
            elevation = null,
        ) {
            Text(text = score.toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxWidth(0.87f)
                    .fillMaxHeight()
                    .padding(6.dp),
                textAlign = TextAlign.Center,
                color = mOffWhite)
//
        }

    }

}

