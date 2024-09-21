package com.example.ruedarent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        btnLogin = findViewById(R.id.btLogin)

        // Inicializamos la base de datos
        val db = DatabaseBuilder.getInstance(applicationContext)

        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Verificar login en la base de datos
                CoroutineScope(Dispatchers.IO).launch {
                    val user = db.userDao().login(email, password)
                    runOnUiThread {
                        if (user != null) {
                            Toast.makeText(this@LoginActivity, "Bienvenido ${user.firstName}", Toast.LENGTH_SHORT).show()
                            // Redirigimos al MainActivity después del login exitoso
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)

                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, introduce tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
