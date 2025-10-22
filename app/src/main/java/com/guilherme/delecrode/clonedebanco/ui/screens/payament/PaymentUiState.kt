package com.guilherme.delecrode.clonedebanco.ui.screens.payament

import com.guilherme.delecrode.clonedebanco.domain.model.Payment

data class PaymentUiState(
    val isLoading: Boolean = false,
    val payments: List<Payment> = emptyList(),
    val error: String? = null
)