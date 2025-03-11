package com.example.projobliveapp.DataBase

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
import retrofit2.http.Query
import retrofit2.http.Streaming
import java.time.LocalDateTime
import java.util.Date

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
    val yearOfPassing: String,
    val percentageOrCGPA: String,
    val certificationName: String?,
    val issuingAuthority: String?,
    val yearOfCompletion: String?
)

data class ExperienceDetails(
    val userId: String,
    val jobTitle: String,
    val companyName: String,
    val startDate: String,
    val endDate: String,
    val responsibilities: String,
)

data class JobPreferenceData(
    val userId: String,
    val jobLocations: List<String>,
    val selectedSkills: List<String>
)

data class ContactInfo(
    val userId: String,
    var email: String = "",
    var phoneNumber: String = "",
    var alternatePhoneNumber: String = "",
    var currentAddress: String = "",
    var permanentAddress: String = "",
    var linkedInProfile: String = "",
    var portfolioWebsite: String = "",
    var detailedAddress: String = "",
    var roadName: String = "",
    var areaName: String = "",
    var city: String = "",
    var state: String = "",
    var pincode: String = ""
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
    val jobid:String,
    val Employerid: String,
    val jobTitle: String,
    val country: String,
    val contractType: String,
    val workingHours: String,
    val minExperience: String,
    val maxExperience: String,
    val keySkills: String,
    val minSalary: String,
    val maxSalary: String,
    val jobDescription: String,
    val jobLocation: String,
    val applicationMethod: String,
    val contactEmail: String,
    val externalLink: String,
    val phoneNumber: String,
    val relocationSupport: String,
    val Companyname:String,
    val createdAt: String,
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
    val userId: String?,
    val UserType:String
)

data class CompanyDetails(
    val userId: String,
    val companyName: String,
    val companyAddress: String,
    val registrationNumber: String,
    val companyWebsite: String,
    val industryType: String,
    val companySize: String,
    val yearOfEstablishment: String,
    val socialMediaLinks: String?,
    val contactNumber: String,
    val companyEmail: String,
    val contactPerson: String,
    val contactPersonTitle: String,
    val aboutCompany: String
)

data class Resume(
    val success: Boolean,
    val message: String,
    val id: String? = null,
    val filePath: String? = null
)

data class ResumeUploadResponse(
    val success: Boolean,
    val message: String,
    val filePath: String? = null,
    val error: String? = null
)

data class jobapplications(
    val jobid: String,
    val employerid: String,
    val userid: String,
    val timestamp: String
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

    @GET("getCandidateeducationladata/{userId}")
    suspend fun getCandidateeducationladata(@Path("userId") userId: String): EducationDetails

    @POST("Candidateexperienceladata")
    suspend fun Candidateexperienceladata(@Body jobData: ExperienceDetails): Response<ApiResponse>

    @GET("getCandidateexperienceladata/{userId}")
    suspend fun getCandidateexperienceladata(@Path("userId") userId: String): ExperienceDetails

    @POST("Candidatecontactladata")
    suspend fun Candidatecontactladata(@Body jobData: ContactInfo): Response<ApiResponse>

    @GET("getCandidatecontactladata/{userId}")
    suspend fun getCandidatecontactladata(@Path("userId") userId: String): ContactInfo

    @POST("Candidatejobprefrencedata")
    suspend fun Candidatejobprefrencedata(@Body jobData: JobPreferenceData): Response<ApiResponse>

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
    suspend fun getAllJobs(): List<JobPost>

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

    @POST("JobPost")
    suspend fun storeJob(@Body jobPost: JobPost): Response<ApiResponse>

    @POST("jobapplications")
    suspend fun addJobApplication(@Body jobapplications: jobapplications): Response<ApiResponse>

    @GET("jobapplications/{id}")
    suspend fun getJobApplicationById(@Path("id") id: String): Response<jobapplications>

    @POST("comapnyData")
    suspend fun PostcomapnyData(@Body companyDetails: CompanyDetails): Response<ApiResponse>

    @GET("getcomapnyData/{userId}")
    suspend fun getcomapnyData(@Path("userId") userId: String): CompanyDetails

    @GET("getJobById/{jobid}")
    suspend fun jobbyid(@Path("jobid") userId: String): JobPost


    @GET("getJobByemployerId/{Employerid}")
    suspend fun getJobByemployerId(@Path("Employerid") email: String): List<JobPost>



    @Multipart
    @POST("uploadResume")
    suspend fun uploadResume(
        @Part resume: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Response<Resume>

    @GET("downloadResume/{userId}")
    @Streaming
    suspend fun downloadResume(@Path("userId") userId: String): Response<ResponseBody>

    @GET("checkResumeExists/{userId}")
    suspend fun checkResumeExists(@Path("userId") userId: String): Response<Resume>

    // **New Endpoint: Update Resume**
    @Multipart
    @POST("updateResume")
    suspend fun updateResume(
        @Part resume: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Response<ResumeUploadResponse>
}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
