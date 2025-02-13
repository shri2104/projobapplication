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
import java.time.LocalDateTime

data class PersonalData(
    val userId: String,
    val Firstname: String,
    val Lastname: String,
    val dateOfBirth: String,
    val gender: String,
    val nationality: String,
    val maritalStatus: String,
    val languagesKnown: List<String>,
)

data class EducationDetails(
    val userId: String,
    val degree: String,
    val fieldOfStudy: String,
    val universityName: String,
    val yearOfPassing: Int,
    val percentageOrCGPA: String,
    val certificationName: String?,
    val issuingAuthority: String?,
    val yearOfCompletion: Int?
)

data class ExperienceDetails(
    val userId: String, // Added userId
    val jobTitle: String,
    val companyName: String,
    val experience: String,
    val startDate: String,
    val endDate: String,
    val jobLocation: String,
    val responsibilities: String,
    val achievements: String
)

data class ContactInfo(
    val userId: String, // Added userId
    var email: String = "",
    var phoneNumber: String = "",
    var alternatePhoneNumber: String = "",
    var currentAddress: String = "",
    var permanentAddress: String = "",
    var linkedInProfile: String = "",
    var portfolioWebsite: String = ""
)



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

data class JobPost(
    val jobTitle: String,
    val country: String,
    val contractType: String,
    val workingHours: String,
    val minExperience: Int,
    val maxExperience: Int,
    val keySkills: String,
    val minSalary: Int,
    val maxSalary: Int,
    val jobDescription: String,
    val jobLocation: String,
    val applicationMethod: String,
    val contactEmail: String,
    val externalLink: String,
    val phoneNumber: String,
    val relocationSupport: Boolean
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

data class emailuserid(
    val email:String,
    val userId: String?
)


data class SavedJobResponse(val email: String, val jobIds: List<List<String>>)
data class JobApiResponse(val success: Boolean, val jobs: List<Job>)
data class ApiResponse(val success: Boolean, val id: String?)

interface ApiService {
    @POST("Candidatepersonaldata")
    suspend fun Candidatepersonaldata(@Body personalData: PersonalData): Response<ApiResponse>

    @GET("getCandidatepersonaldata/{userId}")
    suspend fun getCandidatepersonaldata(@Path("userId") userId: String): PersonalData

    @POST("Candidateeducationladata")
    suspend fun Candidateeducationladata(@Body jobData: EducationDetails): Response<ApiResponse>
    @POST("Candidateexperienceladata")
    suspend fun Candidateexperienceladata(@Body jobData: ExperienceDetails): Response<ApiResponse>
    @POST("Candidatecontactladata")
    suspend fun Candidatecontactladata(@Body jobData: ContactInfo): Response<ApiResponse>


    @GET("getAllData")
    suspend fun getJobData(): List<JobApplication>

    @GET("getData/{email}")
    suspend fun getProfileDataByEmail(@Path("email") email: String): JobApplication

    @POST("Userid")
    suspend fun Storeuserid(@Body emailuserid: emailuserid): Response<ApiResponse>

    @GET("GetUserid/{email}")
    suspend fun getuserid(@Path("email") email: String): emailuserid

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

    @POST("JobPost")
    suspend fun storeJob(@Body jobData: JobPost): Response<ApiResponse>

    @Multipart
    @POST("uploadResume")
    suspend fun uploadResume(
        @Part email: MultipartBody.Part,
        @Part resume: MultipartBody.Part
    ): Response<ApiResponse>
}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
