package com.kotlin.tutorial.tutorial.service

import com.kotlin.tutorial.tutorial.datasource.BankDataSource
import com.kotlin.tutorial.tutorial.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService ( @Qualifier("mock") private val dataSource: BankDataSource){

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(newBank: Bank): Bank = dataSource.addBank(newBank)

    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)

    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)
}