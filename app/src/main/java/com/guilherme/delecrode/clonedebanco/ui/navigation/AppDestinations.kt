package com.guilherme.delecrode.clonedebanco.ui.navigation


sealed class AppDestinations(val route: String) {
    object Login : AppDestinations("login")
}