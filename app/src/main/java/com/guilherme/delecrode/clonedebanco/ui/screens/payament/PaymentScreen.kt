package com.guilherme.delecrode.clonedebanco.ui.screens.payament

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryTextColor
import com.guilherme.delecrode.clonedebanco.ui.theme.SecondaryTextColor
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    paymentViewModel: PaymentViewModel
) {

    val user = authViewModel.savedUser.collectAsState()
    val uiState by paymentViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        paymentViewModel.getPayments()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            Log.i("PaymentScreen", "PaymentScreen: $msg")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pagamentos",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        authViewModel.clearUser()
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Detalhes do Pagamento",
                color = PrimaryTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Cliente: ${user.value?.name}",
                color = PrimaryTextColor,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Agência: ${user.value?.branchNumber} | Conta: ${user.value?.accountNumber}",
                color = SecondaryTextColor,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Saldo: R$${user.value?.checkingAccountBalance}",
                color = PrimaryTextColor,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Contas Pagas",
                color = PrimaryTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(uiState.payments) { payment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Conta de Luz",
                                color = PrimaryTextColor,
                                fontSize = 16.sp,
                            )
                            Text(
                                text = payment.paymentDate ?: "00/00/0000",
                                color = SecondaryTextColor,
                                fontSize = 14.sp,
                            )
                        }
                        Text(
                            text = payment.electricityBill ?: "R$00,00",
                            color = SecondaryTextColor,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    val navController = rememberNavController()

    CloneDeBancoTheme {
        PaymentScreen(
            navController = navController,
            koinViewModel(),
            koinViewModel()
        )
    }
}