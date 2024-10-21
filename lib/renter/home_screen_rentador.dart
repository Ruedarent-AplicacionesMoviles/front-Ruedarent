import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import '../services/vehicle_service.dart';
import 'widgets/vehicle_card_renter.dart';

class HomeScreenRenter extends StatefulWidget {
  const HomeScreenRenter({Key? key}) : super(key: key);

  @override
  _HomeScreenRenterState createState() => _HomeScreenRenterState();
}

class _HomeScreenRenterState extends State<HomeScreenRenter> {
  late Future<List<Vehicle>> _vehiclesFuture;
  final VehicleService _vehicleService = VehicleService();
  String? _selectedVehicleType;
  RangeValues _priceRange = const RangeValues(0, 50000);  // Rango de precios

  @override
  void initState() {
    super.initState();
    _vehiclesFuture = _vehicleService.getVehicles('1');  // Cargar los vehículos del propietario con idOwner 1
  }

  List<Vehicle> _filterVehicles(List<Vehicle> vehicles) {
    return vehicles.where((vehicle) {
      final matchesType = _selectedVehicleType == null || vehicle.vehicleType == _selectedVehicleType;
      final matchesPrice = vehicle.purchasePrice >= _priceRange.start && vehicle.purchasePrice <= _priceRange.end;
      return matchesType && matchesPrice;
    }).toList();
  }

  void _openFilters() {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (BuildContext context) {
        return StatefulBuilder(
          builder: (BuildContext context, StateSetter setModalState) {
            return Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  DropdownButton<String>(
                    hint: const Text('Seleccionar tipo de vehículo'),
                    value: _selectedVehicleType,
                    isExpanded: true,
                    onChanged: (String? newValue) {
                      setModalState(() {
                        _selectedVehicleType = newValue;
                      });
                    },
                    items: <String>['Car', 'Scooter', 'Bicycle', 'Motorbike']
                        .map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                  ),
                  const SizedBox(height: 10),
                  const Text('Rango de precio de compra'),
                  RangeSlider(
                    values: _priceRange,
                    min: 0,
                    max: 50000,
                    divisions: 50,
                    labels: RangeLabels(
                      '\$${_priceRange.start.round()}',
                      '\$${_priceRange.end.round()}',
                    ),
                    onChanged: (RangeValues values) {
                      setModalState(() {
                        _priceRange = values;
                      });
                    },
                  ),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                      setState(() {});
                    },
                    child: const Text('Aplicar Filtros'),
                  ),
                ],
              ),
            );
          },
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Vehículos Disponibles'),
        actions: [
          IconButton(
            icon: const Icon(Icons.filter_list),
            onPressed: _openFilters,  // Botón de abrir filtros
          ),
        ],
      ),
      body: FutureBuilder<List<Vehicle>>(
        future: _vehiclesFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return const Center(child: Text('Error al cargar los vehículos'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No hay vehículos disponibles'));
          }

          final filteredVehicles = _filterVehicles(snapshot.data!);

          return ListView.builder(
            itemCount: filteredVehicles.length,
            itemBuilder: (context, index) {
              final vehicle = filteredVehicles[index];
              return VehicleCardRenter(vehicle: vehicle);  // Reutilizamos la tarjeta de vehículo
            },
          );
        },
      ),
    );
  }
}
