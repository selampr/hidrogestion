package tfg.selampr.hidrogestion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "not_wid") val waterCutId: String,
    @ColumnInfo(name = "not_sub") val subject: String,
    @ColumnInfo(name = "not_bod") val body: String,
    @ColumnInfo(name = "not_sti") val scheduledTime: String,
    @ColumnInfo(name = "not_zid") val zoneId: Int
)