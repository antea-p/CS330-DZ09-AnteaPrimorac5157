package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Recept
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Torta

// TODO: provjeriti veze
data class TortaAndRecept(
    @Embedded val torta: Torta,
    @Relation(
        parentColumn = "id",
        entityColumn = "tortaId"
    )
    val recept: Recept
)
