package tfg.selampr.hidrogestion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.database.AppDatabase
import tfg.selampr.hidrogestion.ui.adapters.HistorialAdapter
import tfg.selampr.hidrogestion.data.model.WaterCutEntity

/**
 * Fragmento que muestra el historial de cortes de agua realizados.
 *
 * Funcionalidades:
 * - Lista todos los cortes registrados en la base de datos.
 * - Permite acceder al detalle de cada corte haciendo clic sobre él.
 * - Permite añadir un nuevo corte desde el botón flotante.
 */
class HistorialFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter

    /**
     * Método que se llama al crear la vista del fragmento.
     * Infla el layout, configura el RecyclerView y carga los datos desde la base de datos.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Infla la vista del fragmento
        val view = inflater.inflate(R.layout.fragment_historial, container, false)

        // Inicializa el RecyclerView y su adaptador
        recyclerView = view.findViewById(R.id.recyclerHistorial)
        adapter = HistorialAdapter { corte: WaterCutEntity ->
            // Al hacer clic en un corte, navega al fragmento de edición con su ID
            val action = HistorialFragmentDirections
                .actionHistorialFragmentToCorteFragment(corte.wacId)
            findNavController().navigate(action)
        }

        // Configuración del RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Carga inicial de los cortes desde la base de datos
        cargarCortes()

        // Configura el botón flotante para añadir un nuevo corte
        val fab = view.findViewById<View>(R.id.btnAñadirCorte)
        fab.setOnClickListener {
            // Navega al fragmento de Corte con ID = -1 (nuevo)
            val action = HistorialFragmentDirections
                .actionHistorialFragmentToCorteFragment(-1)
            findNavController().navigate(action)
        }

        return view
    }

    /**
     * Recupera todos los cortes de agua de la base de datos y los pasa al adaptador.
     */
    private fun cargarCortes() {
        val dao = AppDatabase.getInstance(requireContext()).waterCutDao()
        lifecycleScope.launch {
            val cortes = dao.getAllWaterCuts()
            adapter.setCortes(cortes)
        }
    }
}
