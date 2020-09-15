package com.example.givemethefood

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Database.GMTFDatabase
import com.example.givemethefood.Repository.FoodRepository
import com.example.givemethefood.Repository.RestaurantRepository
import com.example.givemethefood.Repository.UserRepository
import com.example.givemethefood.factory.FoodFactory
import com.example.givemethefood.factory.RestaurantFactory
import com.example.givemethefood.factory.UserViewModelFactory
import com.example.givemethefood.viewModel.FoodViewModel
import com.example.givemethefood.viewModel.RestaurantViewModel
import com.example.givemethefood.viewModel.UserViewModel

class ViewModelInstance {


    companion object {
        private  var restaurantViewModel: RestaurantViewModel? = null
        private  var foodViewModel: FoodViewModel? = null
        private  var userViewModel: UserViewModel? = null

        fun getFoodInstance(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            viewModelStoreOwner: ViewModelStoreOwner
        ): FoodViewModel {
            synchronized(this) {
                var foodInstance = foodViewModel
                if (foodInstance == null) {
                    val doa = GMTFDatabase.getInstance(context).foodDao
                    val repo = FoodRepository(doa)
                    val factory = FoodFactory(repo, context, lifecycleOwner)
                    foodInstance = ViewModelProvider(viewModelStoreOwner, factory).get(
                        FoodViewModel(
                            repo, context,
                            lifecycleOwner
                        )::class.java
                    )
                }
                return foodInstance

            }
        }

        fun getRestaurantInstance(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            viewModelStoreOwner: ViewModelStoreOwner
        ): RestaurantViewModel {
            synchronized(this) {
                var resInstance = restaurantViewModel
                if (resInstance == null) {
                    val doa = GMTFDatabase.getInstance(context).restaurantDao
                    val repo = RestaurantRepository(doa)
                    val factory = RestaurantFactory(repo, context, lifecycleOwner)
                    resInstance = ViewModelProvider(viewModelStoreOwner, factory).get(
                        (RestaurantViewModel(
                            repo, context,
                            lifecycleOwner
                        )::class.java)
                    )
                }
                return resInstance

            }
        }

        fun getUserInstance(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            viewModelStoreOwner: ViewModelStoreOwner
        ): UserViewModel {
            synchronized(this) {
                var instance = userViewModel
                if (instance == null) {
                    val doa = GMTFDatabase.getInstance(context).userDao
                    val repo = UserRepository(doa)
                    val factory = UserViewModelFactory(repo, context, lifecycleOwner)
                    instance = ViewModelProvider(viewModelStoreOwner, factory).get(
                        UserViewModel(
                            repo,
                            context,
                            lifecycleOwner
                        )::class.java
                    )
                }
                return instance

            }
        }
    }
}