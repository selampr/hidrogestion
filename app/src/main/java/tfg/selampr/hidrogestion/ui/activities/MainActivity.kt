package tfg.selampr.hidrogestion.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.databinding.ActivityMainBinding

/**
 * Actividad principal de la aplicación que se lanza tras el login exitoso.
 *
 * Se encarga de:
 * - Configurar la navegación entre fragmentos (Home, Historial, Perfil) mediante Navigation Component.
 * - Mostrar una barra de navegación inferior con animaciones suaves.
 * - Capturar y mantener información del trabajador logueado.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding para acceder a los elementos del layout de forma segura
    private lateinit var binding: ActivityMainBinding

    // Controlador de navegación para cambiar entre fragmentos
    private lateinit var navController: NavController

    /**
     * Variables estáticas para almacenar temporalmente los datos del trabajador logueado.
     * Se pueden usar desde otras actividades o fragmentos.
     */
    companion object {
        var workerName: String? = null
        var workerRole: String? = null
        var workerEmail: String? = null
        var loginTime: String? = null
        var workerId: Int? = null
    }

    /**
     * Método principal que se ejecuta al crear la actividad.
     * Inicializa la navegación y recupera los datos del trabajador del intent.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate ejecutado")

        // Se infla el layout con ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupWorkerInfo()
    }

    /**
     * Configura el componente de navegación inferior (BottomNavigationView)
     * junto con Navigation Component.
     */
    private fun setupNavigation() {
        // Recuperamos el fragmento de navegación principal
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Asociamos el BottomNavigationView con el controlador de navegación
        binding.bottomNavigation.setupWithNavController(navController)

        // Listener personalizado para añadir una animación de rebote
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            // Limpia la pila de navegación anterior
            navController.popBackStack()

            // Navega al nuevo fragmento seleccionado
            navController.navigate(item.itemId)

            // Añade animación de rebote al hacer clic
            binding.bottomNavigation.animate()
                .scaleX(1.01f)
                .scaleY(1.01f)
                .setDuration(80)
                .withEndAction {
                    binding.bottomNavigation.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
                .start()

            true
        }
    }

    /**
     * Determina el orden visual del ítem en el BottomNavigation según su ID.
     * (Actualmente no se usa activamente, pero puede ser útil para lógica condicional).
     */
    private fun itemOrder(itemId: Int): Int {
        return when (itemId) {
            R.id.homeFragment -> 0
            R.id.historialFragment -> 1
            R.id.perfilFragment -> 2
            else -> 0
        }
    }

    /**
     * Recupera los datos del trabajador que fueron pasados por el intent tras el login
     * y los guarda en variables estáticas para acceso global durante la sesión.
     */
    private fun setupWorkerInfo() {
        workerName = intent.getStringExtra("worker_name")
        workerEmail = intent.getStringExtra("worker_email")
        workerRole = intent.getStringExtra("worker_rol")
        loginTime = intent.getStringExtra("login_time")
        workerId = intent.getIntExtra("worker_id", 0)

        // Establece el título de la barra superior con el nombre del trabajador
        intent.getStringExtra("worker_name")?.let { name ->
            supportActionBar?.title = "Bienvenid@, $name!"
        }
    }
}
