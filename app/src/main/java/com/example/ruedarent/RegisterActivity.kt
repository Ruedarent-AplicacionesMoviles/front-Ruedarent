package com.example.ruedarent

import android.os.Bundle
import android.widget.ImageView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    // Declaración de variables para las vistas
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContraseña: EditText
    private lateinit var etConfirmarContraseña: EditText
    private lateinit var checkboxTerms: LinearLayout
    private lateinit var checkBoxImage: ImageView
    private lateinit var buttonConfirmar: LinearLayout
    private lateinit var textConfirmar: TextView

    // Variable para mantener el estado del checkbox
    private var termsAccepted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Vinculación de vistas con IDs
        val backArrow = findViewById<ImageView>(R.id.backArrow)
        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etCorreo = findViewById(R.id.etCorreo)
        etContraseña = findViewById(R.id.etContraseña)
        etConfirmarContraseña = findViewById(R.id.etConfirmarContraseña)
        checkboxTerms = findViewById(R.id.checkboxTerms)
        checkBoxImage = findViewById(R.id.checkBox)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)
        textConfirmar = findViewById(R.id.textConfirmar)

        // Listener para la flecha de atrás
        backArrow.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }

        // Listener para el checkbox de términos
        checkboxTerms.setOnClickListener {
            termsAccepted = !termsAccepted
            if (termsAccepted) {
                checkBoxImage.setImageResource(R.drawable.ic_checkbox_checked) // Reemplaza con tu drawable de checkbox marcado
            } else {
                checkBoxImage.setImageResource(R.drawable.ic_checkbox) // Reemplaza con tu drawable de checkbox desmarcado
            }
        }

        // Listener para el botón "CONFIRMAR"
        buttonConfirmar.setOnClickListener {
            val nombres = etNombres.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContraseña.text.toString().trim()
            val confirmarContraseña = etConfirmarContraseña.text.toString().trim()

            // Validaciones básicas
            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() ||
                contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contraseña != confirmarContraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!termsAccepted) {
                Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Inicializamos la base de datos
            val db = DatabaseBuilder.getInstance(applicationContext)

            // Agregar usuario a la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = db.userDao()
                val userExists = userDao.getUserByEmail(correo) != null

                runOnUiThread {
                    if (userExists) {
                        Toast.makeText(this@RegisterActivity, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        // Crear y guardar el nuevo usuario
                        val newUser = User(
                            firstName = nombres,
                            lastName = apellidos,
                            email = correo,
                            password = contraseña
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            userDao.insertUser(newUser)
                            runOnUiThread {
                                Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                // Redirigir a LoginActivity después del registro exitoso
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}