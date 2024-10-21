import 'package:flutter/material.dart';
import '../../model/vehicle.dart';
import '../../renter/vehicle_details_renter_screen.dart';
import '../../services/auth_service.dart';
import '../vehicle_details_owner_screen.dart';
  // Importa el servicio para obtener los detalles del propietario

class VehicleCardOwner extends StatefulWidget {
  final Vehicle vehicle;

  const VehicleCardOwner({Key? key, required this.vehicle}) : super(key: key);

  @override
  _VehicleCardState createState() => _VehicleCardState();
}

class _VehicleCardState extends State<VehicleCardOwner> {
  final AuthService _authService = AuthService();
  Map<String, dynamic>? _ownerDetails;

  @override
  void initState() {
    super.initState();
    _fetchOwnerDetails();
  }

  Future<void> _fetchOwnerDetails() async {
    try {
      final ownerDetails = await _authService.getOwnerDetailsById(widget.vehicle.idOwner);
      setState(() {
        _ownerDetails = ownerDetails;  // Guardamos los detalles del propietario
      });
    } catch (e) {
      print('Error al obtener los detalles del propietario: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(10.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => VehicleDetailsOwnerScreen(vehicle: widget.vehicle),
                ),
              );
            },
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Image.network(widget.vehicle.imageUrl),  // Imagen del vehículo
                Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('Marca: ${widget.vehicle.brand}', style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                      Text('Tipo: ${widget.vehicle.vehicleType}', style: const TextStyle(fontSize: 16)),
                      // Mostrar los detalles del propietario si están disponibles
                      if (_ownerDetails != null) ...[
                        Text('Propietario: ${_ownerDetails!['name']} ${_ownerDetails!['lastName']}', style: const TextStyle(fontSize: 16)),
                      ],
                      const SizedBox(height: 10),
                      Text('Precio de Compra: \$${widget.vehicle.purchasePrice}', style: const TextStyle(fontSize: 16)),
                      Text('Precio de Alquiler: \$${widget.vehicle.rentalPrice} por día', style: const TextStyle(fontSize: 16)),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
