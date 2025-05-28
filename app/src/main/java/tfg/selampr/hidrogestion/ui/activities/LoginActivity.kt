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


class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase
    private lateinit var workerDao: WorkerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        workerDao = database.workerDao()

        binding.loginButton.setOnClickListener {
            val loginUsername = binding.username.text.toString()
            val loginPassword = binding.password.text.toString()
            logIn(loginUsername, loginPassword)
        }

        binding.signupRedirect.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun logIn(username: String, password: String) {
        lifecycleScope.launch {
            try {
                // Usamos el DAO directamente
                val worker = workerDao.getWorkerByUsername(username)
                if (worker != null && worker.passwordHash == hashPassword(password)) {
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login correcto",
                            Toast.LENGTH_SHORT
                        ).show()

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
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Credenciales inválidas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
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