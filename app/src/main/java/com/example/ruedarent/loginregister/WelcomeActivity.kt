package com.example.ruedarent.loginregister

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.R
import com.example.ruedarent.notifications.NotificationsActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val loginButton = findViewById<Button>(R.id.btLoginWelcome)
        val registerButton = findViewById<Button>(R.id.btRegisterWelcome)


        loginButton.setOnClickListener{
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)

        }
    //CAMBIAR POR REGISTER
        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}