package rs.ac.metropolitan.cs330_dz09_anteaprimorac5157

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.AppDao
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.data.AppDatabase
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.fake.fakeRecepti
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.fake.fakeSastojci
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.fake.fakeSastojciForTorta
import rs.ac.metropolitan.cs330_dz09_anteaprimorac5157.fake.fakeTorte

@RunWith(AndroidJUnit4::class)
class DBTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: AppDao

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.appDao()
    }

    @After
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun upsertAndSearchRecept() = runTest {
        // Given
        val recept = fakeRecepti[0]

        // When
        val id = dao.upsertRecept(recept)
        val fetchedRecept = dao.getReceptByName("Recept 1").first()

        // Then
        assert(fetchedRecept.isNotEmpty())
        assert(fetchedRecept[0].id == id)
        assert(fetchedRecept[0].naziv == recept.naziv)
    }

    @Test
    fun upsertAndGetTortaAndRecept() = runTest {
        // Given
        val torta = fakeTorte[0]
        val recept = fakeRecepti[0]

        // When
        val tortaId = dao.upsertTorta(torta)
        recept.tortaId = tortaId
        val receptId = dao.upsertRecept(recept)
        val fetchedTortaAndRecept = dao.getTortaAndRecept(tortaId).first()

        // Then
        assert(fetchedTortaAndRecept.torta.id == tortaId)
        assert(fetchedTortaAndRecept.torta.naziv == torta.naziv)
        assert(fetchedTortaAndRecept.recept.id == receptId)
        assert(fetchedTortaAndRecept.recept.naziv == recept.naziv)
    }

    @Test
    fun upsertTortaWithSastojak() = runTest {
        // Given
        val torta = fakeTorte[0]
        val sastojci = fakeSastojciForTorta()

        // When
        val tortaId = dao.upsertTorta(torta)
        sastojci.forEach { it.tortaId = tortaId; dao.upsertSastojak(it) }
        val fetchedTorta = dao.getTortaWithSastojci(tortaId).first()

        // Then
        assert(fetchedTorta.torta.id == tortaId)
        assert(fetchedTorta.torta.naziv == torta.naziv)
        assert(fetchedTorta.sastojci.isNotEmpty())
    }

    @Test
    fun testCaseNoTortaWithSastojciInKom() = runTest {
        // Given
        val torta = fakeTorte[0]
        val sastojci = listOf(
            fakeSastojci[0].copy(kolicina = 3000, jedinicaMere = "g"),
            fakeSastojci[1].copy(kolicina = 1500, jedinicaMere = "g")
        )

        // When
        val tortaId = dao.upsertTorta(torta)
        sastojci.forEach { it.tortaId = tortaId; dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciByUnit().first()

        // Then
        assert(tortaWithSastojci.isEmpty())
    }

    @Test
    fun testCaseOneTortaWithSastojciInKom() = runTest {
        // Given
        val torta = fakeTorte[1]
        val sastojci = listOf(
            fakeSastojci[0].copy(kolicina = 3000, jedinicaMere = "g"),
            fakeSastojci[1].copy(kolicina = 2, jedinicaMere = "kom")
        )

        // When
        val tortaId = dao.upsertTorta(torta)
        sastojci.forEach { it.tortaId = tortaId; dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciByUnit().first()

        // Then
        assert(tortaWithSastojci.isNotEmpty())
        assert(tortaWithSastojci.size == 1)
        assert(tortaWithSastojci[0].tortaNaziv == "Torta 2")
        assert(tortaWithSastojci[0].sastojakNaziv == "Sastojak 2")
    }

    @Test
    fun testCaseMultipleTortaWithSastojciInKom() = runTest {
        // Given
        val torte = listOf(fakeTorte[0], fakeTorte[1])
        val sastojci = listOf(
            fakeSastojci[0].copy(kolicina = 2, jedinicaMere = "kom", tortaId = 1),
            fakeSastojci[1].copy(kolicina = 3, jedinicaMere = "kom", tortaId = 2)
        )

        // When
        torte.forEach { dao.upsertTorta(it) }
        sastojci.forEach { dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciByUnit().first()

        // Then
        assert(tortaWithSastojci.isNotEmpty())
        assert(tortaWithSastojci.size == 2)
        assert(tortaWithSastojci.any { it.tortaNaziv == "Torta 1" && it.sastojakNaziv == "Sastojak 1" })
        assert(tortaWithSastojci.any { it.tortaNaziv == "Torta 2" && it.sastojakNaziv == "Sastojak 2" })
    }


    @Test
    fun testCaseNoTortaWithSastojciOver2Kg() = runTest {
        // Given
        val torta = fakeTorte[0]
        val sastojci = listOf(
            fakeSastojci[1].copy(kolicina = 1500, jedinicaMere = "g"),
            fakeSastojci[2].copy(kolicina = 1000, jedinicaMere = "g")
        )

        // When
        val tortaId = dao.upsertTorta(torta)
        sastojci.forEach { it.tortaId = tortaId; dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciOver2Kg().first()

        // Then
        assert(tortaWithSastojci.isEmpty())
    }

    @Test
    fun testCaseOneTortaWithSastojciOver2Kg() = runTest {
        // Given
        val torta = fakeTorte[1]
        val sastojci = listOf(
            fakeSastojci[0].copy(kolicina = 3000, jedinicaMere = "g"),
            fakeSastojci[1].copy(kolicina = 1500, jedinicaMere = "g")
        )

        // When
        val tortaId = dao.upsertTorta(torta)
        sastojci.forEach { it.tortaId = tortaId; dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciOver2Kg().first()

        // Then
        assert(tortaWithSastojci.isNotEmpty())
        assert(tortaWithSastojci.size == 1)
        assert(tortaWithSastojci[0].tortaNaziv == "Torta 2")
    }


    @Test
    fun testCaseMultipleTortaWithSastojciOver2Kg() = runTest {
        // Given
        val torte = listOf(fakeTorte[0], fakeTorte[1])
        val sastojci = listOf(
            fakeSastojci[0].copy(kolicina = 3000, jedinicaMere = "g", tortaId = 1),
            fakeSastojci[1].copy(kolicina = 1500, jedinicaMere = "g", tortaId = 1),
            fakeSastojci[2].copy(kolicina = 2500, jedinicaMere = "g", tortaId = 2),
            fakeSastojci[3].copy(kolicina = 500, jedinicaMere = "g", tortaId = 2)
        )

        // When
        torte.forEach { dao.upsertTorta(it) }
        sastojci.forEach { dao.upsertSastojak(it) }
        val tortaWithSastojci = dao.listTortaWithSastojciOver2Kg().first()

        // Then
        assert(tortaWithSastojci.isNotEmpty())
        assert(tortaWithSastojci.size == 2)
        assert(tortaWithSastojci.any { it.tortaNaziv == "Torta 1" })
        assert(tortaWithSastojci.any { it.tortaNaziv == "Torta 2" })
    }


}