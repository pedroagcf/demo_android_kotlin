package com.ufc.appdemo.authentication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ufc.appdemo.R
import com.ufc.appdemo.Util
import com.ufc.appdemo.authentication.viewmodel.AuthenticationViewModel
import com.ufc.appdemo.databinding.ActivityLoginBinding
import com.ufc.appdemo.home.view.HomeActivity

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var googleClient: GoogleSignInClient

    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(
            AuthenticationViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btLogin.setOnClickListener {
            val email = binding.etvEmail.text.toString()
            val password = binding.etvPassword.text.toString()

            when {
                Util.validateEmailPassword(email, password) -> {
                    viewModel.loginEmailPassword(email, password)
                }
                else -> {
                    Snackbar.make(binding.btLogin, "login failed", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this, options)
        binding.btLoginGoogle.setOnClickListener {
            val intent = googleClient.signInIntent
            startActivityForResult(intent, 10001)
        }

        binding.tvLoginRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful) {
                        navigateHome(true)
                    } else {
                        showErrorMessage("login Failed")
                    }

                }
        }
    }

    private fun initViewModel() {
        viewModel.stateLogin.observe(this, { state ->
            state?.let {
                navigateHome(it)
            }
        })

        viewModel.stateLogin.observe(this, { loading ->
            loading?.let {
                showErrorMessage("login Failed")
            }
        })
    }

    private fun navigateHome(status: Boolean) {
        when {
            status -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.btLogin, message, Snackbar.LENGTH_LONG).show()
    }
}