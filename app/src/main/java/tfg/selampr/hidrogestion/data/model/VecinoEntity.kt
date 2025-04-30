package tfg.selampr.hidrogestion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vecinos")
data class VecinoEntity(
    @PrimaryKey(autoGenerate = true) val vec_id: Int = 0,
    @ColumnInfo(name = "vec_nom") val nombre: String,
    @ColumnInfo(name = "vec_dir") val direccion: String,
    @ColumnInfo(name = "vec_con") val contacto: String,
    @ColumnInfo(name = "vec_zon") val zona: String
)
