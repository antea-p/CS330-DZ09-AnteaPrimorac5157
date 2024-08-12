package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.ReceptEntity
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.SastojakEntity
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.TortaEntity

@Database(entities = [TortaEntity::class, ReceptEntity::class, SastojakEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}

