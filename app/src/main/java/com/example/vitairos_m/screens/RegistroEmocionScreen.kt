
package com.example.vitairos_m.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.viewmodel.EmocionViewModel
import com.example.vitairos_m.validation.RegistroEmocionValidator

@Composable
fun RegistroEmocionScreen(
    onVolver: () -> Unit,
    emocionViewModel: EmocionViewModel = viewModel()
) {

    // Datos del formulario
    var emocionSeleccionada by rememberSaveable {
        mutableStateOf("")
    }

    var categoriaSeleccionada by rememberSaveable {
        mutableStateOf("")
    }

    var energiaSeleccionada by rememberSaveable {
        mutableIntStateOf(0)
    }

    var nota by rememberSaveable {
        mutableStateOf("")
    }

    var mensaje by rememberSaveable {
        mutableStateOf("")
    }

    var mostrarErrores by rememberSaveable {
        mutableStateOf(false)
    }

    var menuExpandido by remember {
        mutableStateOf(false)
    }

    // Colores de VITAIROS-M
    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)
    val rojo = MaterialTheme.colorScheme.error

    // Emociones disponibles
    val emociones = listOf(
        "😊 Feliz",
        "😌 Tranquilo",
        "😟 Ansioso",
        "😢 Triste"
    )

    // Categorías disponibles
    val categorias = listOf(
        "Estudios",
        "Trabajo",
        "Familia",
        "Personal",
        "Otro"
    )

    // Validaciones separadas de la interfaz
    val errorEmocion = if (mostrarErrores) {
        RegistroEmocionValidator.validarEmocion(
            emocionSeleccionada
        )
    } else {
        null
    }

    val errorNota = RegistroEmocionValidator.validarNota(nota)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // Volver al inicio
        TextButton(
            onClick = onVolver
        ) {
            Text(
                text = "← Volver al inicio",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Cómo te sientes hoy?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona una emoción",
            color = azul
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ANIMACIÓN 1: Tarjetas de emociones
        emociones.forEach { emocion ->

            val seleccionada =
                emocionSeleccionada == emocion

            // Animación del color
            val colorAnimado by animateColorAsState(
                targetValue = if (seleccionada) {
                    verde
                } else {
                    Color.White
                },
                animationSpec = tween(durationMillis = 300),
                label = "Color de emoción"
            )

            // Animación de elevación
            val elevacionAnimada by animateDpAsState(
                targetValue = if (seleccionada) {
                    8.dp
                } else {
                    2.dp
                },
                animationSpec = tween(durationMillis = 300),
                label = "Elevación de emoción"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        emocionSeleccionada = emocion
                        mensaje = ""
                    },
                colors = CardDefaults.cardColors(
                    containerColor = colorAnimado
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = elevacionAnimada
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = emocion,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(20.dp),
                    color = azul
                )
            }
        }

        // Error si no selecciona emoción
        if (errorEmocion != null) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "⚠️",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = errorEmocion,
                    color = rojo,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Categoría opcional
        Text(
            text = "Categoría (opcional)",
            fontSize = 18.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = {
                    menuExpandido = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = categoriaSeleccionada.ifBlank {
                        "Seleccionar categoría"
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

                DropdownMenuItem(
                    text = {
                        Text("Sin categoría")
                    },
                    onClick = {
                        categoriaSeleccionada = ""
                        menuExpandido = false
                    }
                )

                categorias.forEach { categoria ->

                    DropdownMenuItem(
                        text = {
                            Text(categoria)
                        },
                        onClick = {
                            categoriaSeleccionada = categoria
                            menuExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nivel de energía opcional
        Text(
            text = "Nivel de energía (opcional)",
            fontSize = 18.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (energiaSeleccionada == 0) {
                "Selecciona tu nivel de energía"
            } else {
                "Nivel seleccionado: $energiaSeleccionada de 5"
            },
            color = azul
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            (1..5).forEach { nivel ->

                val seleccionado =
                    energiaSeleccionada == nivel

                Button(
                    onClick = {
                        energiaSeleccionada =
                            if (seleccionado) 0 else nivel
                    },
                    modifier = Modifier.size(48.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (seleccionado) {
                            verde
                        } else {
                            azul
                        }
                    )
                ) {
                    Text(
                        text = nivel.toString(),
                        color = if (seleccionado) {
                            azul
                        } else {
                            Color.White
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nota personal opcional
        Text(
            text = "Nota personal (opcional)",
            fontSize = 18.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nota,
            onValueChange = {
                nota = it
                mensaje = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("¿Quieres contar algo sobre tu día?")
            },
            minLines = 3,
            maxLines = 5,
            isError = errorNota != null,
            supportingText = {
                Text(
                    text = errorNota
                        ?: "${nota.length}/${RegistroEmocionValidator.MAX_CARACTERES_NOTA} caracteres",
                    color = if (errorNota != null) {
                        rojo
                    } else {
                        Color.DarkGray
                    }
                )
            },
            trailingIcon = {
                if (errorNota != null) {
                    Text(
                        text = "⚠️",
                        fontSize = 20.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Guardar registro emocional
        Button(
            onClick = {

                mostrarErrores = true

                val formularioValido =
                    RegistroEmocionValidator.validarFormulario(
                        emocion = emocionSeleccionada,
                        nota = nota
                    )

                if (formularioValido) {

                    emocionViewModel.guardarEmocion(
                        nombre = emocionSeleccionada,
                        categoria = categoriaSeleccionada
                            .ifBlank { null },
                        energia = energiaSeleccionada
                            .takeIf { it > 0 },
                        nota = nota.trim()
                            .ifBlank { null }
                    )

                    mensaje = "Se solicitó guardar tu emoción"

                    // Limpiar formulario
                    emocionSeleccionada = ""
                    categoriaSeleccionada = ""
                    energiaSeleccionada = 0
                    nota = ""
                    mostrarErrores = false

                } else {

                    mensaje = "Revisa los campos marcados."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = verde
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Guardar mi emoción",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ANIMACIÓN 2: Mensajes de confirmación y error
        AnimatedVisibility(
            visible = mensaje.isNotEmpty(),
            enter = fadeIn(
                animationSpec = tween(300)
            ) + expandVertically(
                animationSpec = tween(300)
            ),
            exit = fadeOut(
                animationSpec = tween(300)
            ) + shrinkVertically(
                animationSpec = tween(300)
            )
        ) {

            Text(
                text = mensaje,
                color = if (mostrarErrores) {
                    rojo
                } else {
                    azul
                },
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
