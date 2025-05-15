package tfg.selampr.hidrogestion.data.dao

import androidx.room.Dao
import androidx.room.Query
import tfg.selampr.hidrogestion.data.model.NotificationEntity
import tfg.selampr.hidrogestion.data.model.WorkerEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications")
    suspend fun getAnyNotification(): NotificationEntity?
}