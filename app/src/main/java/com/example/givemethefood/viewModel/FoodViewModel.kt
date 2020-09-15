package com.example.givemethefood.viewModel

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.Repository.FoodRepository
import com.example.givemethefood.event.Event
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call

class FoodViewModel(
    var foodRepository: FoodRepository, var context: Context, var lifecycleOwner:
    LifecycleOwner
) : ViewModel(),
    Observable {

    private val restApi: RestApi = RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)

    val allFood = foodRepository.getAllFood
    val totalPrice = foodRepository.getTotalPrice

    private val eventMessage = MutableLiveData<Event<String>>()

    @Bindable
    var inputName = MutableLiveData<String>()

    @Bindable
    var inputPrice = MutableLiveData<String>()

    @Bindable
    var inputIngredients = MutableLiveData<ArrayList<String>>()

    @Bindable
    var inputType = MutableLiveData<String>()

    @Bindable
    var inputPicture = MutableLiveData<String>()

    @Bindable
    var inputIsFev = MutableLiveData<Boolean>()

    @Bindable
    var inputResIdFk = MutableLiveData<Int>()

    @Bindable
    val totalPiece = MutableLiveData<Int>()
    val getTotalPiece : LiveData<Int>
    get() = totalPiece

    init {
        totalPiece.postValue(0)
    }

    fun insertFood() = viewModelScope.launch {
        val foodname = inputName.value!!
        val price = inputPrice.value!!
        val picture = inputPicture.value!!
        val ingredient = inputIngredients.value!!
        val type = inputType.value!!
        val isFev = inputIsFev.value!!
        val resIdFk = inputResIdFk.value!!

        val response: LiveData<Call<FoodsItem>> = liveData {
            val food = FoodsItem(
                0, foodname, picture, price, type,
                ingredient,
                isFev, resIdFk, 0
            )
            val addFood = restApi.insertFood(food)
            emit(addFood)
        }

        response.observe(lifecycleOwner, Observer {
            eventMessage.value = Event("Register Successfully")
        })
    }

    fun clearEditText() {
        inputName.value = null
        inputPrice.value = null
        inputIngredients.value = null
        inputType.value = null
    }

    suspend fun callApiAndPutInDB() {
        foodRepository.callApiAndPutInDB()
    }


    fun getFoodByName(foodname: String): LiveData<List<FoodsItem>> {
        return foodRepository.getFoodByName(foodname)
    }

    fun getRestaurantItemById(id: Int): LiveData<RestaurantItem> {
        return foodRepository.getResNameOfFoodById(id)
    }

    fun addPiece(id: Int,food: FoodsItem) = viewModelScope.launch {
        foodRepository.addPiece(id, food)
    }
    fun minusPiece(id: Int, food: FoodsItem) = viewModelScope.launch {
        foodRepository.minusPiece(id, food)
    }
    fun updateFavourite(id: Int, food: FoodsItem) = viewModelScope.launch {
        foodRepository.updateFavourite(id, food)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}
