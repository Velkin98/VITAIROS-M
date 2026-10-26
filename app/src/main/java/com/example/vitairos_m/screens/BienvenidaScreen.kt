package com.example.vitairos_m.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BienvenidaScreen(onComenzar: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F8FB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "VITAIROS-M",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000E2E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu espacio de bienestar emocional",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF000E2E)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                onComenzar()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00C98E)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Comenzar",
                color = Color(0xFF000E2E)
            )
        }
    }
}
