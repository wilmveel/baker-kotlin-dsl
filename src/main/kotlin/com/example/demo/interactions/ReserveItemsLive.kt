package com.example.demo.interactions

import com.example.demo.baker.Ingredients
import com.example.demo.baker.Interactions.ReserveItems
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReserveItemsLive : ReserveItems {
    override fun apply(items: List<Ingredients.Item>): ReserveItems.ReserveItemsOutcome {
        val b = ByteArray(20)
        Random().nextBytes(b)
        return ReserveItems.ItemsReserved(Ingredients.ReservedItems(items, String(b)))
    }
}