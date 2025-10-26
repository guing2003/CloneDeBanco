package com.guilherme.delecrode.clonedebanco.data.mapper

import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity
import com.guilherme.delecrode.clonedebanco.data.model.PaymentResponseDTO
import com.guilherme.delecrode.clonedebanco.domain.model.Payment


fun PaymentResponseDTO.toDomain(): Payment {
    return Payment(
        paymentDate = this.paymentDate,
        electricityBill = this.electricityBill,
        id = this.id
    )
}

fun PaymentEntity.toDomain(): Payment {
    return Payment(
        id = paymentId,
        paymentDate = paymentDate,
        electricityBill = electricityBill
    )
}
fun Payment.toEntity(): PaymentEntity {
    return PaymentEntity(
        paymentDate = this.paymentDate,
        electricityBill = this.electricityBill,
        paymentId = this.id
    )
}