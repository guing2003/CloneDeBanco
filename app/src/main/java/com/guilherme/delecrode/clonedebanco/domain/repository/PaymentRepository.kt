package com.guilherme.delecrode.clonedebanco.domain.repository

import com.guilherme.delecrode.clonedebanco.domain.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun getPaymentFromAPI(): Result<List<Payment>>

    suspend fun savePaymentFromLocal(payments: List<Payment>) : Result<Unit>

    fun getLocalPayments():  Flow<Result<List<Payment>>>

    fun getPayments():  Flow<Result<List<Payment>>>

}