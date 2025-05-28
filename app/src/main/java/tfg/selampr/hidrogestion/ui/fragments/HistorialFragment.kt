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

class HistorialFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial, container, false)

        recyclerView = view.findViewById(R.id.recyclerHistorial)
        adapter = HistorialAdapter { corte: WaterCutEntity ->
            val action = HistorialFragmentDirections
                .actionHistorialFragmentToCorteFragment(corte.wacId)
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        cargarCortes()
        val fab = view.findViewById<View>(R.id.btnAñadirCorte)
        fab.setOnClickListener {
            val action = HistorialFragmentDirections
                .actionHistorialFragmentToCorteFragment(-1)
            findNavController().navigate(action)
        }

        return view
    }

    private fun cargarCortes() {
        val dao = AppDatabase.getInstance(requireContext()).waterCutDao()
        lifecycleScope.launch {
            val cortes = dao.getAllWaterCuts()
            adapter.setCortes(cortes)
        }

    }

}
