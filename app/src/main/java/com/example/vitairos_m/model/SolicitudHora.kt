
package com.example.vitairos_m.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes_hora")
data class SolicitudHora(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val profesional: String,

    val fecha: String,

    val hora: String,

    val fechaSolicitud: Long = System.currentTimeMillis()
)
