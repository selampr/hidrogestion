package tfg.selampr.hidrogestion.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tfg.selampr.hidrogestion.databinding.ActivityMainBinding

class ResumeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AÑADIR LAS PARTES DEL INICIO
    }
}
