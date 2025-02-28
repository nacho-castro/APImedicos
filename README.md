# API de Gestión de Turnos Médicos

## Descripción
Esta API permite gestionar médicos, pacientes y consultas dentro de un sistema de salud. Implementa un CRUD para las entidades de médicos, pacientes, usuarios y consultas, con endpoints protegidos mediante autenticación Bearer.

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

# 📘 Modelo de Dominio - API de Médicos

Este documento describe las entidades principales del dominio en la base de datos de la API de Médicos. La API gestiona consultas médicas, pacientes, médicos y usuarios, asegurando un correcto almacenamiento y recuperación de información.

## 📌 Entidades del Dominio

### 1. 🏥 Medico
Representa a los médicos registrados en la plataforma.

- **id** (Long) - Identificador único.
- **nombre** (String) - Nombre completo del médico.
- **email** (String) - Correo electrónico del médico.
- **telefono** (String) - Número de teléfono.
- **especialidad** (Enum) - Especialidad médica.
- **direccion** (Embeddable) - Datos de la dirección del médico.
- **activo** (Boolean) - Indica si el médico está activo.

### 2. 🩺 Paciente
Representa a los pacientes registrados en la plataforma.

- **id** (Long) - Identificador único.
- **nombre** (String) - Nombre completo del paciente.
- **email** (String) - Correo electrónico del paciente.
- **telefono** (String) - Número de contacto.
- **documento** (String) - Documento de identidad.
- **direccion** (Embeddable) - Datos de la dirección del paciente.
- **activo** (Boolean) - Indica si el paciente está activo.

### 3. 📅 Consulta
Representa las consultas médicas entre médicos y pacientes.

- **id** (Long) - Identificador único.
- **paciente** (Paciente) - Relación con el paciente.
- **medico** (Medico) - Relación con el médico.
- **fecha** (LocalDateTime) - Fecha y hora de la consulta.
- **motivo** (String) - Descripción del motivo de la consulta.
- **activa** (Boolean) - Indica si la consulta sigue activa o fue cancelada.

### 4. 🔐 Usuario
Representa a los usuarios del sistema con credenciales de acceso.

- **id** (Long) - Identificador único.
- **nombre** (String) - Nombre del usuario.
- **email** (String) - Correo electrónico (usado para autenticación).
- **password** (String) - Contraseña cifrada.
- **rol** (Enum) - Rol del usuario en el sistema (ADMIN, MEDICO, PACIENTE).

## 🔗 Relaciones entre Entidades

- Un **médico** puede tener múltiples **consultas**.
- Un **paciente** puede tener múltiples **consultas**.
- Un **usuario** puede ser un **médico**, un **paciente** o un **administrador**.

## 📂 Notas Técnicas

- La **dirección** es una entidad embebida utilizada tanto en **médicos** como en **pacientes**.
- La seguridad se maneja con autenticación basada en tokens Bearer.
- Las eliminaciones de médicos y pacientes son **lógicas**, estableciendo el campo `activo` en `false`.

---

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
