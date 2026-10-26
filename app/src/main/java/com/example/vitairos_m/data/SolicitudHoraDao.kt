
package com.example.vitairos_m.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.example.vitairos_m.model.SolicitudHora
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudHoraDao {

    // Guardar una solicitud
    @Insert
    suspend fun guardarSolicitud(
        solicitud: SolicitudHora
    )

    // Obtener todas las solicitudes
    @Query(
        "SELECT * FROM solicitudes_hora ORDER BY fechaSolicitud DESC"
    )
    fun obtenerSolicitudes(): Flow<List<SolicitudHora>>

    // Eliminar una solicitud
    @Delete
    suspend fun eliminarSolicitud(
        solicitud: SolicitudHora
    )
}
