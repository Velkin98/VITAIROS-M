package com.example.vitairos_m.viewmodel

import android.app.Application
import com.example.vitairos_m.model.Emocion
import com.example.vitairos_m.network.EmocionApi
import com.example.vitairos_m.network.RetrofitClient
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class EmocionViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val application = mockk<Application>(relaxed = true)
    private val api = mockk<EmocionApi>()

    private val emocion = Emocion(
        id = 1,
        nombre = "Feliz",
        categoria = "Positiva",
        energia = 5,
        nota = "Buen día"
    )

    @Before
    fun preparar() {
        Dispatchers.setMain(dispatcher)

        mockkObject(RetrofitClient)

        every { RetrofitClient.emocionApi } returns api

        coEvery {
            api.listarEmociones()
        } returns emptyList()
    }

    @After
    fun limpiar() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun cargarEmocionesCorrectamente() = runTest {

        coEvery {
            api.listarEmociones()
        } returns listOf(emocion)

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        assertEquals(1, viewModel.emociones.value.size)
        assertEquals(emocion, viewModel.emociones.value.first())
    }

    @Test
    fun listaInicialVacia() = runTest {

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        assertTrue(viewModel.emociones.value.isEmpty())
    }

    @Test
    fun guardarEmocionCorrectamente() = runTest {

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        coEvery {
            api.guardarEmocion(any())
        } returns emocion

        coEvery {
            api.listarEmociones()
        } returns listOf(emocion)

        viewModel.guardarEmocion(
            nombre = "Feliz",
            categoria = "Positiva",
            energia = 5,
            nota = "Buen día"
        )

        advanceUntilIdle()

        assertEquals(1, viewModel.emociones.value.size)

        coVerify(exactly = 1) {
            api.guardarEmocion(
                match { it.nombre == "Feliz" }
            )
        }
    }

    @Test
    fun guardarEmocionConValoresOpcionales() = runTest {

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        val nueva = Emocion(
            id = 2,
            nombre = "Triste"
        )

        coEvery {
            api.guardarEmocion(any())
        } returns nueva

        coEvery {
            api.listarEmociones()
        } returns listOf(nueva)

        viewModel.guardarEmocion("Triste")

        advanceUntilIdle()

        assertEquals(
            "Triste",
            viewModel.emociones.value.first().nombre
        )
    }

    @Test
    fun actualizarEmocionCorrectamente() = runTest {

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        val actualizada = emocion.copy(
            nombre = "Tranquilo"
        )

        coEvery {
            api.actualizarEmocion(1, any())
        } returns actualizada

        coEvery {
            api.listarEmociones()
        } returns listOf(actualizada)

        viewModel.actualizarEmocion(
            emocion,
            "Tranquilo"
        )

        advanceUntilIdle()

        assertEquals(
            "Tranquilo",
            viewModel.emociones.value.first().nombre
        )

        coVerify(exactly = 1) {
            api.actualizarEmocion(1, actualizada)
        }
    }

    @Test
    fun eliminarEmocionCorrectamente() = runTest {

        coEvery {
            api.listarEmociones()
        } returns listOf(emocion)

        val viewModel = EmocionViewModel(application)

        advanceUntilIdle()

        coEvery {
            api.eliminarEmocion(1)
        } returns Response.success<Unit>(204, null)

        coEvery {
            api.listarEmociones()
        } returns emptyList()

        viewModel.eliminarEmocion(emocion)

        advanceUntilIdle()

        assertTrue(viewModel.emociones.value.isEmpty())

        coVerify(exactly = 1) {
            api.eliminarEmocion(1)
        }
    }
}