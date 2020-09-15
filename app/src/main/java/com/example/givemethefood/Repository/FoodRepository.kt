package com.example.givemethefood.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Dao.FoodDao
import com.example.givemethefood.Data.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRepository(var foodDao: FoodDao) {
    private val restApi: RestApi =
        RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)
    var getAllFood = foodDao.getAllFood()

    var getTotalPrice = foodDao.getTotalPrice()

    suspend fun insertFood(food: FoodsItem) {
        foodDao.insertFood(food)
    }

    fun getFoodByName(foodname: String): LiveData<List<FoodsItem>> {
        return foodDao.getFoodByName(foodname)
    }

    fun getFoodByType(type: String): LiveData<List<FoodsItem>> {
        return foodDao.getFoodByType(type)
    }

    suspend fun updateFood(food: FoodsItem) {
        foodDao.update(food)
    }

    suspend fun addPiece(id: Int, food: FoodsItem) {
        foodDao.addPiece(id, food.piece)
    } //

    suspend fun minusPiece(id: Int, food: FoodsItem) {
        foodDao.minusPiece(id, food.piece)
    } //

    suspend fun updateFavourite(id: Int, food: FoodsItem) {
        foodDao.updateFavourite(id, food.isfev!!)
    } //

    fun getResNameOfFoodById(id: Int): LiveData<RestaurantItem> {
        return foodDao.getFoodByRestaurantName(id)
    }


    suspend fun callApiAndPutInDB() {

        val restApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

        restApi.getAllFoods().enqueue(object : Callback<List<FoodsItem>> {

            override fun onFailure(call: Call<List<FoodsItem>>, t: Throwable) {
                Log.i("Food", "Error insert all food to Database")
            }

            override fun onResponse(
                call: Call<List<FoodsItem>>,
                response: Response<List<FoodsItem>>
            ) {
                when (response.code()) {
                    200 -> {
                        CoroutineScope(IO).launch {
                            foodDao.delete()
                            foodDao.insertAllFood(response.body()!!)
                        }.start()
                    }
                }
            }
        })

    }
}


