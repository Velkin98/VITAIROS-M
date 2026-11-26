package com.example.vitairos_m.network

import com.example.vitairos_m.model.Emocion
import retrofit2.Response
import retrofit2.http.*

interface EmocionApi {

    @GET("api/emociones")
    suspend fun listarEmociones(): List<Emocion>

    @POST("api/emociones")
    suspend fun guardarEmocion(
        @Body emocion: Emocion
    ): Emocion

    @PUT("api/emociones/{id}")
    suspend fun actualizarEmocion(
        @Path("id") id: Int,
        @Body emocion: Emocion
    ): Emocion

    @DELETE("api/emociones/{id}")
    suspend fun eliminarEmocion(
        @Path("id") id: Int
    ): Response<Unit>
}