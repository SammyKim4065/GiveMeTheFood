package com.example.givemethefood.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.givemethefood.Database.CachingDB
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.viewModel.FoodViewModel
import com.example.givemethefood.viewModel.RestaurantViewModel
import com.example.givemethefood.viewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetworkConnected(this)
        Handler().postDelayed({
            startActivity(
                Intent(
                    baseContext,
                    LoginActivity::class.java
                )
            )
            finish()
        }, 1000)

    }

    private fun isNetworkConnected(context: Context) {
        val isConnect = CachingDB.isNetworkConnected(context)
        CoroutineScope(Dispatchers.IO).launch {
            if (isConnect) {
                foodViewModelProvider().callApiAndPutInDB()
                restaurantViewModelProvider().callApiAndPutInDB()
                userViewModelProvider().refreshAllUser()
            } else
                CoroutineScope(Main).launch {
                    Toast.makeText(
                        this@SplashScreenActivity,
                        "No internet found. Showing cached list in the view",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun restaurantViewModelProvider():RestaurantViewModel{
        return   ViewModelInstance.getRestaurantInstance(
            this@SplashScreenActivity,
            this@SplashScreenActivity,
            this@SplashScreenActivity
        )
    }

    private fun userViewModelProvider(): UserViewModel {
        return ViewModelInstance.getUserInstance(
            this@SplashScreenActivity,
            this@SplashScreenActivity,
            this@SplashScreenActivity
        )
    }

    private fun foodViewModelProvider(): FoodViewModel {
        return ViewModelInstance.getFoodInstance(
            this@SplashScreenActivity,
            this@SplashScreenActivity,
            this@SplashScreenActivity
        )
    }


}