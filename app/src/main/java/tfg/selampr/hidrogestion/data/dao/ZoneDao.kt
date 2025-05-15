package tfg.selampr.hidrogestion.data.dao

import androidx.room.Dao
import androidx.room.Query
import tfg.selampr.hidrogestion.data.model.ZoneEntity


@Dao
interface ZoneDao {
    @Query("SELECT * FROM zone")
    suspend fun getAnyZone(): ZoneEntity?
}