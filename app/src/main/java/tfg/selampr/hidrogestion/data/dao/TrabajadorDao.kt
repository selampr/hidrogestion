package tfg.selampr.hidrogestion.data.dao

import androidx.room.Dao
import androidx.room.Query
import tfg.selampr.hidrogestion.data.model.TrabajadorEntity

@Dao
interface TrabajadorDao {
    @Query("SELECT * FROM trabajadores WHERE tra_usu = :usuario AND tra_con = :contrasena")
    suspend fun login(usuario: String, contrasena: String): TrabajadorEntity?
}
