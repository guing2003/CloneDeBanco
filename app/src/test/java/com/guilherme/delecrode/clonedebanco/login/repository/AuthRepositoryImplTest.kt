package com.guilherme.delecrode.clonedebanco.login.repository

import com.guilherme.delecrode.clonedebanco.data.datasource.AuthLocalDataSource
import com.guilherme.delecrode.clonedebanco.data.model.UserResponseDTO
import com.guilherme.delecrode.clonedebanco.data.remote.service.AuthApiService
import com.guilherme.delecrode.clonedebanco.data.repository.AuthRepositoryImpl
import com.guilherme.delecrode.clonedebanco.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    private lateinit var apiService: AuthApiService
    private lateinit var localDataSource: AuthLocalDataSource
    private lateinit var repository: AuthRepositoryImpl

    private val testUser = User(
        name = "Test User",
        accountNumber = "123456",
        branchNumber = "0001",
        checkingAccountBalance = "1000.00",
        id = "1"
    )

    private val testUserResponseDTO = UserResponseDTO(
        customerName = "Test User",
        accountNumber = "123456",
        branchNumber = "0001",
        checkingAccountBalance = "1000.00",
        id = "1"
    )

    @Before
    fun setUp() {
        apiService = mockk()
        localDataSource = mockk(relaxed = true)
        repository = AuthRepositoryImpl(apiService, localDataSource)
    }

    @Test
    fun `login com sucesso retorna usuario e salva localmente`() = runTest {
        // Dado
        val response = Response.success(listOf(testUserResponseDTO))
        coEvery { apiService.login() } returns response

        // Quando
        val result = repository.login()

        // Então
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(testUser, result.getOrNull())
        coVerify { localDataSource.saveUser(testUser) }
    }

    @Test
    fun `login com resposta vazia retorna falha`() = runTest {
        // Dado
        val response = Response.success<List<UserResponseDTO>>(emptyList())
        coEvery { apiService.login() } returns response

        // Quando
        val result = repository.login()

        // Então
        Assert.assertTrue(result.isFailure)
        Assert.assertEquals("Resposta vazia do servidor", result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { localDataSource.saveUser(any()) }
    }

    @Test
    fun `login com falha da API retorna falha`() = runTest {
        // Dado
        val response = Response.error<List<UserResponseDTO>>(401, mockk(relaxed = true))
        coEvery { apiService.login() } returns response

        // Quando
        val result = repository.login()

        // Então
        Assert.assertTrue(result.isFailure)
        Assert.assertEquals("Erro da API: 401", result.exceptionOrNull()?.message)
        coVerify(exactly = 0) { localDataSource.saveUser(any()) }
    }

    @Test
    fun `login com excecao da API retorna falha`() = runTest {
        // Dado
        val exception = RuntimeException("Network error")
        coEvery { apiService.login() } throws exception

        // Quando
        val result = repository.login()

        // Então
        Assert.assertTrue(result.isFailure)
        Assert.assertEquals(
            "Falha ao fazer login: Network error",
            result.exceptionOrNull()?.message
        )
        coVerify(exactly = 0) { localDataSource.saveUser(any()) }
    }

    @Test
    fun `getUser retorna o usuario do dataSource`() = runTest {
        // Dado
        coEvery { localDataSource.getUser() } returns flowOf(testUser)

        // Quando
        val userFlow = repository.getUser()

        // Então
        Assert.assertEquals(testUser, userFlow.first())
    }
}