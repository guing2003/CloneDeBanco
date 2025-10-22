package com.guilherme.delecrode.clonedebanco.data.repository

import com.guilherme.delecrode.clonedebanco.data.mapper.toPaymentDomain
import com.guilherme.delecrode.clonedebanco.data.remote.service.PaymentApiService
import com.guilherme.delecrode.clonedebanco.domain.model.Payment
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository

class PaymentRepositoryImpl(private val apiService: PaymentApiService) : PaymentRepository {

    override suspend fun payament(): Result<List<Payment>> {
        return try {
            val response = apiService.getPayament()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val payments = body.map { it.toPaymentDomain() }
                    Result.success(payments)
                } else {
                    Result.failure(Exception("Resposta vazia do Servidor"))
                }
            } else {
                Result.failure(Exception("Erro da API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Falha ao fazer login: ${e.message}", e))
        }

    }
}