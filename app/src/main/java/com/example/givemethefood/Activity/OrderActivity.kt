package com.example.givemethefood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.givemethefood.Apdater.OrderAdapter
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.databinding.ActivityOrderBinding
import com.example.givemethefood.viewModel.FoodViewModel

class OrderActivity : AppCompatActivity() {
    lateinit var foodViewModel: FoodViewModel
    lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
    }

    override fun onStart() {
        super.onStart()

        foodViewModel = ViewModelInstance.getFoodInstance(this, this, this)
        binding.lifecycleOwner = this
        binding.foodviewmodel = foodViewModel
        binding.orderRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
        }
        displayAllFood()
    }

    private fun displayAllFood() {
        val totalPiece = intent.getIntExtra("totalPiece", 0)
        binding.totalPeiceTextview.text = totalPiece.toString() + "Piece"
        foodViewModel.totalPiece.postValue(totalPiece)
        foodViewModel.allFood.observe(this, Observer {
            val orderFood: ArrayList<FoodsItem> = ArrayList()
            for (item in it) if (item.piece != 0) orderFood.add(item)

            binding.orderRecyclerview.apply {
                adapter = OrderAdapter(orderFood, foodViewModel, this@OrderActivity)
            }
        })
    }

    fun onOrderClick(view: View) {
        startActivity(
            Intent(
                baseContext,
                WaitForDeliveryActivity::class.java
            )
        )
    }
}