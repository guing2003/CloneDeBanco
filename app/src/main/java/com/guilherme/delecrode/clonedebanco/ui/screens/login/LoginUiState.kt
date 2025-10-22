package com.guilherme.delecrode.clonedebanco.ui.screens.login

import com.guilherme.delecrode.clonedebanco.domain.model.User

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)