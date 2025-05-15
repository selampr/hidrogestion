package tfg.selampr.hidrogestion.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tfg.selampr.hidrogestion.data.model.WaterCutEntity

@Dao
interface WaterCutDao {
    @Insert
    suspend fun insertWaterCut(waterCut: WaterCutEntity): Long

    @Query("SELECT * FROM watercut ORDER BY wac_sta DESC")
    fun getAllWaterCuts(): Flow<List<WaterCutEntity>>

    @Query("SELECT * FROM watercut WHERE wac_idw = :workerId ORDER BY startTime DESC")
    fun getWaterCutsByWorker(workerId: String): Flow<List<WaterCutEntity>>
}
