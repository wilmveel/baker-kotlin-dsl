package com.example.demo

import com.example.demo.baker.Recipe
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class Application(private val baker: Baker) {

    val instanceId: UUID = UUID.randomUUID()
    init {
        runBlocking {
            baker.orderPlaced(instanceId)
            baker.shippingAddressReceived(instanceId)
            baker.paymentInformationReceived(instanceId)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
