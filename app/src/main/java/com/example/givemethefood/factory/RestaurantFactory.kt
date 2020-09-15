package com.example.givemethefood.factory

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.givemethefood.Repository.RestaurantRepository
import com.example.givemethefood.viewModel.RestaurantViewModel
import java.lang.IllegalArgumentException

class RestaurantFactory(var repo:RestaurantRepository,var context: Context,var lifecycleOwner: LifecycleOwner ) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            return RestaurantViewModel(repo,context,lifecycleOwner) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}