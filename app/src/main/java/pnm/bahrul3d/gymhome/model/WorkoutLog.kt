package pnm.bahrul3d.gymhome.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: Int,
    val exerciseName: String,
    val timestamp: Long = System.currentTimeMillis(),
    val durationSeconds: Int,
    val repetitions: Int? = null,
    val caloriesBurned: Float = 0f
)
