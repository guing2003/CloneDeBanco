package com.guilherme.delecrode.clonedebanco.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.delecrode.clonedebanco.domain.model.User
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val emailValidation = validateEmail(email)
            if (emailValidation != null) {
                _uiState.value = _uiState.value.copy(
                    emailError = emailValidation,
                    passwordError = null,
                    error = null
                )
                return@launch
            }

            val passwordValidation = validatePassword(password)
            if (passwordValidation != null) {
                _uiState.value = _uiState.value.copy(
                    emailError = null,
                    passwordError = passwordValidation,
                    error = null
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                emailError = null,
                passwordError = null
            )

            val result = authRepository.login()

            result.fold(
                onSuccess = { user -> onLoginSuccess(user) },
                onFailure = { error -> onLoginFailure(error.message ?: "Erro desconhecido") }
            )
        }
    }

    private fun onLoginSuccess(user: User) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            user = user,
            isLoginSuccessful = true,
            error = null
        )
    }

    private fun onLoginFailure(message: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = message,
            isLoginSuccessful = false
        )
    }

    val savedUser = authRepository.getUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun clearState() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearUser() {
        _uiState.value = _uiState.value.copy(user = null)
    }

    fun clearEmailError() {
        _uiState.value = _uiState.value.copy(emailError = null)
    }

    fun clearPasswordError() {
        _uiState.value = _uiState.value.copy(passwordError = null)
    }

    fun logout() {
        _uiState.value = LoginUiState()
    }
    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email é obrigatório"
            !isValidEmailFormat(email) -> "Email inválido"
            else -> null
        }
    }
    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Senha é obrigatória"
            password.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
            !containsLetterAndNumber(password) -> "Senha deve conter pelo menos 1 letra e 1 número"
            else -> null
        }
    }
    private fun isValidEmailFormat(email: String): Boolean {
        return Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email)
    }
    private fun containsLetterAndNumber(password: String): Boolean {
        return Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$").matches(password)
    }
}