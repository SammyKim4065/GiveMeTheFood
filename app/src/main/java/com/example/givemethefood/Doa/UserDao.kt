package com.example.givemethefood.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.givemethefood.Data.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFoodToUser(userFoodCrossRef: UserFoodCrossRef) : Long

    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User): Int

    @Query("Delete From user_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM user_table WHERE :userId == user_table.userId")
    fun getUserById(userId: Int): LiveData<User>

    @Query("SELECT * FROM user_table WHERE :username == user_table.username")
    fun getUserByName(username: String): LiveData<List<User>>


    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE :username == username AND :password == password)")
    suspend fun isLogin(username: String, password: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE :username == username)")
    suspend fun isUsernameExists(username: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE :email == email)")
    suspend fun isEmailExists(email: String): Boolean

    @Query("SELECT isRestaurant FROM user_table WHERE :username == username")
    suspend fun isRestaurant(username: String): Boolean

    @Query("SELECT * FROM user_table")
    fun getAllUserWithFood(): LiveData<List<UserWithFood>>

    @Query("SELECT * FROM user_table WHERE userId == :userId")
    fun getUserWithFoodByName(userId:Int): LiveData<List<UserWithFood>>

    @Query("SELECT * FROM food_table")
    fun getAllFood(): LiveData<List<FoodsItem>>

  @Update
   suspend fun updateUserWithFood(userFoodCrossRef: UserFoodCrossRef)
}




