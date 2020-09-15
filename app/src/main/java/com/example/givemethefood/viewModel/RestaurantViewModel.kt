package com.example.givemethefood.viewModel

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Repository.RestaurantRepository
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.event.Event
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantViewModel(var restaurant_repository : RestaurantRepository,var context: Context,
                          var lifecycleOwner: LifecycleOwner) :ViewModel(),
    androidx.databinding.Observable {

    val restApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)
    val allRestaurant = restaurant_repository.AllRestaurant
    lateinit var currentRestaurant : RestaurantItem

    @Bindable
    var inputName = MutableLiveData<String>()
    @Bindable
    var inputPicture = MutableLiveData<String>()
    @Bindable
    var inputPrice = MutableLiveData<String>()
    @Bindable
    var inputIngrdient = MutableLiveData<ArrayList<String>>()
    @Bindable
    var inputType = MutableLiveData<String>()
    @Bindable
    var inputIsFev = MutableLiveData<Boolean>()

    private val eventMessage = MutableLiveData<Event<String>>()
    val getEventMessage: LiveData<Event<String>>
        get() = eventMessage


    fun getCurrent_Restaurant(resname:String){
        currentRestaurant = restaurant_repository.getRestaurant(resname).value!!
    }

    fun InsertFood(){
            when {
                inputName.value == null -> eventMessage.value = Event("Please Enter name")
                inputPrice.value == null -> eventMessage.value = Event("Please enter price")
                inputIngrdient.value == null -> eventMessage.value = Event("Please enter ingredient")
                inputType.value == null -> eventMessage.value = Event("Please enter type")

                else -> {

                    val response: LiveData<Call<FoodsItem>>
                            = liveData {
                        val food = FoodsItem(
                            0,
                            inputName.toString(),
                            inputPicture.toString(),
                            inputPrice.toString(),
                            inputType.toString(),
                            inputIngrdient.value!!,
                            inputIsFev.value!!,
                            currentRestaurant.resId!!.toInt(),
                            0
                        )

                        val item =  restApi.insertFood(food)
                        emit(item)

                        item.request().body.apply {

                            val response : LiveData<Call<RestaurantItem>> = liveData {
                                val restaurant = restApi.updateListFoodInRestaurant(food.foodId!!,food)
                                emit(restaurant)
                            }

                            response.value!!.enqueue(object : Callback<RestaurantItem>{
                                override fun onFailure(call: Call<RestaurantItem>, t: Throwable) {
                                    Log.d("insertFoodInRestaurant","Unsuccessfully")
                                }

                                override fun onResponse(
                                    call: Call<RestaurantItem>,
                                    response: Response<RestaurantItem>
                                ) {
                                    val value = response.body()!!.listfood
                                    Log.d("insertFoodInRestaurant","$value")
                                }
                            })
                        }
                    }
                }
            }
    }

    fun insert(res:RestaurantItem) = viewModelScope.launch {
        restaurant_repository.insertRestaurant(res)
        Log.d("res","restaurant = $res")
    }
    fun deleteListRestaurant() = viewModelScope.launch {
        restaurant_repository.deleteAllRestaurant()
        Log.d("res","Delete All Restaurant")
    }

    fun update(restaurant: RestaurantItem) = viewModelScope.launch {
        restaurant_repository.updateRestaurant(restaurant)
        Log.d("resUpdate","Update Restaurant")
    }

    fun getRestaurantByName(username:String): LiveData<RestaurantItem> {
       return restaurant_repository.getRestaurant(username)
    }

    fun clearEditext(){
        inputName.value = null
        inputPrice.value = null
        inputIngrdient.value = null
        inputType.value = null
    }

    suspend fun callApiAndPutInDB(){
        restaurant_repository.callApiAndPutInDB()
    }

    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {

    }
}

