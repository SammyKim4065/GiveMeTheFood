package com.example.givemethefood.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FavouriteFood(
    @PrimaryKey
    val id: Int,
    val foodId: Int,
    val userId: Int
)