package com.example.vitairos_m.validation

import org.junit.Assert.*
import org.junit.Test

class LoginValidatorTest {

    @Test
    fun correoVacioEsInvalido() {
        assertEquals(
            "Debes ingresar tu correo",
            LoginValidator.validarCorreo("")
        )
    }

    @Test
    fun correoConEspaciosEsInvalido() {
        assertNotNull(
            LoginValidator.validarCorreo("   ")
        )
    }

    @Test
    fun correoIncorrectoEsInvalido() {
        assertEquals(
            "Ingresa un correo válido",
            LoginValidator.validarCorreo("correoIncorrecto")
        )
    }

    @Test
    fun correoCorrectoEsValido() {
        assertNull(
            LoginValidator.validarCorreo("usuario@gmail.com")
        )
    }

    @Test
    fun contrasenaVaciaEsInvalida() {
        assertEquals(
            "Debes ingresar tu contraseña",
            LoginValidator.validarContrasena("")
        )
    }

    @Test
    fun contrasenaCortaEsInvalida() {
        assertEquals(
            "La contraseña debe tener al menos 6 caracteres",
            LoginValidator.validarContrasena("12345")
        )
    }

    @Test
    fun contrasenaDeSeisCaracteresEsValida() {
        assertNull(
            LoginValidator.validarContrasena("123456")
        )
    }

    @Test
    fun formularioCorrectoEsValido() {
        assertTrue(
            LoginValidator.validarFormulario(
                "usuario@gmail.com",
                "123456"
            )
        )
    }

    @Test
    fun formularioConCorreoIncorrectoEsInvalido() {
        assertFalse(
            LoginValidator.validarFormulario(
                "correoIncorrecto",
                "123456"
            )
        )
    }

    @Test
    fun formularioConContrasenaIncorrectaEsInvalido() {
        assertFalse(
            LoginValidator.validarFormulario(
                "usuario@gmail.com",
                "123"
            )
        )
    }
}