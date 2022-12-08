package com.example.demo.repository

import org.springframework.stereotype.Repository

@Repository
class PaymentRepository {

    fun savePayment(info: String){
        println("--- Store payment: $info ---")
    }
}