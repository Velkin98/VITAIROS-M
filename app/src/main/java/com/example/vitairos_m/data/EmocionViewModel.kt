package com.example.vitairos_m.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vitairos_m.model.Emocion
import com.example.vitairos_m.network.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmocionViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Conexion con el microservicio
    private val api = RetrofitClient.emocionApi

    // Lista de emociones
    private val _emociones = MutableStateFlow<List<Emocion>>(emptyList())
    val emociones = _emociones.asStateFlow()

    init {
        cargarEmociones()
    }

    // Obtener emociones desde Spring Boot
    fun cargarEmociones() {
        ejecutarOperacion {
            _emociones.value = api.listarEmociones()
        }
    }

    // Guardar una nueva emocion
    fun guardarEmocion(
        nombre: String,
        categoria: String? = null,
        energia: Int? = null,
        nota: String? = null
    ) {
        ejecutarOperacion {

            val nuevaEmocion = Emocion(
                nombre = nombre,
                categoria = categoria,
                energia = energia,
                nota = nota
            )

            api.guardarEmocion(nuevaEmocion)
            _emociones.value = api.listarEmociones()
        }
    }

    // Actualizar una emocion
    fun actualizarEmocion(
        emocion: Emocion,
        nuevoNombre: String,
        nuevaCategoria: String? = emocion.categoria,
        nuevaEnergia: Int? = emocion.energia,
        nuevaNota: String? = emocion.nota
    ) {
        ejecutarOperacion {

            val emocionActualizada = emocion.copy(
                nombre = nuevoNombre,
                categoria = nuevaCategoria,
                energia = nuevaEnergia,
                nota = nuevaNota
            )

            api.actualizarEmocion(
                emocion.id,
                emocionActualizada
            )

            _emociones.value = api.listarEmociones()
        }
    }

    // Eliminar una emocion
    fun eliminarEmocion(emocion: Emocion) {
        ejecutarOperacion {

            val respuesta = api.eliminarEmocion(emocion.id)

            if (!respuesta.isSuccessful) {
                throw retrofit2.HttpException(respuesta)
            }

            _emociones.value = api.listarEmociones()
        }
    }

    // Manejo de errores de conexion
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
                    "Error de comunicacion: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}