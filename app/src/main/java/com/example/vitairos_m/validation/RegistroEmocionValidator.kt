
package com.example.vitairos_m.validation

object RegistroEmocionValidator {

    const val MAX_CARACTERES_NOTA = 300

    fun validarEmocion(emocion: String): String? {
        return if (emocion.isBlank()) {
            "Debes seleccionar una emoción antes de guardar."
        } else {
            null
        }
    }

    fun validarNota(nota: String): String? {
        return if (nota.length > MAX_CARACTERES_NOTA) {
            "La nota no puede superar los 300 caracteres."
        } else {
            null
        }
    }

    fun validarFormulario(
        emocion: String,
        nota: String
    ): Boolean {
        return validarEmocion(emocion) == null &&
                validarNota(nota) == null
    }
}
