package com.guilherme.delecrode.clonedebanco.ui.screens.payament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun getPayments() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                isRefreshing = false
            )

            paymentRepository.getPayments()
                .collect { result ->
                    result.fold(
                        onSuccess = { payments ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                payments = payments,
                                isEmpty = payments.isEmpty(),
                                error = null
                            )
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = "Não foi possível carregar seus pagamentos. Verifique sua conexão e tente novamente.",
                                isRefreshing = false
                            )
                        }
                    )
                }
        }
    }
}