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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc, val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            //when //then
            mockMvc.get(baseUrl).andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].account_number") { value("1234") }
            }

        }

    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number`() {
            //given
            val accountNumber = 1234

            //when //then
            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.trust") { value("12.5") }
                jsonPath("$.default_transaction_fee") { value("10") }
            }

        }

        @Test
        fun `should return not found if the account number doesn't exist`() {
            //given
            val accountNumber = "does_not_exist"

            //when //then
            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostBank {

        @Test
        fun `should return the bank created`() {
            //given
            val newBank = Bank("8000", 10.5, 100)

            //when
            val performPost = mockMvc.post("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost.andDo { print() }.andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newBank))
                }

            }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }
        }

        @Test
        fun `should return bad request if account exists`() {
            //given
            val invalidBank = Bank("1234", 10.5, 100)

            //when
            val performPost = mockMvc.post("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost.andDo { print() }.andExpect {
                status { isBadRequest() }
            }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchBank {

        @Test
        fun `should return the bank updated`() {
            //given
            val updatedBank = Bank("1234", 10.5, 100)

            //when
            val performPost = mockMvc.patch("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            //then
            performPost.andDo { print() }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))
                }

            }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
        }

        @Test
        fun `should not update if the account number doesn't exist`() {
            //given
            val invalidBank = Bank("account1234", 10.5, 100)

            //when
            val performPatch = mockMvc.patch("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPatch.andDo { print() }.andExpect {
                status { isNotFound() }
            }

        }

    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {

        @Test
        @DirtiesContext
        fun `should delete the bank with the given account number`() {
            //given
            val accountNumber = 1234

            //when //then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                status { isNoContent() }

            }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }

        }

        @Test
        fun `should not delete if account number doesn't exist`() {
            //given
            val invalidAccountNumber = "does_not_exist"

            //when //then
            mockMvc.get("$baseUrl/$invalidAccountNumber").andDo { print() }.andExpect {
                status { isNotFound() }
            }
        }

    }

}