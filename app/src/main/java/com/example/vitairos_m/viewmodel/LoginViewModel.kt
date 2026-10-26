
package com.example.vitairos_m.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.example.vitairos_m.validation.LoginValidator

class LoginViewModel : ViewModel() {

    // Datos ingresados por el usuario
    var correo by mutableStateOf("")
        private set

    var contrasena by mutableStateOf("")
        private set

    // Mensaje general del formulario
    var mensaje by mutableStateOf("")
        private set

    // Errores individuales
    var errorCorreo by mutableStateOf<String?>(null)
        private set

    var errorContrasena by mutableStateOf<String?>(null)
        private set

    // Control del inicio de sesión
    var loginExitoso by mutableStateOf(false)
        private set

    // Actualizar correo
    fun actualizarCorreo(nuevoCorreo: String) {

        correo = nuevoCorreo
        errorCorreo = null
        mensaje = ""
        loginExitoso = false
    }

    // Actualizar contraseña
    fun actualizarContrasena(nuevaContrasena: String) {

        contrasena = nuevaContrasena
        errorContrasena = null
        mensaje = ""
        loginExitoso = false
    }

    // Validar el formulario completo
    fun validarLogin() {

        loginExitoso = false

        errorCorreo = LoginValidator.validarCorreo(correo)

        errorContrasena =
            LoginValidator.validarContrasena(contrasena)

        val formularioValido =
            LoginValidator.validarFormulario(
                correo = correo,
                contrasena = contrasena
            )

        if (formularioValido) {

            mensaje = "Datos válidos. Inicio de sesión simulado"

            loginExitoso = true

        } else {

            mensaje = "Revisa los campos marcados."
        }
    }
}
