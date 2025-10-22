package com.guilherme.delecrode.clonedebanco.data.mapper

import com.guilherme.delecrode.clonedebanco.data.model.PaymentResponseDTO
import com.guilherme.delecrode.clonedebanco.domain.model.Payment

fun PaymentResponseDTO.toPaymentDomain(): Payment {
    return Payment(
        paymentDate = paymentDate,
        electricityBill = electricityBill,
        id = id
    )
}