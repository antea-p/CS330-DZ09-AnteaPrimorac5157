package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Recept
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Sastojak
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.entity.Torta
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations.TortaAndRecept
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations.TortaWithSastojci
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.relations.TortaWithSastojciInfo

// TODO: provjeriti da su tu sve potrebne metode
@Dao
interface AppDao {
    @Upsert
    suspend fun upsertTorta(torta: Torta): Long

    @Upsert
    suspend fun upsertSastojak(sastojak: Sastojak): Long

    @Upsert
    suspend fun upsertRecept(recept: Recept): Long

    @Transaction
    @Query("SELECT * FROM torta WHERE id = :id")
    fun getTortaWithSastojci(id: Long): Flow<TortaWithSastojci>

    @Transaction
    @Query("SELECT * FROM torta")
    fun getAllTortaWithSastojci(): Flow<List<TortaWithSastojci>>

    @Transaction
    @Query("SELECT * FROM torta WHERE id = :id")
    fun getTortaAndRecept(id: Long): Flow<TortaAndRecept>

    @Query("SELECT * FROM recept WHERE naziv LIKE :name")
    fun searchReceptByName(name: String): Flow<List<Recept>>

    @Query("SELECT t.naziv as tortaNaziv, s.naziv as sastojakNaziv, s.kolicina, s.jedinicaMere " +
           "FROM torta t JOIN sastojak s ON t.id = s.tortaId " +
           "WHERE s.jedinicaMere = 'kom'")
    fun listTortaWithSastojciByUnit(): Flow<List<TortaWithSastojciInfo>>

    @Query("SELECT t.naziv as tortaNaziv, s.naziv as sastojakNaziv, s.kolicina, s.jedinicaMere " +
           "FROM torta t JOIN sastojak s ON t.id = s.tortaId " +
           "WHERE s.kolicina > 2000")
    fun listTortaWithSastojciOver2Kg(): Flow<List<TortaWithSastojciInfo>>
}
