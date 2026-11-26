package com.example.vitairos_m.network

import com.example.vitairos_m.model.SolicitudHora
import retrofit2.Response
import retrofit2.http.*

interface SolicitudHoraApi {

    @GET("api/solicitudes")
    suspend fun listarSolicitudes(): List<SolicitudHora>

    @POST("api/solicitudes")
    suspend fun guardarSolicitud(
        @Body solicitud: SolicitudHora
    ): SolicitudHora

    @PUT("api/solicitudes/{id}")
    suspend fun actualizarSolicitud(
        @Path("id") id: Int,
        @Body solicitud: SolicitudHora
    ): SolicitudHora

    @DELETE("api/solicitudes/{id}")
    suspend fun eliminarSolicitud(
        @Path("id") id: Int
    ): Response<Unit>
}