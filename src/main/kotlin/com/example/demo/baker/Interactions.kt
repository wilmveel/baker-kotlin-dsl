package com.example.demo.baker

import com.ing.baker.recipe.javadsl.Interaction

import java.util.*

object Interactions {
    //--- MakePayment
    interface MakePayment : Interaction {
        sealed interface MakePaymentOutcome
        object PaymentSuccessful : MakePaymentOutcome
        object PaymentFailed : MakePaymentOutcome

        fun apply(
            reservedItems: Ingredients.ReservedItems,
            paymentInformation: Ingredients.PaymentInformation
        ): MakePaymentOutcome
    }

    class MakePaymentInstance : MakePayment {
        override fun apply(
            reservedItems: Ingredients.ReservedItems,
            paymentInformation: Ingredients.PaymentInformation
        ): MakePayment.MakePaymentOutcome {
            return MakePayment.PaymentSuccessful
        }
    }

    //--- ShipItems
    interface ShipItems : Interaction {
        object ShippingConfirmed

        fun apply(
            shippingAddress: Ingredients.ShippingAddress,
            reservedItems: Ingredients.ReservedItems
        ): ShippingConfirmed
    }

    class ShipItemsInstance : ShipItems {
        override fun apply(
            shippingAddress: Ingredients.ShippingAddress,
            reservedItems: Ingredients.ReservedItems
        ): ShipItems.ShippingConfirmed {
            return ShipItems.ShippingConfirmed
        }
    }

    //--- ReserveItems
    interface ReserveItems : Interaction {
        interface ReserveItemsOutcome
        class OrderHadUnavailableItems(val unavailableItems: List<Ingredients.Item>) : ReserveItemsOutcome
        class ItemsReserved(val reservedItems: Ingredients.ReservedItems) : ReserveItemsOutcome

        fun apply(items: List<Ingredients.Item>): ReserveItemsOutcome
    }

    class ReserveItemsInstance : ReserveItems {
        override fun apply(items: List<Ingredients.Item>): ReserveItems.ReserveItemsOutcome {
            val b = ByteArray(20)
            Random().nextBytes(b)
            return ReserveItems.ItemsReserved(Ingredients.ReservedItems(items, String(b)))
        }
    }
}
