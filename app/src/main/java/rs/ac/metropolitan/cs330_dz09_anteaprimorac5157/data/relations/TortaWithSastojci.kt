package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.SastojakEntity
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.TortaEntity

data class TortaWithSastojci(
    @Embedded val torta: TortaEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tortaId"
    )
    val sastojci: List<SastojakEntity>
)