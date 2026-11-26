package com.example.vitairos_m.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val EMOCIONES_URL = "http://10.0.2.2:8081/"
    private const val SOLICITUDES_URL = "http://10.0.2.2:8082/"

    val emocionApi: EmocionApi by lazy {

        Retrofit.Builder()
            .baseUrl(EMOCIONES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmocionApi::class.java)
    }

    val solicitudHoraApi: SolicitudHoraApi by lazy {

        Retrofit.Builder()
            .baseUrl(SOLICITUDES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SolicitudHoraApi::class.java)
    }
}