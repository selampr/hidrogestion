package tfg.selampr.hidrogestion.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.model.WaterCutEntity

/**
 * Adaptador para el RecyclerView del historial de cortes de agua.
 *
 * Se encarga de mostrar una lista de objetos [WaterCutEntity] en la vista tipo tarjeta.
 * Implementa el patrón ViewHolder para mejorar el rendimiento de scroll.
 *
 * @param onItemClick Función lambda que se ejecuta al hacer clic sobre un ítem.
 */
class HistorialAdapter(
    private val onItemClick: (WaterCutEntity) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    // Lista mutable que contiene los cortes a mostrar en el RecyclerView
    private val listaCortes = mutableListOf<WaterCutEntity>()

    /**
     * Reemplaza el contenido de la lista actual por una nueva.
     * @param cortes Lista de nuevos cortes a mostrar.
     */
    fun setCortes(cortes: List<WaterCutEntity>) {
        listaCortes.clear()
        listaCortes.addAll(cortes)
        notifyDataSetChanged() // Notifica al RecyclerView para que se repinte
    }

    /**
     * Crea un nuevo ViewHolder cuando se necesita una nueva vista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    /**
     * Asocia los datos de un corte a una vista representada por un ViewHolder.
     */
    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        holder.bind(listaCortes[position], onItemClick)
    }

    /**
     * Devuelve el número total de ítems a mostrar.
     */
    override fun getItemCount(): Int = listaCortes.size

    /**
     * ViewHolder que encapsula la vista de cada ítem del historial.
     */
    class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Enlaza los datos de un [WaterCutEntity] con los elementos de la interfaz.
         * @param item Objeto de corte de agua.
         * @param onClick Acción a ejecutar al hacer clic en el ítem.
         */
        fun bind(item: WaterCutEntity, onClick: (WaterCutEntity) -> Unit) {
            itemView.findViewById<TextView>(R.id.tvMotivo).text = item.reason
            itemView.findViewById<TextView>(R.id.tvTiempo).text = item.startTime

            // Asocia el click con la función proporcionada
            itemView.setOnClickListener { onClick(item) }
        }
    }
}
