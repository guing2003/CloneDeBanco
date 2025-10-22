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

    fun login() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = authRepository.login()

            result.fold(
                onSuccess = { user -> onLoginSuccess(user) },
                onFailure = { error -> onLoginFailure(error.message ?: "Erro desconhecido") }
            )
        }
    }

    private fun onLoginSuccess(user: User) {
        _uiState.value = _uiState.value.copy(isLoading = false, user = user)
    }

    private fun onLoginFailure(message: String) {
        _uiState.value = _uiState.value.copy(isLoading = false, error = message)
    }

    val savedUser = authRepository.getUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun clearState() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}