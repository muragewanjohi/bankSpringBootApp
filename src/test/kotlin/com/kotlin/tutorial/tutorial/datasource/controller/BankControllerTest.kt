package com.kotlin.tutorial.tutorial.datasource.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.tutorial.tutorial.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
     val mockMvc: MockMvc,
     val objectMapper: ObjectMapper
){
    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks`(){
            //when //then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234")}
                }

        }

    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank{

        @Test
        fun `should return the bank with the given account number`(){
            //given
            val accountNumber = 1234

            //when //then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("12.5")}
                    jsonPath("$.transactionFee") { value("10")}
                }

        }

        @Test
        fun `should return not found if the account number doesn't exist`(){
            //given
            val accountNumber = "does_not_exist"

            //when //then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostBank{

        @Test
        fun `should return the bank created`(){
            //given
            val newBank = Bank("8000", 10.5, 100)

            //when
            val performPost = mockMvc.post("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("8000")}
                    jsonPath("$.trust") { value("10.5")}
                    jsonPath("$.transactionFee") { value("100")}
                }
        }

        @Test
        fun `should return bad request if account exists`(){
            //given
            val invalidBank = Bank("1234", 10.5, 100)

            //when
            val performPost = mockMvc.post("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }


}