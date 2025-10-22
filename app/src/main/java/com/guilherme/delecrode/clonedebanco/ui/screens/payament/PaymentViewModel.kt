package com.guilherme.delecrode.clonedebanco.ui.screens.payament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.delecrode.clonedebanco.domain.model.Payment
import com.guilherme.delecrode.clonedebanco.domain.model.User
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository
import com.guilherme.delecrode.clonedebanco.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun getPayments() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = paymentRepository.payament()

            result.fold(
                onSuccess = { payments ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        payments = payments
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }
}