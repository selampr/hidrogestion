package tfg.selampr.hidrogestion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "neighbors")
data class NeighborEntity(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nei_nam") val name: String,
    @ColumnInfo(name = "nei_sur") val surname: String,
    @ColumnInfo(name = "nei_ema") val email: String,
    @ColumnInfo(name = "nei_add") val address: String,
    @ColumnInfo(name = "nei_zon") val zoneId: Int
)
