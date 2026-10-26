
package com.example.vitairos_m.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.example.vitairos_m.model.Emocion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmocionDao {

    // Guardar una nueva emoción
    @Insert
    suspend fun guardarEmocion(emocion: Emocion)

    // Obtener todas las emociones registradas
    @Query("SELECT * FROM emociones ORDER BY fecha DESC")
    fun obtenerEmociones(): Flow<List<Emocion>>

    // Editar una emoción existente
    @Update
    suspend fun actualizarEmocion(emocion: Emocion)

    // Eliminar una emoción registrada
    @Delete
    suspend fun eliminarEmocion(emocion: Emocion)
}
