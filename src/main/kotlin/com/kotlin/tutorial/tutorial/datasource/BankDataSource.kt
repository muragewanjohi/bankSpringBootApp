package com.kotlin.tutorial.tutorial.datasource

import com.kotlin.tutorial.tutorial.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun addBank(newBank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBank(accountNumber: String): Unit
}