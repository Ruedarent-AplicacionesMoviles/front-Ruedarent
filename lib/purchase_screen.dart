import 'package:flutter/material.dart';

class PurchaseScreen extends StatefulWidget {
  final String vehicleName;
  final double purchasePrice;

  const PurchaseScreen({Key? key, required this.vehicleName, required this.purchasePrice}) : super(key: key);

  @override
  _PurchaseScreenState createState() => _PurchaseScreenState();
}

class _PurchaseScreenState extends State<PurchaseScreen> {
  final TextEditingController _cardNumberController = TextEditingController();
  final TextEditingController _cardHolderController = TextEditingController();
  final TextEditingController _expiryDateController = TextEditingController();
  final TextEditingController _cvvController = TextEditingController();

  // Simulación de compra
  void _completePurchase() {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Compra completada para ${widget.vehicleName}')),
    );
    Navigator.pop(context);  // Volver a la pantalla anterior
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Detalles de Pago'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Vehículo: ${widget.vehicleName}', style: const TextStyle(fontSize: 18)),
            Text('Precio: \$${widget.purchasePrice}', style: const TextStyle(fontSize: 18)),
            const SizedBox(height: 20),
            // Campos para los datos de la tarjeta
            TextField(
              controller: _cardNumberController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(
                labelText: 'Número de la Tarjeta',
              ),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _cardHolderController,
              decoration: const InputDecoration(
                labelText: 'Titular de la Tarjeta',
              ),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _expiryDateController,
              keyboardType: TextInputType.datetime,
              decoration: const InputDecoration(
                labelText: 'Fecha de Expiración (MM/AA)',
              ),
            ),
            const SizedBox(height: 10),
            TextField(
              controller: _cvvController,
              keyboardType: TextInputType.number,
              obscureText: true,
              decoration: const InputDecoration(
                labelText: 'CVV',
              ),
            ),
            const SizedBox(height: 20),
            // Botón para confirmar la compra
            ElevatedButton(
              onPressed: _completePurchase,
              child: const Text('Completar Compra'),
            ),
          ],
        ),
      ),
    );
  }
}