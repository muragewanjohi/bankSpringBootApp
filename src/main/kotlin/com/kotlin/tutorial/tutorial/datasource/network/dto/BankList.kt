package com.kotlin.tutorial.tutorial.datasource.network.dto

import com.kotlin.tutorial.tutorial.model.Bank

data class BankList (
    var results: Collection<Bank>
        ){
}