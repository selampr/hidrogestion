package tfg.selampr.hidrogestion.ui.activities

import tfg.selampr.hidrogestion.data.model.WorkerEntity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.databinding.LoginPageBinding
import tfg.selampr.hidrogestion.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginPageBinding //acceder a las vistas
    private val viewModel: LoginViewModel by viewModels() //instanciar el viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupLoginButton()

        //configura el boton de login, llama a la funcion login del viewmodel e inicializa el binding
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginViewModel.LoginState.Idle -> Unit
                        is LoginViewModel.LoginState.Loading -> showLoading(true)
                        is LoginViewModel.LoginState.Success -> navigateToMain(state.worker)
                        is LoginViewModel.LoginState.Error -> showError(state.message)
                    }
                }
            }
        }
        //observador de cambios en el estado de login
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(username, password)
        }
        //cuando se hace click se cogen los valores puestos en el edittext y llama al metodo de login
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            loginButton.isEnabled = !loading
            username.isEnabled = !loading
            password.isEnabled = !loading
            loginButton.text = if (loading) "Verificando..." else "Iniciar sesión"
        }
        //lo que se muestra mientras cara, se deshabilitan los campos y boton y cambia el texto
    }

    private fun navigateToMain(worker: WorkerEntity) {
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra("worker_id", worker.id)
            putExtra("worker_name", worker.name)
        })
                finish()

        //crea un intent para ir a la mainactivity y pasa los datos del trabajador
        //el finish() cierra la actividad actual para que el usuario no pueda volver a ella
    }

    private fun showError(message: String) {
        showLoading(false)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    //muestra un snackbar con el mensaje de error
}


//Flujo Completo
// 1. Usuario ingresa credenciales
//
// 2. Al hacer click, ViewModel inicia autenticación
//
// 3. Activity muestra estado de carga
//
// 4. Si es exitoso:
//
//  - Navega a MainActivity
//
//  - Cierra LoginActivity
//
// 5 .Si hay error:
//
//  - Muestra mensaje
//
//  - Reactiva la UI