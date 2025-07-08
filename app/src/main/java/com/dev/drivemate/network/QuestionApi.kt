package com.dev.drivemate.network

import com.dev.drivemate.model.DrivingTheoryQuestion
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("data.json")
    suspend fun getAllQuestions() : DrivingTheoryQuestion
}