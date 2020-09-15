package com.example.givemethefood.API


import com.example.givemethefood.Data.*
import retrofit2.Call
import retrofit2.http.*


interface RestApi {

    //User
    @GET("/User")
    fun getAllUser(): Call<List<User>>

    @GET("/User")
     fun validateUser(
        @Query("username")
        username: String,
        @Query("password")
        password: String
    ): Call<User>

    @POST("/User")
    fun insertUser(@Body user: User): Call<User>



    //Restaurant
    @GET("/Restaurant")
     fun getAllRestaurant(): Call<List<RestaurantItem>>

    @PATCH("/Restaurant")
     fun updateListFoodInRestaurant(
        @Path("id") id: Int, @Body foodsItem:
        FoodsItem
    ): Call<RestaurantItem>


    //Food
    @GET("/Food")
     fun getAllFoods(): Call<List<FoodsItem>>

    @GET("/Food")
     fun getAllFoodsByType(@Query("foodtype") foodType: String): Call<List<FoodsItem>>

    @POST("/Food")
     fun insertFood(@Body foodsItem: FoodsItem): Call<FoodsItem>

    @PUT("/Food")
     fun updateFood(@Body foodsItem: FoodsItem)

    @PUT("/Food")
    suspend fun AddIdFoodInRestaurant(@Body id: String)

    @DELETE("/Food")
    suspend fun deleteFood(@Body foodsItem: FoodsItem)

    @PATCH("/Food/{id}")
     fun updatePiece(@Path("id") id: Int, @Body test: FoodsItem?): Call<FoodsItem>

    @PATCH("/Food/{id}")
     fun updateFavourite(@Path("id") id: Int, @Body test: FoodsItem?): Call<FoodsItem>

    //Order
    @GET("/Order")
     fun getAllOrder(): Call<List<OrderItem>>
}