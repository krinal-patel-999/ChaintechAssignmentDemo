package com.chintechassignmentdemo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chintechassignmentdemo.app.roomData.Password
import com.chintechassignmentdemo.app.roomData.PasswordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordDao: PasswordDao // Inject the DAO here
) : ViewModel() {

    private val _passwords = MutableStateFlow<List<Password>>(emptyList())
    val passwords: StateFlow<List<Password>> = _passwords

    // StateFlow to manage dialog visibility
    private val _showAddPasswordDialog = MutableStateFlow(false)
    val showAddPasswordDialog: StateFlow<Boolean> = _showAddPasswordDialog.asStateFlow()

    // StateFlow to manage the password being edited
    private val _passwordToEdit = MutableStateFlow<Password?>(null)
    val passwordToEdit: StateFlow<Password?> = _passwordToEdit.asStateFlow()

    init {
        loadPasswords()
    }

    private fun loadPasswords() {

        viewModelScope.launch {
            passwordDao.getAllPasswords().collect { passwords ->
                _passwords.value = passwords
            }
        }
    }

    fun addPassword(password: Password) {
        viewModelScope.launch {
            passwordDao.insert(password)
            loadPasswords()
        }
    }

    fun updatePassword(password: Password) {
        viewModelScope.launch {
            passwordDao.update(password)
            loadPasswords()
        }
    }

    fun deletePassword(id: Int) {
        viewModelScope.launch {
            passwordDao.deleteById(id)
            loadPasswords()
        }
    }

    fun openAddPasswordDialog() {
        _showAddPasswordDialog.value = true
    }

    fun closeAddPasswordDialog() {
        _showAddPasswordDialog.value = false
        _passwordToEdit.value = null
    }

    fun openEditPasswordDialog(password: Password) {
        _passwordToEdit.value = password
        _showAddPasswordDialog.value = true
    }
}
