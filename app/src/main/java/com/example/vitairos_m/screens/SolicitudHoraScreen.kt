
package com.example.vitairos_m.screens

import android.app.DatePickerDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.viewmodel.SolicitudHoraViewModel

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SolicitudHoraScreen(
    onVolver: () -> Unit,
    onVerSolicitudes: () -> Unit,
    solicitudHoraViewModel: SolicitudHoraViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    val context = LocalContext.current

    // Datos seleccionados por el usuario
    var profesionalSeleccionado by rememberSaveable {
        mutableStateOf("")
    }

    var fechaSeleccionada by rememberSaveable {
        mutableStateOf("")
    }

    var horaSeleccionada by rememberSaveable {
        mutableStateOf("")
    }

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    var menuExpandido by remember {
        mutableStateOf(false)
    }

    // Datos de la solicitud enviada
    var profesionalConfirmado by remember {
        mutableStateOf("")
    }

    var fechaConfirmada by remember {
        mutableStateOf("")
    }

    var horaConfirmada by remember {
        mutableStateOf("")
    }

    val profesionales = listOf(
        "Psicólogo/a de orientación",
        "Profesional de apoyo emocional",
        "Orientador/a de bienestar"
    )

    val horarios = listOf(
        "09:00",
        "10:00",
        "11:00",
        "15:00",
        "16:00"
    )

    // Calendario para seleccionar la fecha
    val calendario = Calendar.getInstance()

    val selectorFecha = DatePickerDialog(
        context,
        { _, anio, mes, dia ->

            val fecha = Calendar.getInstance()
            fecha.set(anio, mes, dia)

            fechaSeleccionada = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(fecha.time)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    selectorFecha.datePicker.minDate =
        Calendar.getInstance().timeInMillis

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = onVolver
        ) {
            Text(
                text = "← Volver al inicio",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Solicitar atención profesional",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Selecciona un profesional, una fecha y un horario.",
            fontSize = 16.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Seleccionar profesional
        Text(
            text = "Profesional",
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {

            OutlinedButton(
                onClick = {
                    menuExpandido = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = profesionalSeleccionado.ifBlank {
                        "Seleccionar profesional"
                    },
                    color = azul
                )
            }

            DropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = {
                    menuExpandido = false
                }
            ) {

                profesionales.forEach { profesional ->

                    DropdownMenuItem(
                        text = {
                            Text(profesional)
                        },
                        onClick = {
                            profesionalSeleccionado = profesional
                            menuExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Seleccionar fecha
        Text(
            text = "Fecha de atención",
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                selectorFecha.show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = fechaSeleccionada.ifBlank {
                    "Seleccionar fecha"
                },
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Seleccionar horario
        Text(
            text = "Horario (simulado)",
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(12.dp))

        horarios.forEach { horario ->

            OutlinedButton(
                onClick = {
                    horaSeleccionada = horario
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor =
                        if (horaSeleccionada == horario) {
                            verde
                        } else {
                            Color.White
                        }
                )
            ) {
                Text(
                    text = horario,
                    color = azul
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirmar y guardar solicitud
        Button(
            onClick = {

                profesionalConfirmado = profesionalSeleccionado
                fechaConfirmada = fechaSeleccionada
                horaConfirmada = horaSeleccionada

                // Guardamos la solicitud en Room
                solicitudHoraViewModel.guardarSolicitud(
                    profesional = profesionalSeleccionado,
                    fecha = fechaSeleccionada,
                    hora = horaSeleccionada
                )

                mostrarConfirmacion = true

            },
            enabled =
                profesionalSeleccionado.isNotBlank() &&
                        fechaSeleccionada.isNotBlank() &&
                        horaSeleccionada.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = verde
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Confirmar solicitud",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // NUEVO BOTÓN: Ver historial de solicitudes
        OutlinedButton(
            onClick = onVerSolicitudes,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Ver mis solicitudes",
                color = azul,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Esta función es una simulación académica. " +
                    "No genera reservas reales ni contacta " +
                    "a profesionales de salud mental.",
            fontSize = 12.sp,
            color = Color.DarkGray
        )
    }

    // Ventana de confirmación
    if (mostrarConfirmacion) {

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },
            title = {
                Text("Solicitud simulada enviada")
            },
            text = {
                Text(
                    "Se ha enviado tu solicitud de prueba " +
                            "para guardarla en el dispositivo.\n\n" +
                            "Profesional: $profesionalConfirmado\n" +
                            "Fecha: $fechaConfirmada\n" +
                            "Hora: $horaConfirmada\n\n" +
                            "Esta solicitud es solamente una simulación. " +
                            "No se ha reservado una atención real."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false

                        // Limpiamos el formulario
                        profesionalSeleccionado = ""
                        fechaSeleccionada = ""
                        horaSeleccionada = ""
                    }
                ) {
                    Text(
                        text = "Entendido",
                        color = verde
                    )
                }
            }
        )
    }
}
