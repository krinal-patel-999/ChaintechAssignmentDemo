package com.chintechassignmentdemo.app.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Password strength calculation
fun calculatePasswordStrength(password: String): PasswordStrength {
    return when {
        password.length < 6 -> PasswordStrength.WEAK
        password.length < 12 -> PasswordStrength.MODERATE
        else -> PasswordStrength.STRONG
    }
}

// Password strength visual indicator
@Composable
fun PasswordStrengthMeter(password: String) {
    val strength = calculatePasswordStrength(password)
    val strengthColor = when (strength) {
        PasswordStrength.WEAK -> Color.Red
        PasswordStrength.MODERATE -> Color.Yellow
        PasswordStrength.STRONG -> Color.Green
    }

    Column {
        Text("Password Strength", color = strengthColor)
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(strengthColor)
        )
    }
}
