package com.guilherme.delecrode.clonedebanco.di

import com.guilherme.delecrode.clonedebanco.data.remote.RetrofitInstance
import com.guilherme.delecrode.clonedebanco.data.repository.AuthRepositoryImpl
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { RetrofitInstance.authApi }

    single<AuthRepository> { AuthRepositoryImpl(get()) }

    viewModel { AuthViewModel(get()) }
}
