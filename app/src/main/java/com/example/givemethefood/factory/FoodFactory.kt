package com.example.givemethefood.factory

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.givemethefood.Repository.FoodRepository
import com.example.givemethefood.viewModel.FoodViewModel
import java.lang.IllegalArgumentException

class FoodFactory(var repo : FoodRepository,var context:Context,var lifecycleOwner: LifecycleOwner) : ViewModelProvider
.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            return FoodViewModel(repo,context,lifecycleOwner) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}