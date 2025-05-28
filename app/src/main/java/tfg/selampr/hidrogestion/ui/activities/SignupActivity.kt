package tfg.selampr.hidrogestion.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.data.model.WorkerEntity
import tfg.selampr.hidrogestion.databinding.ActivitySignupBinding
import tfg.selampr.hidrogestion.util.hashPassword

/**
 * Actividad encargada del registro de nuevos trabajadores en la app.
 *
 * Permite que los usuarios creen una cuenta introduciendo su información personal y credenciales.
 * Si el nombre de usuario ya existe, se notifica; en caso contrario, se guarda en la base de datos.
 */
class SignupActivity : AppCompatActivity() {

    // Binding para acceder de forma segura a los elementos del layout
    private lateinit var binding: ActivitySignupBinding

    /**
     * Método que se ejecuta al crear la actividad.
     * Se encarga de inflar el layout, configurar la base de datos y registrar el listener del botón de registro.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de la base de datos y su DAO
        val database = AppDatabase.getInstance(this)
        val workerDao = database.workerDao()

        // Acción al pulsar el botón de registro
        binding.signupButton.setOnClickListener {
            // Se capturan los datos introducidos por el usuario
            val name = binding.signupName.text.toString().trim()
            val email = binding.signupEmail.text.toString().trim()
            val username = binding.signupUsername.text.toString().trim()
            val password = binding.signupPassword.text.toString().trim()
            val role = "worker" // Rol por defecto, puede ampliarse si se quiere que el usuario elija

            // Validación de campos obligatorios
            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Se lanza una corrutina para acceder a la base de datos sin bloquear el hilo principal
            lifecycleScope.launch {
                // Comprobamos si ya existe un usuario con ese username
                val existingUser = workerDao.getWorkerByUsername(username)
                if (existingUser != null) {
                    // Usuario duplicado
                    runOnUiThread {
                        Toast.makeText(this@SignupActivity, "Usuario ya existente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si no existe, hasheamos la contraseña
                    val hashedPassword = hashPassword(password)
                    Log.d("SignupActivity", "Password hasheada: $hashedPassword")

                    // Creamos una nueva entidad WorkerEntity
                    val newWorker = WorkerEntity(
                        id = 0, // ID autogenerado por Room
                        username = username,
                        passwordHash = hashedPassword,
                        name = name,
                        email = email,
                        role = role
                    )

                    // Insertamos el nuevo trabajador en la base de datos
                    workerDao.insert(newWorker)

                    // Avisamos al usuario y lo redirigimos al login
                    runOnUiThread {
                        Toast.makeText(this@SignupActivity, "Registro correcto", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}
