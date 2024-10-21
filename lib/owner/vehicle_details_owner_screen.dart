import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import '../services/vehicle_service.dart';
import 'vehicle_edit_screen.dart'; // Pantalla para editar vehículo

class VehicleDetailsOwnerScreen extends StatelessWidget {
  final Vehicle vehicle;
  final VehicleService _vehicleService = VehicleService();

  VehicleDetailsOwnerScreen({Key? key, required this.vehicle}) : super(key: key);

  // Función para eliminar vehículo
  void _deleteVehicle(BuildContext context) async {
    try {
      await _vehicleService.deleteVehicle(vehicle.id);
      Navigator.pop(context);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Vehículo eliminado con éxito')),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al eliminar el vehículo: $e')),
      );
    }
  }

  // Función para editar vehículo
  void _editVehicle(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => VehicleEditScreen(vehicle: vehicle),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Detalles del Vehículo'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image.network(vehicle.imageUrl),
            const SizedBox(height: 20),
            Text('Marca: ${vehicle.brand}', style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            Text('Modelo: ${vehicle.model}', style: const TextStyle(fontSize: 18)),
            Text('Color: ${vehicle.color}', style: const TextStyle(fontSize: 18)),
            Text('Tipo: ${vehicle.vehicleType}', style: const TextStyle(fontSize: 18)),
            Text('Descripción: ${vehicle.description}', style: const TextStyle(fontSize: 18)),
            Text('Precio de Compra: \$${vehicle.purchasePrice}', style: const TextStyle(fontSize: 18)),
            Text('Precio de Alquiler: \$${vehicle.rentalPrice} por día', style: const TextStyle(fontSize: 18)),
            const SizedBox(height: 20),
            // Botones de editar y eliminar
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: () => _editVehicle(context), // Botón de editar
                  child: const Text('Editar'),
                ),
                ElevatedButton(
                  onPressed: () => _deleteVehicle(context), // Botón de eliminar
                  style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
                  child: const Text('Eliminar'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}