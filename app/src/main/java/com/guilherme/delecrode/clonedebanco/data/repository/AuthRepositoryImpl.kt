package com.guilherme.delecrode.clonedebanco.data.repository

import com.guilherme.delecrode.clonedebanco.data.mapper.toDomain
import com.guilherme.delecrode.clonedebanco.data.remote.service.AuthApiService
import com.guilherme.delecrode.clonedebanco.domain.model.User
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: AuthApiService
) : AuthRepository {

    override suspend fun login(): Result<User> {
        return try {
            val response = apiService.login()
            if (response.isSuccessful) {
                val body = response.body()
                if (!body.isNullOrEmpty()) {
                    val user = body.first().toDomain() // primeiro usuário
                    Result.success(user)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                Result.failure(Exception("Erro da API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Falha ao fazer login: ${e.message}", e))
        }
    }

}