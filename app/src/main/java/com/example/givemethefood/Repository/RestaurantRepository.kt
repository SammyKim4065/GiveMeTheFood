package com.example.givemethefood.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Dao.RestaurantDao
import com.example.givemethefood.Data.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantRepository(var restaurantDao: RestaurantDao) {

    var AllRestaurant = restaurantDao.getAllRestaurant()

    fun getRestaurant(restaurantName: String):LiveData<RestaurantItem>{
        return restaurantDao.getRestaurantByName(restaurantName)
    }

    fun getAllFood(restaurantId: Int): LiveData<List<FoodsItem>> {
        return restaurantDao.getAllFood(restaurantId)
    }

    suspend fun insertRestaurant(res: RestaurantItem){
        restaurantDao.insert(res)
    }

    suspend fun insertFood(food: FoodsItem){
        restaurantDao.insertFood(food)
    }

    suspend fun updateRestaurant(res: RestaurantItem){
        restaurantDao.update(res)
    }
    suspend fun deleteRestaurant(restaurantEntity: RestaurantItem){
        restaurantDao.delete(restaurantEntity)
    }
    suspend fun deleteAllRestaurant(){
        restaurantDao.deleteAll()
    }

    suspend fun callApiAndPutInDB(){

        val restApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

        restApi.getAllRestaurant().enqueue(object : Callback<List<RestaurantItem>> {

            override fun onFailure(call: Call<List<RestaurantItem>>, t: Throwable) {
                Log.i("restaurant","Error insert all restaurant to Database")
            }

            override fun onResponse(call: Call<List<RestaurantItem>>, response: Response<List<RestaurantItem>>) {
                when (response.code()) {
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            restaurantDao.deleteAll()
                            restaurantDao.insertAllRestaurant(response.body()!!)
                        }.start()
                    }
                }
            }
        })

    }

}