package com.guilherme.delecrode.clonedebanco.domain.repository

import com.guilherme.delecrode.clonedebanco.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login():  Result<User>

    fun getUser(): Flow<User?>
}