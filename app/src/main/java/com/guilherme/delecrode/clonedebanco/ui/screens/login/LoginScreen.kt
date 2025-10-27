package com.guilherme.delecrode.clonedebanco.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val uiState by authViewModel.uiState.collectAsState()


    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            navController.navigate(AppDestinations.Payament.route)
        }
    }


    LaunchedEffect(uiState.error) {
        uiState.error?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            authViewModel.clearState()
        }
    }

    LaunchedEffect(email) {
        if (uiState.emailError != null) {
            authViewModel.clearEmailError()
        }
    }

    LaunchedEffect(password) {
        if (uiState.passwordError != null) {
            authViewModel.clearPasswordError()
        }
    }


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
                    containerColor = Color.White
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


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = "Logo do App",
                    modifier = Modifier.size(250.dp),
                    tint = Color.Unspecified
                )
            }
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
                modifier = Modifier.testTag("email_field"),
                value = email,
                onValueChange = { email = it },
                imeAction = ImeAction.Next,
                isError = uiState.emailError != null,
                errorMessage = uiState.emailError ?: ""
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
                modifier = Modifier.testTag("password_field"),
                value = password,
                onValueChange = { password = it },
                imeAction = ImeAction.Done,
                isPasswordVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible },
                isError = uiState.passwordError != null,
                errorMessage = uiState.passwordError ?: ""
            )

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                modifier = Modifier.testTag("login_button"),
                text = "ENTRAR",
                onClick = {
                    authViewModel.login(email, password)
                },
                enabled = uiState.canLogin && email.isNotBlank() && password.isNotBlank()
            )


            Spacer(modifier = Modifier.height(24.dp))


        }
    }
    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()

    CloneDeBancoTheme {
        LoginScreen(
            navController = navController,
            koinViewModel()
        )
    }
}
