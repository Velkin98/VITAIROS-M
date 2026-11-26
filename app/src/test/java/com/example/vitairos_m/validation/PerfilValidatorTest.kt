package com.example.vitairos_m.validation

import org.junit.Assert.*
import org.junit.Test

class PerfilValidatorTest {

    @Test
    fun nombreVacioEsInvalido() {
        assertEquals(
            "Debes ingresar tu nombre",
            PerfilValidator.validarNombre("")
        )
    }

    @Test
    fun nombreConEspaciosEsInvalido() {
        assertNotNull(
            PerfilValidator.validarNombre("   ")
        )
    }

    @Test
    fun nombreCortoEsInvalido() {
        assertEquals(
            "El nombre debe tener al menos 2 caracteres",
            PerfilValidator.validarNombre("A")
        )
    }

    @Test
    fun nombreCorrectoEsValido() {
        assertNull(
            PerfilValidator.validarNombre("Pedro")
        )
    }

    @Test
    fun nombreDeDosCaracteresEsValido() {
        assertNull(
            PerfilValidator.validarNombre("Al")
        )
    }

    @Test
    fun correoVacioEsInvalido() {
        assertEquals(
            "Debes ingresar tu correo electrónico",
            PerfilValidator.validarCorreo("")
        )
    }

    @Test
    fun correoIncorrectoEsInvalido() {
        assertEquals(
            "Ingresa un correo electrónico válido",
            PerfilValidator.validarCorreo("correoIncorrecto")
        )
    }

    @Test
    fun correoCorrectoEsValido() {
        assertNull(
            PerfilValidator.validarCorreo("usuario@gmail.com")
        )
    }

    @Test
    fun formularioCorrectoEsValido() {
        assertTrue(
            PerfilValidator.validarFormulario(
                "Pedro",
                "usuario@gmail.com"
            )
        )
    }

    @Test
    fun formularioConNombreIncorrectoEsInvalido() {
        assertFalse(
            PerfilValidator.validarFormulario(
                "",
                "usuario@gmail.com"
            )
        )
    }

    @Test
    fun formularioConCorreoIncorrectoEsInvalido() {
        assertFalse(
            PerfilValidator.validarFormulario(
                "Pedro",
                "correoIncorrecto"
            )
        )
    }
}