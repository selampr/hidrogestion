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

/**
 * Clase principal que define la base de datos local de la app utilizando Room.
 *
 * Esta clase extiende de RoomDatabase y define las entidades (tablas) y sus DAOs (interfaces de acceso).
 * Sirve como punto de acceso principal para todas las operaciones de base de datos.
 */
@Database(
    entities = [WorkerEntity::class, WaterCutEntity::class, NeighborEntity::class], // Entidades de la BD
    version = 4, // Versión actual de la base de datos
    exportSchema = false // No se exporta el esquema a un archivo .json
)
abstract class AppDatabase : RoomDatabase() {

    // Métodos abstractos que devuelven los DAOs para cada entidad
    abstract fun workerDao(): WorkerDao
    abstract fun waterCutDao(): WaterCutDao
    abstract fun neighborDao(): NeighborDao

    companion object {
        // Instancia singleton para evitar múltiples conexiones a la BD
        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Obtiene la instancia de la base de datos. Si no existe, la crea.
         * @param context Contexto de la aplicación.
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * Crea y configura la base de datos Room.
         * @param context Contexto de la aplicación.
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "hidrogestion1.db" // Nombre físico del archivo de la BD
            )
                .fallbackToDestructiveMigration(true) // Borra y recrea la BD si hay cambios de versión
                .addCallback(DatabaseCallback(context)) // Callback para precargar datos
                .build()
        }
    }

    /**
     * Callback personalizado que se ejecuta cuando se crea la base de datos por primera vez.
     * Aquí se pueden insertar datos iniciales.
     */
    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        /**
         * Método llamado automáticamente al crear la base de datos por primera vez.
         * Se utiliza para insertar datos por defecto (vecinos en este caso).
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Se lanza una corrutina en hilo de fondo para insertar los datos sin bloquear la interfaz
            CoroutineScope(Dispatchers.IO).launch {
                // Asegura que la instancia esté creada antes de insertar
                val db = getInstance(context)

                // Inserción de vecinos predefinidos
                db.neighborDao().insertAll(
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
