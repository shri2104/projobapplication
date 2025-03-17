package com.example.projobliveapp.DataBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch






class JobRepository(private val jobDao: JobDao) {

    suspend fun insertJob(job: RecentlyViewedJob) {
        jobDao.insertJob(job)
    }

    fun getRecentlyViewedJobs(): LiveData<List<RecentlyViewedJob>> {
        return jobDao.getRecentlyViewedJobs()
    }

}


class JobViewModel(private val repository: JobRepository) : ViewModel() {

    val recentJobs: LiveData<List<RecentlyViewedJob>> = repository.getRecentlyViewedJobs()

    fun addJobToRecentlyViewed(job: JobPost) {
        viewModelScope.launch {
            val recentlyViewedJob = RecentlyViewedJob(
                jobid = job.jobid,
                jobTitle = job.jobTitle,
                Companyname = job.Companyname,
                jobLocation = job.jobLocation.joinToString(", "),
                minSalary = job.minSalary,
                maxSalary = job.maxSalary,
                Employerid =job.Employerid
            )
            repository.insertJob(recentlyViewedJob)
        }
    }
}

