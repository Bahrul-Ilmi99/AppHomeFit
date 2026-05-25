package pnm.bahrul3d.gymhome.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _user = mutableStateOf(auth.currentUser)
    val user: State<com.google.firebase.auth.FirebaseUser?> = _user

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        _isLoading.value = true
        _error.value = null
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    onSuccess()
                } else {
                    _error.value = task.exception?.message
                }
            }
    }

    fun register(email: String, pass: String, onSuccess: () -> Unit) {
        _isLoading.value = true
        _error.value = null
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                _isLoading.value = false // Selesaikan loading di sini
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    onSuccess()
                } else {
                    _error.value = task.exception?.message
                }
            }
    }

    fun logout() {
        auth.signOut()
        _user.value = null
    }
}
