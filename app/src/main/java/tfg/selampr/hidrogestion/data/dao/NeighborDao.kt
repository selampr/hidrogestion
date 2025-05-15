package tfg.selampr.hidrogestion.data.dao

import androidx.room.*
import tfg.selampr.hidrogestion.data.model.*

@Dao
interface NeighborDao {
    @Query("SELECT * FROM neighbors WHERE nei_zon = :zoneId")
    suspend fun getNeighborsByZone(zoneId: String): List<NeighborEntity>
}
