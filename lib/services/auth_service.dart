import 'package:http/http.dart' as http;
import 'dart:convert';

class AuthService {
  static const String baseUrl = 'http://localhost:3000';

  // Método para obtener los detalles de un propietario por su idOwner
  Future<Map<String, dynamic>> getOwnerDetailsById(int idOwner) async {
    final response = await http.get(Uri.parse('$baseUrl/students?idOwner=$idOwner'));

    if (response.statusCode == 200) {
      final List owners = jsonDecode(response.body);
      if (owners.isNotEmpty) {
        return owners.first;  // Retorna el primer resultado si hay un propietario con ese idOwner
      } else {
        throw Exception('Propietario no encontrado');
      }
    } else {
      throw Exception('Error al obtener detalles del propietario');
    }
  }

  // Método para iniciar sesión
  Future<bool> login(String email, String password) async {
    final response = await http.get(Uri.parse('$baseUrl/students?email=$email&password=$password'));

    if (response.statusCode == 200) {
      final List students = jsonDecode(response.body);
      return students.isNotEmpty;
    } else {
      return false;
    }
  }

  // Método para registrar rentadores
  Future<void> registerRenter(
      String name, String lastName, int age, String email, String phone, String dni, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/acquirers'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'name': name,
        'lastName': lastName,
        'age': age,
        'email': email,
        'phone': phone,
        'dni': dni,
        'password': password,
      }),
    );

    // Imprimir el estado y el cuerpo de la respuesta para depurar
    print('Response status: ${response.statusCode}');
    print('Response body: ${response.body}');

    if (response.statusCode != 201) {
      throw Exception('Error al registrar rentador');
    }
  }

  // Método para registrar propietarios
  Future<void> registerOwner(
      String name, String lastName, int age, String email, String phone, String dni, String password, String planType) async {
    final response = await http.post(
      Uri.parse('$baseUrl/students'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'name': name,
        'lastName': lastName,
        'age': age,
        'email': email,
        'phone': phone,
        'dni': dni,
        'password': password,
        'plan': {
          'idPlan': planType == 'Basic' ? 1 : planType == 'Premium' ? 2 : 3,
          'planType': planType,
          'planDescription': '$planType subscription plan',
          'planPrice': planType == 'Basic' ? 100 : planType == 'Premium' ? 200 : 300,
        },
      }),
    );

    print('Response status (Owner): ${response.statusCode}');
    print('Response body (Owner): ${response.body}');

    if (response.statusCode != 201) {
      throw Exception('Error al registrar propietario');
    }
  }

  // Método para obtener el rol del usuario
  Future<String> getRole(String email) async {

    final rentadorResponse = await http.get(Uri.parse('$baseUrl/acquirers?email=$email'));
    if (rentadorResponse.statusCode == 200) {
      final List rentadores = jsonDecode(rentadorResponse.body);
      if (rentadores.isNotEmpty) {
        return 'rentador';
      }
    }

    // Verificar si el usuario es un propietario
    final propietarioResponse = await http.get(Uri.parse('$baseUrl/students?email=$email'));
    if (propietarioResponse.statusCode == 200) {
      final List propietarios = jsonDecode(propietarioResponse.body);
      if (propietarios.isNotEmpty) {
        return 'propietario';
      }
    }

    // Si no es ninguno, retornar un valor por defecto o lanzar un error
    throw Exception('Rol de usuario no encontrado');
  }

  // Método para obtener los detalles de un propietario
  Future<Map<String, dynamic>> getOwnerDetails(String email) async {
    // Hacemos una petición GET a la API para obtener los detalles del propietario por email
    final response = await http.get(Uri.parse('$baseUrl/students?email=$email'));

    if (response.statusCode == 200) {
      final List owners = jsonDecode(response.body);
      if (owners.isNotEmpty) {
        return owners.first; // Retornamos el primer resultado si hay un propietario con ese email
      } else {
        throw Exception('Propietario no encontrado');
      }
    } else {
      throw Exception('Error al obtener detalles del propietario');
    }
  }

}
