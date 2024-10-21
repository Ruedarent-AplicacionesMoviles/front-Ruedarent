import 'package:flutter/material.dart';
import 'owner/home_screen_owner.dart';  // Asegúrate de importar correctamente
import 'renter/home_screen_rentador.dart';  // Asegúrate de importar correctamente

class UserTypeSelectionScreen extends StatelessWidget {
  const UserTypeSelectionScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Selecciona Tipo de Usuario'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // Botón para Propietario
            ElevatedButton(
              onPressed: () {
                // Redirige al Home del Propietario con el ownerId correspondiente
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => HomeScreenOwner(ownerId: '1'),  // Asegúrate de pasar el ownerId correcto
                  ),
                );
              },
              child: const Text('Entrar como Propietario'),
            ),
            const SizedBox(height: 20),
            // Botón para Rentador
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => HomeScreenRenter(),  // Redirige al Home del Rentador
                  ),
                );
              },
              child: const Text('Entrar como Rentador'),
            ),
          ],
        ),
      ),
    );
  }
}
