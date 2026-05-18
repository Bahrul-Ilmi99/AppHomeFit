package pnm.bahrul3d.gymhome.data

import kotlinx.coroutines.flow.Flow
import pnm.bahrul3d.gymhome.model.Exercise
import pnm.bahrul3d.gymhome.model.WorkoutLog

class GymRepository(private val gymDao: GymDao) {
    val allExercises: Flow<List<Exercise>> = gymDao.getAllExercises()
    val allLogs: Flow<List<WorkoutLog>> = gymDao.getAllLogs()

    fun getExercisesByDifficulty(difficulty: String): Flow<List<Exercise>> {
        return gymDao.getExercisesByDifficulty(difficulty)
    }

    suspend fun insertLog(log: WorkoutLog) {
        gymDao.insertLog(log)
    }

    suspend fun prepopulateData() {
        val exercises = listOf(
            Exercise(1, "Push Up", "Latihan kekuatan tubuh bagian atas.", "Push-up", "Beginner", durationSeconds = 30, imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=500&q=80"),
            Exercise(2, "Squat", "Latihan otot kaki", "Squat", "Beginner", durationSeconds = 45, imageUrl = "https://images.unsplash.com/photo-1574680096145-d05b474e2155?w=500&q=80"),
            Exercise(3, "Plank", "Latihan kekuatan core.", "Plank", "Intermediate", durationSeconds = 60, imageUrl = "https://images.unsplash.com/photo-1566241142559-40e1bfc26ee7?w=500&q=80"),
            Exercise(4, "Diamond Push Up", "Variasi push up untuk trisep.", "Push-up", "Advanced", durationSeconds = 30, imageUrl = "https://images.unsplash.com/photo-1599058917212-d750089bc07e?w=500&q=80")
        )
        gymDao.insertExercises(exercises)
    }
}
