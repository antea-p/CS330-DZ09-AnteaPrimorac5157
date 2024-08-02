package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Recept
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Sastojak
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Torta

@Database(entities = [Torta::class, Recept::class, Sastojak::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
