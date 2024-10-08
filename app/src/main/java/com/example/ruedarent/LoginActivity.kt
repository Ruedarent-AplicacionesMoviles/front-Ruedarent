package com.example.ruedarent

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var btLogin: RelativeLayout // Cambiado de Button a RelativeLayout
    private lateinit var registerSection: LinearLayout // Añadido para registrarse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Vinculación de vistas con IDs actualizados
        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        btLogin = findViewById(R.id.btLogin) // Referencia actualizada
        registerSection = findViewById(R.id.registerSection)

        val db = DatabaseBuilder.getInstance(applicationContext)

        btLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = db.userDao().login(email, password)
                    runOnUiThread {
                        if (user != null) {
                            Toast.makeText(this@LoginActivity, "Bienvenido ${user.firstName}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, VehicleListActivity::class.java)
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

        registerSection.setOnClickListener {
            // Redirigir a la actividad de registro (asegúrate de tener RegisterActivity)
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
