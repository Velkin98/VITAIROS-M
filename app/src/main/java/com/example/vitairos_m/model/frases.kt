package com.example.vitairos_m.model

import com.google.gson.annotations.SerializedName

data class Frase(

    @SerializedName("q")
    val frase: String,

    @SerializedName("a")
    val autor: String
)