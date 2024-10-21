import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import '../services/vehicle_service.dart';

class VehicleEditScreen extends StatefulWidget {
  final Vehicle vehicle;

  const VehicleEditScreen({Key? key, required this.vehicle}) : super(key: key);

  @override
  _VehicleEditScreenState createState() => _VehicleEditScreenState();
}

class _VehicleEditScreenState extends State<VehicleEditScreen> {
  late TextEditingController _brandController;
  late TextEditingController _modelController;
  late TextEditingController _colorController;
  late TextEditingController _vehicleTypeController;
  late TextEditingController _descriptionController;  // Controlador para descripción
  late TextEditingController _purchasePriceController; // Controlador para precio de compra
  late TextEditingController _rentalPriceController;   // Controlador para precio de alquiler

  final VehicleService _vehicleService = VehicleService();

  @override
  void initState() {
    super.initState();
    _brandController = TextEditingController(text: widget.vehicle.brand);
    _modelController = TextEditingController(text: widget.vehicle.model);
    _colorController = TextEditingController(text: widget.vehicle.color);
    _vehicleTypeController = TextEditingController(text: widget.vehicle.vehicleType);
    _descriptionController = TextEditingController(text: widget.vehicle.description);  // Iniciar con el valor actual
    _purchasePriceController = TextEditingController(text: widget.vehicle.purchasePrice.toString());  // Iniciar con valor actual
    _rentalPriceController = TextEditingController(text: widget.vehicle.rentalPrice.toString());  // Iniciar con valor actual
  }

  @override
  void dispose() {
    _brandController.dispose();
    _modelController.dispose();
    _colorController.dispose();
    _vehicleTypeController.dispose();
    _descriptionController.dispose();   // Disponer controlador
    _purchasePriceController.dispose(); // Disponer controlador
    _rentalPriceController.dispose();   // Disponer controlador
    super.dispose();
  }

  Future<void> _saveChanges() async {
    // Aquí llamamos al servicio para actualizar el vehículo
    final updatedVehicle = Vehicle(
      id: widget.vehicle.id,
      brand: _brandController.text,
      model: _modelController.text,
      color: _colorController.text,
      vehicleType: _vehicleTypeController.text,
      imageUrl: widget.vehicle.imageUrl, // Mantiene la URL de la imagen actual
      idOwner: widget.vehicle.idOwner,
      description: _descriptionController.text,  // Actualizamos la descripción
      purchasePrice: int.parse(_purchasePriceController.text), // Convertir a int
      rentalPrice: int.parse(_rentalPriceController.text),     // Convertir a int
    );

    // Lógica para actualizar el vehículo en la Fake API
    try {
      await _vehicleService.updateVehicle(updatedVehicle);
      Navigator.pop(context, updatedVehicle);  // Volvemos con el vehículo actualizado
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al actualizar el vehículo: $e')),
      );
    }
  }

  Future<void> _deleteVehicle() async {
    // Lógica para eliminar el vehículo de la Fake API
    try {
      await _vehicleService.deleteVehicle(widget.vehicle.id);
      Navigator.pop(context, null);  // Volvemos indicando que se eliminó el vehículo
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al eliminar el vehículo: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Editar Vehículo'),
        actions: [
          IconButton(
            icon: Icon(Icons.delete),
            onPressed: () {
              // Confirmación de eliminación
              showDialog(
                context: context,
                builder: (context) => AlertDialog(
                  title: Text('Eliminar Vehículo'),
                  content: Text('¿Estás seguro de que deseas eliminar este vehículo?'),
                  actions: [
                    TextButton(
                      onPressed: () => Navigator.pop(context),
                      child: Text('Cancelar'),
                    ),
                    TextButton(
                      onPressed: () {
                        Navigator.pop(context);  // Cerrar el diálogo
                        _deleteVehicle();  // Eliminar vehículo
                      },
                      child: Text('Eliminar'),
                    ),
                  ],
                ),
              );
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(  // Scroll para evitar desbordes en pantalla pequeña
          child: Column(
            children: [
              TextField(
                controller: _brandController,
                decoration: InputDecoration(labelText: 'Marca'),
              ),
              TextField(
                controller: _modelController,
                decoration: InputDecoration(labelText: 'Modelo'),
              ),
              TextField(
                controller: _colorController,
                decoration: InputDecoration(labelText: 'Color'),
              ),
              TextField(
                controller: _vehicleTypeController,
                decoration: InputDecoration(labelText: 'Tipo de Vehículo'),
              ),
              TextField(
                controller: _descriptionController,
                decoration: InputDecoration(labelText: 'Descripción'),
              ),
              TextField(
                controller: _purchasePriceController,
                decoration: InputDecoration(labelText: 'Precio de Compra'),
                keyboardType: TextInputType.number,  // Campo numérico
              ),
              TextField(
                controller: _rentalPriceController,
                decoration: InputDecoration(labelText: 'Precio de Alquiler'),
                keyboardType: TextInputType.number,  // Campo numérico
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: _saveChanges,
                child: Text('Guardar Cambios'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}