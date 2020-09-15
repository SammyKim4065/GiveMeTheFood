package com.example.givemethefood.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Dao.UserDao
import com.example.givemethefood.Data.UserFoodCrossRef
import com.example.givemethefood.Data.User
import com.example.givemethefood.Data.UserWithFood
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// we need to call the functions of the Dao from this repository
class UserRepository(var userDao: UserDao) {

    val allUserWithFood = userDao.getAllUserWithFood()

    val allFood = userDao.getAllFood()

    suspend fun updateUserWithFood(userFoodCrossRef: UserFoodCrossRef){
        userDao.updateUserWithFood(userFoodCrossRef)
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    } //

    suspend fun insertFoodToUser(userWithFood: UserFoodCrossRef): Long {
        return userDao.insertFoodToUser(userWithFood)
    } //

    fun getUserWithFoodByName(userId:Int): LiveData<List<UserWithFood>> {
        return userDao.getUserWithFoodByName(userId)
    }

    fun getUserByName(username: String): LiveData<List<User>>{
        return userDao.getUserByName(username)
    }
    suspend fun updateUser(user: User): Int {
        return userDao.update(user)
    }

    suspend fun deleteUser(user: User): Int {
        return userDao.delete(user)
    }

    suspend fun deleteAll(): Int {
        return userDao.deleteAll()
    }

    suspend fun validateUser(username: String, password: String): Boolean {
        return userDao.isLogin(username, password)
    }

    suspend fun isUsernameExists(username: String): Boolean {
        return userDao.isUsernameExists(username)
    }

    suspend fun isEmailExists(email: String): Boolean {
        return userDao.isEmailExists(email)
    }

    suspend fun isLoginOfRestaurant(username: String): Boolean {
        return userDao.isRestaurant(username)
    }

     fun callApiAndPutInDB() {
         val restApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

         restApi.getAllUser().enqueue(object : Callback<List<User>> {

             override fun onFailure(call: Call<List<User>>, t: Throwable) {
                 Log.i("restaurant", "Error insert all restaurant to Database")
             }

             override fun onResponse(
                 call: Call<List<User>>,
                 response: Response<List<User>>
             ) {
                 when (response.code()) {
                     200 -> {
                         CoroutineScope(Dispatchers.IO).launch {
                             userDao.deleteAll()
                             userDao.insertAllUser(response.body()!!)
                         }.start()
                     }
                 }
             }
         })

    }

}