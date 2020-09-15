package com.example.givemethefood.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["userId", "foodId"])
data class UserFoodCrossRef(
    val userId: Int = 0,
    val foodId: Int = 0
)