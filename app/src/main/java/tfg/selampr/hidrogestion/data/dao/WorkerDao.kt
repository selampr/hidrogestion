package tfg.selampr.hidrogestion.data.dao

// data/dao/WorkerDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tfg.selampr.hidrogestion.data.model.WorkerEntity

@Dao
interface WorkerDao {
    @Query("SELECT * FROM workers WHERE wor_use = :username AND wor_pass = :password LIMIT 1")
    suspend fun login(username: String, password: String): WorkerEntity?

    @Query("SELECT * FROM workers WHERE wor_use = :username LIMIT 1")
    suspend fun getWorkerByUsername(username: String): WorkerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(worker: WorkerEntity)

    @Query("SELECT * FROM workers ")
    suspend fun getAnyWorker(): WorkerEntity?

    @Query("SELECT * FROM workers WHERE id = :id")
    fun getWorkerById(id: Int): Flow<WorkerEntity?>


}
