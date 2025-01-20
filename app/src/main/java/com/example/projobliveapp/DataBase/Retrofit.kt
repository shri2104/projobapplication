package com.example.projobliveapp.DataBase

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

data class JobApplication(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val resume: String?,
    val location: String,
    val skills: String,
    val about: String?,
    val workExperience: String?,
    val jobCity: String?,
    val roleLooking: String?
)

data class Job(
    val _id: String,
    val jobTitle: String,
    val jobDescription: String,
    val jobLocation: String,
    val minExperience: String,
    val maxExperience: String,
    val minSalary: String,
    val maxSalary: String,
    val keySkills: String,
    val company: String,
    val createdBy: String,
    val createdByEmp: String,
    val shortlisted: List<String>,
    val applications: List<String>,
    val createdAt: String,
    val updatedAt: String,
)
data class SaveJob(
    val email: String,
    val jobIds: List<String>
)
data class SavedJobResponse(val email: String, val jobIds: List<List<String>>)
data class JobApiResponse(val success: Boolean, val jobs: List<Job>)
data class ApiResponse(val success: Boolean, val id: String?)

interface ApiService {
    @GET("getAllData")
    suspend fun getJobData(): List<JobApplication>

    @GET("getData/{email}")
    suspend fun getProfileDataByEmail(@Path("email") email: String): JobApplication

    @POST("storeData")
    suspend fun storeUserData(@Body jobData: JobApplication): Response<ApiResponse>

    @GET("getJobs")
    suspend fun getAllJobs(): List<Job>

    @GET("getDataID/{id}")
    suspend fun getJobById(@Path("id") id: String): Job

    @POST("addJobToFavorites")
    suspend fun addJobToFavorite(@Body saveJob: SaveJob): Response<ApiResponse>

    @POST("deleteFavorite")
    suspend fun deleteFavorite(@Body saveJob: SaveJob): Response<ApiResponse>

    @GET("isJobInFavorites/{email}/{jobId}")
    suspend fun isJobInFavorites(
        @Path("email") email: String,
        @Path("jobId") jobId: String
    ): Response<ApiResponse>

    @GET("getSavedJobIDs/{email}")
    suspend fun getSavedJobs(@Path("email") email: String): SavedJobResponse

    @POST("getJobsByIds")
    suspend fun getJobsByIds(@Body jobIds: List<String>): List<Job>

    @Multipart
    @POST("uploadResume")
    suspend fun uploadResume(
        @Part email: MultipartBody.Part, // Do not include "email" here in the annotation
        @Part resume: MultipartBody.Part // Do not include "resume" here in the annotation
    ): Response<ApiResponse>

}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
