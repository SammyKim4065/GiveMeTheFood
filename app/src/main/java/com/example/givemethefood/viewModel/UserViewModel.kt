package com.example.givemethefood.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Activity.DashboardActivity
import com.example.givemethefood.Data.UserFoodCrossRef
import com.example.givemethefood.Data.User
import com.example.givemethefood.Data.UserWithFood
import com.example.givemethefood.Repository.UserRepository
import com.example.givemethefood.event.Event
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(
    var user_repository: UserRepository, var context: Context, var lifecycleOwner: LifecycleOwner
) : ViewModel(), Observable {

    private val restApi: RestApi =
        RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

    val allFood = user_repository.allFood

    val allUserWithFood = user_repository.allUserWithFood

    private val eventMessage = MutableLiveData<Event<String>>()

    val getEventMessage: LiveData<Event<String>>
        get() = eventMessage

    private val navigationTo = MutableLiveData<Event<Intent>>()

    val navigation_to: LiveData<Event<Intent>>
        get() = navigationTo

    @Bindable
    var inputUsername = MutableLiveData<String>()

    @Bindable
    var inputEmail = MutableLiveData<String>()

    @Bindable
    var inputPassword = MutableLiveData<String>()


    fun login() {
        when {
            inputUsername.value == null -> {
                eventMessage.value = Event("Please Enter your username")
            }
            inputPassword.value == null -> {
                eventMessage.value = Event("Please enter your password")
            }
            else -> isLogin()
        }
    }

    private fun isLogin() = viewModelScope.launch {
        val username = inputUsername.value!!
        val password = inputPassword.value!!

        when (user_repository.validateUser(username, password)) {
            true -> {
                    user_repository.getUserByName(username).observe(lifecycleOwner, Observer {
                        for (item in it) {
                            val intent = Intent(context, DashboardActivity::class.java)
                            intent.putExtra("currentUser", item.userId)
                            navigationTo.value = Event(intent)
                        }
                    })
            }
            else -> eventMessage.value =
                Event("Username or Password is not correct, Please try again")
        }
    }


    fun register() {
        when {
            inputUsername.value == null -> eventMessage.value = Event("Please Enter your username")
            inputEmail.value == null -> eventMessage.value = Event("Please enter your email")
            inputPassword.value == null -> eventMessage.value = Event("Please enter your password")
            !Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches() -> eventMessage.value =
                Event("Please enter correct email")
            else -> isRegister()
        }
    }


    private fun isRegister() = viewModelScope.launch {
        val username = inputUsername.value!!
        val email = inputEmail.value!!
        val password = inputPassword.value!!

        when (user_repository.isEmailExists(email) || user_repository.isUsernameExists(username)) {
            true -> eventMessage.value = Event("The user have already exists")
            else -> {
                val user = User(0, email, false, password, username)

                restApi.insertUser(user).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Event("Unsuccessfully, Please try again")
                    }

                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        val postUser = response.body()!!
                        if (response.isSuccessful) {
                            Log.d("POST USER", "POST ${response.body()}")

                            allFood.observe(lifecycleOwner, Observer {
                                for (food in it) {

                                    val userFoodCrossRef = UserFoodCrossRef(
                                        postUser.userId!!,
                                        food.foodId!!
                                    )
                                    CoroutineScope(IO).launch {

                                        while (true) {
                                            val validate =
                                                user_repository.insertFoodToUser(
                                                    userFoodCrossRef
                                                )
                                            if (validate > -1) {
                                                Log.d(
                                                    "USERWITHFOOD",
                                                    "INSERT $userFoodCrossRef INTO DB"
                                                )
                                                CoroutineScope(Main).launch {
                                                    val intent = Intent(
                                                        context,
                                                        DashboardActivity::class.java
                                                    )
                                                    intent.putExtra(
                                                        "currentUser",
                                                        postUser.userId
                                                    )
                                                    navigationTo.value = Event(intent)
                                                }
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    }
                })
            }
        }
    }


    fun updateUserWithFood(userFoodCrossRef: UserFoodCrossRef) = viewModelScope.launch {
        user_repository.updateUserWithFood(userFoodCrossRef)
    }

    private fun updateUser(user: User) = viewModelScope.launch {
        user_repository.updateUser(user)
        eventMessage.value = Event("Update the $user Completely")
    }

    private fun deleteUser(user: User) = viewModelScope.launch {
        user_repository.deleteUser(user)
        eventMessage.value = Event("Delete the $user Completely")
    }

    private fun insertUser(user: User) = viewModelScope.launch {
        user_repository.insertUser(user)
    }

    fun getUserWithFoodByName(userId: Int): LiveData<List<UserWithFood>> {
        return user_repository.getUserWithFoodByName(userId)
    }


    fun refreshAllUser() {
        user_repository.callApiAndPutInDB()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}



