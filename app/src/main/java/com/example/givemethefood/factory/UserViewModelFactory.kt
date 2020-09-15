package com.example.givemethefood.factory

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.givemethefood.Repository.UserRepository
import com.example.givemethefood.viewModel.UserViewModel
import java.lang.IllegalArgumentException

class UserViewModelFactory(var repo:UserRepository, var context:
Context,var lifecycleOwner: LifecycleOwner) :
    ViewModelProvider
.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repo,context,lifecycleOwner) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}