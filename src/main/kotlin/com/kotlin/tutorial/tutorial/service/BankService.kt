package com.kotlin.tutorial.tutorial.service

import com.kotlin.tutorial.tutorial.datasource.BankDataSource
import com.kotlin.tutorial.tutorial.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService (private val dataSource: BankDataSource){

    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank {
        return dataSource.retrieveBank(accountNumber)
    }
}