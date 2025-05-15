// data/database/AppDatabase.kt
package tfg.selampr.hidrogestion.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tfg.selampr.hidrogestion.data.dao.WorkerDao
import tfg.selampr.hidrogestion.data.model.WorkerEntity

@Database(
    version = 1, entities = [
        WorkerEntity::class, //en este caso solo necesitamos los datos del trabajador
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }
        private fun buildDatabase(context: Context): AppDatabase = //instancia la bbdd con room
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "hidrogestion.db"
            ).build()
    }
}