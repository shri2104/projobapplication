package com.example.projobliveapp.Screens.Login

import android.util.Log
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.DataBase.JobApplication

class UserRepository(private val apiService: ApiService) {
    suspend fun getProfileDataByEmail(email: String): JobApplication? {
        return try {
            apiService.getProfileDataByEmail(email)
        } catch (e: Exception) {
            Log.e("API Error", "Error fetching profile data: ${e.message}", e)
            null
        }
    }
}
