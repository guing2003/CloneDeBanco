package com.guilherme.delecrode.clonedebanco.payment.repository


import app.cash.turbine.test
import com.guilherme.delecrode.clonedebanco.data.local.dao.PaymentDao
import com.guilherme.delecrode.clonedebanco.data.local.entity.PaymentEntity
import com.guilherme.delecrode.clonedebanco.data.mapper.toDomain
import com.guilherme.delecrode.clonedebanco.data.mapper.toEntity
import com.guilherme.delecrode.clonedebanco.data.model.PaymentResponseDTO
import com.guilherme.delecrode.clonedebanco.data.remote.service.PaymentApiService
import com.guilherme.delecrode.clonedebanco.data.repository.PaymentRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentRepositoryImplTest {

    private lateinit var apiService: PaymentApiService
    private lateinit var dao: PaymentDao
    private lateinit var repository: PaymentRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    private val paymentEntity = PaymentEntity(
        paymentId = "1",
        paymentDate = "12/12/2020",
        electricityBill = "100"
    )

    private val payment = paymentEntity.toDomain()

    @Before
    fun setup() {
        apiService = mockk()
        dao = mockk(relaxed = true)
        repository = PaymentRepositoryImpl(apiService, dao)
    }

    private val paymentResponseDTO = PaymentResponseDTO(
        id = "1",
        paymentDate = "12/12/2020",
        electricityBill = "100"
    )

    @Test
    fun `getPaymentFromAPI sucesso retorna lista de payments e salva local`() = runTest(testDispatcher) {
        val response = Response.success(listOf(paymentResponseDTO))
        coEvery { apiService.getPayament() } returns response
        coEvery { dao.insertPayments(any()) } returns Unit

        val result = repository.getPaymentFromAPI()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals(paymentResponseDTO.toEntity().toDomain(), result.getOrNull()?.first())

        coVerify { dao.insertPayments(any()) }
    }


    @Test
    fun `getPaymentFromAPI falha retorna erro`() = runTest(testDispatcher) {
        val response = Response.error<List<PaymentResponseDTO>>(500, mockk(relaxed = true))
        coEvery { apiService.getPayament() } returns response

        val result = repository.getPaymentFromAPI()

        assertTrue(result.isFailure)
        assertEquals("Erro da API: 500", result.exceptionOrNull()?.message)
    }


    @Test
    fun `getLocalPayments retorna flow com payments`() = runTest(testDispatcher) {
        every { dao.getAllPayments() } returns flowOf(listOf(paymentEntity))

        repository.getLocalPayments().test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals(payment, result.getOrNull()?.first())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPayments emite API se sucesso`() = runTest(testDispatcher) {
        val responseDTO = PaymentResponseDTO(
            id = "1",
            paymentDate = "12/12/2020",
            electricityBill = "100"
        )

        val response = Response.success(listOf(responseDTO))
        coEvery { apiService.getPayament() } returns response
        coEvery { dao.insertPayments(any()) } returns Unit

        repository.getPayments().test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals(payment, result.getOrNull()?.first())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `getPayments emite local se API falha`() = runTest(testDispatcher) {
        val response = Response.error<List<PaymentResponseDTO>>(500, mockk(relaxed = true))
        coEvery { apiService.getPayament() } returns response

        every { dao.getAllPayments() } returns flowOf(listOf(paymentEntity))

        repository.getPayments().test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals(payment, result.getOrNull()?.first())
            cancelAndIgnoreRemainingEvents()
        }
    }

}
