/*import 'package:flutter/material.dart';
import 'package:ruedarentfrontend/services/auth_service.dart';
import 'package:ruedarentfrontend/user_type_selection_screen.dart'; // Pantalla para selección de tipo de usuario
import 'renter/home_screen_rentador.dart'; // Importa la pantalla Home del Rentador
import 'owner/home_screen_owner.dart'; // Importa la pantalla Home del Propietario

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _authService = AuthService();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _isLoading = false;
  String _errorMessage = '';

  // Función para manejar el inicio de sesión
  Future<void> _login() async {
    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    final email = _emailController.text;
    final password = _passwordController.text;

    try {
      // Llamada al servicio de login
      final success = await _authService.login(email, password);

      if (success) {
        // Obtener el rol del usuario desde la Fake API
        final String role = await _authService.getRole(email);

        // Navegar según el rol
        if (role == 'rentador') {
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => const HomeScreenRentador()),
          );
        } else {
          // Suponemos que es propietario
          final ownerDetails = await _authService.getOwnerDetails(email); // Obtener detalles del propietario

          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => HomeScreenOwner(
              name: ownerDetails['name'],
              lastName: ownerDetails['lastName'],
              email: ownerDetails['email'],
              planType: ownerDetails['planType'], // Plan del propietario
            )),
          );
        }
      } else {
        setState(() {
          _errorMessage = 'Usuario o contraseña incorrectos';
        });
      }
    } catch (e) {
      setState(() {
        _errorMessage = 'Error en el inicio de sesión. Por favor, inténtalo de nuevo.';
      });
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  // Función para navegar a la pantalla de selección de tipo de usuario
  void _navigateToTypeSelection() {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const UserTypeSelectionScreen()), // Redirigir a la pantalla de selección de tipo de usuario
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: _emailController,
              decoration: const InputDecoration(labelText: 'Email'),
            ),
            TextField(
              controller: _passwordController,
              decoration: const InputDecoration(labelText: 'Password'),
              obscureText: true,
            ),
            const SizedBox(height: 20),
            _isLoading
                ? const CircularProgressIndicator()
                : ElevatedButton(
              onPressed: _login,
              child: const Text('Login'),
            ),
            if (_errorMessage.isNotEmpty)
              Padding(
                padding: const EdgeInsets.only(top: 16),
                child: Text(
                  _errorMessage,
                  style: const TextStyle(color: Colors.red),
                ),
              ),
            const SizedBox(height: 20),
            // Añadir enlace para registrarse
            TextButton(
              onPressed: _navigateToTypeSelection,
              child: const Text(
                '¿No tienes una cuenta? Regístrate aquí',
                style: TextStyle(color: Colors.blue),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
*/