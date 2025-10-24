package com.guilherme.delecrode.clonedebanco.di

import androidx.room.Room
import com.guilherme.delecrode.clonedebanco.data.datasource.AuthLocalDataSource
import com.guilherme.delecrode.clonedebanco.data.datasource.AuthLocalDataSourceImpl
import com.guilherme.delecrode.clonedebanco.data.local.AppDatabase
import com.guilherme.delecrode.clonedebanco.data.remote.RetrofitInstance
import com.guilherme.delecrode.clonedebanco.data.repository.AuthRepositoryImpl
import com.guilherme.delecrode.clonedebanco.data.repository.PaymentRepositoryImpl
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import com.guilherme.delecrode.clonedebanco.ui.screens.payament.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { RetrofitInstance.authApi }
    single { RetrofitInstance.paymentApi }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().paymentDao() }

    single<AuthLocalDataSource> { AuthLocalDataSourceImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<PaymentRepository> { PaymentRepositoryImpl(get(), get()) }

    viewModel { AuthViewModel(get()) }
    viewModel { PaymentViewModel(get()) }
}
