// EditCategoryActivity.kt
package com.example.ruedarent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.VehicleCategory
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCategoryActivity : AppCompatActivity() {

    private lateinit var imageViewEditCategory: ImageView
    private lateinit var editTextEditCategoryName: EditText
    private lateinit var editTextEditCategoryDescription: EditText
    private lateinit var buttonUpdateCategory: LinearLayout
    private lateinit var buttonSelectImageEdit: MaterialButton


    private var imageUri: Uri? = null
    private var categoryId: Int = -1
    private var currentCategory: VehicleCategory? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_category)

        imageViewEditCategory = findViewById(R.id.imageViewEditCategory)
        editTextEditCategoryName = findViewById(R.id.editTextEditCategoryName)
        editTextEditCategoryDescription = findViewById(R.id.editTextEditCategoryDescription)
        buttonUpdateCategory = findViewById(R.id.buttonUpdateCategory)
        buttonSelectImageEdit = findViewById(R.id.buttonSelectImage)

        // Obtener el ID de la categoría a editar
        categoryId = intent.getIntExtra("CATEGORY_ID", -1)
        if (categoryId == -1) {
            Toast.makeText(this, "Error: Categoría no identificada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar los datos de la categoría
        cargarDatosCategoria()

        // Listener para seleccionar imagen
        buttonSelectImageEdit.setOnClickListener {
            abrirSelectorImagen()
        }

        // Listener para actualizar la categoría
        buttonUpdateCategory.setOnClickListener {
            actualizarCategoria()
        }
    }

    private fun cargarDatosCategoria() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseBuilder.getInstance(applicationContext)
            val category = db.vehicleDao().getCategoryById(categoryId)
            if (category != null) {
                currentCategory = category
                withContext(Dispatchers.Main) {
                    editTextEditCategoryName.setText(category.name)
                    editTextEditCategoryDescription.setText(category.description)
                    if (category.image != null) {
                        Glide.with(this@EditCategoryActivity)
                            .load(category.image)
                            .placeholder(R.drawable.login_logo_image)
                            .into(imageViewEditCategory)
                    } else {
                        imageViewEditCategory.setImageResource(R.drawable.login_logo_image)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EditCategoryActivity, "Categoría no encontrada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun abrirSelectorImagen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageViewEditCategory.setImageURI(imageUri)
        }
    }

    private fun actualizarCategoria() {
        val nombre = editTextEditCategoryName.text.toString().trim()
        val descripcion = editTextEditCategoryDescription.text.toString().trim()

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir la imagen a una cadena (URI)
        val imagenUrl = imageUri?.toString() ?: currentCategory?.image

        if (imagenUrl == null) {
            Toast.makeText(this, "Por favor, selecciona una imagen", Toast.LENGTH_SHORT).show()
            return
        }

        val categoriaActualizada = currentCategory?.copy(
            name = nombre,
            description = descripcion,
            image = imagenUrl
        )

        if (categoriaActualizada != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = DatabaseBuilder.getInstance(applicationContext)
                db.vehicleDao().updateCategory(categoriaActualizada)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EditCategoryActivity, "Categoría actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, "Error al actualizar la categoría", Toast.LENGTH_SHORT).show()
        }
    }
}
