package com.guilherme.delecrode.clonedebanco.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import com.guilherme.delecrode.clonedebanco.ui.screens.login.LoginScreen
import com.guilherme.delecrode.clonedebanco.ui.screens.payament.PaymentScreen
import com.guilherme.delecrode.clonedebanco.ui.screens.payament.PaymentViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = koinViewModel()

    val paymentViewModel: PaymentViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login.route
    ) {
        composable(AppDestinations.Login.route) {
            LoginScreen(navController = navController, authViewModel)
        }

        composable(AppDestinations.Payament.route){
            PaymentScreen(navController = navController, authViewModel, paymentViewModel)
        }

    }
}