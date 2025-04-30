package tfg.selampr.hidrogestion.data.dao

import androidx.room.*
import tfg.selampr.hidrogestion.data.model.CorteEntity

@Dao
interface CorteDao {
    @Insert
    suspend fun insertarCorte(corte: CorteEntity)

    @Query("SELECT * FROM cortes WHERE cor_zon = :zona")
    suspend fun obtenerCortesPorZona(zona: String): List<CorteEntity>
}
