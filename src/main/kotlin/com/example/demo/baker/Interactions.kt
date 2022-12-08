package com.example.demo.baker

import com.ing.baker.recipe.javadsl.Interaction

import java.util.*

object Interactions {

    interface MakePayment : Interaction {
        sealed interface MakePaymentOutcome
        class PaymentSuccessful : MakePaymentOutcome
        class PaymentFailed : MakePaymentOutcome

        fun apply(
            reservedItems: Ingredients.ReservedItems,
            paymentInformation: Ingredients.PaymentInformation
        ): MakePaymentOutcome
    }

    interface ShipItems : Interaction {
        class ShippingConfirmed

        fun apply(
            shippingAddress: Ingredients.ShippingAddress,
            reservedItems: Ingredients.ReservedItems
        ): ShippingConfirmed
    }

    interface ReserveItems : Interaction {
        interface ReserveItemsOutcome
        class OrderHadUnavailableItems(val unavailableItems: List<Ingredients.Item>) : ReserveItemsOutcome
        class ItemsReserved(val reservedItems: Ingredients.ReservedItems) : ReserveItemsOutcome

        fun apply(items: List<Ingredients.Item>): ReserveItemsOutcome
    }

}
