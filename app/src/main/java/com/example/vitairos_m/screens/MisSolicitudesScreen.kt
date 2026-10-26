
package com.example.vitairos_m.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.model.SolicitudHora
import com.example.vitairos_m.viewmodel.SolicitudHoraViewModel

@Composable
fun MisSolicitudesScreen(
    onVolver: () -> Unit,
    solicitudHoraViewModel: SolicitudHoraViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    // Recuperamos las solicitudes guardadas en Room
    val solicitudes by solicitudHoraViewModel.solicitudes
        .collectAsState()

    // Solicitud seleccionada para eliminar
    var solicitudEliminar by remember {
        mutableStateOf<SolicitudHora?>(null)
    }

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
