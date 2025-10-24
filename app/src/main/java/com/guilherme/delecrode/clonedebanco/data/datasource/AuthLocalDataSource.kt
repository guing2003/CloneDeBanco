package com.guilherme.delecrode.clonedebanco.data.datasource

import com.guilherme.delecrode.clonedebanco.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    fun getUser(): Flow<User?>
    suspend fun saveUser(user: User)
}
