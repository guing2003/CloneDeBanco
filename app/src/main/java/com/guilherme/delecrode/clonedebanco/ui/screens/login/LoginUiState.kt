package com.guilherme.delecrode.clonedebanco.ui.screens.login

import com.guilherme.delecrode.clonedebanco.domain.model.User

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoginSuccessful: Boolean = false
) {
    val hasValidationErrors: Boolean
        get() = emailError != null || passwordError != null

    val canLogin: Boolean
        get() = !isLoading && !hasValidationErrors && error == null
}