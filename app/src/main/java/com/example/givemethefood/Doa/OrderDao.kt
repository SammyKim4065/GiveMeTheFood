package com.example.givemethefood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.givemethefood.Data.OrderItem

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: OrderItem)

    @Insert
    suspend fun insertAll(orders: List<OrderItem>)

    @Update
    suspend fun update(order: OrderItem)

    @Delete
    suspend fun delete(order: OrderItem)

    @Query("Delete From order_table")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM order_table Where :orderId == orderId ")
    fun getOrder(orderId: Int):LiveData<OrderItem>
}