package com.example.givemethefood.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.givemethefood.R
import com.example.givemethefood.ViewModelInstance
import com.example.givemethefood.databinding.ActivityRegisterBinding
import com.example.givemethefood.viewModel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        userViewModel = ViewModelInstance.getUserInstance(this, this, this)

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        toMainActivity()
        actionMessage()
        displayAllUser()

    }

    private fun toMainActivity() {
        userViewModel.navigation_to.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                startActivity(it)
                finish()
            }
        })
    }

    private fun actionMessage() {
        userViewModel.getEventMessage.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayAllUser() {

    }

    fun navigationToLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}

