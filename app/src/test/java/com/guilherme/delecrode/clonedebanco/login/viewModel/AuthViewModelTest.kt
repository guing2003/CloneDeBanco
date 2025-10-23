package com.guilherme.delecrode.clonedebanco.login.viewModel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.guilherme.delecrode.clonedebanco.domain.repository.AuthRepository
import com.guilherme.delecrode.clonedebanco.domain.model.User
import com.guilherme.delecrode.clonedebanco.ui.screens.login.AuthViewModel
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After


@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: AuthViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val testUser = User(
        name = "Guilherme",
        accountNumber = "123",
        branchNumber = "0000",
        checkingAccountBalance = "1200",
        id = "1"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // <<< ESSENCIAL

        authRepository = mockk()

        // Configura o login
        coEvery { authRepository.login() } returns Result.success(testUser)

        // Configura o getUser para retornar um Flow
        every { authRepository.getUser() } returns flowOf(testUser)

        viewModel = AuthViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // volta ao normal
    }

    @Test
    fun `login com sucesso atualiza uiState com user`() = runTest(testDispatcher) {
        viewModel.login()
        testDispatcher.scheduler.advanceUntilIdle() // garante execução de todas coroutines

        viewModel.uiState.test {
            viewModel.login()  // dispara o login enquanto o Turbine já está coletando
            testDispatcher.scheduler.advanceUntilIdle()

            awaitItem() // estado inicial
            awaitItem() // isLoading = true
            val successState = awaitItem() // user preenchido

            assertEquals(testUser, successState.user)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `login com falha atualiza uiState com erro`() = runTest(testDispatcher) {
        val errorMessage = "Falha de autenticação"
        coEvery { authRepository.login() } returns Result.failure(Exception(errorMessage))

        viewModel.uiState.test {
            viewModel.login() // dispara login enquanto Turbine coleta
            testDispatcher.scheduler.advanceUntilIdle()

            awaitItem() // estado inicial
            awaitItem() // isLoading = true
            val failureState = awaitItem() // erro

            assertEquals(errorMessage, failureState.error)
            assertFalse(failureState.isLoading)
            assertNull(failureState.user)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `clearState remove erro do uiState`() = runTest(testDispatcher) {
        viewModel.clearState()
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `clearUser remove user do uiState`() = runTest(testDispatcher) {
        viewModel.clearUser()
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.uiState.value.user)
    }
}
