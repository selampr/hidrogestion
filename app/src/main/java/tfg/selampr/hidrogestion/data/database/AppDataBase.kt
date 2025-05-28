package tfg.selampr.hidrogestion.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tfg.selampr.hidrogestion.data.dao.NeighborDao
import tfg.selampr.hidrogestion.data.dao.WaterCutDao
import tfg.selampr.hidrogestion.data.dao.WorkerDao
import tfg.selampr.hidrogestion.data.model.NeighborEntity
import tfg.selampr.hidrogestion.data.model.WaterCutEntity
import tfg.selampr.hidrogestion.data.model.WorkerEntity

@Database(entities = [WorkerEntity::class, WaterCutEntity::class, NeighborEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    abstract fun waterCutDao(): WaterCutDao
    abstract fun neighborDao(): NeighborDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "hidrogestion1.db"
            )
                .fallbackToDestructiveMigration(true)
                .addCallback(DatabaseCallback(context))
                .build()
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Inicializa con datos precargados
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).workerDao() // si tienes otros daos

                getInstance(context).neighborDao().insertAll(
                    listOf(
                        NeighborEntity(name = "Julia", surname = "Serrano", email = "serranolampre@gmail.com", address = "Calle A", zoneId = 1),
                        NeighborEntity(name = "Luis", surname = "Gómez", email = "luis@example.com", address = "Calle B", zoneId = 2),
                        NeighborEntity(name = "Carmen", surname = "Rodríguez", email = "carmen@example.com", address = "Calle C", zoneId = 3),
                        NeighborEntity(name = "Miguel", surname = "Martínez", email = "miguel@example.com", address = "Calle D", zoneId = 4),
                        NeighborEntity(name = "Laura", surname = "Sánchez", email = "laura@example.com", address = "Calle E", zoneId = 2),
                        NeighborEntity(name = "Carlos", surname = "Fernández", email = "carlos@example.com", address = "Calle F", zoneId = 2),
                        NeighborEntity(name = "Elena", surname = "López", email = "elena@example.com", address = "Calle G", zoneId = 2),
                        NeighborEntity(name = "Diego", surname = "Ruiz", email = "diego@example.com", address = "Calle H", zoneId = 2),
                        NeighborEntity(name = "Marta", surname = "García", email = "marta@example.com", address = "Calle I", zoneId = 3),
                        NeighborEntity(name = "Jorge", surname = "Alonso", email = "jorge@example.com", address = "Calle J", zoneId = 3),
                        NeighborEntity(name = "Lucía", surname = "Morales", email = "lucia@example.com", address = "Calle K", zoneId = 3),
                        NeighborEntity(name = "Iván", surname = "Ortega", email = "ivan@example.com", address = "Calle L", zoneId = 3),
                        NeighborEntity(name = "Sofía", surname = "Vega", email = "sofia@example.com", address = "Calle M", zoneId = 4),
                        NeighborEntity(name = "Raúl", surname = "Iglesias", email = "raul@example.com", address = "Calle N", zoneId = 4),
                        NeighborEntity(name = "Paula", surname = "Silva", email = "paula@example.com", address = "Calle O", zoneId = 4),
                        NeighborEntity(name = "Rubén", surname = "Domínguez", email = "ruben@example.com", address = "Calle P", zoneId = 4),
                        NeighborEntity(name = "Nuria", surname = "Delgado", email = "nuria@example.com", address = "Calle Q", zoneId = 1),
                    )
                )
            }
        }
    }

}

