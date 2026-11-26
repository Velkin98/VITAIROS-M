package com.example.vitairos_m.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun preparar() {
        viewModel = LoginViewModel()
    }

    @Test
    fun estadoInicialCorrecto() {
        assertEquals("", viewModel.correo)
        assertEquals("", viewModel.contrasena)
        assertEquals("", viewModel.mensaje)
        assertNull(viewModel.errorCorreo)
        assertNull(viewModel.errorContrasena)
        assertFalse(viewModel.loginExitoso)
    }

    @Test
    fun actualizarCorreoCorrectamente() {
        viewModel.actualizarCorreo("usuario@gmail.com")

        assertEquals(
            "usuario@gmail.com",
            viewModel.correo
        )
    }

    @Test
    fun actualizarContrasenaCorrectamente() {
        viewModel.actualizarContrasena("123456")

        assertEquals("123456", viewModel.contrasena)
    }

    @Test
    fun loginConDatosValidos() {
        viewModel.actualizarCorreo("usuario@gmail.com")
        viewModel.actualizarContrasena("123456")

        viewModel.validarLogin()

        assertTrue(viewModel.loginExitoso)
        assertNull(viewModel.errorCorreo)
        assertNull(viewModel.errorContrasena)

        assertEquals(
            "Datos válidos. Inicio de sesión simulado",
            viewModel.mensaje
        )
    }

    @Test
    fun loginConCamposVacios() {
        viewModel.validarLogin()

        assertFalse(viewModel.loginExitoso)
        assertNotNull(viewModel.errorCorreo)
        assertNotNull(viewModel.errorContrasena)

        assertEquals(
            "Revisa los campos marcados.",
            viewModel.mensaje
        )
    }

    @Test
    fun loginConCorreoInvalido() {
        viewModel.actualizarCorreo("correoIncorrecto")
        viewModel.actualizarContrasena("123456")

        viewModel.validarLogin()

        assertFalse(viewModel.loginExitoso)
        assertNotNull(viewModel.errorCorreo)
        assertNull(viewModel.errorContrasena)
    }

    @Test
    fun loginConContrasenaInvalida() {
        viewModel.actualizarCorreo("usuario@gmail.com")
        viewModel.actualizarContrasena("123")

        viewModel.validarLogin()

        assertFalse(viewModel.loginExitoso)
        assertNull(viewModel.errorCorreo)
        assertNotNull(viewModel.errorContrasena)
    }

    @Test
    fun actualizarCorreoLimpiaError() {
        viewModel.validarLogin()

        viewModel.actualizarCorreo("usuario@gmail.com")

        assertNull(viewModel.errorCorreo)
        assertEquals("", viewModel.mensaje)
        assertFalse(viewModel.loginExitoso)
    }

    @Test
    fun actualizarContrasenaLimpiaError() {
        viewModel.validarLogin()

        viewModel.actualizarContrasena("123456")

        assertNull(viewModel.errorContrasena)
        assertEquals("", viewModel.mensaje)
        assertFalse(viewModel.loginExitoso)
    }

    @Test
    fun modificarDatosReiniciaLoginExitoso() {
        viewModel.actualizarCorreo("usuario@gmail.com")
        viewModel.actualizarContrasena("123456")
        viewModel.validarLogin()

        assertTrue(viewModel.loginExitoso)

        viewModel.actualizarCorreo("otro@gmail.com")

        assertFalse(viewModel.loginExitoso)
        assertEquals("", viewModel.mensaje)

        viewModel.validarLogin()

        assertTrue(viewModel.loginExitoso)

        viewModel.actualizarContrasena("654321")

        assertFalse(viewModel.loginExitoso)
        assertEquals("", viewModel.mensaje)
    }
}