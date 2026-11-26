package com.example.vitairos_m.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.model.SolicitudHora
import com.example.vitairos_m.viewmodel.SolicitudHoraViewModel

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MisSolicitudesScreen(
    onVolver: () -> Unit,
    solicitudHoraViewModel: SolicitudHoraViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    val context = LocalContext.current

    // Recuperamos las solicitudes del microservicio
    val solicitudes by solicitudHoraViewModel.solicitudes
        .collectAsState()

    LaunchedEffect(Unit) {
        solicitudHoraViewModel.cargarSolicitudes()
    }

    // Solicitud seleccionada para eliminar
    var solicitudEliminar by remember {
        mutableStateOf<SolicitudHora?>(null)
    }

    // Solicitud seleccionada para editar
    var solicitudEditar by remember {
        mutableStateOf<SolicitudHora?>(null)
    }

    var profesionalEditado by remember {
        mutableStateOf("")
    }

    var fechaEditada by remember {
        mutableStateOf("")
    }

    var horaEditada by remember {
        mutableStateOf("")
    }

    var menuProfesional by remember {
        mutableStateOf(false)
    }

    var menuHorario by remember {
        mutableStateOf(false)
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = onVolver
            ) {
                Text(
                    text = "← Volver",
                    color = azul
                )
            }
        }

        item {

            Text(
                text = "Mis solicitudes",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = azul
            )
        }

        item {

            Text(
                text = "Solicitudes de atención profesional guardadas",
                fontSize = 16.sp,
                color = azul
            )
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text = "Total de solicitudes: ${solicitudes.size}",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = azul
                )
            }
        }

        if (solicitudes.isEmpty()) {

            item {

                Text(
                    text = "Todavía no tienes solicitudes guardadas.",
                    color = azul
                )
            }

        } else {

            items(
                items = solicitudes,
                key = { it.id }
            ) { solicitud ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = solicitud.profesional,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Fecha: ${solicitud.fecha}",
                            fontSize = 15.sp,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Hora: ${solicitud.hora}",
                            fontSize = 15.sp,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Solicitud simulada (sin reserva real)",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón para editar solicitud
                        Button(
                            onClick = {

                                profesionalEditado =
                                    solicitud.profesional

                                fechaEditada = solicitud.fecha
                                horaEditada = solicitud.hora

                                menuProfesional = false
                                menuHorario = false

                                solicitudEditar = solicitud
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = verde
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = "Editar solicitud",
                                color = azul
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botón para eliminar solicitud
                        OutlinedButton(
                            onClick = {
                                solicitudEliminar = solicitud
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text = "Eliminar solicitud",
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    // Ventana para editar solicitud
    solicitudEditar?.let { solicitud ->

        AlertDialog(
            onDismissRequest = {
                solicitudEditar = null
            },
            title = {
                Text("Editar solicitud")
            },
            text = {

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Profesional",
                        fontWeight = FontWeight.Bold
                    )

                    Box {

                        OutlinedButton(
                            onClick = {
                                menuProfesional = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(profesionalEditado)
                        }

                        DropdownMenu(
                            expanded = menuProfesional,
                            onDismissRequest = {
                                menuProfesional = false
                            }
                        ) {

                            profesionales.forEach { profesional ->

                                DropdownMenuItem(
                                    text = {
                                        Text(profesional)
                                    },
                                    onClick = {
                                        profesionalEditado = profesional
                                        menuProfesional = false
                                    }
                                )
                            }
                        }
                    }

                    Text(
                        text = "Fecha",
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedButton(
                        onClick = {

                            val calendario = Calendar.getInstance()

                            val selectorFecha = DatePickerDialog(
                                context,
                                { _, anio, mes, dia ->

                                    val fecha = Calendar.getInstance()
                                    fecha.set(anio, mes, dia)

                                    fechaEditada = SimpleDateFormat(
                                        "dd/MM/yyyy",
                                        Locale.getDefault()
                                    ).format(fecha.time)
                                },
                                calendario.get(Calendar.YEAR),
                                calendario.get(Calendar.MONTH),
                                calendario.get(Calendar.DAY_OF_MONTH)
                            )

                            selectorFecha.datePicker.minDate =
                                System.currentTimeMillis()

                            selectorFecha.show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(fechaEditada)
                    }

                    Text(
                        text = "Horario",
                        fontWeight = FontWeight.Bold
                    )

                    Box {

                        OutlinedButton(
                            onClick = {
                                menuHorario = true
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(horaEditada)
                        }

                        DropdownMenu(
                            expanded = menuHorario,
                            onDismissRequest = {
                                menuHorario = false
                            }
                        ) {

                            horarios.forEach { horario ->

                                DropdownMenuItem(
                                    text = {
                                        Text(horario)
                                    },
                                    onClick = {
                                        horaEditada = horario
                                        menuHorario = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        solicitudHoraViewModel.actualizarSolicitud(
                            solicitud = solicitud,
                            profesional = profesionalEditado,
                            fecha = fechaEditada,
                            hora = horaEditada
                        )

                        solicitudEditar = null
                    },
                    enabled =
                        profesionalEditado.isNotBlank() &&
                                fechaEditada.isNotBlank() &&
                                horaEditada.isNotBlank()
                ) {

                    Text(
                        text = "Guardar cambios",
                        color = verde
                    )
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        solicitudEditar = null
                    }
                ) {

                    Text(
                        text = "Cancelar",
                        color = azul
                    )
                }
            }
        )
    }

    // Confirmación antes de eliminar
    solicitudEliminar?.let { solicitud ->

        AlertDialog(
            onDismissRequest = {
                solicitudEliminar = null
            },
            title = {
                Text("Eliminar solicitud")
            },
            text = {
                Text(
                    "¿Quieres eliminar esta solicitud simulada " +
                            "del ${solicitud.fecha} a las ${solicitud.hora}? " +
                            "Esta acción no se puede deshacer."
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        solicitudHoraViewModel.eliminarSolicitud(
                            solicitud
                        )

                        solicitudEliminar = null
                    }
                ) {

                    Text(
                        text = "Eliminar",
                        color = Color.Red
                    )
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        solicitudEliminar = null
                    }
                ) {

                    Text(
                        text = "Cancelar",
                        color = azul
                    )
                }
            }
        )
    }
}