package com.example.projobliveapp.DataBase

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    val jobTitle: String,
    val jobDescription: String,
    val jobLocation: String,
    val minExperience: String,
    val maxExperience: String,
    val minSalary: String,
    val maxSalary: String,
    val keySkills: String,
    val company: String,
    val createdBy: String,  // Assuming ObjectId is a string for simplicity
    val createdByEmp: String,  // Assuming ObjectId is a string for simplicity
    val shortlisted: List<String>,  // Assuming it's a list of user IDs or application IDs
    val applications: List<String>,  // Assuming it's a list of application IDs
    val createdAt: String,
    val updatedAt: String,
)

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

    @GET("getJob/{id}")
    suspend fun getJobById(@Path("id") id: String): Job
}


fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
