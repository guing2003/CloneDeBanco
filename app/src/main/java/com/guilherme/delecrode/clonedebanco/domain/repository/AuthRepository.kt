package com.guilherme.delecrode.clonedebanco.domain.repository

import com.guilherme.delecrode.clonedebanco.domain.model.User

interface AuthRepository {
    suspend fun login():  Result<User>
}