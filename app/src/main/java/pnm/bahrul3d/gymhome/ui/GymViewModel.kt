package pnm.bahrul3d.gymhome.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pnm.bahrul3d.gymhome.data.AppDatabase
import pnm.bahrul3d.gymhome.data.GymRepository
import pnm.bahrul3d.gymhome.model.Exercise
import pnm.bahrul3d.gymhome.model.WorkoutLog

class GymViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GymRepository

    val allExercises: StateFlow<List<Exercise>>
    val allLogs: StateFlow<List<WorkoutLog>>

    init {
        val gymDao = AppDatabase.getDatabase(application).gymDao()
        repository = GymRepository(gymDao)
        allExercises = repository.allExercises.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        allLogs = repository.allLogs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        
        // Prepopulate if empty
        viewModelScope.launch {
            repository.prepopulateData()
        }
    }

    fun insertLog(exercise: Exercise, duration: Int) {
        viewModelScope.launch {
            repository.insertLog(
                WorkoutLog(
                    exerciseId = exercise.id,
                    exerciseName = exercise.name,
                    durationSeconds = duration
                )
            )
        }
    }
}
