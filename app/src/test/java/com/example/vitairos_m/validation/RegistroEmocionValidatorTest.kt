package com.example.vitairos_m.validation

import org.junit.Assert.*
import org.junit.Test

class RegistroEmocionValidatorTest {

    @Test
    fun emocionVaciaEsInvalida() {
        assertEquals(
            "Debes seleccionar una emoción antes de guardar.",
            RegistroEmocionValidator.validarEmocion("")
        )
    }

    @Test
    fun emocionConEspaciosEsInvalida() {
        assertNotNull(
            RegistroEmocionValidator.validarEmocion("   ")
        )
    }

    @Test
    fun emocionSeleccionadaEsValida() {
        assertNull(
            RegistroEmocionValidator.validarEmocion("Feliz")
        )
    }

    @Test
    fun notaVaciaEsValida() {
        assertNull(
            RegistroEmocionValidator.validarNota("")
        )
    }

    @Test
    fun notaDentroDelLimiteEsValida() {
        assertNull(
            RegistroEmocionValidator.validarNota("Me siento bien")
        )
    }

    @Test
    fun notaDe300CaracteresEsValida() {
        assertNull(
            RegistroEmocionValidator.validarNota("a".repeat(300))
        )
    }

    @Test
    fun notaDe301CaracteresEsInvalida() {
        assertEquals(
            "La nota no puede superar los 300 caracteres.",
            RegistroEmocionValidator.validarNota("a".repeat(301))
        )
    }

    @Test
    fun formularioCompletoEsValido() {
        assertTrue(
            RegistroEmocionValidator.validarFormulario(
                "Feliz",
                "Hoy fue un buen día"
            )
        )
    }

    @Test
    fun formularioSinEmocionEsInvalido() {
        assertFalse(
            RegistroEmocionValidator.validarFormulario(
                "",
                "Nota de prueba"
            )
        )
    }

    @Test
    fun formularioConNotaExtensaEsInvalido() {
        assertFalse(
            RegistroEmocionValidator.validarFormulario(
                "Feliz",
                "a".repeat(301)
            )
        )
    }
}