
package com.example.vitairos_m.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// Mensaje que aparece en el chat
data class MensajeOrientacion(
    val texto: String,
    val esUsuario: Boolean
)

@Composable
fun OrientacionScreen(
    onVolver: () -> Unit,
    onSolicitarHora: () -> Unit
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    // Mensajes de la conversación
    val mensajes = remember {
        mutableStateListOf(
            MensajeOrientacion(
                texto = "¡Hola! 💚 Bienvenido a VITAIROS-M. " +
                        "Estoy aquí para entregarte orientación y apoyo. " +
                        "¿Cómo te has sentido últimamente?",
                esUsuario = false
            )
        )
    }

    // Opciones que puede seleccionar el usuario
    var opcionesActuales by remember {
        mutableStateOf(
            listOf(
                "Me siento estresado",
                "Me siento triste",
                "Me siento ansioso",
                "Necesito hablar con alguien"
            )
        )
    }

    val estadoLista = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Función para procesar cada respuesta
    fun seleccionarOpcion(opcion: String) {

        // Agregamos lo que seleccionó el usuario
        mensajes.add(
            MensajeOrientacion(
                texto = opcion,
                esUsuario = true
            )
        )

        // Respuesta de orientación
        val respuesta = when (opcion) {

            "Me siento estresado" ->
                "Entiendo que puedas sentirte estresado. " +
                        "Puedes intentar hacer una pausa, respirar " +
                        "lentamente y descansar unos minutos. " +
                        "¿Te gustaría probar un ejercicio?"

            "Me siento triste" ->
                "Gracias por compartir cómo te sientes. " +
                        "Está bien tener días difíciles. " +
                        "Hablar con alguien de confianza y dedicarte " +
                        "un momento de cuidado personal puede ayudarte. " +
                        "¿Qué te gustaría hacer?"

            "Me siento ansioso" ->
                "Sentir ansiedad puede ser incómodo. " +
                        "Podemos intentar un ejercicio sencillo " +
                        "para concentrarnos en el presente. " +
                        "¿Te gustaría intentarlo?"

            "Necesito hablar con alguien" ->
                "Buscar apoyo es una buena decisión. " +
                        "Puedes comunicarte con alguien de confianza " +
                        "o solicitar orientación de un profesional " +
                        "de salud mental."

            "Quiero hacer un ejercicio" ->
                "Probemos una respiración tranquila. 🌿\n\n" +
                        "1. Siéntate en una posición cómoda.\n" +
                        "2. Inhala suavemente durante 4 segundos.\n" +
                        "3. Exhala lentamente durante 6 segundos.\n" +
                        "4. Repite unas cuantas veces, sin forzarte.\n\n" +
                        "Si te sientes incómodo, puedes detenerte."

            "Quiero un consejo" ->
                "Puedes comenzar con algo pequeño: " +
                        "descansar unos minutos, tomar agua, " +
                        "salir a caminar o conversar con alguien " +
                        "de confianza. Elige lo que te resulte cómodo."

            "Quiero hablar con alguien" ->
                "Puedes contactar a una persona de confianza " +
                        "y contarle cómo te sientes. " +
                        "Si necesitas apoyo más especializado, " +
                        "considera consultar a un profesional " +
                        "de salud mental."

            "Volver al inicio de la orientación" ->
                "Claro 💚. ¿En qué necesitas apoyo hoy?"

            else ->
                "Estoy aquí para acompañarte y ofrecerte " +
                        "orientación general."
        }

        mensajes.add(
            MensajeOrientacion(
                texto = respuesta,
                esUsuario = false
            )
        )

        // Cambiamos las opciones según la conversación
        opcionesActuales = when (opcion) {

            "Me siento estresado",
            "Me siento ansioso" -> listOf(
                "Quiero hacer un ejercicio",
                "Quiero un consejo",
                "Quiero hablar con alguien",
                "Volver al inicio de la orientación"
            )

            "Me siento triste" -> listOf(
                "Quiero un consejo",
                "Quiero hablar con alguien",
                "Volver al inicio de la orientación"
            )

            "Necesito hablar con alguien" -> listOf(
                "Quiero hablar con alguien",
                "Volver al inicio de la orientación"
            )

            "Volver al inicio de la orientación" -> listOf(
                "Me siento estresado",
                "Me siento triste",
                "Me siento ansioso",
                "Necesito hablar con alguien"
            )

            else -> listOf(
                "Quiero hacer un ejercicio",
                "Quiero un consejo",
                "Quiero hablar con alguien",
                "Volver al inicio de la orientación"
            )
        }

        // Bajamos automáticamente al último mensaje
        coroutineScope.launch {
            estadoLista.animateScrollToItem(
                mensajes.lastIndex
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onVolver
        ) {
            Text(
                text = "← Volver al inicio",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Orientación y apoyo",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Un espacio para conversar sobre cómo te sientes.",
            fontSize = 15.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Área de mensajes
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            LazyColumn(
                state = estadoLista,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(mensajes) { mensaje ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            if (mensaje.esUsuario) {
                                Arrangement.End
                            } else {
                                Arrangement.Start
                            }
                    ) {

                        Card(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (mensaje.esUsuario) {
                                        azul
                                    } else {
                                        fondo
                                    }
                            )
                        ) {

                            Text(
                                text = mensaje.texto,
                                modifier = Modifier.padding(14.dp),
                                fontSize = 15.sp,
                                color =
                                    if (mensaje.esUsuario) {
                                        Color.White
                                    } else {
                                        azul
                                    }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Opciones y botón de solicitud
        // Esta sección se puede desplazar si falta espacio
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 340.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Selecciona una opción:",
                fontWeight = FontWeight.Bold,
                color = azul
            )

            opcionesActuales.forEach { opcion ->

                OutlinedButton(
                    onClick = {
                        seleccionarOpcion(opcion)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = verde
                    )
                ) {

                    Text(
                        text = opcion,
                        color = azul,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Esta orientación es informativa y " +
                        "no reemplaza la atención de un profesional " +
                        "de salud mental. Si estás en peligro inmediato, " +
                        "contacta a los servicios de emergencia.",
                fontSize = 12.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nuevo botón para solicitar atención
            Button(
                onClick = onSolicitarHora,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Solicitar atención profesional",
                    color = azul
                )
            }
        }
    }
}
