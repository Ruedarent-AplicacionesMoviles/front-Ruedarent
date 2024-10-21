import 'dart:convert';
import 'package:http/http.dart' as http;
import '../model/vehicle.dart';

class VehicleService {
  final String baseUrl = 'http://localhost:3000';  // Para emulador de Android (localhost para web)

  Future<List<Vehicle>> getVehicles(String ownerId) async {
    final response = await http.get(Uri.parse('$baseUrl/vehicles?idOwner=$ownerId'));  // Cambia a idOwner
    if (response.statusCode == 200) {
      final List<dynamic> jsonData = json.decode(response.body);
      return jsonData.map((json) => Vehicle.fromJson(json)).toList();
    } else {
      throw Exception('Error al cargar los vehículos');
    }
  }

  Future<void> deleteVehicle(int vehicleId) async {
    print('Intentando eliminar el vehículo con ID: $vehicleId');
    final response = await http.delete(Uri.parse('$baseUrl/vehicles/$vehicleId'));

    if (response.statusCode != 200) {
      print('Error: ${response.body}');
      throw Exception('Error al eliminar el vehículo');
    }
  }

  Future<void> updateVehicle(Vehicle vehicle) async {
    final response = await http.put(
      Uri.parse('$baseUrl/vehicles/${vehicle.id}'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(vehicle.toJson()),  // Aquí usamos el método toJson
    );

    if (response.statusCode != 200) {
      throw Exception('Error al actualizar el vehículo');
    }
  }

  // Método para agregar un nuevo vehículo
  Future<void> addVehicle(Vehicle vehicle) async {
    final response = await http.post(
      Uri.parse('$baseUrl/vehicles'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(vehicle.toJson()),
    );

    if (response.statusCode != 201) {  // Normalmente, un código 201 indica creación exitosa
      throw Exception('Error al agregar el vehículo');
    }
  }
}