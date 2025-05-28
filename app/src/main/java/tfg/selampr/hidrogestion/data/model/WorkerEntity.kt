package tfg.selampr.hidrogestion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workers")
data class WorkerEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "wor_use") val username: String,
    @ColumnInfo(name = "wor_pass") val passwordHash: String,
    @ColumnInfo(name = "wor_nam") val name: String,
    @ColumnInfo(name = "wor_ema") val email: String,
    @ColumnInfo(name = "wor_rol") val role: String
)
