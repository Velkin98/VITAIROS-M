
package com.example.vitairos_m.data

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.vitairos_m.model.Emocion
import com.example.vitairos_m.model.Perfil
import com.example.vitairos_m.model.SolicitudHora

@Database(
    entities = [
        Emocion::class,
        Perfil::class,
        SolicitudHora::class
    ],
    version = 4,
    exportSchema = false
)
abstract class EmocionDatabase : RoomDatabase() {

    abstract fun emocionDao(): EmocionDao

    abstract fun perfilDao(): PerfilDao

    abstract fun solicitudHoraDao(): SolicitudHoraDao
}
