package tfg.selampr.hidrogestion.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.ui.adapters.HistorialAdapter

/**
 * Fragmento que actúa como pantalla de inicio (Home) de la aplicación.
 *
 * Funcionalidades:
 * - Da la bienvenida al usuario mostrando su nombre.
 * - Muestra los últimos cortes de agua registrados (máximo 4).
 */
class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter

    /**
     * Crea e infla la vista del fragmento. Configura la interfaz y lanza la carga de datos.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Obtiene el nombre del trabajador desde la Activity principal
        val nombre = activity?.intent?.getStringExtra("worker_name")
        view.findViewById<TextView>(R.id.tvNombreUsuario).text = "Bienvenid@, $nombre"

        // Configuración del RecyclerView para mostrar los últimos cortes
        recyclerView = view.findViewById(R.id.rvHistorial)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adaptador de solo lectura, sin acciones al pulsar
        adapter = HistorialAdapter {}
        recyclerView.adapter = adapter

        // Carga los últimos 4 cortes desde la base de datos
        cargarUltimosCortes()

        return view
    }

    /**
     * Carga desde la base de datos los 4 cortes de agua más recientes.
     */
    private fun cargarUltimosCortes() {
        val dao = AppDatabase.getInstance(requireContext()).waterCutDao()
        lifecycleScope.launch {
            val cortes = dao.getAllWaterCuts().take(5) // Solo los ultimos 5
            adapter.setCortes(cortes)
        }
    }
}
