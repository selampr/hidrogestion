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

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = AppDatabase.getInstance(this)
        val workerDao = database.workerDao()

        binding.signupButton.setOnClickListener {
            val name = binding.signupName.text.toString().trim()
            val email = binding.signupEmail.text.toString().trim()
            val username = binding.signupUsername.text.toString().trim()
            val password = binding.signupPassword.text.toString().trim()
            val role = "worker" // o puedes permitir elegir rol

            if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existingUser = workerDao.getWorkerByUsername(username)
                if (existingUser != null) {
                    runOnUiThread {
                        Toast.makeText(this@SignupActivity, "Usuario ya existente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val hashedPassword = hashPassword(password)
                    Log.d("SignupActivity", "Password hasheada: $hashedPassword")

                    val newWorker = WorkerEntity(
                        id = 0,
                        username = username,
                        passwordHash = hashedPassword,  // Aquí guardamos el hash
                        name = name,
                        email = email,
                        role = role
                    )

                    workerDao.insert(newWorker)

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
