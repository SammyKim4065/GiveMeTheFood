package com.example.givemethefood.Data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
@Entity
data class UserWithFood(
    @Embedded val user: User?,
    @Relation(
        parentColumn = "userId",
        entityColumn = "foodId",
        associateBy = Junction(UserFoodCrossRef::class)
    )
    var foods: List<FoodsItem>?
)