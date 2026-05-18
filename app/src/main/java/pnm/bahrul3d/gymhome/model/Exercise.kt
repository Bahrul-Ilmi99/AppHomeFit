package pnm.bahrul3d.gymhome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String, // e.g., Push-up, Squat, Plank
    val difficulty: String, // Beginner, Intermediate, Advanced
    val durationSeconds: Int? = null,
    val repetitions: Int? = null,
    val imageUrl: String? = null
)
