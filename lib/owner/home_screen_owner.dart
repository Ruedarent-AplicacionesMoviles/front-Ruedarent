import 'package:flutter/material.dart';
import '../model/vehicle.dart';
import 'perfil_screen_owner.dart';
import '../services/vehicle_service.dart';
import 'vehicle_add_screen.dart';
import 'vehicle_details_owner_screen.dart';

class HomeScreenOwner extends StatefulWidget {
  final String ownerId;

  const HomeScreenOwner({Key? key, required this.ownerId}) : super(key: key);

  @override
  _HomeScreenOwnerState createState() => _HomeScreenOwnerState();
}

class _HomeScreenOwnerState extends State<HomeScreenOwner> {
  late Future<List<Vehicle>> _vehiclesFuture;
  final VehicleService _vehicleService = VehicleService();
  int _currentIndex = 1; // Índice inicial en la pestaña de Vehículos

  @override
  void initState() {
    super.initState();
    _vehiclesFuture = _vehicleService.getVehicles(widget.ownerId); // Inicializamos aquí
  }

  // Lista de pantallas a mostrar según la pestaña seleccionada
  List<Widget> _screens(BuildContext context) {
    return [
      Center(child: Text('Favoritos')), // Pantalla de favoritos (ejemplo)
      FutureBuilder<List<Vehicle>>( // Pantalla de vehículos
        future: _vehiclesFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return const Center(child: Text('Error al cargar los vehículos'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No tienes vehículos registrados'));
          }

          final vehicles = snapshot.data!;

          return ListView.builder(
            itemCount: vehicles.length,
            itemBuilder: (context, index) {
              final vehicle = vehicles[index];
              return GestureDetector(
                onTap: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => VehicleDetailsOwnerScreen(vehicle: vehicle),
                  ),
                ),
                child: Card(
                  elevation: 5,
                  margin: const EdgeInsets.all(12),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        ClipRRect(
                          borderRadius: BorderRadius.circular(10),
                          child: Image.network(
                            vehicle.imageUrl,
                            height: 150, // Establece un tamaño específico
                            width: double.infinity,
                            fit: BoxFit.cover, // Asegúrate de que la imagen se ajuste correctamente
                            errorBuilder: (BuildContext context, Object exception, StackTrace? stackTrace) {
                              return const Text('No se pudo cargar la imagen'); // Mensaje en caso de error
                            },
                          ),
                        ),
                        const SizedBox(height: 10),
                        Text(
                          'Marca: ${vehicle.brand}',
                          style: const TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Text('Tipo: ${vehicle.vehicleType}'),
                        const SizedBox(height: 5),
                        Text(
                          'Precio de Compra: \$${vehicle.purchasePrice}',
                          style: const TextStyle(
                            color: Colors.grey,
                          ),
                        ),
                        Text(
                          'Precio de Alquiler: \$${vehicle.rentalPrice} por día',
                          style: const TextStyle(
                            color: Colors.grey,
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              );
            },
          );
        },
      ),
      PerfilScreen(), // Pantalla de perfil
    ];
  }

  // Función para cambiar la pantalla dentro de IndexedStack
  void _onTabTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('RuedaRent'),
        backgroundColor: Colors.green, // Cambiar el color del AppBar
      ),
      body: IndexedStack(
        index: _currentIndex,
        children: _screens(context), // Utilizamos la función _screens
      ),
      floatingActionButton: _currentIndex == 1 // Mostrar botón solo en la pantalla de vehículos
          ? FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => VehicleAddScreen(ownerId: widget.ownerId),
            ),
          );
        },
        child: const Icon(Icons.add),
        backgroundColor: Colors.green,
      )
          : null, // No mostrar FloatingActionButton en otras pantallas
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Colors.white,
        selectedItemColor: Colors.green,
        unselectedItemColor: Colors.grey,
        onTap: _onTabTapped, // Cambia la pantalla al hacer tap
        currentIndex: _currentIndex, // Índice actual
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.favorite),
            label: 'Favoritos',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.directions_car),
            label: 'Vehículos',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Perfil',
          ),
        ],
      ),
    );
  }
}
