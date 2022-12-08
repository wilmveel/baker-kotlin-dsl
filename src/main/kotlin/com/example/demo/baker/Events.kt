package com.example.demo.baker

object Events {
    class OrderPlaced(val items: List<Ingredients.Item>)
    class PaymentInformationReceived(val paymentInformation: Ingredients.PaymentInformation)
    class ShippingAddressReceived(val shippingAddress: Ingredients.ShippingAddress)
}