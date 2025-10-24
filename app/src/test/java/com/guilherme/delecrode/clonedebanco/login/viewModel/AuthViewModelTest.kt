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
        Dispatchers.setMain(testDispatcher)

        authRepository = mockk()

        coEvery { authRepository.login() } returns Result.success(testUser)

        every { authRepository.getUser() } returns flowOf(testUser)

        viewModel = AuthViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login com sucesso atualiza uiState com user`() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            viewModel.login("teste@gmail.com", "teste123")
            testDispatcher.scheduler.advanceUntilIdle()

            awaitItem()
            awaitItem()
            val successState = awaitItem()

            assertEquals(testUser, successState.user)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `login com falha atualiza uiState com erro`() = runTest(testDispatcher) {
        val errorMessage = "Falha de autenticação"
        coEvery { authRepository.login() } returns Result.failure(Exception(errorMessage))

        viewModel.uiState.test {
            viewModel.login("gui@teste.com", "abc123")
            testDispatcher.scheduler.advanceUntilIdle()

            awaitItem()
            awaitItem()
            val failureState = awaitItem()

            assertEquals(errorMessage, failureState.error)
            assertFalse(failureState.isLoading)
            assertNull(failureState.user)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login com email invalido retorna erro de validacao`() = runTest {
        viewModel.uiState.test {
            viewModel.login("gui", "abc123")

            val initialState = awaitItem()
            val validationState = awaitItem()

            assertEquals("Email inválido", validationState.emailError)
            assertNull(validationState.passwordError)
            assertFalse(validationState.isLoading)
            assertNull(validationState.user)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login com senha invalida retorna erro de validacao`() = runTest {
        viewModel.uiState.test {
            viewModel.login("gui@teste.com", "123456")

            val initialState = awaitItem()
            val validationState = awaitItem()

            assertNull(validationState.emailError)
            assertEquals(
                "Senha deve conter pelo menos 1 letra e 1 número",
                validationState.passwordError
            )
            assertFalse(validationState.isLoading)
            assertNull(validationState.user)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login com senha invalida menos de 6 caracteres retorna erro de validacao`() = runTest {
        viewModel.uiState.test {
            viewModel.login("gui@teste.com", "123")

            val initialState = awaitItem()
            val validationState = awaitItem()

            assertNull(validationState.emailError)
            assertEquals(
                "Senha deve ter pelo menos 6 caracteres",
                validationState.passwordError
            )
            assertFalse(validationState.isLoading)
            assertNull(validationState.user)

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
    fun `logout remove user do uiState`() = runTest(testDispatcher) {
        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()
        assertNull(viewModel.uiState.value.user)
    }
}
