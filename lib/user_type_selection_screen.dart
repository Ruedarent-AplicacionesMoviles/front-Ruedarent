import 'package:flutter/material.dart';
import 'register_screen.dart';

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
            ElevatedButton(
              onPressed: () {
                // Llevar al usuario a la pantalla de registro como rentador
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const RegisterScreen(role: 'rentador'),
                  ),
                );
              },
              child: const Text('Registrarse como Rentador'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                // Llevar al usuario a la pantalla de registro como propietario
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const RegisterScreen(role: 'propietario'),
                  ),
                );
              },
              child: const Text('Registrarse como Propietario'),
            ),
          ],
        ),
      ),
    );
  }
}
