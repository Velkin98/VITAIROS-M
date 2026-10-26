
package com.example.vitairos_m.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.example.vitairos_m.data.EmocionDatabase
import com.example.vitairos_m.data.MIGRATION_1_2
import com.example.vitairos_m.data.MIGRATION_2_3
import com.example.vitairos_m.data.MIGRATION_3_4
import com.example.vitairos_m.model.SolicitudHora

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SolicitudHoraViewModel(
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

    // Acceso a la tabla de solicitudes
    private val solicitudHoraDao = database.solicitudHoraDao()

    // Recuperamos las solicitudes guardadas
    val solicitudes = solicitudHoraDao.obtenerSolicitudes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Guarda una solicitud de atención simulada
    fun guardarSolicitud(
        profesional: String,
        fecha: String,
        hora: String
    ) {

        viewModelScope.launch {

            val nuevaSolicitud = SolicitudHora(
                profesional = profesional,
                fecha = fecha,
                hora = hora
            )

            solicitudHoraDao.guardarSolicitud(nuevaSolicitud)
        }
    }

    // Elimina una solicitud guardada
    fun eliminarSolicitud(solicitud: SolicitudHora) {

        viewModelScope.launch {

            solicitudHoraDao.eliminarSolicitud(solicitud)
        }
    }
}
