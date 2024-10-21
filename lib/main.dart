import 'package:flutter/material.dart';
import 'login_screen.dart'; // Importa la pantalla de login

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'RuedaRent',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      home: const LoginScreen(), // La pantalla inicial es LoginScreen
    );
  }
}