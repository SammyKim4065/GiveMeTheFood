package com.example.givemethefood.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.givemethefood.Apdater.FoodsAdapter
import com.example.givemethefood.Apdater.OrderAdapter
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.databinding.ActivityFevoriteBinding
import com.example.givemethefood.viewModel.FoodViewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var orderFood: ArrayList<FoodsItem>
    private lateinit var foodViewModel: FoodViewModel
    lateinit var binding: ActivityFevoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fevorite)
        foodViewModel = ViewModelInstance.getFoodInstance(this, this, this)
        binding.lifecycleOwner = this
        binding.foodViewModel = foodViewModel
        binding.foodFevRecyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        displayAllFood()
    }

    private fun displayAllFood() {
        foodViewModel.allFood.observe(this, Observer {
            val orderFood: ArrayList<FoodsItem> = ArrayList()
            for (item in it) if (item.isfev!!) orderFood.add(item)

            binding.foodFevRecyclerview.apply {
                adapter = FoodsAdapter(orderFood, foodViewModel,this@FavoriteActivity)
            }
        })
    }
}