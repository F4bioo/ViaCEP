package com.fappslab.viacep.form.data.repository

import com.fappslab.viacep.arch.rules.DispatcherTestRule
import com.fappslab.viacep.arch.rules.LocalTestRule
import com.fappslab.viacep.form.data.moddel.extension.toAddress
import com.fappslab.viacep.form.data.moddel.extension.toAddressEntity
import com.fappslab.viacep.form.data.source.local.FormLocalDataSourceImpl
import com.fappslab.viacep.form.domain.model.Address
import com.fappslab.viacep.form.domain.repository.FormRepository
import com.fappslab.viacep.local.database.FormDatabase
import com.fappslab.viacep.remote.utils.stubmockprovider.StubResponse.addressResponse
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class FormRepositoryImplLocalIntegrationTest {

    @get:Rule
    val dispatcherRule = DispatcherTestRule()

    @get:Rule
    val localRule = LocalTestRule()

    private lateinit var database: FormDatabase
    private lateinit var subject: FormRepository

    @Before
    fun setUp() {
        database = localRule.createTestDatabase()

        subject = FormRepositoryImpl(
            remoteDataSource = mockk(),
            localDataSource = FormLocalDataSourceImpl(
                database = database
            )
        )
    }

    @Test
    fun getLocalAddressSuccess_Should_return_address_list_When_invoke_getLocalAddress_from_database() {
        runTest {
            // Given
            val expectedAddress = addressResponse.toAddress().setAddress()

            // When
            val result = subject.getLocalAddress(zipcode = "01001-000")

            // Then
            assertEquals(expectedAddress, result)
        }
    }

    @Test
    fun getLocalAddressesSuccess_Should_return_address_list_When_invoke_getLocalAddresses_from_database() {
        runTest {
            // Given
            val address = addressResponse.toAddress()
            val address1 = address.setAddress()
            val address2 = address.copy(zipcode = "59090-175").setAddress()
            val expectedAddresses = listOf(address1, address2)

            // When
            val result = subject.getLocalAddresses()

            // Then
            assertEquals(expectedAddresses, result)
        }
    }

    @Test
    fun deleteAddressSuccess_Should_delete_address_list_When_invoke_deleteAddress_from_database() {
        runTest {
            // Given
            val address = addressResponse.toAddress()
            val address1 = address.setAddress()
            val address2 = address.copy(zipcode = "59090-175").setAddress()
            address1.deleteAddress()
            address2.deleteAddress()
            val expectedResult = emptyList<Address>()

            // When
            val result = subject.getLocalAddresses()

            // Then
            assertEquals(expectedResult, result)
        }
    }

    private suspend fun Address.setAddress(): Address {
        database.formDao().setAddress(toAddressEntity())
        return this
    }

    private suspend fun Address.deleteAddress() {
        database.formDao().deleteAddress(zipcode)
    }
}
