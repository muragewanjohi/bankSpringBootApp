package com.kotlin.tutorial.tutorial.datasource.mock

import com.kotlin.tutorial.tutorial.datasource.BankDataSource
import com.kotlin.tutorial.tutorial.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockDataSource : BankDataSource {

    val banks = listOf(
        Bank("1234",12.5,10),
        Bank("790",10.5,0),
        Bank("89345",0.0,80)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find bank with account Number $accountNumber")
    }
}