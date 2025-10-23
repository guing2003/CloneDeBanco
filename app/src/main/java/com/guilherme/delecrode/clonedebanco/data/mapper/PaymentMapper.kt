package com.guilherme.delecrode.clonedebanco.data.mapper

import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity
import com.guilherme.delecrode.clonedebanco.data.model.PaymentResponseDTO
import com.guilherme.delecrode.clonedebanco.domain.model.Payment


fun PaymentEntity.toDomain(): Payment {
    return Payment(
        id = paymentId,
        paymentDate = paymentDate,
        electricityBill = electricityBill
    )
}
fun PaymentResponseDTO.toEntity(): PaymentEntity {
    return PaymentEntity(
        paymentDate = paymentDate,
        electricityBill = electricityBill,
        paymentId = id
    )
}