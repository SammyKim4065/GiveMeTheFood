package com.example.givemethefood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.givemethefood.R
import com.example.givemethefood.databinding.ActivityWaitForDeliveryBinding

class WaitForDeliveryActivity : AppCompatActivity() {
    lateinit var binding: ActivityWaitForDeliveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wait_for_delivery)

        Glide.with(this)
            .load("https://1.bp.blogspot.com/-ohQqBFztD_8/XYF13gpYwMI/AAAAAAAABno/JuIC4NkG6rA2NeX3bKS5_9o6QgmWi6F-wCLcBGAsYHQ/s1600/animat-road-trip.gif")
            .fitCenter()
            .into(binding.carImageview)

        Handler().postDelayed({
            startActivity(
                Intent(baseContext,
                    StageOfDeliveryActivity::class.java)
            )
        },5000)
    }
}