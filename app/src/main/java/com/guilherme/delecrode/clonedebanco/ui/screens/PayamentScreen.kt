package com.guilherme.delecrode.clonedebanco.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.guilherme.delecrode.clonedebanco.data.repository.AuthRepositoryImpl
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import com.guilherme.delecrode.clonedebanco.ui.theme.CloneDeBancoTheme
import com.guilherme.delecrode.clonedebanco.ui.theme.PrimaryTextColor
import com.guilherme.delecrode.clonedebanco.ui.theme.SecondaryTextColor
import kotlinx.coroutines.flow.first
import org.koin.androidx.compose.koinViewModel
import org.koin.viewmodel.factory.KoinViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayamentScreen(navController: NavController, authViewModel: AuthViewModel =  koinViewModel()) {

    val user = authViewModel.savedUser.collectAsState()

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp)) {
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

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier.wrapContentHeight().weight(1f)){
                    Text(
                        text = "Conta de Luz",
                        color = PrimaryTextColor,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "R$120,00",
                        color = SecondaryTextColor,
                        fontSize = 14.sp,
                    )
                }
                Text(
                    text = "17/10/2025",
                    color = SecondaryTextColor,
                    fontSize = 16.sp,
                )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PayamentScreenPreview() {
    val navController = rememberNavController()

    CloneDeBancoTheme {
        PayamentScreen(
            navController = navController,
        )
    }
}