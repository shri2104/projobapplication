package com.example.projobliveapp.DataBase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "recently_viewed_jobs")
data class RecentlyViewedJob(
    @PrimaryKey val jobid: String,
    val jobTitle: String,
    val Companyname: String,
    val jobLocation: String,
    val minSalary: String,
    val maxSalary: String,
    val Employerid: String
)


@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJob(job: RecentlyViewedJob)

    @Query("SELECT * FROM recently_viewed_jobs ORDER BY jobid DESC LIMIT 5")
    fun getRecentlyViewedJobs(): LiveData<List<RecentlyViewedJob>>

}

@Database(entities = [RecentlyViewedJob::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    "job_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}

class JobViewModelFactory(
    private val jobDao: JobDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobViewModel(JobRepository(jobDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

