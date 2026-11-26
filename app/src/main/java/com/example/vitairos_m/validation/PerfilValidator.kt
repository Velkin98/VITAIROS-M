package com.example.vitairos_m.validation

import androidx.core.util.PatternsCompat

object PerfilValidator {

    fun validarNombre(nombre: String): String? {
        return when {
            nombre.isBlank() ->
                "Debes ingresar tu nombre"

            nombre.trim().length < 2 ->
                "El nombre debe tener al menos 2 caracteres"

            else -> null
        }
    }

    fun validarCorreo(correo: String): String? {
        return when {
            correo.isBlank() ->
                "Debes ingresar tu correo electrónico"

            !PatternsCompat.EMAIL_ADDRESS.matcher(correo.trim()).matches() ->
                "Ingresa un correo electrónico válido"

            else -> null
        }
    }

    fun validarFormulario(
        nombre: String,
        correo: String
    ): Boolean {
        return validarNombre(nombre) == null &&
                validarCorreo(correo) == null
    }
}