/*import 'package:flutter/material.dart';
import 'package:ruedarentfrontend/services/auth_service.dart';
import 'owner/home_screen_owner.dart';
import 'renter/home_screen_rentador.dart';

class PlanSelectionScreen extends StatefulWidget {
  final String name;
  final String lastName;
  final int age;
  final String email;
  final String phone;
  final String dni;
  final String password;
  final String role;

  const PlanSelectionScreen({
    Key? key,
    required this.name,
    required this.lastName,
    required this.age,
    required this.email,
    required this.phone,
    required this.dni,
    required this.password,
    required this.role,
  }) : super(key: key);

  @override
  _PlanSelectionScreenState createState() => _PlanSelectionScreenState();
}

class _PlanSelectionScreenState extends State<PlanSelectionScreen> {
  final AuthService _authService = AuthService();
  String _selectedPlan = 'Basic';
  String _errorMessage = '';
  bool _isLoading = false;

  // Método para registrar al usuario
  Future<void> _registerUser() async {
    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    try {
      if (widget.role == 'rentador') {
        await _authService.registerRenter(
          widget.name,
          widget.lastName,
          widget.age,
          widget.email,
          widget.phone,
          widget.dni,
          widget.password,
        );
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => const HomeScreenRentador()),
        );
      } else if (widget.role == 'propietario') {
        await _authService.registerOwner(
          widget.name,
          widget.lastName,
          widget.age,
          widget.email,
          widget.phone,
          widget.dni,
          widget.password,
          _selectedPlan,
        );
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (context) => HomeScreenOwner(
              name: widget.name,
              lastName: widget.lastName,
              email: widget.email,
              planType: _selectedPlan,
            ),
          ),
        );
      }
    } catch (e) {
      print('Error durante el registro: $e');
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
      appBar: AppBar(
        title: const Text('Seleccionar Plan'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (widget.role == 'propietario') ...[
              const Text(
                'Elige el plan para propietarios:',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              DropdownButton<String>(
                value: _selectedPlan,
                items: <String>['Basic', 'Premium', 'Gold'].map((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
                onChanged: (String? newValue) {
                  setState(() {
                    _selectedPlan = newValue!;
                  });
                },
              ),
              const SizedBox(height: 40),
            ],
            Center(
              child: _isLoading
                  ? const CircularProgressIndicator()
                  : ElevatedButton(
                onPressed: _registerUser,
                child: const Text('Completar Registro'),
              ),
            ),
            if (_errorMessage.isNotEmpty) // mensaje error
              Padding(
                padding: const EdgeInsets.only(top: 20),
                child: Text(
                  _errorMessage,
                  style: const TextStyle(color: Colors.red),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
*/