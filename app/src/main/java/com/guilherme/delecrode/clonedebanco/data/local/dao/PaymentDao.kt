package com.guilherme.delecrode.clonedebanco.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(payments: List<PaymentEntity>)

    @Query("SELECT * FROM payments")
    fun getAllPayments(): Flow<List<PaymentEntity>>
}
