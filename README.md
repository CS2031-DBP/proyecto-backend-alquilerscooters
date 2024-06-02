# ScooterSmart 🚲

## Descripción 💡
"ScooterSmart" es una plataforma innovadora diseñada para facilitar el alquiler y gestión de scooters eléctricos. Con una interfaz intuitiva y un conjunto robusto de características, esta aplicación permite una administración eficiente y centralizada de todos los aspectos operativos, desde el registro de usuarios y scooters hasta la gestión de viajes y recompensas. Además, ofrece herramientas avanzadas para la optimización del servicio y la seguridad de los datos, proporcionando una experiencia mejorada tanto para los administradores como para los usuarios.

## Propósito 🎯
"Facilitar la gestión integral del alquiler de scooters, permitiendo a los usuarios realizar viajes de manera eficiente y segura, mientras que los administradores pueden gestionar y optimizar la distribución de scooters, monitorear su uso y realizar mantenimiento predictivo. Todo esto se logra a través de un sistema centralizado y automatizado, mejorando la experiencia del usuario y la eficiencia operativa."

## Contexto 🌍
"La gestión de un servicio de alquiler de scooters puede ser compleja, especialmente cuando se trata de optimizar la distribución, mantener los vehículos y asegurar una experiencia de usuario sin problemas. Nuestro sistema aborda estos desafíos proporcionando herramientas para la gestión integral del servicio, desde la administración de usuarios y scooters hasta la optimización de rutas y el mantenimiento predictivo. Esto asegura que los scooters estén disponibles cuando y donde se necesiten, mejorando la satisfacción del usuario y la eficiencia del servicio."

## Miembros del Equipo 😎
| Nombre            | Usuario de GitHub | Contacto                       |
|-------------------|-------------------|-------------------------------|
| Luis Fernando Lopez Chambi | user1             | user1@example.com             |
| David Manuel Silva Mena | user2             | user2@example.com             |
|                   | user3             | user3@example.com             |
| Miembro 4         | user4             | user4@example.com             |

## Arquitectura y Tecnologías Utilizadas ⚙️
"Alquiler de Scooters" emplea una arquitectura modular y escalable, organizada en capas distintas que encapsulan funcionalidades específicas como la gestión de entidades, controladores, objetos de transferencia de datos (DTO) y repositorios. A continuación, se describen las tecnologías utilizadas:

- **Lenguaje de Programación**: Java
- **Frameworks y Librerías**:
    - **Spring Boot**: Para el desarrollo del backend.
- **Herramientas de Persistencia**:
    - **Spring Data JPA**: Para la gestión de la persistencia.
- **Base de Datos**:
    - **PostgreSQL**: Base de datos robusta y escalable.
- **Entorno de Desarrollo**:
    - **Maven**: Para la gestión de dependencias.
- **Control de Versiones**:
    - **Git**: Para el control de versiones del código.
    - **GitHub**: Repositorio para la colaboración y gestión del código.

## Desarrollo del Código 🛠️
El desarrollo del código se inició con las entidades básicas. Utilizando sesiones de codificación colaborativa, el equipo implementó las funcionalidades principales de registro e inicio de sesión, así como la gestión de scooters. Se adoptaron buenas prácticas de desarrollo y se utilizaron herramientas de control de versiones para asegurar la integridad del código. Además, se utilizaron issues con distintos branches, lo que nos permitió trabajar en diferentes partes del código de manera simultánea. Una vez que una funcionalidad estaba lista, se realizaba un pull request hacia el main.

## Estructuración de la Infraestructura 🏗️
Se creó la estructura del proyecto, dividiendo las carpetas y estableciendo la arquitectura necesaria para la persistencia de datos y la lógica de negocio. Se diseñaron y desarrollaron los servicios de localización de scooters, permitiendo el seguimiento en tiempo real y la gestión eficiente de la flota.

## Desarrollo de Controladores y DTO 🚀
Luego de haber realizado el modelo de las entidades y relacionarlas entre sí, comenzamos con el desarrollo de los controladores, servicios y objetos de transferencia de datos (DTO) para asegurar una integración fluida y sin problemas. Realizamos un workspace dentro de Postman para poder probar todos nuestros endpoints.

## Optimización y Mantenimiento 🔧
Se llevaron a cabo optimizaciones del código y de su funcionamiento respecto a la lógica del negocio. Se solucionaron diversos detalles para mejorar la eficiencia del sistema sin realizar cambios sumamente importantes. El equipo continuó monitoreando y ajustando el sistema en función de los comentarios de los usuarios y el análisis de datos de uso.

## Medidas de Seguridad Implementadas 🔒
- **Seguridad de Datos**: Utilizamos técnicas como cifrado de contraseñas con BCrypt, autenticación mediante JWT y una gestión de permisos detallada para asegurar que solo los usuarios autorizados accedan a ciertas funcionalidades.
- **Prevención de Vulnerabilidades**: Implementamos medidas para prevenir vulnerabilidades comunes como inyección SQL, XSS y CSRF. El uso de tokens JWT y la configuración adecuada de CORS ayuda a mitigar estos riesgos.

## Licencia 🌐
Este proyecto está licenciado bajo la Licencia Pública General Affero v3.0 - vea el archivo LICENSE para más detalles.


