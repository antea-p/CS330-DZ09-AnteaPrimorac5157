package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Sastojak
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Torta

data class TortaWithSastojci(
    @Embedded val torta: Torta,
    @Relation(
        parentColumn = "id",
        entityColumn = "tortaId"
    )
    val sastojci: List<Sastojak>
)