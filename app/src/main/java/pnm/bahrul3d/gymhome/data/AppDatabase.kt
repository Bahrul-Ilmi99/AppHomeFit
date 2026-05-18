package pnm.bahrul3d.gymhome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pnm.bahrul3d.gymhome.model.Exercise
import pnm.bahrul3d.gymhome.model.WorkoutLog

@Dao
interface GymDao {
    @Query("SELECT * FROM exercises")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE difficulty = :difficulty")
    fun getExercisesByDifficulty(difficulty: String): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<WorkoutLog>>

    @Insert
    suspend fun insertLog(log: WorkoutLog)
}

@Database(entities = [Exercise::class, WorkoutLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gymDao(): GymDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
