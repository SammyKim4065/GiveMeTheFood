package com.example.givemethefood.Activity


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.givemethefood.Apdater.DeliveryOrderAdapter
import com.example.givemethefood.Data.Driver
import com.example.givemethefood.Data.FoodsItem
import com.example.givemethefood.R
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.databinding.ActivityStageOfDeliveryBinding
import com.example.givemethefood.viewModel.DriverViewModel
import com.example.givemethefood.viewModel.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*


class StageOfDeliveryActivity : AppCompatActivity() {
    lateinit var foodViewModel: FoodViewModel
    lateinit var binding: ActivityStageOfDeliveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stage_of_delivery)
        foodViewModel = ViewModelInstance.getFoodInstance(this, this, this)
        binding.lifecycleOwner = this
        binding.foodviewmodel = foodViewModel
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@StageOfDeliveryActivity, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
        displayAllFood()
        displayColorStage()
    }


    @SuppressLint("SetTextI18n")
    private fun displayDriverInformation() {
        val driverViewModel = DriverViewModel()
        val driver = driverViewModel.getDriver()
        val time = driverViewModel.getDurationTime()
        binding.apply {
            nameTextView.text = driver.driverName
            phoneTextview.text = driver.driverTel
            carTypeTextView.text = driver.carType
            carIdTextView.text = driver.carId
            timeCountTextview.text = "$time Minutes"
        }
        Picasso.get().load(driver.driverPicture).fit().centerCrop().into(binding.driverPic)
    }

    override fun onResume() {
        super.onResume()
        displayDriverInformation()
    }

    private fun displayColorStage() {
        val orange = "#EE8D1E"
        val yellow = "#FCFC07"
        val purple = "#9566C4"
        val green = "#65FF00"
        val darkblue = "#1807FF"

        Handler().postDelayed({
            binding.orange.apply {
                setCardBackgroundColor(Color.parseColor(orange))
                cardElevation = 100f
            }

            Handler().postDelayed({
                binding.yellow.apply {
                    setCardBackgroundColor(Color.parseColor(yellow))
                    cardElevation = 100f
                }

                Handler().postDelayed({
                    binding.purple.apply {
                        setCardBackgroundColor(Color.parseColor(purple))
                        cardElevation = 100f
                    }

                    Handler().postDelayed({
                        binding.green.apply {
                            setCardBackgroundColor(Color.parseColor(green))
                            cardElevation = 100f
                        }

                        Handler().postDelayed({
                            binding.darkblue.apply {
                                setCardBackgroundColor(Color.parseColor(darkblue))
                                cardElevation = 100f
                            }

                        }, 3000)
                    }, 3000)
                }, 3000)
            }, 3000)
        }, 3000)

    }

    private fun displayAllFood() {
        foodViewModel.allFood.observe(this, Observer {
            val orderFood: ArrayList<FoodsItem> = ArrayList()
            for (item in it) if (item.piece != 0) orderFood.add(item)

            binding.recyclerview.apply {
                adapter = DeliveryOrderAdapter(
                    orderFood, foodViewModel, this@StageOfDeliveryActivity
                ).apply {
                    notifyDataSetChanged()
                }
            }
        })
    }
}