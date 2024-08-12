package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "torta")
data class TortaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val naziv: String,
    val opis: String?
)