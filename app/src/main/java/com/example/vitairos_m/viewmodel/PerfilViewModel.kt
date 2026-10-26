
package com.example.vitairos_m.viewmodel

import android.app.Application
import android.net.Uri

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.example.vitairos_m.data.EmocionDatabase
import com.example.vitairos_m.data.MIGRATION_1_2
import com.example.vitairos_m.data.MIGRATION_2_3
import com.example.vitairos_m.data.MIGRATION_3_4
import com.example.vitairos_m.model.Perfil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.io.File

class PerfilViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Conexión con la base de datos Room
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

    // Acceso a la tabla del perfil
    private val perfilDao = database.perfilDao()

    // Recuperamos el nombre y correo guardados
    val perfil = perfilDao.obtenerPerfil()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // Archivo donde se guarda la foto
    private val archivoFoto = File(
        application.filesDir,
        "foto_perfil.jpg"
    )

    // Recuperamos la ruta de la foto guardada
    private val _fotoGuardada = MutableStateFlow(
        if (archivoFoto.exists()) {
            archivoFoto.absolutePath
        } else {
            null
        }
    )

    val fotoGuardada = _fotoGuardada.asStateFlow()

    // Guarda o actualiza nombre y correo en Room
    fun guardarPerfil(nombre: String, correo: String) {

        viewModelScope.launch {

            val nuevoPerfil = Perfil(
                id = 1,
                nombre = nombre,
                correo = correo
            )

            perfilDao.guardarPerfil(nuevoPerfil)
        }
    }

    // Guarda la foto en el almacenamiento interno
    fun guardarFoto(uri: Uri) {

        viewModelScope.launch {

            try {

                withContext(Dispatchers.IO) {

                    val context = getApplication<Application>()

                    // Archivo temporal para evitar perder la foto anterior
                    val archivoTemporal = File(
                        context.cacheDir,
                        "foto_perfil_temporal"
                    )

                    context.contentResolver
                        .openInputStream(uri)
                        ?.use { entrada ->

                            archivoTemporal.outputStream().use { salida ->
                                entrada.copyTo(salida)
                            }

                        } ?: error("No se pudo abrir la imagen")

                    // Reemplazamos la foto anterior
                    archivoTemporal.copyTo(
                        target = archivoFoto,
                        overwrite = true
                    )

                    archivoTemporal.delete()
                }

                // Actualizamos el estado de la foto
                _fotoGuardada.value = archivoFoto.absolutePath

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
