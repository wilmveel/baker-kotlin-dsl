package com.example.demo

import com.example.demo.baker.Events.OrderPlaced
import com.example.demo.baker.Ingredients
import com.example.demo.baker.Interactions
import com.example.demo.baker.Recipe
import com.ing.baker.compiler.RecipeCompiler
import com.ing.baker.runtime.inmemory.InMemoryBaker
import com.ing.baker.runtime.javadsl.EventInstance
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {

    val compiled = RecipeCompiler.compileRecipe(Recipe.webshopRecipe)

    val baker = InMemoryBaker.java(
        listOf(
            Interactions.MakePaymentInstance(),
            Interactions.ReserveItemsInstance(),
            Interactions.ShipItemsInstance()
        )
    ).apply {
        addRecipe(compiled, true)
    }


    val instanceId = UUID.randomUUID().toString()

    baker
        .bake(compiled.recipeId(), instanceId)
        .thenCompose {
            baker.fireEventAndResolveWhenCompleted(
                instanceId,
                EventInstance.from(
                    OrderPlaced(
                        listOf(
                            Ingredients.Item("123"),
                            Ingredients.Item("456"),
                        )
                    )
                )
            )
        }
        .thenApply {
            println(it.ingredients)
            println(it.eventNames)
        }
        .get()

}
