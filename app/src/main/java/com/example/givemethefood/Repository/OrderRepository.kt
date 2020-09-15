package com.example.givemethefood.Repository

import android.util.Log
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Dao.OrderDao
import com.example.givemethefood.Data.OrderItem
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(var orderDao: OrderDao) {

    suspend fun insertOrder(order: OrderItem){
        orderDao.insert(order)
    }
    suspend fun updateOrder(order: OrderItem){
        orderDao.update(order)
    }
    suspend fun deleteOrder(order: OrderItem){
        orderDao.delete(order)
    }
    fun getOrder(orderId: Int){
        orderDao.getOrder(orderId)
    }

    suspend fun callApiAndPutInDB(){

        val restApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

        restApi.getAllOrder().enqueue(object : Callback<List<OrderItem>> {

            override fun onFailure(call: Call<List<OrderItem>>, t: Throwable) {
                Log.i("Food","Error insert all order to Database")
            }

            override fun onResponse(call: Call<List<OrderItem>>, response: Response<List<OrderItem>>) {
                when (response.code()) {
                    200 -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            orderDao.deleteAll()
                            orderDao.insertAll(response.body()!!)
                        }.start()
                    }
                }
            }
        })

    }
}