import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ruedarent.R
import com.example.ruedarent.data.Vehicle

class VehicleAdapter(
    private val vehicleList: List<Vehicle>,
    private val onVehicleClick: (Vehicle) -> Unit // Añadimos el callback onVehicleClick como parámetro
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleImage: ImageView = itemView.findViewById(R.id.ivVehicleImage)
        val vehicleType: TextView = itemView.findViewById(R.id.tvVehicleType)
        val vehicleDescription: TextView = itemView.findViewById(R.id.tvVehicleDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicle_card_item, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.vehicleType.text = vehicle.type
        holder.vehicleDescription.text = vehicle.description

        // Cargar la imagen usando Glide
        Glide.with(holder.itemView.context)
            .load(R.drawable.bike_image) // Usar bike_image.png
            .into(holder.vehicleImage)

        // Manejar el clic en el ítem de la lista
        holder.itemView.setOnClickListener {
            onVehicleClick(vehicle) // Llamar al callback cuando se haga clic
        }
    }

    override fun getItemCount(): Int = vehicleList.size
}

