package com.example.givemethefood.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.givemethefood.Dao.*
import com.example.givemethefood.Data.*
import com.example.givemethefood.Helper.Converters

@Database(entities = [
    FoodsItem::class,
    OrderItem::class,
    RestaurantItem::class,
    UserFoodCrossRef::class,
    User::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GMTFDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val foodDao: FoodDao
    abstract val orderDao: OrderDao
    abstract val restaurantDao: RestaurantDao

    companion object{
        @Volatile
       private var INSTANCE : GMTFDatabase? = null
        fun getInstance(context: Context):GMTFDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GMTFDatabase::class.java,
                        "GMTF_DB"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}


