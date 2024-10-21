import 'package:flutter/material.dart';

class HomeScreenOwner extends StatelessWidget {
  final String name;
  final String lastName;
  final String email;
  final String planType;

  const HomeScreenOwner({
    Key? key,
    required this.name,
    required this.lastName,
    required this.email,
    required this.planType,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Owner Home'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Welcome, Owner!',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 20),
            _buildOwnerInfo(),
            const SizedBox(height: 20),
            _buildPlanInfo(),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                // Acción de cerrar sesión
              },
              child: const Text('Logout'),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildOwnerInfo() {
    return Card(
      elevation: 4.0,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Your Information:',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 10),
            Text('Name: $name $lastName'),
            const SizedBox(height: 5),
            Text('Email: $email'),
          ],
        ),
      ),
    );
  }

  Widget _buildPlanInfo() {
    return Card(
      elevation: 4.0,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Your Plan:',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 10),
            Text('Current Plan: $planType'),
            const SizedBox(height: 5),
            // Aquí puedes agregar más detalles sobre el plan, o botones para cambiar de plan, etc.
          ],
        ),
      ),
    );
  }
}