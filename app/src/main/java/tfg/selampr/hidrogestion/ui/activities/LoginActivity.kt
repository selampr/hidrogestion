package tfg.selampr.hidrogestion.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.data.dao.WorkerDao
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.databinding.ActivityLoginBinding
import tfg.selampr.hidrogestion.util.hashPassword
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Actividad encargada del inicio de sesión (Login) de los trabajadores.
 *
 * Su función principal es verificar las credenciales del usuario usando Room
 * y redirigir a la actividad principal si son correctas.
 */
class LoginActivity : AppCompatActivity() {

    // ViewBinding para acceder a los elementos de la interfaz XML de forma segura
    private lateinit var binding: ActivityLoginBinding

    // Referencias a la base de datos y al DAO de trabajadores
    private lateinit var database: AppDatabase
    private lateinit var workerDao: WorkerDao

    /**
     * Método llamado cuando se crea la actividad.
     * Se configura el binding, se inicializa la base de datos y se definen los listeners de los botones.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de la base de datos y el DAO
        database = AppDatabase.getInstance(this)
        workerDao = database.workerDao()

        // Listener para el botón de login
        binding.loginButton.setOnClickListener {
            val loginUsername = binding.username.text.toString()
            val loginPassword = binding.password.text.toString()
            logIn(loginUsername, loginPassword)
        }

        // Listener para redirigir a la pantalla de registro
        binding.signupRedirect.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Función que gestiona el proceso de autenticación del usuario.
     * Verifica si el usuario existe y si su contraseña es válida.
     *
     * @param username Nombre de usuario ingresado.
     * @param password Contraseña ingresada.
     */
    private fun logIn(username: String, password: String) {
        // Se lanza una corrutina para hacer la consulta en segundo plano
        lifecycleScope.launch {
            try {
                // Consultamos si existe el trabajador con ese nombre
                val worker = workerDao.getWorkerByUsername(username)

                // Comprobamos si el usuario existe y si la contraseña coincide
                if (worker != null && worker.passwordHash == hashPassword(password)) {
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login correcto",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Creamos intent para pasar a la actividad principal
                        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                            putExtra("worker_id", worker.id)
                            putExtra("worker_name", worker.name)
                            putExtra("worker_email", worker.email)
                            putExtra("worker_rol", worker.role)
                            putExtra("login_time", SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()))
                        }

                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Si las credenciales son incorrectas
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Credenciales inválidas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                // En caso de error inesperado
                runOnUiThread {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
