package com.example.givemethefood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.Data.UserFoodCrossRef

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(food: FoodsItem)
    @Insert
    suspend fun insertAllFood(allFood: List<FoodsItem>)
    @Update
    suspend fun update(food: FoodsItem)
    @Delete
    suspend fun deleteFood(food: FoodsItem)

    @Query("Delete From food_table")
    suspend fun delete()

    @Query("SELECT * FROM food_table")
    fun getAllFood() : LiveData<List<FoodsItem>>

    @Query("SELECT * FROM food_table Where :foodname == foodname")
    fun getFoodByName(foodname:String):LiveData<List<FoodsItem>>

    @Query("SELECT * FROM food_table Where :type == foodtype")
    fun getFoodByType(type:String):LiveData<List<FoodsItem>>

    @Query("SELECT * FROM  restaurant_table Where :id == resId")
    fun getFoodByRestaurantName(id:Int):LiveData<RestaurantItem>

    @Transaction
    @Query("Update food_table Set piece = piece + :piece where :id = foodId")

    suspend fun addPiece(id:Int, piece:Int) : Int
    @Transaction
    @Query("Update food_table Set piece = piece - :piece where :id = foodId")
    suspend fun minusPiece(id:Int, piece:Int) : Int

    @Query("Update food_table Set isfev = :fev where :id = foodId")
    suspend fun updateFavourite(id:Int, fev:Boolean) : Int

    @Query("Update food_table Set piece = 0")
    suspend fun clearPiece() : Int

    @Query("Update food_table Set piece = 0 + piece where :id == foodId")
    suspend fun clearPieceById(id:Int) : Int

    @Query("Select Sum(foodprice * piece) from food_table where piece > 0")
     fun getTotalPrice() : LiveData<Int>

}