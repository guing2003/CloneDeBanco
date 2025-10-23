package com.guilherme.delecrode.clonedebanco.data.remote.service

import com.guilherme.delecrode.clonedebanco.data.model.UserResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface AuthApiService {

    //Login
    @GET("treinamento/Login")
    suspend fun login(): Response<List<UserResponseDTO>>
}