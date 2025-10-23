package com.guilherme.delecrode.clonedebanco.data.repository

import com.guilherme.delecrode.clonedebanco.data.local.dao.PaymentDao
import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity
import com.guilherme.delecrode.clonedebanco.data.mapper.toDomain
import com.guilherme.delecrode.clonedebanco.data.mapper.toEntity
import com.guilherme.delecrode.clonedebanco.data.remote.service.PaymentApiService
import com.guilherme.delecrode.clonedebanco.domain.model.Payment
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PaymentRepositoryImpl(
    private val apiService: PaymentApiService,
    private val dao: PaymentDao
) : PaymentRepository {
    override suspend fun getPaymentFromAPI(): Result<List<Payment>> {
        return try {
            val response = apiService.getPayament()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val paymentsEntity = body.map { it.toEntity() }
                    savePaymentFromLocal(paymentsEntity)
                    Result.success(paymentsEntity.map { it.toDomain() })
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                Result.failure(Exception("Erro da API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Falha ao buscar dados: ${e.message}", e))
        }
    }

    override suspend fun savePaymentFromLocal(payment: List<PaymentEntity>): Result<Unit> {
        return try {
            dao.insertPayments(payment)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getLocalPayments(): Flow<Result<List<Payment>>> {
        return dao.getAllPayments()
            .map { list -> Result.success(list.map { it.toDomain() }) }
            .catch { e -> emit(Result.failure(e)) }
    }

    override fun getPayments(): Flow<Result<List<Payment>>> = flow {
        val apiResult = getPaymentFromAPI()
        if (apiResult.isSuccess) {
            emit(apiResult)
        } else {
            emitAll(getLocalPayments())
        }
    }
}
