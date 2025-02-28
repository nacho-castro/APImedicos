# API de Gestión de Turnos Médicos

## Descripción
Esta API permite gestionar médicos, pacientes y consultas dentro de un sistema de salud. Implementa un CRUD para la entidad de médicos con endpoints protegidos mediante autenticación Bearer.

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- JPA/Hibernate
- PostgreSQL
- Jakarta Validation
- Swagger para documentación

## Instalación
1. Clona este repositorio.
2. Configura la base de datos en `application.properties`.
3. Ejecuta la aplicación con `mvn spring-boot:run`.

## Endpoints
### Médicos
#### Registrar un médico
**POST** `/medicos`
```json
{
  "nombre": "Dr. Juan Pérez",
  "email": "juan.perez@example.com",
  "telefono": "123456789",
  "especialidad": "CARDIOLOGIA",
  "direccion": {
    "calle": "Calle Falsa 123",
    "distrito": "Centro",
    "ciudad": "Ciudad Ejemplo",
    "numero": "100",
    "complemento": "Piso 2"
  }
}
```

#### Listar médicos activos
**GET** `/medicos`

#### Obtener médico por ID
**GET** `/medicos/{id}`

#### Actualizar un médico
**PUT** `/medicos`
```json
{
  "id": 1,
  "nombre": "Dr. Juan Pérez",
  "telefono": "987654321"
}
```

#### Eliminar un médico (borrado lógico)
**DELETE** `/medicos/{id}`

## Seguridad
Esta API usa autenticación Bearer para proteger los endpoints. Se recomienda configurar un proveedor OAuth2 o JWT.
