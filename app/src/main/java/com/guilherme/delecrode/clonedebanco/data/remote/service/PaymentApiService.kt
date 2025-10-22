package com.guilherme.delecrode.clonedebanco.data.remote.service

import com.guilherme.delecrode.clonedebanco.data.model.PaymentResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface PaymentApiService {

    //Payament
    @GET("treinamento/payments")
    suspend fun getPayament() : Response<List<PaymentResponseDTO>>
}