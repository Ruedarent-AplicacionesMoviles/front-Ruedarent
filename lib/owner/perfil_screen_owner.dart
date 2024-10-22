import 'package:flutter/material.dart';
import '../model/student.dart';

class PerfilScreen extends StatefulWidget {
  @override
  _PerfilScreenState createState() => _PerfilScreenState();
}

class _PerfilScreenState extends State<PerfilScreen> {
  late Future<Student> futureStudent;

  @override
  void initState() {
    super.initState();
    // Llamar a la API al cargar la pantalla
    futureStudent = fetchStudent();
  }

  // Método para simular la obtención de datos de un estudiante
  Future<Student> fetchStudent() async {
    // Simulamos un retraso como si fuera una llamada a la API
    await Future.delayed(const Duration(seconds: 2));

    // Simulamos la respuesta de la API con los datos del estudiante
    final Map<String, dynamic> fakeApiResponse = {
      "idOwner": 1,
      "name": "Ana",
      "lastName": "Martínez",
      "age": 22,
      "email": "ana.martinez@example.com",
      "phone": "555-5678",
      "dni": "87654321",
      "password": "password2",
      "idplan": 2
    };

    return Student.fromJson(fakeApiResponse); // Convertimos los datos en un objeto Student
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<Student>(
        future: futureStudent,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator()); // Mostrar un loader mientras se cargan los datos
          } else if (snapshot.hasError) {
            return const Center(child: Text('Error al cargar datos'));
          } else if (snapshot.hasData) {
            // Si tenemos los datos, mostramos la interfaz con los datos del estudiante
            Student student = snapshot.data!;
            return Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  // Título centrado en el cuerpo
                  const Text(
                    'Perfil',
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                      color: Colors.green,
                    ),
                    textAlign: TextAlign.center, // Centrar el texto
                  ),
                  const SizedBox(height: 20), // Espacio debajo del título

                  // Imagen de usuario centrada
                  const CircleAvatar(
                    radius: 50, // Tamaño de la imagen de usuario
                    backgroundImage: AssetImage('assets/images/user_avatar.png'), // Asegúrate de tener esta imagen en tu proyecto
                  ),
                  const SizedBox(height: 20), // Espacio entre la imagen y la siguiente sección

                  // Detalles del estudiante
                  Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    elevation: 4,
                    child: ListTile(
                      leading: const Icon(Icons.person, color: Colors.green),
                      title: Text('${student.name} ${student.lastName}'),
                      subtitle: const Text('Nombre de usuario'),
                    ),
                  ),
                  const SizedBox(height: 10),
                  Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    elevation: 4,
                    child: ListTile(
                      leading: const Icon(Icons.email, color: Colors.green),
                      title: Text(student.email),
                      subtitle: const Text('Correo electrónico'),
                    ),
                  ),
                  const SizedBox(height: 10),
                  Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    elevation: 4,
                    child: ListTile(
                      leading: const Icon(Icons.phone, color: Colors.green),
                      title: Text(student.phone),
                      subtitle: const Text('Celular'),
                    ),
                  ),
                  const SizedBox(height: 20), // Espacio antes del botón

                  // Botón de actualizar perfil
                  ElevatedButton.icon(
                    onPressed: () {
                      // Acción para actualizar perfil
                    },
                    icon: const Icon(Icons.arrow_forward, color: Colors.white),
                    label: const Text('Actualizar Perfil'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.green, // Color del botón
                      padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                      textStyle: const TextStyle(fontSize: 18),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0),
                      ),
                    ),
                  ),
                ],
              ),
            );
          } else {
            return const Center(child: Text('No se encontraron datos'));
          }
        },
      ),
    );
  }
}