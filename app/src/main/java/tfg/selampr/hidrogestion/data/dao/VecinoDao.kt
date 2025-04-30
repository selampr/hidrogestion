package tfg.selampr.hidrogestion.data.dao

import androidx.room.*
import tfg.selampr.hidrogestion.data.model.*

@Dao
interface VecinoDao {
    @Query("SELECT * FROM vecinos WHERE vec_zon = :zona")
    suspend fun vecinosPorZona(zona: String): List<VecinoEntity>

    @Insert
    suspend fun insertar(vecino: VecinoEntity)
}
