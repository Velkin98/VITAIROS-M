
package com.example.vitairos_m

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.screens.BienvenidaScreen
import com.example.vitairos_m.screens.LoginScreen
import com.example.vitairos_m.screens.HomeScreen
import com.example.vitairos_m.screens.RegistroEmocionScreen
import com.example.vitairos_m.screens.HistorialEmocionalScreen
import com.example.vitairos_m.screens.OrientacionScreen
import com.example.vitairos_m.screens.RecursosBienestarScreen
import com.example.vitairos_m.screens.PerfilScreen
import com.example.vitairos_m.screens.SolicitudHoraScreen
import com.example.vitairos_m.screens.MisSolicitudesScreen

import com.example.vitairos_m.viewmodel.LoginViewModel
import com.example.vitairos_m.ui.theme.VITAIROSMTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            VITAIROSMTheme {

                // Guarda la pantalla en la que estamos
                var pantalla by rememberSaveable {
                    mutableStateOf("bienvenida")
                }

                // ViewModel del inicio de sesión
                val loginViewModel: LoginViewModel = viewModel()

                // Si el login es correcto, entramos al Home
                LaunchedEffect(loginViewModel.loginExitoso) {
                    if (loginViewModel.loginExitoso) {
                        pantalla = "home"
                    }
                }

                // NUEVO: Control del botón Atrás de Android
                BackHandler(
                    enabled = pantalla != "bienvenida" &&
                            pantalla != "login" &&
                            pantalla != "home"
                ) {

                    pantalla = when (pantalla) {

                        // Desde mis solicitudes volvemos a solicitar hora
                        "mis_solicitudes" -> "solicitud_hora"

                        // Desde solicitar hora volvemos a orientación
                        "solicitud_hora" -> "orientacion"

                        // Desde estas pantallas volvemos al Home
                        "registro_emocion",
                        "historial",
                        "orientacion",
                        "recursos",
                        "perfil" -> "home"

                        else -> "home"
                    }
                }

                // Navegación entre pantallas
                when (pantalla) {

                    // Pantalla de bienvenida
                    "bienvenida" -> {
                        BienvenidaScreen(
                            onComenzar = {
                                pantalla = "login"
                            }
                        )
                    }

                    // Inicio de sesión
                    "login" -> {
                        LoginScreen(
                            loginViewModel = loginViewModel
                        )
                    }

                    // Pantalla principal
                    "home" -> {
                        HomeScreen(
                            onRegistrarEmocion = {
                                pantalla = "registro_emocion"
                            },
                            onVerHistorial = {
                                pantalla = "historial"
                            },
                            onOrientacion = {
                                pantalla = "orientacion"
                            },
                            onRecursos = {
                                pantalla = "recursos"
                            },
                            onPerfil = {
                                pantalla = "perfil"
                            }
                        )
                    }

                    // Registrar una emoción
                    "registro_emocion" -> {
                        RegistroEmocionScreen(
                            onVolver = {
                                pantalla = "home"
                            }
                        )
                    }

                    // Historial emocional
                    "historial" -> {
                        HistorialEmocionalScreen(
                            onVolver = {
                                pantalla = "home"
                            }
                        )
                    }

                    // Orientación y apoyo
                    "orientacion" -> {
                        OrientacionScreen(
                            onVolver = {
                                pantalla = "home"
                            },
                            onSolicitarHora = {
                                pantalla = "solicitud_hora"
                            }
                        )
                    }

                    // Solicitud de atención profesional
                    "solicitud_hora" -> {
                        SolicitudHoraScreen(
                            onVolver = {
                                pantalla = "orientacion"
                            },
                            onVerSolicitudes = {
                                pantalla = "mis_solicitudes"
                            }
                        )
                    }

                    // Historial de solicitudes
                    "mis_solicitudes" -> {
                        MisSolicitudesScreen(
                            onVolver = {
                                pantalla = "solicitud_hora"
                            }
                        )
                    }

                    // Recursos de bienestar
                    "recursos" -> {
                        RecursosBienestarScreen(
                            onVolver = {
                                pantalla = "home"
                            }
                        )
                    }

                    // Mi perfil
                    "perfil" -> {
                        PerfilScreen(
                            onVolver = {
                                pantalla = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}
