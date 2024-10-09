package com.example.ruedarent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.VehicleCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var imageViewCategory: ImageView
    private lateinit var editTextCategoryName: EditText
    private lateinit var buttonSaveCategory: LinearLayout
    private lateinit var buttonSelectImage: Button
    private lateinit var editTextCategoryDescription: EditText


    private var imageUri: Uri? = null
    private var currentUserId: Int = -1

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        imageViewCategory = findViewById(R.id.imageViewCategory)
        editTextCategoryName = findViewById(R.id.editTextCategoryName)
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory)
        buttonSelectImage = findViewById(R.id.buttonSelectImage)
        editTextCategoryDescription = findViewById(R.id.editTextCategoryDescription)


        currentUserId = intent.getIntExtra("USER_ID", -1)
        if (currentUserId == -1) {
            // Manejar error
            finish()
            return
        }

        buttonSelectImage.setOnClickListener {
            openImageSelector()
        }

        buttonSaveCategory.setOnClickListener {
            saveCategory()
        }
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageViewCategory.setImageURI(imageUri)
        }
    }

    private fun saveCategory() {
        val categoryName = editTextCategoryName.text.toString().trim()
        val categoryDescription = editTextCategoryDescription.text.toString().trim()

        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un nombre para la categoría", Toast.LENGTH_SHORT).show()
            return
        }

        val imageUriString = imageUri?.toString()

        val newCategory = VehicleCategory(
            name = categoryName,
            description = categoryDescription,
            image = imageUriString,
            userId = currentUserId
        )

        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseBuilder.getInstance(applicationContext)
            db.vehicleDao().insertCategory(newCategory)

            runOnUiThread {
                Toast.makeText(this@AddCategoryActivity, "Categoría agregada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
