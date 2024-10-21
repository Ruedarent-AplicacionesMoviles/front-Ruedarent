import 'package:flutter/material.dart';
import 'package:ruedarentfrontend/services/auth_service.dart';
import 'renter/home_screen_rentador.dart';
import 'plan_selection_screen.dart'; // Pantalla solo para propietarios

class RegisterScreen extends StatefulWidget {
  final String role;

  const RegisterScreen({Key? key, required this.role}) : super(key: key);

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _authService = AuthService();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final TextEditingController _ageController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _dniController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _isLoading = false;
  String _errorMessage = '';

  Future<void> _register() async {
    setState(() {
      _isLoading = true;
    });

    String name = _nameController.text;
    String lastName = _lastNameController.text;
    int age = int.parse(_ageController.text);
    String email = _emailController.text;
    String phone = _phoneController.text;
    String dni = _dniController.text;
    String password = _passwordController.text;

    try {
      if (widget.role == 'rentador') {
        // Registro como rentador
        await _authService.registerRenter(
          name,
          lastName,
          age,
          email,
          phone,
          dni,
          password,
        );
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => const HomeScreenRentador()),
        );
      } else if (widget.role == 'propietario') {
        // Si es propietario, pasamos a la pantalla de selección de plan
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => PlanSelectionScreen(
              name: name,
              lastName: lastName,
              age: age,
              email: email,
              phone: phone,
              dni: dni,
              password: password,
              role: widget.role,
            ),
          ),
        );
      }
    } catch (error) {
      setState(() {
        _errorMessage = 'Error al registrar el usuario. Inténtalo de nuevo.';
      });
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Registro de Usuario')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              TextField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Nombre'),
              ),
              TextField(
                controller: _lastNameController,
                decoration: const InputDecoration(labelText: 'Apellido'),
              ),
              TextField(
                controller: _ageController,
                decoration: const InputDecoration(labelText: 'Edad'),
                keyboardType: TextInputType.number,
              ),
              TextField(
                controller: _emailController,
                decoration: const InputDecoration(labelText: 'Correo'),
              ),
              TextField(
                controller: _phoneController,
                decoration: const InputDecoration(labelText: 'Teléfono'),
                keyboardType: TextInputType.phone,
              ),
              TextField(
                controller: _dniController,
                decoration: const InputDecoration(labelText: 'DNI'),
              ),
              TextField(
                controller: _passwordController,
                decoration: const InputDecoration(labelText: 'Contraseña'),
                obscureText: true,
              ),
              const SizedBox(height: 20),
              if (_isLoading)
                const Center(child: CircularProgressIndicator())
              else
                Center(
                  child: ElevatedButton(
                    onPressed: _register,
                    child: const Text('Registrarse'),
                  ),
                ),
              if (_errorMessage.isNotEmpty)
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Center(
                    child: Text(
                      _errorMessage,
                      style: const TextStyle(color: Colors.red),
                    ),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }
}
