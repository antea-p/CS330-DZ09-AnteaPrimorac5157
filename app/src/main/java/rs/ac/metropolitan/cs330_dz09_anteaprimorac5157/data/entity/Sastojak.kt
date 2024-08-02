package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sastojak")
data class Sastojak(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val naziv: String,
    val kolicina: Int,
    val jedinicaMere: String,
    var tortaId: Long
)