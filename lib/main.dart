import 'package:flutter/material.dart';
import '/user_type_selection_screen.dart';

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
      home: const UserTypeSelectionScreen(), // La pantalla inicial es LoginScreen
    );
  }
}
