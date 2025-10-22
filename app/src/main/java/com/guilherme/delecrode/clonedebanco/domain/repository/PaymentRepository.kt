package com.guilherme.delecrode.clonedebanco.domain.repository

import com.guilherme.delecrode.clonedebanco.domain.model.Payment

interface PaymentRepository {
    suspend fun payament(): Result<List<Payment>>
}