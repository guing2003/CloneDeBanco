package com.guilherme.delecrode.clonedebanco.data.remote.service

import com.guilherme.delecrode.clonedebanco.data.model.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    //Login
    @GET("treinamento/Login")
    suspend fun login(): Response<List<LoginResponseDTO>>
}