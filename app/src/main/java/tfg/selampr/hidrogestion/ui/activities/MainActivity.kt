package tfg.selampr.hidrogestion.ui.activities

import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.dao.WaterCutDao
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.databinding.ActivityMainBinding
import tfg.selampr.hidrogestion.databinding.LoginPageBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    private fun setupWorkerInfo() {
        intent.getStringExtra("worker_name")?.let { name ->
            supportActionBar?.title = "Bienvenido, $name"
        }
    }
}