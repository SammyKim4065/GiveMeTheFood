package com.example.givemethefood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Data.RestaurantItem

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(restaurantItem: RestaurantItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRestaurant(restaurants: List<RestaurantItem>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(foodsItem: FoodsItem)

    @Update
    suspend fun update(restaurantItem: RestaurantItem)

    @Delete
    suspend fun delete(restaurantItem: RestaurantItem)

    @Query("Delete from restaurant_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM restaurant_table WHERE restaurant_table.resName = :restaurantName")
    fun getRestaurantByName(restaurantName:String): LiveData<RestaurantItem>

    @Query("SELECT * FROM restaurant_table")
    fun getAllRestaurant(): LiveData<List<RestaurantItem>>

    @Query("SELECT * FROM food_table WHERE :restaurantId = resIdFk")
    fun getAllFood(restaurantId:Int): LiveData<List<FoodsItem>>

//
//    @Query("SELECT EXISTS(SELECT * FROM restaurant_table WHERE :username = username And :password == password)")
//    suspend fun isLoginOfRestaurant(username: String,password:String): Boolean




}