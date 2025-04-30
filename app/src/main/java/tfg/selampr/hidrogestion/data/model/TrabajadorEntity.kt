package tfg.selampr.hidrogestion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trabajadores")
data class TrabajadorEntity(
    @PrimaryKey(autoGenerate = true) val tra_id: Int = 0,
    @ColumnInfo(name = "tra_nom") val nombre: String,
    @ColumnInfo(name = "tra_usu") val usuario: String,
    @ColumnInfo(name = "tra_con") val contrasena: String
)
