package com.example.givemethefood.Data

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.google.gson.annotations.Expose

@Entity(tableName = "food_table", indices = [Index(value = ["foodId"],unique = true)])
data class FoodsItem(
    @PrimaryKey
    val foodId: Int?,
    val foodname: String?,
    val foodpic: String?,
    val foodprice: String?,
    val foodtype: String?,
    val ingredient: List<String>?,
    var isfev: Boolean?,
    val resIdFk: Int?,
    var piece : Int
)