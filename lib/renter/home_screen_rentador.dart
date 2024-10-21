import 'package:flutter/material.dart';

class HomeScreenRentador extends StatelessWidget {
  const HomeScreenRentador({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Rentador'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('Bienvenido Rentador'),
            ElevatedButton(
              onPressed: () {
                // Lógica para gestionar alquileres, agregar vehículos, etc.
              },
              child: const Text('Gestionar Alquileres'),
            ),
          ],
        ),
      ),
    );
  }
}

class HomeScreenPropietario extends StatelessWidget {
  const HomeScreenPropietario({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Propietario'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('Bienvenido Propietario'),
            ElevatedButton(
              onPressed: () {
                // Lógica para gestionar el vehículo propio, ver estadísticas, etc.
              },
              child: const Text('Gestionar Vehículos'),
            ),
          ],
        ),
      ),
    );
  }
}