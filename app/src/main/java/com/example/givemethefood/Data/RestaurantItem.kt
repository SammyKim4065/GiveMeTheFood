package com.example.givemethefood.Data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table",indices = [Index(value = ["resId"],unique = true)])
data class RestaurantItem(
    @PrimaryKey
    val resId: Int?,
    val listfood: List<String>?,
    val resPicture: String?,
    val resname: String?,
    val userIdFk: Int?
)