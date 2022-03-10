package com.kotlin.tutorial.tutorial.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockDataSourceTest {
    private val mockDataSource = MockDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // when
        val banks = mockDataSource.retrieveBanks()
        //then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`(){
        // when
        val banks = mockDataSource.retrieveBanks()
        //then
        assertThat(banks).allMatch{ it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch{ it.trust != 0.0 }
        assertThat(banks).anyMatch{ it.transactionFee != 0 }
    }
}