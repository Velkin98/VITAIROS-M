
package com.example.vitairos_m.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emociones")
data class Emocion(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Estado de ánimo
    val nombre: String,

    // Fecha del registro
    val fecha: Long = System.currentTimeMillis(),

    // Categoría opcional
    val categoria: String? = null,

    // Nivel de energía opcional (1 al 5)
    val energia: Int? = null,

    // Nota personal opcional
    val nota: String? = null
)
