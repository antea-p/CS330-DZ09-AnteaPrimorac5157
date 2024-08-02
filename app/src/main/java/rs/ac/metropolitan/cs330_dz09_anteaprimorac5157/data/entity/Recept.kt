package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recept")
data class Recept(
    @PrimaryKey val id: Long,
    var tortaId: Long,
    val naziv: String,
    val uputstvo: String
)