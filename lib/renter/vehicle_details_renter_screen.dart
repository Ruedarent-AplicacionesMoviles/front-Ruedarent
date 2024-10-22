import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import '../purchase_screen.dart';
import '../rent_screen.dart';

class VehicleDetailsRenterScreen extends StatefulWidget {
  final Vehicle vehicle;

  const VehicleDetailsRenterScreen({Key? key, required this.vehicle}) : super(key: key);

  @override
  _VehicleDetailsRenterScreenState createState() => _VehicleDetailsRenterScreenState();
}

class _VehicleDetailsRenterScreenState extends State<VehicleDetailsRenterScreen> {
  bool isFavorited = false;

  // Función para el botón "Comprar"
  void _purchaseVehicle(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => PurchaseScreen(
          vehicleName: widget.vehicle.brand,
          purchasePrice: widget.vehicle.purchasePrice.toDouble(),  // Convertir a double
        ),
      ),
    );
  }

  // Función para el botón "Alquilar"
  void _rentVehicle(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => RentScreen(
          vehicleName: widget.vehicle.brand,
          rentalPrice: widget.vehicle.rentalPrice.toDouble(),  // Convertir a double
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Detalles del Vehículo'),
        actions: [
          IconButton(
            icon: Icon(isFavorited ? Icons.favorite : Icons.favorite_border),
            onPressed: () {
              setState(() {
                isFavorited = !isFavorited;
              });
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image.network(widget.vehicle.imageUrl),
            const SizedBox(height: 20),
            Text('Marca: ${widget.vehicle.brand}', style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            Text('Modelo: ${widget.vehicle.model}', style: const TextStyle(fontSize: 18)),
            Text('Color: ${widget.vehicle.color}', style: const TextStyle(fontSize: 18)),
            Text('Tipo: ${widget.vehicle.vehicleType}', style: const TextStyle(fontSize: 18)),
            Text('Descripción: ${widget.vehicle.description}', style: const TextStyle(fontSize: 18)),
            Text('Precio de Compra: \$${widget.vehicle.purchasePrice}', style: const TextStyle(fontSize: 18)),
            Text('Precio de Alquiler: \$${widget.vehicle.rentalPrice} por día', style: const TextStyle(fontSize: 18)),
            const SizedBox(height: 20),
            // Botones de comprar y alquilar
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(
                  onPressed: () => _purchaseVehicle(context), // Comprar
                  child: const Text('Comprar'),
                ),
                ElevatedButton(
                  onPressed: () => _rentVehicle(context), // Alquilar
                  child: const Text('Alquilar'),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}