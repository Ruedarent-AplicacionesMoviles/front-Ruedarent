class Vehicle {
  final int id;
  final String brand;
  final String model;
  final String color;
  final String vehicleType;
  final String imageUrl;
  final String description;
  final int purchasePrice;
  final int rentalPrice;
  final int idOwner; // Asegúrate de tener esta propiedad definida

  Vehicle({
    required this.id,
    required this.brand,
    required this.model,
    required this.color,
    required this.vehicleType,
    required this.imageUrl,
    required this.description,
    required this.purchasePrice,
    required this.rentalPrice,
    required this.idOwner,  // Asegúrate de tener este campo
  });

  // Método para convertir el objeto Vehicle a un mapa JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'brand': brand,
      'model': model,
      'color': color,
      'vehicleType': vehicleType,
      'imageUrl': imageUrl,
      'description': description,
      'purchasePrice': purchasePrice,
      'rentalPrice': rentalPrice,
      'idOwner': idOwner,  // Asegúrate de incluir esto aquí también
    };
  }

  // Método para crear un objeto Vehicle desde un JSON
  factory Vehicle.fromJson(Map<String, dynamic> json) {
    return Vehicle(
      id: json['id'],
      brand: json['brand'],
      model: json['model'],
      color: json['color'],
      vehicleType: json['vehicleType'],
      imageUrl: json['imageUrl'],
      description: json['description'],
      purchasePrice: json['purchasePrice'],
      rentalPrice: json['rentalPrice'],
      idOwner: json['idOwner'],  // Asegúrate de tener esta asignación
    );
  }
}
