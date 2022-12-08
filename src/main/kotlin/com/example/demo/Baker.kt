package com.example.demo

import com.example.demo.baker.Events
import com.example.demo.baker.Ingredients
import com.example.demo.baker.Recipe.webshopRecipe
import com.ing.baker.compiler.RecipeCompiler
import com.ing.baker.runtime.inmemory.InMemoryBaker
import com.ing.baker.runtime.javadsl.EventInstance
import convertRecipe
import kotlinx.coroutines.future.await
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import ownerClass
import java.util.*

@Service
class Baker(
    context: ApplicationContext,
) {
    val recipe = convertRecipe(webshopRecipe)
    val compiled = RecipeCompiler.compileRecipe(recipe)
    val baker = InMemoryBaker
        .java(
            webshopRecipe.interactions
                .map { it.func.ownerClass().java }
                .map { context.getBean(it) }
        )
        .apply {
            addRecipe(compiled, true)
        }

    suspend fun orderPlaced(instanceId: UUID) {
        baker.bake(compiled.recipeId(), instanceId.toString()).await()
        baker
            .fireEventAndResolveWhenCompleted(
                instanceId.toString(),
                EventInstance.from(
                    Events.OrderPlaced(
                        items = listOf(
                            Ingredients.Item("123"),
                            Ingredients.Item("456"),
                        )
                    )
                )
            )
            .thenApply {
                println(it.ingredients)
                println(it.eventNames)
            }
            .await()
    }

    suspend fun shippingAddressReceived(instanceId: UUID) {
        baker
            .fireEventAndResolveWhenCompleted(
                instanceId.toString(),
                EventInstance.from(
                    Events.ShippingAddressReceived(
                        shippingAddress = Ingredients.ShippingAddress(
                            address = "The Netherlands"
                        )
                    )
                )
            )
            .thenApply {
                println(it.ingredients)
                println(it.eventNames)
            }
            .await()
    }

    suspend fun paymentInformationReceived(instanceId: UUID) {
        baker
            .fireEventAndResolveWhenCompleted(
                instanceId.toString(),
                EventInstance.from(
                    Events.PaymentInformationReceived(
                        paymentInformation = Ingredients.PaymentInformation(
                            info = "100 EURO"
                        )
                    )
                )
            )
            .await()
    }
}