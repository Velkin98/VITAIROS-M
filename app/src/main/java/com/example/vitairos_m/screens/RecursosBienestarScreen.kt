package com.example.vitairos_m.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.vitairos_m.network.FraseRetrofitClient
import kotlinx.coroutines.CancellationException

@Composable
fun RecursosBienestarScreen(
    onVolver: () -> Unit
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    val context = LocalContext.current

    var recursoSeleccionado by remember {
        mutableStateOf("")
    }

    // Estados de la API externa
    var frase by remember {
        mutableStateOf("")
    }

    var autor by remember {
        mutableStateOf("")
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var errorApi by remember {
        mutableStateOf(false)
    }

    // Consumimos la API externa mediante Retrofit
    LaunchedEffect(Unit) {

        try {

            val respuesta = FraseRetrofitClient
                .fraseApi
                .obtenerFrase()
                .firstOrNull()

            if (respuesta != null) {

                frase = respuesta.frase
                autor = respuesta.autor

            } else {

                errorApi = true
            }

        } catch (e: CancellationException) {

            throw e

        } catch (e: Exception) {

            errorApi = true

        } finally {

            cargando = false
        }
    }

    val recursos = listOf(
        "Respiración consciente",
        "Descanso y sueño",
        "Actividad física",
        "Hablar con alguien"
    )

    val descripcion = when (recursoSeleccionado) {

        "Respiración consciente" ->
            "Busca un lugar tranquilo. Inhala suavemente durante 4 segundos y exhala durante 6 segundos. Repite algunas veces sin forzar tu respiración."

        "Descanso y sueño" ->
            "Intenta mantener horarios regulares para dormir, reducir las pantallas antes de acostarte y descansar lo suficiente."

        "Actividad física" ->
            "Caminar, andar en bicicleta o realizar movimientos suaves puede ayudarte a despejar la mente y mejorar tu bienestar."

        "Hablar con alguien" ->
            "Compartir lo que sientes con una persona de confianza puede ayudarte a sentirte acompañado. También puedes buscar orientación profesional."

        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

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
            text = "Recursos de bienestar",
            fontSize = 26.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona una actividad para conocer sus beneficios.",
            fontSize = 16.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(24.dp))

        recursos.forEach { recurso ->

            Button(
                onClick = {
                    recursoSeleccionado = recurso
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verde
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = recurso,
                    color = azul
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (descripcion.isNotEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = recursoSeleccionado,
                        fontSize = 20.sp,
                        color = azul
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = descripcion,
                        fontSize = 16.sp,
                        color = azul
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Información obtenida desde la API externa
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Frase motivacional",
                    fontSize = 20.sp,
                    color = azul
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {

                    cargando -> {

                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            color = verde
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Cargando frase...",
                            color = azul
                        )
                    }

                    errorApi -> {

                        Text(
                            text = "No se pudo obtener la frase. Verifica tu conexión a Internet.",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }

                    else -> {

                        Text(
                            text = "\"$frase\"",
                            fontSize = 16.sp,
                            color = azul
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "— $autor",
                            fontSize = 14.sp,
                            color = azul
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {

                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://zenquotes.io/")
                        )

                        context.startActivity(intent)
                    }
                ) {

                    Text(
                        text = "Inspirational quotes provided by ZenQuotes API",
                        fontSize = 12.sp,
                        color = azul
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}