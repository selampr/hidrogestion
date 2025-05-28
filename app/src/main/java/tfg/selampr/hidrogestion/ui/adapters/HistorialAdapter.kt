package tfg.selampr.hidrogestion.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tfg.selampr.hidrogestion.R
import tfg.selampr.hidrogestion.data.model.WaterCutEntity

class HistorialAdapter(
    private val onItemClick: (WaterCutEntity) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    private val listaCortes = mutableListOf<WaterCutEntity>()

    fun setCortes(cortes: List<WaterCutEntity>) {
        listaCortes.clear()
        listaCortes.addAll(cortes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        holder.bind(listaCortes[position], onItemClick)
    }

    override fun getItemCount(): Int = listaCortes.size

    class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: WaterCutEntity, onClick: (WaterCutEntity) -> Unit) {
            itemView.findViewById<TextView>(R.id.tvMotivo).text = item.reason
            itemView.findViewById<TextView>(R.id.tvTiempo).text = item.startTime
            itemView.setOnClickListener { onClick(item) }
        }
    }
}

