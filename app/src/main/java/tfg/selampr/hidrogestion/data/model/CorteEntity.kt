package tfg.selampr.hidrogestion.data.model

import androidx.room.*

@Entity(
    tableName = "cortes",
    foreignKeys = [
        ForeignKey(
            entity = TrabajadorEntity::class,
            parentColumns = ["tra_id"],
            childColumns = ["cor_tra"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CorteEntity(
    @PrimaryKey(autoGenerate = true) val cor_id: Int = 0,
    @ColumnInfo(name = "cor_zon") val zona: String,
    @ColumnInfo(name = "cor_hin") val horaInicio: String,
    @ColumnInfo(name = "cor_hfi") val horaFin: String,
    @ColumnInfo(name = "cor_mot") val motivo: String,
    @ColumnInfo(name = "cor_tra") val idTrabajador: Int
)
