package tfg.selampr.hidrogestion.data.model

import androidx.room.*


@Entity(
    tableName = "watercut",
    foreignKeys = [
        ForeignKey(
            entity = WorkerEntity::class,
            parentColumns = ["tra_id"],
            childColumns = ["cor_tra"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WaterCutEntity(
    @PrimaryKey(autoGenerate = true) val wacId: Int = 0,
    @ColumnInfo(name = "wac_zon") val zone: String,
    @ColumnInfo(name = "wac_sta") val startTime: String,
    @ColumnInfo(name = "wac_end") val endTime: String,
    @ColumnInfo(name = "wac_rea") val reason: String,
    @ColumnInfo(name = "wac_idw") val idWorker: Int
)

