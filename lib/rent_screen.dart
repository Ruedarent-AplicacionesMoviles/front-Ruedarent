import 'package:flutter/material.dart';

class RentScreen extends StatefulWidget {
  final String vehicleName; // El nombre del vehículo
  final double rentalPrice;  // El precio de alquiler por día

  const RentScreen({Key? key, required this.vehicleName, required this.rentalPrice}) : super(key: key);

  @override
  _RentScreenState createState() => _RentScreenState();
}

class _RentScreenState extends State<RentScreen> {
  final TextEditingController _daysController = TextEditingController(); // Controlador para la cantidad de días

  @override
  void dispose() {
    _daysController.dispose();
    super.dispose();
  }

  void _rentVehicle() {
    final int days = int.tryParse(_daysController.text) ?? 0; // Convertir la cantidad de días a entero
    if (days <= 0) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Por favor, introduce un número válido de días')),
      );
      return;
    }

    final double totalPrice = days * widget.rentalPrice;

    // Aquí puedes añadir la lógica para realizar el alquiler
    // Por ejemplo, guardar la información o hacer una llamada a una API.

    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Alquiler exitoso. Total: \$${totalPrice.toStringAsFixed(2)}')),
    );

    Navigator.pop(context); // Volver a la pantalla anterior
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Alquilar Vehículo'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Estás alquilando: ${widget.vehicleName}',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 20),
            Text(
              'Precio por día: \$${widget.rentalPrice.toStringAsFixed(2)}',
              style: TextStyle(fontSize: 16),
            ),
            const SizedBox(height: 20),
            TextField(
              controller: _daysController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(
                labelText: 'Cantidad de días',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _rentVehicle,
              child: const Text('Alquilar'),
            ),
          ],
        ),
      ),
    );
  }
}