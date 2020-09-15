package com.example.givemethefood.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class OrderItem(
    @PrimaryKey
    val orderId: String,
    val driverId:String ,
    val foodlist: List<String>,
    val totalprice: Int,
    val userid: String
)