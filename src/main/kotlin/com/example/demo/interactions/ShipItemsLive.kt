package com.example.demo.interactions

import com.example.demo.baker.Ingredients
import com.example.demo.baker.Interactions
import org.springframework.stereotype.Service

@Service
class ShipItemsLive : Interactions.ShipItems {
    override fun apply(
        shippingAddress: Ingredients.ShippingAddress,
        reservedItems: Ingredients.ReservedItems
    ): Interactions.ShipItems.ShippingConfirmed {
        println("-- Ship items ${reservedItems.date} ---")
        return Interactions.ShipItems.ShippingConfirmed()
    }
}