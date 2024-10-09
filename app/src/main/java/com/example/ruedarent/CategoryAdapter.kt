// CategoryAdapter.kt
package com.example.ruedarent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ruedarent.data.VehicleCategory

class CategoryAdapter(
    private val categories: List<VehicleCategory>,
    private val onCategoryClick: (VehicleCategory) -> Unit,
    private val onEditClick: (VehicleCategory) -> Unit,
    private val onDeleteClick: (VehicleCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCategory: ImageView = itemView.findViewById(R.id.imageCategory)
        val textCategoryName: TextView = itemView.findViewById(R.id.textCategoryName)
        val textCategoryDescription: TextView = itemView.findViewById(R.id.textCategoryDescription)
        val buttonEditar: ImageView = itemView.findViewById(R.id.buttonEditar)
        val buttonEliminar: ImageView = itemView.findViewById(R.id.buttonEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.textCategoryName.text = category.name
        holder.textCategoryDescription.text = category.description

        // Cargar la imagen de la categoría
        if (category.image != null) {
            Glide.with(holder.itemView.context)
                .load(category.image)
                .placeholder(R.drawable.login_logo_image) // Usar logo como placeholder
                .into(holder.imageCategory)
        } else {
            holder.imageCategory.setImageResource(R.drawable.login_logo_image)
        }

        // Manejar el clic en el ítem de la lista
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }

        // Botón de editar
        holder.buttonEditar.setOnClickListener {
            onEditClick(category)
        }

        // Botón de eliminar
        holder.buttonEliminar.setOnClickListener {
            onDeleteClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size
}
