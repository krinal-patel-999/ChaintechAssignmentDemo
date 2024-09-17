package com.chintechassignmentdemo.app.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chintechassignmentdemo.app.roomData.Password
import kotlinx.coroutines.launch
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector


import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.VisualTransformation
import com.chintechassignmentdemo.app.R
import com.chintechassignmentdemo.app.security.PasswordStrengthMeter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPasswordBottomSheet(
    sheetState: ModalBottomSheetState,
    password: Password?,
    onSave: (Password) -> Unit,
    onDelete: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var accountType by remember { mutableStateOf(password?.accountType ?: "") }
    var username by remember { mutableStateOf(password?.username ?: "") }
    var passwordText by remember { mutableStateOf(password?.encryptedPassword ?: "") }
    var passwordVisible by remember { mutableStateOf(false) }
    val isEditing = password != null

    // Error state
    val accountTypeError = remember { mutableStateOf<String?>(null) }
    val usernameError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }

    // Validation function
    fun validateInputs(): Boolean {
        var isValid = true
        if (accountType.isBlank()) {
            accountTypeError.value = "Account type cannot be empty"
            isValid = false
        } else {
            accountTypeError.value = null
        }
        if (username.isBlank()) {
            usernameError.value = "Username cannot be empty"
            isValid = false
        } else {
            usernameError.value = null
        }
        if (passwordText.isBlank()) {
            passwordError.value = "Password cannot be empty"
            isValid = false
        } else {
            passwordError.value = null
        }
        return isValid
    }

    // Random password generator
    fun generateRandomPassword(length: Int = 12): String {
        val characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+"
        return (1..length)
            .map { characters.random() }
            .joinToString("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = if (isEditing) "Account Details" else "Add Password",
            color = colorResource(id = R.color.color_4733FF),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = accountType,
            onValueChange = {
                accountType = it
            },
            label = { Text("Account Type") },
            isError = accountTypeError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        accountTypeError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = { Text("Username") },
            isError = usernameError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        usernameError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordText,
            onValueChange = {
                passwordText = it
            },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError.value != null,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                val icon: ImageVector = if (passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )
        passwordError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password strength meter
        PasswordStrengthMeter(passwordText)

        Spacer(modifier = Modifier.height(8.dp))

        // Button to generate random password
        Button(
            onClick = {
                passwordText = generateRandomPassword()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.black))
        ) {
            Text("Generate Strong Password")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    coroutineScope.launch { sheetState.hide() }
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.black)
                )
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (isEditing) {
                Button(
                    onClick = {
                        password?.id?.let {
                            onDelete(it)
                            coroutineScope.launch { sheetState.hide() }
                            onDismiss()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.color_F44336)
                    )
                ) {
                    Text("Delete")
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Button(
                onClick = {
                    if (validateInputs()) {
                        onSave(
                            Password(
                                id = password?.id ?: 0,
                                accountType = accountType,
                                username = username,
                                encryptedPassword = passwordText
                            )
                        )
                        coroutineScope.launch { sheetState.hide() }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.black)
                )
            ) {
                Text("Save")
            }
        }
    }
}
