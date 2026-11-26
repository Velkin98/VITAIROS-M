package com.example.vitairos_m.network

import com.example.vitairos_m.model.Frase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FraseApi {

    @GET("api/random")
    suspend fun obtenerFrase(): List<Frase>
}

object FraseRetrofitClient {

    private const val BASE_URL = "https://zenquotes.io/"

    val fraseApi: FraseApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FraseApi::class.java)
    }
}