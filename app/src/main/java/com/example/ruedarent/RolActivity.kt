package com.example.ruedarent

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RolActivity : AppCompatActivity() {

    // Declaración de variables para las vistas
    private lateinit var backArrow: ImageView
    private lateinit var buttonPropietario: LinearLayout
    private lateinit var buttonRentador: LinearLayout
    private lateinit var textPropietario: TextView
    private lateinit var textRentador: TextView

    // Variable para almacenar el correo del usuario actual
    private var currentUserEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rol)

        // Vinculación de vistas con IDs
        backArrow = findViewById(R.id.backArrow)
        buttonPropietario = findViewById(R.id.buttonPropietario)
        buttonRentador = findViewById(R.id.buttonRentador)
        textPropietario = findViewById(R.id.textPropietario)
        textRentador = findViewById(R.id.textRentador)

        // Obtener el correo electrónico del usuario actual
        // Este dato debe ser pasado desde la actividad anterior (por ejemplo, LoginActivity)
        currentUserEmail = intent.getStringExtra("USER_EMAIL")

        // Listener para la flecha de atrás
        backArrow.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }

        // Listener para el botón "Propietario"
        buttonPropietario.setOnClickListener {
            selectRole("Propietario")
        }

        // Listener para el botón "Rentador"
        buttonRentador.setOnClickListener {
            selectRole("Rentador")
        }
    }

    /**
     * Función para manejar la selección del rol
     * @param role El rol seleccionado por el usuario ("Propietario" o "Rentador")
     */
    private fun selectRole(role: String) {
        if (currentUserEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show()
            return
        }

        // Actualizar el rol del usuario en la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseBuilder.getInstance(applicationContext)
            val userDao = db.userDao()

            // Obtener el usuario actual por correo electrónico
            val user = userDao.getUserByEmail(currentUserEmail!!)

            if (user != null) {
                // Crear un nuevo objeto User con el rol actualizado
                val updatedUser = user.copy(role = role)

                // Actualizar el usuario en la base de datos
                userDao.updateUser(updatedUser)

                // Mostrar mensaje de éxito y redirigir al siguiente paso
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RolActivity, "Rol seleccionado: $role", Toast.LENGTH_SHORT).show()
                    // Redirigir a la actividad principal o a donde desees
                    val intent = if (role == "Propietario") {
                        Intent(this@RolActivity, VehicleCategoryListActivity::class.java)
                    } else {
                        // Si es Rentador, redirige a otra actividad
                        Intent(this@RolActivity, MainActivity::class.java)
                    }
                    intent.putExtra("USER_ID", user.id)
                    startActivity(intent)
                    finish()
                }
            } else {
                // Usuario no encontrado
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RolActivity, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
