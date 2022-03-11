package com.kotlin.tutorial.tutorial.datasource.mock

import com.kotlin.tutorial.tutorial.datasource.BankDataSource
import com.kotlin.tutorial.tutorial.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockDataSource : BankDataSource {

    val banks = mutableListOf(
        Bank("1234",12.5,10),
        Bank("790",10.5,0),
        Bank("89345",0.0,80),
        Bank("34525",8.0,25),
    Bank("893513452545",10.0,50)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find bank with account Number $accountNumber")
    }

    override fun addBank(newBank: Bank): Bank {
        if(banks.any { it.accountNumber == newBank.accountNumber}){
            throw java.lang.IllegalArgumentException(" Bank with account number ${newBank.accountNumber} already exists")
        }
        banks.add(newBank)

        return newBank
    }
}