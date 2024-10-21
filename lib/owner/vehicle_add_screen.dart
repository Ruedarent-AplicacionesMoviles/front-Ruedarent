import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import '../services/vehicle_service.dart';

class VehicleAddScreen extends StatefulWidget {
  final String ownerId;

  const VehicleAddScreen({Key? key, required this.ownerId}) : super(key: key);

  @override
  _VehicleAddScreenState createState() => _VehicleAddScreenState();
}

class _VehicleAddScreenState extends State<VehicleAddScreen> {
  final _brandController = TextEditingController();
  final _modelController = TextEditingController();
  final _colorController = TextEditingController();
  final _vehicleTypeController = TextEditingController();
  final _descriptionController = TextEditingController();
  final _purchasePriceController = TextEditingController();
  final _rentalPriceController = TextEditingController();

  final VehicleService _vehicleService = VehicleService();

  Future<void> _addVehicle() async {
    final newVehicle = Vehicle(
      id: 0, // El ID será asignado por la Fake API
      brand: _brandController.text,
      model: _modelController.text,
      color: _colorController.text,
      vehicleType: _vehicleTypeController.text,
      description: _descriptionController.text,
      purchasePrice: int.parse(_purchasePriceController.text),
      rentalPrice: int.parse(_rentalPriceController.text),
      imageUrl: 'https://example.com/vehicle.jpg',  // Imagen por defecto o URL proporcionada
      idOwner: int.parse(widget.ownerId),  // Convertimos ownerId a int
    );

    try {
      await _vehicleService.addVehicle(newVehicle);
      Navigator.pop(context, newVehicle);  // Vuelve a la pantalla anterior con el vehículo agregado
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al agregar el vehículo: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Agregar Vehículo'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _brandController,
              decoration: const InputDecoration(labelText: 'Marca'),
            ),
            TextField(
              controller: _modelController,
              decoration: const InputDecoration(labelText: 'Modelo'),
            ),
            TextField(
              controller: _colorController,
              decoration: const InputDecoration(labelText: 'Color'),
            ),
            TextField(
              controller: _vehicleTypeController,
              decoration: const InputDecoration(labelText: 'Tipo de Vehículo'),
            ),
            TextField(
              controller: _descriptionController,
              decoration: const InputDecoration(labelText: 'Descripción'),
            ),
            TextField(
              controller: _purchasePriceController,
              decoration: const InputDecoration(labelText: 'Precio de Compra'),
              keyboardType: TextInputType.number,
            ),
            TextField(
              controller: _rentalPriceController,
              decoration: const InputDecoration(labelText: 'Precio de Alquiler'),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _addVehicle,
              child: const Text('Agregar Vehículo'),
            ),
          ],
        ),
      ),
    );
  }
}
