
package com.example.vitairos_m.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.vitairos_m.model.Perfil
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarPerfil(perfil: Perfil)

    @Query("SELECT * FROM perfil WHERE id = 1")
    fun obtenerPerfil(): Flow<Perfil?>
}
