package tfg.selampr.hidrogestion.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import tfg.selampr.hidrogestion.data.model.WaterCutEntity

@Dao
interface WaterCutDao {
    @Insert
    suspend fun insertWaterCut(waterCut: WaterCutEntity): Long

    @Query("SELECT * FROM watercut ORDER BY wac_sta DESC")
    suspend fun getAllWaterCuts(): List<WaterCutEntity>

    @Query("SELECT * FROM watercut WHERE wac_idw = :workerId ORDER BY wac_sta DESC")
    fun getWaterCutsByWorker(workerId: Int): List<WaterCutEntity>

    @Query("SELECT * FROM watercut WHERE wacId = :id LIMIT 1")
    suspend fun getWaterCutById(id: Int): WaterCutEntity?


}
