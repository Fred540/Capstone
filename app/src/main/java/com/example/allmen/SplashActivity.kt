package com.example.allmen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.allmen.login.LoginActivity
import com.example.allmen.login.RegisterActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val home = Intent(this, RegisterActivity::class.java)
            startActivity(home)
            finish()
        }, 3000)
    }
}