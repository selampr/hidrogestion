package tfg.selampr.hidrogestion.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    companion object {
        var workerName: String? = null
        var workerRole: String? = null
        var workerEmail: String? = null
        var loginTime: String? = null
        var workerId: Int? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate ejecutado")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupWorkerInfo()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // Listener con animación de rebote
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val success = navController.popBackStack() // limpiar navegación previa
            navController.navigate(item.itemId)

            // Animar leve escalado del BottomNavigationView (como efecto visual del toque)
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


    private fun itemOrder(itemId: Int): Int {
        return when (itemId) {
            R.id.homeFragment -> 0
            R.id.historialFragment -> 1
            R.id.perfilFragment -> 2
            else -> 0
        }
    }

    private fun setupWorkerInfo() {
        workerName = intent.getStringExtra("worker_name")
        workerEmail = intent.getStringExtra("worker_email")
        workerRole = intent.getStringExtra("worker_rol")
        loginTime = intent.getStringExtra("login_time")
        workerId = intent.getIntExtra("worker_id", 0)

        intent.getStringExtra("worker_name")?.let { name ->
            supportActionBar?.title = "Bienvenid@, $name, !"
        }
    }
}
