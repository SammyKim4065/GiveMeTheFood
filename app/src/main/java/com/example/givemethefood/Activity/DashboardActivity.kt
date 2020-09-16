package com.example.givemethefood.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.givemethefood.API.RestApi
import com.example.givemethefood.API.RetrofitInstance
import com.example.givemethefood.Apdater.FoodsAdapter
import com.example.givemethefood.Apdater.RestaurantFilterAdapter
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.Data.RestaurantItem
import com.example.givemethefood.R
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.databinding.ActivityDashboardBinding
import com.example.givemethefood.viewModel.FoodViewModel
import com.example.givemethefood.viewModel.UserViewModel
import com.google.gson.Gson

class DashboardActivity : AppCompatActivity(), RestaurantFilterAdapter.ClickListener {

    private var currentUser: Int = 0
    private lateinit var foodAdapter: FoodsAdapter
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityDashboardBinding
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        foodViewModel = ViewModelInstance.getFoodInstance(this, this, this)
        userViewModel = ViewModelInstance.getUserInstance(this, this, this)
        binding.lifecycleOwner = this
        binding.foodviewmodel = foodViewModel
        isVisibleView(false)
        userViewModel.refreshAllUser()
        observeAllFood()
        filterFoodByName()
    }

    private fun displayResFilter() {
        binding.restaurangRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val restaurant = ViewModelInstance.getRestaurantInstance(this, this, this).allRestaurant
        restaurant.observe(this, Observer {
            binding.restaurangRecyclerView.adapter = RestaurantFilterAdapter(it, this)
        })
    }

    private fun filterFoodByName() {
        binding.searchView.setOnClickListener() {
            binding.searchView.onActionViewExpanded()
            isVisibleView(true)
            displayResFilter()
            setAdapterLayoutManager(1)

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    when (newText!!.isEmpty()) {
                        true -> {
                            hideKeyboard()
                            setAdapterLayoutManager(1)
                            observeAllFood()
                            binding.searchView.clearFocus()
                        }
                        else -> {
                            foodAdapter.filter.filter(newText)
                            setAdapterLayoutManager(1)
                        }
                    }
                    return true
                }
            })
        }

        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            setAdapterLayoutManager(1)
            if (hasFocus) {
                isVisibleView(true)
                displayResFilter()
            } else {
                hideKeyboard()
                binding.searchView.clearFocus()
            }
        }
    }

    private fun isVisibleView(isVisible: Boolean) {
        when (isVisible) {
            true -> {
                binding.restaurangRecyclerView.visibility = View.VISIBLE
                binding.foodType.visibility = View.VISIBLE
            }
            else -> {
                binding.restaurangRecyclerView.visibility = View.GONE
                binding.foodType.visibility = View.GONE
            }
        }
    }

    private fun observeAllFood() {
        currentUser = intent.getIntExtra("currentUser", 0)
        userViewModel.getUserWithFoodByName(currentUser).observe(this, Observer {
            for (item in it) {
                initialsAllFoodRecyclerview(item.foods)
            }
        })
    }

    private fun initialsAllFoodRecyclerview(it: List<FoodsItem>?) {
        binding.allFoodRecyclerview.apply {
            foodAdapter = FoodsAdapter(it!!, foodViewModel, this@DashboardActivity)
            adapter = foodAdapter
            itemAnimator = null
        }
        setAdapterLayoutManager(2)
    }

    private fun setAdapterLayoutManager(spanCount: Int) {
        binding.allFoodRecyclerview.layoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
    }

    fun gotoOrder(view: View) {
        startActivity(
            Intent(this, OrderActivity::class.java).putExtra(
                "totalPiece",
                foodViewModel.getTotalPiece.value
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        foodViewModel.totalPiece.postValue(0)
    }

    private fun clearPiece() {
        foodViewModel.allFood.observe(this, Observer {
            for (item in it) {
                val restApi: RestApi =
                    RetrofitInstance.getRetrofitInstance(Gson()).create(RestApi::class.java)
                item.piece = 0
                restApi.updateFavourite(item.foodId!!, item)
            }
        })
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            when (binding.searchView.isActivated) {
                true -> hideKeyboard()
                else -> {
                    binding.searchView.clearFocus()
                    isVisibleView(false)
                }
            }
            observeAllFood()
            setAdapterLayoutManager(2)
        }
        mBackPressed = System.currentTimeMillis()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun fevClick(view: View) {
        val intent = Intent(
            this,
            FavoriteActivity::class.java
        )
        intent.putExtra(
            "currentUser",
            currentUser
        )
        startActivity(intent)
    }

    override fun onResFilterClickListener(restaurantItem: RestaurantItem) {
        val resFood: ArrayList<FoodsItem> = ArrayList()
        userViewModel.getUserWithFoodByName(currentUser).observe(this, Observer {
            for (item in it) {
                for (food in item.foods!!) {
                    if (food.resIdFk == restaurantItem.resId) resFood.add(food)
                }
            }
            initialsAllFoodRecyclerview(resFood)
            setAdapterLayoutManager(1)
        })
    }

    fun mealClick(view: View) {
        val mealFood: ArrayList<FoodsItem> = ArrayList()
        userViewModel.getUserWithFoodByName(currentUser).observe(this, Observer {
            for (item in it) {
                for (food in item.foods!!) {
                    if (food.foodtype == "dish meal") mealFood.add(food)
                }
            }
            initialsAllFoodRecyclerview(mealFood)
            setAdapterLayoutManager(1)
        })
    }

    fun dessertClick(view: View) {
        val dessertFood: ArrayList<FoodsItem> = ArrayList()
        userViewModel.getUserWithFoodByName(currentUser).observe(this, Observer {
            for (item in it) {
                for (food in item.foods!!) {
                    if (food.foodtype == "dessert") dessertFood.add(food)
                }
            }
            initialsAllFoodRecyclerview(dessertFood)
            setAdapterLayoutManager(1)
        })
    }

    fun beverageClick(view: View) {
        val beverageFood: ArrayList<FoodsItem> = ArrayList()
        userViewModel.getUserWithFoodByName(currentUser).observe(this, Observer {
            for (item in it) {
                for (food in item.foods!!) {
                    if (food.foodtype == "beverage") beverageFood.add(food)
                }
            }
            initialsAllFoodRecyclerview(beverageFood)
            setAdapterLayoutManager(1)
        })

    }
}