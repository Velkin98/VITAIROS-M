package com.example.vitairos_m.viewmodel

import android.app.Application
import com.example.vitairos_m.model.SolicitudHora
import com.example.vitairos_m.network.SolicitudHoraApi
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
class SolicitudHoraViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val application = mockk<Application>(relaxed = true)
    private val api = mockk<SolicitudHoraApi>()

    private val solicitud = SolicitudHora(
        id = 1,
        profesional = "Psicólogo/a de orientación",
        fecha = "30/11/2026",
        hora = "10:00"
    )

    @Before
    fun preparar() {

        Dispatchers.setMain(dispatcher)

        mockkObject(RetrofitClient)

        every {
            RetrofitClient.solicitudHoraApi
        } returns api

        coEvery {
            api.listarSolicitudes()
        } returns emptyList()
    }

    @After
    fun limpiar() {

        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun cargarSolicitudesCorrectamente() = runTest {

        coEvery {
            api.listarSolicitudes()
        } returns listOf(solicitud)

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        assertEquals(
            1,
            viewModel.solicitudes.value.size
        )

        assertEquals(
            solicitud,
            viewModel.solicitudes.value.first()
        )
    }

    @Test
    fun listaInicialVacia() = runTest {

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        assertTrue(
            viewModel.solicitudes.value.isEmpty()
        )
    }

    @Test
    fun guardarSolicitudCorrectamente() = runTest {

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        coEvery {
            api.guardarSolicitud(any())
        } returns solicitud

        viewModel.guardarSolicitud(
            profesional = "Psicólogo/a de orientación",
            fecha = "30/11/2026",
            hora = "10:00"
        )

        advanceUntilIdle()

        assertEquals(
            1,
            viewModel.solicitudes.value.size
        )

        assertEquals(
            solicitud,
            viewModel.solicitudes.value.first()
        )

        coVerify(exactly = 1) {
            api.guardarSolicitud(
                match {
                    it.profesional == solicitud.profesional &&
                            it.fecha == solicitud.fecha &&
                            it.hora == solicitud.hora
                }
            )
        }
    }

    @Test
    fun guardarSolicitudEjecutaOnSuccess() = runTest {

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        var guardadoExitoso = false

        coEvery {
            api.guardarSolicitud(any())
        } returns solicitud

        viewModel.guardarSolicitud(
            profesional = solicitud.profesional,
            fecha = solicitud.fecha,
            hora = solicitud.hora,
            onSuccess = {
                guardadoExitoso = true
            }
        )

        advanceUntilIdle()

        assertTrue(guardadoExitoso)
    }

    @Test
    fun actualizarSolicitudCorrectamente() = runTest {

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        val actualizada = solicitud.copy(
            fecha = "29/11/2026",
            hora = "11:00"
        )

        coEvery {
            api.actualizarSolicitud(1, any())
        } returns actualizada

        coEvery {
            api.listarSolicitudes()
        } returns listOf(actualizada)

        viewModel.actualizarSolicitud(
            solicitud = solicitud,
            profesional = solicitud.profesional,
            fecha = "29/11/2026",
            hora = "11:00"
        )

        advanceUntilIdle()

        assertEquals(
            "29/11/2026",
            viewModel.solicitudes.value.first().fecha
        )

        assertEquals(
            "11:00",
            viewModel.solicitudes.value.first().hora
        )

        coVerify(exactly = 1) {
            api.actualizarSolicitud(1, actualizada)
        }
    }

    @Test
    fun eliminarSolicitudCorrectamente() = runTest {

        coEvery {
            api.listarSolicitudes()
        } returns listOf(solicitud)

        val viewModel = SolicitudHoraViewModel(application)

        advanceUntilIdle()

        coEvery {
            api.eliminarSolicitud(1)
        } returns Response.success<Unit>(204, null)

        coEvery {
            api.listarSolicitudes()
        } returns emptyList()

        viewModel.eliminarSolicitud(solicitud)

        advanceUntilIdle()

        assertTrue(
            viewModel.solicitudes.value.isEmpty()
        )

        coVerify(exactly = 1) {
            api.eliminarSolicitud(1)
        }
    }
}