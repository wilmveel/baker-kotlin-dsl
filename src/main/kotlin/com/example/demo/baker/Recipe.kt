package com.example.demo.baker

import interaction
import recipe

object Recipe {
    val webshopRecipe = recipe {
        name = "WebshopRecipe"
        interactions(
            interaction {
                func (
                    Interactions.MakePayment::apply
                )
//                events(
//                    Interactions.MakePayment.PaymentSuccessful::class,
//                    Interactions.MakePayment.PaymentFailed::class,
//                )
            },
            interaction {
                func = Interactions.ReserveItems::apply
                events(
                    Interactions.ReserveItems.OrderHadUnavailableItems::class,
                    Interactions.ReserveItems.ItemsReserved::class
                )
            },
            interaction {
                func = Interactions.ShipItems::apply
                events(
                    Interactions.ShipItems.ShippingConfirmed::class
                )
            }.withRequiredEvent(Interactions.MakePayment.PaymentSuccessful::class.java)
        )
        sensoryEvents(
            Events.OrderPlaced::class,
            Events.PaymentInformationReceived::class,
            Events.ShippingAddressReceived::class
        )
    }
}
