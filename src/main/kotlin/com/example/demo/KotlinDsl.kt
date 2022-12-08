import com.example.demo.ScalaExtensions.toScalaMap
import com.example.demo.ScalaExtensions.toScalaSeq
import com.example.demo.ScalaExtensions.toScalaSet
import com.ing.baker.recipe.common.EventOutputTransformer
import com.ing.baker.recipe.common.Ingredient
import com.ing.baker.recipe.javadsl.Event
import com.ing.baker.recipe.javadsl.InteractionDescriptor
import com.ing.baker.recipe.javadsl.Recipe
import com.ing.baker.types.Converters
import com.ing.baker.types.Value
import scala.Option
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaType

fun interactionFunctionToCommonInteraction(func: KFunction<*>, events: Set<KClass<*>>): InteractionDescriptor {
    val inputIngredients = func.parameters.drop(1)
        .map {
            val type = Converters.readJavaType(it.type.javaType)
            Ingredient(it.name, type)
        }
    val owner = (func as CallableReference).owner
    val name = (owner as KClass<*>).simpleName



    return InteractionDescriptor(
        name,
        inputIngredients.toScalaSeq(),
        events.map { Event(it.java, Option.empty()) }.toScalaSeq(),
        listOf<String>().toScalaSet(),
        setOf<scala.collection.immutable.Set<String>>().toScalaSet(),
        mapOf<String, Value>().toScalaMap(),
        mapOf<String, String>().toScalaMap(),
        Option.empty(),
        Option.empty(),
        Option.empty(),
        mapOf<com.ing.baker.recipe.common.Event, EventOutputTransformer>().toScalaMap(),
        Option.empty(),
    )
}

fun interaction(init: InteractionBuilder.() -> Unit): InteractionDescriptor {
    val builder = InteractionBuilder()
    builder.apply(init)
    return interactionFunctionToCommonInteraction(builder.func, builder.events)
}

class InteractionBuilder {

    lateinit var func: KFunction<*>
    lateinit var events: Set<KClass<*>>

    fun func(func: KFunction<*>) {
        val sealedSubclasses = (func.returnType.classifier as KClass<*>).sealedSubclasses
        if(sealedSubclasses.isNotEmpty()){
            this.events = sealedSubclasses.toSet()
        }
        this.func = func
    }

    fun events(vararg events: KClass<*>) {
        this.events = events.toSet()
    }
}


fun recipe(init: RecipeBuilder.() -> Unit): Recipe {
    val builder = RecipeBuilder()
    builder.apply(init)
    val recipe = Recipe(builder.name)
        .withInteractions(builder.interactions.toScalaSeq())
        .withSensoryEvents(builder.sensoryEvents.map { it.java }.toScalaSeq())
    return recipe
}

class RecipeBuilder {

    lateinit var name: String
    lateinit var interactions: Set<InteractionDescriptor>
    lateinit var sensoryEvents: Set<KClass<*>>

    fun interactions(vararg interactions: InteractionDescriptor) {
        this.interactions = interactions.toSet()
    }

    fun sensoryEvents(vararg sensoryEvents: KClass<*>) {
        this.sensoryEvents = sensoryEvents.toSet()
    }

}
