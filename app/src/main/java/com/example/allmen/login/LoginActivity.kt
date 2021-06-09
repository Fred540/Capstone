package com.example.allmen.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.allmen.MainActivity
import com.example.allmen.app.ApiConfig
import com.example.allmen.databinding.ActivityLoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            getUserLogin()
        }

        binding.textViewCreateAccount.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    RegisterActivity::class.java
                )
            )
        }
    }

    private fun getUserLogin() {
        val email = binding.editTextEmailforLogin.text.toString().trim { it <= ' ' }
        val password = binding.editTextPasswordforLogin.text.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(email)) {
            binding.editTextEmailforLogin.error = "Email is Required"
            return
        }
        if (TextUtils.isEmpty(password)) {
            binding.editTextPasswordforLogin.error = "Password is Required"
            return
        }
        if (password.length < 6) {
            binding.editTextPasswordforLogin.error = "Password must greater than 6 characters"
            return
        }
        binding.progressBarLogin.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(
            binding.editTextEmailforLogin.text.toString(),
            binding.editTextPasswordforLogin.text.toString()
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Login Succesfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                binding.progressBarLogin.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}