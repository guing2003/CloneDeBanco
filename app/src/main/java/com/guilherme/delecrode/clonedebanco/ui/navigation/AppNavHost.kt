package com.guilherme.delecrode.clonedebanco.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilherme.delecrode.clonedebanco.ui.screens.login.LoginScreen
import com.guilherme.delecrode.clonedebanco.ui.screens.PayamentScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login.route
    ) {
        composable(AppDestinations.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(AppDestinations.Payament.route){
            PayamentScreen(navController = navController)
        }

    }
}