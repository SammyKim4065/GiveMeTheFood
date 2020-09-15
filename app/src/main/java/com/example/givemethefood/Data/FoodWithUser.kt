package com.example.givemethefood.Data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
@Entity
data class FoodWithUser(
    @Embedded val foodsItem: FoodsItem,
    @Relation(
        parentColumn ="foodId" ,
        entityColumn = "userId",
        associateBy = Junction(UserFoodCrossRef::class)
    )
    val users: List<User>
)