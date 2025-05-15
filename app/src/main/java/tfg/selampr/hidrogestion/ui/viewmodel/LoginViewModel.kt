package tfg.selampr.hidrogestion.ui.viewmodel

/// ui/viewmodels/LoginViewModel.kt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.data.model.WorkerEntity

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val worker: WorkerEntity) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Usuario y contraseña son obligatorios")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = LoginState.Loading

            try {
                val db = AppDatabase(getApplication()) // Cambio clave aquí
                val worker = db.workerDao().authenticate(username, password)

                if (worker != null) {
                    _loginState.value = LoginState.Success(worker)
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }
}