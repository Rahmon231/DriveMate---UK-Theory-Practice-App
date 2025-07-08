package com.dev.drivemate.repository

import android.util.Log
import com.dev.drivemate.data.DataOrException
import com.dev.drivemate.model.DrivingTheoryQuestionItem
import com.dev.drivemate.model.QuestionState
import com.dev.drivemate.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val questionApi: QuestionApi) {
    private val dataOrException = DataOrException<
            ArrayList<DrivingTheoryQuestionItem>,
            Boolean,
            Exception>()

//    suspend fun getAllQuestions(): DataOrException<ArrayList<DrivingTheoryQuestionItem>, Boolean, Exception> {
//        try {
//            dataOrException.loading = true
//            dataOrException.data = questionApi.getAllQuestions()
//            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
//        }catch (exception: Exception) {
//            dataOrException.e = exception
//            Log.d("Exc", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
//        }finally {
//            dataOrException.loading = false
//        }
//        return dataOrException
//    }

    suspend fun getAllQuestions(): QuestionState{
        return try {
            val result = questionApi.getAllQuestions()

            if (result.isEmpty()) {
                QuestionState.Empty
            } else {
                QuestionState.Success(result)
            }
        } catch (e: Exception) {
            Log.e("QuestionRepository", "Error fetching questions: ${e.localizedMessage}")
            QuestionState.Failure(e)
        }
    }
}