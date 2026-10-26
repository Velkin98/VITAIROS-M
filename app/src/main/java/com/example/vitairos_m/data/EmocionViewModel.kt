
package com.example.vitairos_m.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.example.vitairos_m.data.EmocionDatabase
import com.example.vitairos_m.data.MIGRATION_1_2
import com.example.vitairos_m.data.MIGRATION_2_3
import com.example.vitairos_m.data.MIGRATION_3_4
import com.example.vitairos_m.model.Emocion

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmocionViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Conexión con nuestra base de datos Room
    private val database = Room.databaseBuilder(
        application,
        EmocionDatabase::class.java,
        "vitairos_database"
    )
        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4
        )
        .build()

    // Acceso a las operaciones de la base de datos
    private val emocionDao = database.emocionDao()

    // Lista de emociones guardadas
    val emociones = emocionDao.obtenerEmociones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Guarda una nueva emoción
    fun guardarEmocion(
        nombre: String,
        categoria: String? = null,
        energia: Int? = null,
        nota: String? = null
    ) {
        viewModelScope.launch {

            val nuevaEmocion = Emocion(
                nombre = nombre,
                categoria = categoria,
                energia = energia,
                nota = nota
            )

            emocionDao.guardarEmocion(nuevaEmocion)
        }
    }

    // Edita todos los datos de una emoción registrada
    fun actualizarEmocion(
        emocion: Emocion,
        nuevoNombre: String,
        nuevaCategoria: String? = emocion.categoria,
        nuevaEnergia: Int? = emocion.energia,
        nuevaNota: String? = emocion.nota
    ) {
        viewModelScope.launch {

            val emocionActualizada = emocion.copy(
                nombre = nuevoNombre,
                categoria = nuevaCategoria,
                energia = nuevaEnergia,
                nota = nuevaNota
            )

            emocionDao.actualizarEmocion(emocionActualizada)
        }
    }

    // Elimina una emoción registrada
    fun eliminarEmocion(emocion: Emocion) {
        viewModelScope.launch {

            emocionDao.eliminarEmocion(emocion)
        }
    }
}
