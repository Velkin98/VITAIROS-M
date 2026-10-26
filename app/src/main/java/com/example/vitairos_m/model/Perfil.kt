
package com.example.vitairos_m.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil")
data class Perfil(

    @PrimaryKey
    val id: Int = 1,

    val nombre: String,

    val correo: String
)
