package com.guilherme.delecrode.clonedebanco.payment.viewModel

import app.cash.turbine.test
import com.guilherme.delecrode.clonedebanco.domain.model.Payment
import com.guilherme.delecrode.clonedebanco.domain.repository.PaymentRepository
import com.guilherme.delecrode.clonedebanco.ui.screens.payament.PaymentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentViewModelTest {

    private lateinit var repository: PaymentRepository
    private lateinit var viewModel: PaymentViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = PaymentViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `quando API retorna sucesso, uiState recebe pagamentos`() = runTest {
        val payments = listOf(Payment("2025-10-23", "R$100", "1"))
        coEvery { repository.getPayments() } returns flow { emit(Result.success(payments)) }

        viewModel.getPayments()

        testDispatcher.scheduler.advanceUntilIdle()

        Assert.assertEquals(payments, viewModel.uiState.value.payments)
        Assert.assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `quando API falha e local retorna sucesso, uiState recebe pagamentos do local`() =
        runTest {
            val payments = listOf(Payment("2025-10-22", "R$50", "2"))

            coEvery { repository.getPayments() } returns flow {
                emit(Result.failure(Exception("API falhou")))
                emit(Result.success(payments)) // fallback local
            }

            viewModel.getPayments()
            testDispatcher.scheduler.advanceUntilIdle()

            viewModel.uiState.test {
                val state = awaitItem()
                // Espera fallback
                Assert.assertEquals(payments, state.payments)
                Assert.assertEquals(false, state.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `quando API e local falham, uiState recebe erro`() = runTest {
        val errorMessage = "API e Local falharam"

        coEvery { repository.getPayments() } returns flow {
            emit(Result.failure(Exception(errorMessage)))
        }

        viewModel.getPayments()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            Assert.assertEquals(errorMessage, state.error)
            Assert.assertEquals(false, state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}