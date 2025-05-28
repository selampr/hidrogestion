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

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val nombre = activity?.intent?.getStringExtra("worker_name")
        view.findViewById<TextView>(R.id.tvNombreUsuario).text = "Bienvenid@, $nombre"

        recyclerView = view.findViewById(R.id.rvHistorial)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistorialAdapter{}
        recyclerView.adapter = adapter

        cargarUltimosCortes()

        return view
    }

    private fun cargarUltimosCortes() {
        val dao = AppDatabase.getInstance(requireContext()).waterCutDao()
        lifecycleScope.launch {
            val cortes = dao.getAllWaterCuts().take(4)
            adapter.setCortes(cortes)
        }
    }
}
