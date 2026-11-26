package com.example.vitairos_m.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitairos_m.model.SolicitudHora
import com.example.vitairos_m.network.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SolicitudHoraViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val api = RetrofitClient.solicitudHoraApi

    private val _solicitudes =
        MutableStateFlow<List<SolicitudHora>>(emptyList())

    val solicitudes = _solicitudes.asStateFlow()

    init {
        cargarSolicitudes()
    }

    // READ: Obtener solicitudes
    fun cargarSolicitudes() {
        ejecutarOperacion {
            _solicitudes.value = api.listarSolicitudes()
        }
    }

    // CREATE: Guardar solicitud
    fun guardarSolicitud(
        profesional: String,
        fecha: String,
        hora: String,
        onSuccess: () -> Unit = {}
    ) {
        ejecutarOperacion {

            val nuevaSolicitud = SolicitudHora(
                profesional = profesional,
                fecha = fecha,
                hora = hora
            )

            val guardada = api.guardarSolicitud(nuevaSolicitud)

            _solicitudes.value = _solicitudes.value + guardada

            onSuccess()
        }
    }

    // UPDATE: Actualizar solicitud
    fun actualizarSolicitud(
        solicitud: SolicitudHora,
        profesional: String,
        fecha: String,
        hora: String
    ) {
        ejecutarOperacion {

            val solicitudActualizada = solicitud.copy(
                profesional = profesional,
                fecha = fecha,
                hora = hora
            )

            api.actualizarSolicitud(
                solicitud.id,
                solicitudActualizada
            )

            _solicitudes.value = api.listarSolicitudes()
        }
    }

    // DELETE: Eliminar solicitud
    fun eliminarSolicitud(solicitud: SolicitudHora) {
        ejecutarOperacion {

            val respuesta = api.eliminarSolicitud(solicitud.id)

            if (!respuesta.isSuccessful) {
                throw HttpException(respuesta)
            }

            _solicitudes.value = api.listarSolicitudes()
        }
    }

    // Manejo de errores de comunicación
    private fun ejecutarOperacion(
        operacion: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {

                operacion()

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                Toast.makeText(
                    getApplication<Application>(),
                    "Error de comunicación: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}