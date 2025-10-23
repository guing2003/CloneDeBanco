package com.guilherme.delecrode.clonedebanco.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guilherme.delecrode.clonedebanco.data.local.dao.PaymentDao
import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity

@Database(
    entities = [
        PaymentEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "clone_bank.db"
            )//.addMigrations(MIGRATION_1_2)
                .build().also {
                    db = it
                }
        }
    }
}