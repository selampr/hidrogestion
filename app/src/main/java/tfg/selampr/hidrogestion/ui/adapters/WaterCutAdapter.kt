import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.selampr.hidrogestion.data.model.WaterCutEntity
import tfg.selampr.hidrogestion.databinding.ItemHistorialBinding
import android.view.ViewGroup


class WaterCutAdapter /*:
    ListAdapter<WaterCutEntity, WaterCutAdapter.WaterCutViewHolder>(DiffCallback()) */{
/*
    // ViewHolder usando ViewBinding
    inner class WaterCutViewHolder(private val binding: ItemHistorialBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(waterCut: WaterCutEntity) {
            with(binding) {
                // Asignar datos a las vistas (según item_historial.xml)
                tvMotivo.text = waterCut.reason ?: "Sin motivo especificado"
                tvTiempo.text = formatTime(waterCut.scheduledTime) // Formatear hora manual

                // Opcional: Manejar clics en items
                root.setOnClickListener {
                    onItemClick?.invoke(waterCut)
                }
            }
        }

        // Formatear hora (ej: "14:30" → "02:30 PM")
        private fun formatTime(time: String): String {
            return try {
                val (hour, minute) = time.split(":")
                val hourInt = hour.toInt()
                val amPm = if (hourInt < 12) "AM" else "PM"
                val displayHour = if (hourInt > 12) hourInt - 12 else hourInt
                String.format("%02d:%02d %s", displayHour, minute.toInt(), amPm)
            } catch (e: Exception) {
                time // Retorna el formato original si hay error
            }
        }
    }

    // Callback para diferencias entre listas (optimiza RecyclerView)
    class DiffCallback : DiffUtil.ItemCallback<WaterCutEntity>() {
        override fun areItemsTheSame(oldItem: WaterCutEntity, newItem: WaterCutEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WaterCutEntity, newItem: WaterCutEntity): Boolean {
            return oldItem == newItem
        }
    }

    // Listener para clics (opcional)
    var onItemClick: ((WaterCutEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterCutViewHolder {
        val binding = ItemHistorialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WaterCutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaterCutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }*/
}