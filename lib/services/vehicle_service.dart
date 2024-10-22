import 'dart:convert';
import 'package:http/http.dart' as http;
import '../model/vehicle.dart';

class VehicleService {
  final String baseUrl = 'http://10.0.2.2:3000';

  //metodo para mostrar los vehiculos
  Future<List<Vehicle>> getVehicles(String ownerId) async {
    final response = await http.get(Uri.parse('$baseUrl/vehicles?idOwner=$ownerId'));
    print('Respuesta de la API: ${response.body}');
    if (response.statusCode == 200) {
      final List<dynamic> jsonData = json.decode(response.body);
      return jsonData.map((json) => Vehicle.fromJson(json)).toList();
    } else {
      print('Error al cargar vehículos: ${response.statusCode}, ${response.body}');
      return []; // Temporalmente devuelve una lista vacía para ver si funciona
    }
  }

  //metodo para eliminar un vehiculo
  Future<void> deleteVehicle(int vehicleId) async {
    print('Intentando eliminar el vehículo con ID: $vehicleId');
    final response = await http.delete(Uri.parse('$baseUrl/vehicles/$vehicleId'));

    if (response.statusCode != 200) {
      print('Error: ${response.body}');
      throw Exception('Error al eliminar el vehículo');
    }
  }
  //metodo para editar un vehiculo
  Future<void> updateVehicle(Vehicle vehicle) async {
    final response = await http.put(
      Uri.parse('$baseUrl/vehicles/${vehicle.id}'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(vehicle.toJson()),
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

    if (response.statusCode != 201) {
      throw Exception('Error al agregar el vehículo');
    }
  }
}
