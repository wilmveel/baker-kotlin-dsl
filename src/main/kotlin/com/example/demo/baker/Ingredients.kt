package com.example.demo.baker

object Ingredients {
    data class Item(val itemId: String)
    data class PaymentInformation(val info: String)
    data class ReservedItems(val items: List<Item>, val date: String)
    data class ShippingAddress(val address: String)
}
