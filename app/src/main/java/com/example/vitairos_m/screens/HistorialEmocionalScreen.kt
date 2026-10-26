
package com.example.vitairos_m.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.model.Emocion
import com.example.vitairos_m.viewmodel.EmocionViewModel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialEmocionalScreen(
    onVolver: () -> Unit,
    emocionViewModel: EmocionViewModel = viewModel()
) {

    val emociones = emocionViewModel.emociones.collectAsState()

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    // Registro seleccionado para editar
    var emocionEditar by remember {
        mutableStateOf<Emocion?>(null)
    }

    // Registro seleccionado para eliminar
    var emocionEliminar by remember {
        mutableStateOf<Emocion?>(null)
    }

    // Datos que se pueden modificar
    var nuevoNombre by remember {
        mutableStateOf("")
    }

    var nuevaCategoria by remember {
        mutableStateOf("")
    }

    var nuevaEnergia by remember {
        mutableIntStateOf(0)
    }

    var nuevaNota by remember {
        mutableStateOf("")
    }

    // Resumen de las emociones
    val resumenEmociones = emociones.value
        .groupingBy { it.nombre }
        .eachCount()
        .toList()
        .sortedByDescending { it.second }

    val totalEmociones = emociones.value.size

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
                    text = "← Volver al inicio",
                    color = azul
                )
            }
        }

        item {

            Text(
                text = "Historial emocional",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = azul
            )
        }

        item {

            Text(
                text = "Tus emociones registradas",
                fontSize = 16.sp,
                color = azul
            )
        }

        if (emociones.value.isEmpty()) {

            item {

                Text(
                    text = "Todavía no tienes emociones registradas.",
                    color = azul
                )
            }

        } else {

            // Resumen emocional
            item {

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
                            text = "Mi resumen emocional",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Total de registros: $totalEmociones",
                            fontSize = 16.sp,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        resumenEmociones.forEach { (nombre, cantidad) ->

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = nombre,
                                    fontSize = 16.sp,
                                    color = azul
                                )

                                Text(
                                    text = "$cantidad veces",
                                    fontSize = 14.sp,
                                    color = azul
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            val porcentaje =
                                cantidad.toFloat() / totalEmociones

                            LinearProgressIndicator(
                                progress = { porcentaje },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp),
                                color = verde,
                                trackColor = Color(0xFFE0E7EB)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Mis registros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = azul
                )
            }

            // Lista de registros emocionales
            items(
                items = emociones.value,
                key = { it.id }
            ) { emocion ->

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
                            text = emocion.nombre,
                            fontSize = 20.sp,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val fechaFormateada =
                            SimpleDateFormat(
                                "dd/MM/yyyy HH:mm",
                                Locale.getDefault()
                            ).format(Date(emocion.fecha))

                        Text(
                            text = fechaFormateada,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        // Categoría
                        if (!emocion.categoria.isNullOrBlank()) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Categoría: ${emocion.categoria}",
                                fontSize = 15.sp,
                                color = azul
                            )
                        }

                        // Energía
                        emocion.energia?.let { energia ->

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Nivel de energía: $energia de 5",
                                fontSize = 15.sp,
                                color = azul
                            )
                        }

                        // Nota personal
                        if (!emocion.nota.isNullOrBlank()) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Nota: ${emocion.nota}",
                                fontSize = 15.sp,
                                color = azul
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            // Editar registro
                            Button(
                                onClick = {

                                    emocionEditar = emocion

                                    nuevoNombre = emocion.nombre

                                    nuevaCategoria =
                                        emocion.categoria ?: ""

                                    nuevaEnergia =
                                        emocion.energia ?: 0

                                    nuevaNota =
                                        emocion.nota ?: ""
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = verde
                                )
                            ) {
                                Text(
                                    text = "Editar",
                                    color = azul
                                )
                            }

                            // Eliminar registro
                            OutlinedButton(
                                onClick = {
                                    emocionEliminar = emocion
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Eliminar",
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Ventana para editar el registro
    emocionEditar?.let { emocion ->

        AlertDialog(
            onDismissRequest = {
                emocionEditar = null
            },
            title = {
                Text("Editar registro emocional")
            },
            text = {

                Column(
                    modifier = Modifier
                        .heightIn(max = 420.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Emoción",
                        fontWeight = FontWeight.Bold,
                        color = azul
                    )

                    val opcionesEmocion = listOf(
                        "😊 Feliz",
                        "😌 Tranquilo",
                        "😟 Ansioso",
                        "😢 Triste"
                    )

                    opcionesEmocion.forEach { opcion ->

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = nuevoNombre == opcion,
                                onClick = {
                                    nuevoNombre = opcion
                                }
                            )

                            Text(
                                text = opcion,
                                color = azul
                            )
                        }
                    }

                    HorizontalDivider()

                    // Categoría
                    Text(
                        text = "Categoría (opcional)",
                        fontWeight = FontWeight.Bold,
                        color = azul
                    )

                    val categorias = listOf(
                        "Sin categoría",
                        "Estudios",
                        "Trabajo",
                        "Familia",
                        "Personal",
                        "Otro"
                    )

                    var menuExpandido by remember {
                        mutableStateOf(false)
                    }

                    Box {

                        OutlinedButton(
                            onClick = {
                                menuExpandido = true
                            }
                        ) {
                            Text(
                                text = nuevaCategoria.ifBlank {
                                    "Sin categoría"
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

                            categorias.forEach { categoria ->

                                DropdownMenuItem(
                                    text = {
                                        Text(categoria)
                                    },
                                    onClick = {

                                        nuevaCategoria =
                                            if (
                                                categoria ==
                                                "Sin categoría"
                                            ) {
                                                ""
                                            } else {
                                                categoria
                                            }

                                        menuExpandido = false
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // Nivel de energía
                    Text(
                        text = "Nivel de energía (opcional)",
                        fontWeight = FontWeight.Bold,
                        color = azul
                    )

                    Text(
                        text = if (nuevaEnergia == 0) {
                            "Sin seleccionar"
                        } else {
                            "$nuevaEnergia de 5"
                        },
                        color = azul
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceEvenly
                    ) {

                        (1..5).forEach { nivel ->

                            Button(
                                onClick = {
                                    nuevaEnergia =
                                        if (nuevaEnergia == nivel) {
                                            0
                                        } else {
                                            nivel
                                        }
                                },
                                modifier = Modifier.size(42.dp),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (nuevaEnergia == nivel) {
                                            verde
                                        } else {
                                            azul
                                        }
                                )
                            ) {
                                Text(
                                    text = nivel.toString(),
                                    color =
                                        if (nuevaEnergia == nivel) {
                                            azul
                                        } else {
                                            Color.White
                                        }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // Nota personal
                    Text(
                        text = "Nota personal (opcional)",
                        fontWeight = FontWeight.Bold,
                        color = azul
                    )

                    OutlinedTextField(
                        value = nuevaNota,
                        onValueChange = {
                            nuevaNota = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Escribe una nota")
                        },
                        minLines = 2,
                        maxLines = 4
                    )
                }
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        if (nuevoNombre.isNotBlank()) {

                            emocionViewModel.actualizarEmocion(
                                emocion = emocion,
                                nuevoNombre = nuevoNombre,
                                nuevaCategoria = nuevaCategoria
                                    .ifBlank { null },
                                nuevaEnergia = nuevaEnergia
                                    .takeIf { it > 0 },
                                nuevaNota = nuevaNota.trim()
                                    .ifBlank { null }
                            )

                            emocionEditar = null
                        }
                    },
                    enabled = nuevoNombre.isNotBlank()
                ) {
                    Text(
                        text = "Guardar",
                        color = verde
                    )
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        emocionEditar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Ventana para confirmar eliminación
    emocionEliminar?.let { emocion ->

        AlertDialog(
            onDismissRequest = {
                emocionEliminar = null
            },
            title = {
                Text("Eliminar emoción")
            },
            text = {
                Text(
                    "¿Estás seguro de que quieres eliminar " +
                            "el registro '${emocion.nombre}'? " +
                            "Esta acción no se puede deshacer."
                )
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        emocionViewModel.eliminarEmocion(emocion)

                        emocionEliminar = null
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
                        emocionEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
