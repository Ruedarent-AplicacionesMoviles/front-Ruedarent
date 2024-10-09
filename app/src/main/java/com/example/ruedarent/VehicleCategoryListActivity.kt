// VehicleCategoryListActivity.kt
package com.example.ruedarent

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ruedarent.data.DatabaseBuilder
import com.example.ruedarent.data.VehicleCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class VehicleCategoryListActivity : AppCompatActivity() {

    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var buttonAddCategory: View

    private var categoryList: MutableList<VehicleCategory> = mutableListOf()
    private var currentUserId: Int = -1 // Debes obtener el ID del usuario actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_category_list)

        // Vinculación de vistas
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories)
        buttonAddCategory = findViewById(R.id.buttonAddCategory)

        // Obtener el ID del usuario actual (debes pasar este dato desde la actividad anterior)
        currentUserId = intent.getIntExtra("USER_ID", -1)

        if (currentUserId == -1) {
            // Manejar error
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Configurar RecyclerView
        categoryAdapter = CategoryAdapter(
            categoryList,
            onCategoryClick = { category ->
                // Manejar clic en una categoría (mostrar los vehículos de esa categoría)
                val intent = Intent(this, VehicleListActivity::class.java)
                intent.putExtra("CATEGORY_ID", category.id)
                intent.putExtra("CATEGORY_NAME", category.name)
                startActivity(intent)
            },
            onEditClick = { category ->
                // Manejar la edición de una categoría
                val intent = Intent(this, EditCategoryActivity::class.java)
                intent.putExtra("CATEGORY_ID", category.id)
                startActivity(intent)
            },
            onDeleteClick = { category ->
                // Manejar la eliminación de una categoría
                mostrarDialogoEliminar(category)
            }
        )

        recyclerViewCategories.layoutManager = GridLayoutManager(this, 2)
        recyclerViewCategories.adapter = categoryAdapter

        // Cargar categorías
        cargarCategorias()

        // Listener para el botón ADD +
        buttonAddCategory.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            intent.putExtra("USER_ID", currentUserId)
            startActivity(intent)
        }

        // Configurar NavigationBar
        setupNavigationBar()
    }

    override fun onResume() {
        super.onResume()
        cargarCategorias()
    }

    private fun cargarCategorias() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseBuilder.getInstance(applicationContext)
            val categoryDao = db.vehicleDao()
            val categories = categoryDao.getCategoriesByUser(currentUserId)

            withContext(Dispatchers.Main) {
                categoryList.clear()
                categoryList.addAll(categories)
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun mostrarDialogoEliminar(category: VehicleCategory) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Categoría")
            .setMessage("¿Estás seguro de que deseas eliminar esta categoría?")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarCategoria(category)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarCategoria(category: VehicleCategory) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseBuilder.getInstance(applicationContext)
            db.vehicleDao().deleteCategory(category)

            withContext(Dispatchers.Main) {
                cargarCategorias() // Recargar las categorías después de eliminar
                Toast.makeText(this@VehicleCategoryListActivity, "Categoría eliminada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNavigationBar() {
        // Configurar navegación entre tabs
        val navPedidos = findViewById<LinearLayout>(R.id.navPedidos)
        val navVehiculos = findViewById<LinearLayout>(R.id.navVehiculos)
        val navPerfil = findViewById<LinearLayout>(R.id.navPerfil)

        navPedidos.setOnClickListener {
            val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)
            finish()
        }

        navVehiculos.setOnClickListener {
            // Ya estás en VehicleCategoryListActivity, puedes resaltar el icono si no está ya
            Toast.makeText(this, "Ya estás en Categorías", Toast.LENGTH_SHORT).show()
        }

        navPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }

        highlightActiveTab()
    }

    private fun highlightActiveTab() {
        val iconVehiculos = findViewById<ImageView>(R.id.iconVehiculos)
        val textVehiculos = findViewById<TextView>(R.id.textVehiculos)

        // Cambiar el color del texto y del ícono para resaltar
        textVehiculos.setTextColor(resources.getColor(R.color.colorPrimary)) // Define colorPrimary en colors.xml
        iconVehiculos.setImageResource(R.drawable.ic_vehiculos_active) // Ícono activo
    }
}
