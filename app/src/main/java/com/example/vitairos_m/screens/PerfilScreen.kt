
package com.example.vitairos_m.screens

import android.graphics.BitmapFactory
import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.viewmodel.PerfilViewModel
import com.example.vitairos_m.validation.PerfilValidator

@Composable
fun PerfilScreen(
    onVolver: () -> Unit,
    perfilViewModel: PerfilViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)
    val rojo = MaterialTheme.colorScheme.error

    val context = LocalContext.current

    // Recuperamos el perfil guardado en Room
    val perfilGuardado by perfilViewModel.perfil.collectAsState()

    // Recuperamos la foto guardada
    val rutaFotoGuardada by perfilViewModel.fotoGuardada.collectAsState()

    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    var mostrarErrores by remember {
        mutableStateOf(false)
    }

    // Imagen seleccionada temporalmente
    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Abre la galería y guarda la foto
    val selectorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {

            fotoUri = uri

            // Guardamos la foto permanentemente
            perfilViewModel.guardarFoto(uri)
        }
    }

    // Recuperamos nombre y correo desde Room
    LaunchedEffect(perfilGuardado) {

        perfilGuardado?.let { perfil ->

            nombre = perfil.nombre
            correo = perfil.correo
        }
    }

    // Recuperamos la imagen seleccionada o guardada
    val bitmap = remember(fotoUri, rutaFotoGuardada) {

        try {

            if (fotoUri != null) {

                context.contentResolver
                    .openInputStream(fotoUri!!)
                    ?.use { entrada ->
                        BitmapFactory.decodeStream(entrada)
                    }

            } else if (rutaFotoGuardada != null) {

                BitmapFactory.decodeFile(rutaFotoGuardada)

            } else {

                null
            }

        } catch (e: Exception) {

            null
        }
    }

    // Validaciones separadas de la interfaz
    val errorNombre = if (mostrarErrores) {
        PerfilValidator.validarNombre(nombre)
    } else {
        null
    }

    val errorCorreo = if (mostrarErrores) {
        PerfilValidator.validarCorreo(correo)
    } else {
        null
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
            text = "Mi perfil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = azul
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mostramos la foto de perfil
        if (bitmap != null) {

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botón para seleccionar una imagen
        Button(
            onClick = {
                selectorGaleria.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = verde
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Seleccionar foto de perfil",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (fotoUri != null || rutaFotoGuardada != null) {

            Text(
                text = "Foto de perfil seleccionada",
                fontSize = 14.sp,
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Completa tus datos personales",
            fontSize = 16.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Campo de nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                mensaje = ""
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorNombre != null,
            supportingText = {
                if (errorNombre != null) {
                    Text(
                        text = "⚠️ $errorNombre",
                        color = rojo
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                mensaje = ""
            },
            label = {
                Text("Correo electrónico")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = errorCorreo != null,
            supportingText = {
                if (errorCorreo != null) {
                    Text(
                        text = "⚠️ $errorCorreo",
                        color = rojo
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para guardar el perfil
        Button(
            onClick = {

                mostrarErrores = true

                val formularioValido =
                    PerfilValidator.validarFormulario(
                        nombre = nombre,
                        correo = correo
                    )

                if (formularioValido) {

                    // Guardamos nombre y correo en Room
                    perfilViewModel.guardarPerfil(
                        nombre = nombre.trim(),
                        correo = correo.trim()
                    )

                    mensaje = "Se solicitó guardar el perfil"

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
                text = "Guardar perfil",
                color = azul
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de resultado
        if (mensaje.isNotEmpty()) {

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
