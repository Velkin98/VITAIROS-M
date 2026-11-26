package com.example.vitairos_m.viewmodel

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vitairos_m.data.EmocionDatabase
import com.example.vitairos_m.data.PerfilDao
import com.example.vitairos_m.model.Perfil
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class PerfilViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var application: Application
    private lateinit var database: EmocionDatabase
    private lateinit var dao: PerfilDao
    private lateinit var builder: RoomDatabase.Builder<EmocionDatabase>
    private lateinit var directorio: File
    private lateinit var viewModel: PerfilViewModel

    private lateinit var flujoPerfil: MutableStateFlow<Perfil?>

    @Before
    fun preparar() {

        Dispatchers.setMain(dispatcher)

        application = mockk(relaxed = true)
        database = mockk(relaxed = true)
        dao = mockk(relaxed = true)
        builder = mockk(relaxed = true)

        directorio = java.nio.file.Files
            .createTempDirectory("perfil_test")
            .toFile()

        flujoPerfil = MutableStateFlow(null)

        every {
            application.filesDir
        } returns directorio

        mockkStatic(Room::class)

        every {
            Room.databaseBuilder(
                application,
                EmocionDatabase::class.java,
                "vitairos_database"
            )
        } returns builder

        every {
            builder.addMigrations(*anyVararg())
        } returns builder

        every {
            builder.build()
        } returns database

        every {
            database.perfilDao()
        } returns dao

        every {
            dao.obtenerPerfil()
        } returns flujoPerfil

        coEvery {
            dao.guardarPerfil(any())
        } just Runs
    }

    @After
    fun limpiar() {

        unmockkAll()
        Dispatchers.resetMain()

        if (::directorio.isInitialized) {
            directorio.deleteRecursively()
        }
    }

    @Test
    fun perfilInicialEsNulo() = runTest(dispatcher) {

        viewModel = PerfilViewModel(application)

        assertNull(viewModel.perfil.value)
    }

    @Test
    fun fotoInicialEsNula() = runTest(dispatcher) {

        viewModel = PerfilViewModel(application)

        assertNull(viewModel.fotoGuardada.value)
    }

    @Test
    fun recuperaFotoExistente() = runTest(dispatcher) {

        val foto = File(
            directorio,
            "foto_perfil.jpg"
        )

        foto.writeText("foto de prueba")

        viewModel = PerfilViewModel(application)

        assertEquals(
            foto.absolutePath,
            viewModel.fotoGuardada.value
        )
    }

    @Test
    fun guardarPerfilCorrectamente() = runTest(dispatcher) {

        viewModel = PerfilViewModel(application)

        viewModel.guardarPerfil(
            "Usuario",
            "usuario@gmail.com"
        )

        advanceUntilIdle()

        coVerify(exactly = 1) {
            dao.guardarPerfil(
                Perfil(
                    id = 1,
                    nombre = "Usuario",
                    correo = "usuario@gmail.com"
                )
            )
        }
    }

    @Test
    fun actualizarPerfilCorrectamente() = runTest(dispatcher) {

        viewModel = PerfilViewModel(application)

        viewModel.guardarPerfil(
            "Nuevo nombre",
            "nuevo@gmail.com"
        )

        advanceUntilIdle()

        coVerify(exactly = 1) {
            dao.guardarPerfil(
                Perfil(
                    id = 1,
                    nombre = "Nuevo nombre",
                    correo = "nuevo@gmail.com"
                )
            )
        }
    }
}