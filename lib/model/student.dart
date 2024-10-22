class Student {
  final int idOwner;
  final String name;
  final String lastName;
  final int age;
  final String email;
  final String phone;
  final String dni;
  final String password;
  final int idplan;

  Student({
    required this.idOwner,
    required this.name,
    required this.lastName,
    required this.age,
    required this.email,
    required this.phone,
    required this.dni,
    required this.password,
    required this.idplan,
  });

  factory Student.fromJson(Map<String, dynamic> json) {
    return Student(
      idOwner: json['idOwner'],
      name: json['name'],
      lastName: json['lastName'],
      age: json['age'],
      email: json['email'],
      phone: json['phone'],
      dni: json['dni'],
      password: json['password'],
      idplan: json['idplan'],
    );
  }
}