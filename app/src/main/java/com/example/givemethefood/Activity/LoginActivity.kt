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
import com.example.givemethefood.databinding.ActivityLoginBinding
import com.example.givemethefood.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    }

    private fun navigationToMainActivity() {
        userViewModel.navigation_to.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                startActivity(it)
                finish()
            }
        })
    }

    private fun displayActionMessage() {
        userViewModel.getEventMessage.observe(this, Observer { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayAllUser() {

    }


    fun navigationToRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        userViewModel = ViewModelInstance.getUserInstance(this, this, this)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        displayAllUser()
        displayActionMessage()
        navigationToMainActivity()
    }
}


