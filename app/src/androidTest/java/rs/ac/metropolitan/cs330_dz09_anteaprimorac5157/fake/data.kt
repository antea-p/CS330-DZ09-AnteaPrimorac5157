package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.fake

import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Recept
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Sastojak
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Torta

val fakeTorte = listOf(
    Torta(1, "Torta 1", "Opis Torta 1"),
    Torta(2, "Torta 2", "Opis Torta 2"),
    Torta(3, "Torta 3", "Opis Torta 3")
)

val fakeRecepti = listOf(
    Recept(1, 1, "Recept 1", "Uputstvo za Recept 1"),
    Recept(2, 2, "Recept 2", "Uputstvo za Recept 2"),
    Recept(3, 3, "Recept 3", "Uputstvo za Recept 3")
)

val fakeSastojci = listOf(
    Sastojak(1, "Sastojak 1", 3000, "g", 1),
    Sastojak(2, "Sastojak 2", 2, "kom", 1),
    Sastojak(3, "Sastojak 3", 5000, "g", 2),
    Sastojak(4, "Sastojak 4", 3, "kom", 3)
)

fun fakeSastojciForTorta(): List<Sastojak> {
    val existingSastojci = fakeSastojci.toMutableList()
    existingSastojci[0].tortaId = 1
    existingSastojci[1].tortaId = 1
    existingSastojci[2].tortaId = 2
    existingSastojci[3].tortaId = 3
    return existingSastojci
}
