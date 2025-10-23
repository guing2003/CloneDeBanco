package com.guilherme.delecrode.clonedebanco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val paymentId: String,
    val paymentDate: String,
    val electricityBill: String,
)
