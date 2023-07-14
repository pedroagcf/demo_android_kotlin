package com.ufc.appdemo.authentication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ufc.appdemo.Util
import com.ufc.appdemo.authentication.viewmodel.AuthenticationViewModel
import com.ufc.appdemo.databinding.ActivityRegisterBinding
import com.ufc.appdemo.home.view.HomeActivity

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding;
    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(
            AuthenticationViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegister.setOnClickListener {
            val name = binding.etvNameRegister.text.toString()
            val email = binding.etvEmailRegister.text.toString()
            val password = binding.etvPasswordRegister.text.toString()

            when {
                Util.validateNameEmailPassword(name, email, password) -> {
                    viewModel.registerUser(email, password)
                }
            }

            initViewModel()
        }
    }

    //observers da viewModel
    private fun initViewModel() {
        viewModel.stateRegister.observe(this, { state ->
            state?.let {
                navigateToHome(it)
            }
        })

        viewModel.loading.observe(this, { loading ->
            loading?.let {
                showLoading(it)
            }
        })

        viewModel.error.observe(this, {loading ->
            loading?.let {
                showErrorMessage(it)
            }
        })
    }

    private fun navigateToHome(status: Boolean) {
        when {
            status -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.btnRegister, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(status: Boolean) {
        when {
            status -> {
                binding.pbRegister.visibility = View.VISIBLE
            }
            else -> {
                binding.pbRegister.visibility = View.GONE
            }
        }
    }


}