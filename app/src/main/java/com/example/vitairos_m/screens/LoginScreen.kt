
package com.example.vitairos_m.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.vitairos_m.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {

    val azul = Color(0xFF000E2E)
    val verde = Color(0xFF00C98E)
    val fondo = Color(0xFFF2F8FB)
    val rojo = MaterialTheme.colorScheme.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Iniciar sesión",
            fontSize = 28.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = loginViewModel.correo,
            onValueChange = {
                loginViewModel.actualizarCorreo(it)
            },
            label = {
                Text("Correo electrónico")
            },
            isError = loginViewModel.errorCorreo != null,
            supportingText = {
                loginViewModel.errorCorreo?.let { error ->
                    Text(
                        text = "⚠️ $error",
                        color = rojo
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = loginViewModel.contrasena,
            onValueChange = {
                loginViewModel.actualizarContrasena(it)
            },
            label = {
                Text("Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = loginViewModel.errorContrasena != null,
            supportingText = {
                loginViewModel.errorContrasena?.let { error ->
                    Text(
                        text = "⚠️ $error",
                        color = rojo
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje general
        if (loginViewModel.mensaje.isNotEmpty()) {

            Text(
                text = loginViewModel.mensaje,
                color = if (loginViewModel.loginExitoso) {
                    verde
                } else {
                    rojo
                },
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón de inicio de sesión
        Button(
            onClick = {
                loginViewModel.validarLogin()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = verde
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ingresar",
                color = azul
            )
        }
    }
}
