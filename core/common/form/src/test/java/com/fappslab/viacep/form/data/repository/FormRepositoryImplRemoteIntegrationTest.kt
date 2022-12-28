package com.fappslab.viacep.form.data.repository

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.arch.rules.RemoteTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.data.service.FormService
import com.fappslab.viacep.form.data.source.remote.FormRemoteDataSourceImpl
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.remote.exception.RemoteThrowable.ApiServiceThrowable
import com.fappslab.viacep.remote.utils.stubmockprovider.StubResponse.addressResponse
import com.fappslab.viacep.remote.utils.stubmockprovider.StubResponse.failureBodyResponse
import com.fappslab.viacep.remote.utils.stubmockprovider.StubResponse.successBodyResponse
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection.HTTP_OK
import javax.net.ssl.HttpsURLConnection.HTTP_BAD_REQUEST
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
internal class FormRepositoryImplRemoteIntegrationTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    @get:Rule
    val remoteRule = RemoteTestRule()

    private lateinit var service: FormService
    private lateinit var subject: FormRepository

    @Before
    fun setUp() {
        service = remoteRule.createTestService()

        subject = FormRepositoryImpl(
            remoteDataSource = FormRemoteDataSourceImpl(
                service = service,
                dispatcher = dispatcherRule.testDispatcher
            ),
            localDataSource = mockk()
        )
    }

    @Test
    fun `getRemoteAddressSuccess Should return success response When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = addressResponse.toAddress()
            remoteRule.mockWebServerResponse(successBodyResponse, code = HTTP_OK)

            // When
            val result = subject.getRemoteAddress(zipcode = "01001-000")

            // Then
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getRemoteAddressFailure Should return failure response When invoke getAddress`() {
        runTest {
            // Given
            val expectedResult = true
            remoteRule.mockWebServerResponse(failureBodyResponse, code = HTTP_BAD_REQUEST)

            // When
            val result = assertFailsWith {
                subject.getRemoteAddress(zipcode = "01001-000")
            } as ApiServiceThrowable

            // Then
            assertEquals(expectedResult, result.error)
        }
    }
}
