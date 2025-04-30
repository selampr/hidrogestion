package tfg.selampr.hidrogestion.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import tfg.selampr.hidrogestion.data.dao.*
import tfg.selampr.hidrogestion.data.model.*
import tfg.selampr.hidrogestion.data.database.*

import tfg.selampr.hidrogestion.data.model.TrabajadorEntity

@Database(
    entities = [TrabajadorEntity::class, CorteEntity::class, VecinoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trabajadorDao(): TrabajadorDao
    abstract fun corteDao(): CorteDao
    abstract fun vecinoDao(): VecinoDao
}
