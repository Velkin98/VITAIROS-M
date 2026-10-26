
package com.example.vitairos_m.validation

import android.util.Patterns

object LoginValidator {

    const val MIN_CARACTERES_CONTRASENA = 6

    fun validarCorreo(correo: String): String? {

        return when {
            correo.isBlank() ->
                "Debes ingresar tu correo"

            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() ->
                "Ingresa un correo válido"

            else -> null
        }
    }

    fun validarContrasena(contrasena: String): String? {

        return when {
            contrasena.isBlank() ->
                "Debes ingresar tu contraseña"

            contrasena.length < MIN_CARACTERES_CONTRASENA ->
                "La contraseña debe tener al menos 6 caracteres"

            else -> null
        }
    }

    fun validarFormulario(
        correo: String,
        contrasena: String
    ): Boolean {

        return validarCorreo(correo) == null &&
                validarContrasena(contrasena) == null
    }
}
