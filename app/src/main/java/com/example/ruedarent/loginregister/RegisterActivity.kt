package com.example.ruedarent.loginregister

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.R
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.loginregister.User.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(){

    private lateinit var tiFirstName: TextInputEditText
    private lateinit var tiLastName: TextInputEditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tiFirstName = findViewById(R.id.tiNameRegister)
        tiLastName = findViewById(R.id.tiLastNameRegister)
        etEmail = findViewById(R.id.etEmailRegister)
        etPassword = findViewById(R.id.etPasswordRegister)
        btRegister = findViewById(R.id.btRegister)

        // Inicializamos la base de datos
        val db = DatabaseBuilder.getInstance(applicationContext)

        btRegister.setOnClickListener {

            val firstName = tiFirstName.text.toString()
            val lastName = tiLastName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Verificar si los campos están llenos
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Guardar usuario en la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().insertUser(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            password = password
                        )
                    )
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        finish() // Cerramos la actividad y regresamos a la anterior
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
