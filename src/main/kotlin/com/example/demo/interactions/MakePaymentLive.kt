package com.example.demo.interactions

import com.example.demo.baker.Ingredients
import com.example.demo.baker.Interactions
import com.example.demo.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class MakePaymentLive(val paymentRepository: PaymentRepository) : Interactions.MakePayment {
    override fun apply(
        reservedItems: Ingredients.ReservedItems,
        paymentInformation: Ingredients.PaymentInformation
    ): Interactions.MakePayment.MakePaymentOutcome {
        paymentRepository.savePayment(paymentInformation.info)
        return Interactions.MakePayment.PaymentSuccessful()
    }
}