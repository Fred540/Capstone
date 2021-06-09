package com.example.allmen.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.allmen.MainActivity
import com.example.allmen.app.ApiConfig
import com.example.allmen.databinding.ActivityRegisterBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener(View.OnClickListener {
            val name = binding.editTextUserName.text.toString().trim { it <= ' ' }
            val email = binding.editTextEmail.text.toString().trim { it <= ' ' }
            val password = binding.editTextPassword.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(name)) {
                binding.editTextEmail.error = "Name is Required"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                binding.editTextEmail.error = "Email is Required"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                binding.editTextPassword.error = "Password is Required"
                return@OnClickListener
            }
            if (password.length < 6) {
                binding.editTextPassword.error = "Password must greater than 6 characters"
                return@OnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE

            ApiConfig.instanceRetrofit.register(
                binding.editTextUserName.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            ).enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "User Created", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
                }

            })
        })

        binding.textViewLogin.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                )
            )
        }
    }
}