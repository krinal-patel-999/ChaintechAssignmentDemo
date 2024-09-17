package com.chintechassignmentdemo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chintechassignmentdemo.app.R
import com.chintechassignmentdemo.app.roomData.Password
import com.chintechassignmentdemo.app.viewmodel.PasswordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordViewModel = hiltViewModel()
) {
    val passwords by viewModel.passwords.collectAsState()
    val showAddPasswordDialog by viewModel.showAddPasswordDialog.collectAsState()
    val passwordToEdit by viewModel.passwordToEdit.collectAsState()

    val bottomSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ToolBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    bottomSheetState.show()
                }
                viewModel.openAddPasswordDialog()
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Password")
            }
        }
    ) { padding ->

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.color_EDF4F6))

        ) {
            items(passwords) { password ->
                PasswordItem(password = password, onEdit = {
                    viewModel.openEditPasswordDialog(password)
                    scope.launch {
                        bottomSheetState.show()
                    }
                })
            }
        }
    }

    if (showAddPasswordDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                }
                viewModel.closeAddPasswordDialog()
            }
           // sheetState = bottomSheetState
        ) {
            AddEditPasswordBottomSheet(
                sheetState = bottomSheetState,
                password = passwordToEdit,
                onSave = { password ->
                    if (passwordToEdit == null) {
                        viewModel.addPassword(password)
                    } else {
                        viewModel.updatePassword(password)
                    }
                    scope.launch {
                        bottomSheetState.hide()
                    }
                    viewModel.closeAddPasswordDialog()
                },
                onDelete = { id ->
                    viewModel.deletePassword(id)
                    scope.launch {
                        bottomSheetState.hide()
                    }
                    viewModel.closeAddPasswordDialog()
                },
                onDismiss = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                    viewModel.closeAddPasswordDialog()
                }
            )
        }
    }
}

@Composable
fun PasswordItem(password: Password, onEdit: (Password) -> Unit) {
    val maskedPassword = "*".repeat(password.encryptedPassword.length)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit(password) },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)),
    ) {
        Row (
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 10.dp)
        ) {
            Text(text = password.accountType)
            Text(text = maskedPassword,Modifier.padding(start = 10.dp))
        }
    }
}
