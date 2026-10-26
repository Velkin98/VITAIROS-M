
package com.example.vitairos_m.screens

import android.graphics.BitmapFactory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.viewmodel.PerfilViewModel
import com.example.vitairos_m.viewmodel.EmocionViewModel

@Composable
fun HomeScreen(
    onRegistrarEmocion: () -> Unit,
    onVerHistorial: () -> Unit,
    onOrientacion: () -> Unit,
    onRecursos: () -> Unit,
    onPerfil: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel(),
    emocionViewModel: EmocionViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)

    // Recuperamos los datos del perfil
    val perfilGuardado by perfilViewModel.perfil.collectAsState()
    val rutaFotoGuardada by perfilViewModel.fotoGuardada.collectAsState()

    // Recuperamos las emociones registradas en Room
    val emociones by emocionViewModel.emociones.collectAsState()

    // Total de registros emocionales
    val totalEmociones = emociones.size

    // Nombre del usuario
    val nombreUsuario = perfilGuardado?.nombre
        ?.takeIf { it.isNotBlank() }
        ?: "Usuario"

    // Recuperamos la foto de perfil
    val bitmap = remember(rutaFotoGuardada) {
        try {
            rutaFotoGuardada?.let { ruta ->
                BitmapFactory.decodeFile(ruta)
            }
        } catch (e: Exception) {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "VITAIROS-M",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta de bienvenida personalizada
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = azul
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (bitmap != null) {

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "¡Hola, $nombreUsuario!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Bienvenido a tu espacio de bienestar",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Resumen emocional
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Mi actividad emocional",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = azul
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$totalEmociones",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = azul
                )

                Text(
                    text = if (totalEmociones == 1) {
                        "Emoción registrada"
                    } else {
                        "Emociones registradas"
                    },
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Cada registro te ayuda a conocer mejor " +
                            "cómo te has sentido.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onVerHistorial
                ) {
                    Text(
                        text = "Ver mi historial emocional",
                        color = azul,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "¿Qué te gustaría hacer hoy?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botones originales: conservamos la navegación

        BotonInicio(
            texto = "Registrar mi emoción",
            color = verde,
            onClick = onRegistrarEmocion
        )

        BotonInicio(
            texto = "Historial emocional",
            color = verde,
            onClick = onVerHistorial
        )

        BotonInicio(
            texto = "Orientación y apoyo",
            color = verde,
            onClick = onOrientacion
        )

        BotonInicio(
            texto = "Recursos de bienestar",
            color = verde,
            onClick = onRecursos
        )

        BotonInicio(
            texto = "Mi perfil",
            color = verde,
            onClick = onPerfil
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// Botón reutilizable de la pantalla principal
@Composable
fun BotonInicio(
    texto: String,
    color: Color,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(55.dp)
    ) {

        Text(
            text = texto,
            color = Color(0xFF000E2E),
            fontSize = 16.sp
        )
    }
}
