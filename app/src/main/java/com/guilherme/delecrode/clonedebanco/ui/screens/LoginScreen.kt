package com.guilherme.delecrode.clonedebanco.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.guilherme.delecrode.clonedebanco.R
import com.guilherme.delecrode.clonedebanco.ui.components.EmailTextField
import com.guilherme.delecrode.clonedebanco.ui.components.PasswordTextField
import com.guilherme.delecrode.clonedebanco.ui.components.PrimaryButton
import com.guilherme.delecrode.clonedebanco.ui.navigation.AppDestinations
import com.guilherme.delecrode.clonedebanco.ui.theme.Background
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Login",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White // fundo branco
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Icon(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "Logo do App",
                modifier = Modifier.size(250.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "E-mail",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 14.sp
            )

            EmailTextField(
                value = email,
                onValueChange = { email = it },
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Senha",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 14.sp
            )

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                imeAction = ImeAction.Done,
                isPasswordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(32.dp))



            PrimaryButton(
                text = "ENTRAR",
                onClick = {
                    when {
                        !isEmailValid(email) -> Toast.makeText(context, "Email inválido!", Toast.LENGTH_SHORT).show()
                        !isPasswordValid(password) -> Toast.makeText(context, "Senha deve ter 6 caracteres, 1 letra e 1 número!", Toast.LENGTH_SHORT).show()
                        else -> {
                            // ação de login
                            navController.navigate(AppDestinations.Payament.route)
                        }
                    }
                },
                enabled = email.isNotBlank() && password.isNotBlank()
            )


            Spacer(modifier = Modifier.height(24.dp))


        }
    }
}

fun isEmailValid(email: String): Boolean {
    return Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matches(email)
}

fun isPasswordValid(password: String): Boolean {
    return Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$").matches(password)
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    CloneDeBancoTheme {
        LoginScreen(
            navController = navController,
        )
    }
}

