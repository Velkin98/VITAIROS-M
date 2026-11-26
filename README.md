# VITAIROS-M

## Evaluación Parcial 4 - Desarrollo de Aplicaciones Móviles

**Asignatura:** DSY1105  
**Institución:** Duoc UC  
**Sede:** Valparaíso  
**Rama:** evaluacion-4  
**Versión:** 1.0

## 1. Descripción del proyecto

VITAIROS-M es una aplicación móvil de bienestar emocional
desarrollada en Kotlin y Jetpack Compose.

Su objetivo es facilitar el registro emocional, la orientación,
el acceso a recursos de bienestar y la solicitud simulada
de horas con profesionales.

La aplicación corresponde a un MVP académico y no reemplaza
la atención profesional ni realiza diagnósticos médicos.

## 2. Funcionalidades principales

- Pantalla de bienvenida.
- Inicio de sesión simulado.
- Validación de formularios.
- Registro de emociones.
- Consulta del historial emocional.
- Edición y eliminación de emociones.
- Gestión del perfil de usuario.
- Selector nativo de fotografías.
- Calendario nativo para seleccionar fechas.
- Creación de solicitudes de atención.
- Consulta, edición y eliminación de solicitudes.
- Recursos de bienestar emocional.
- Consumo de frases motivacionales mediante una API externa.

## 3. Tecnologías utilizadas

### Aplicación móvil

- Kotlin.
- Jetpack Compose.
- Material Design 3.
- Android Studio.
- Arquitectura MVVM.
- Room y SQLite.
- Retrofit 2.11.0.
- Gson.
- Kotlin Coroutines.
- StateFlow.
- Gradle.

### Backend

- Java 21.
- Spring Boot.
- Spring Web.
- Spring Data JPA.
- Jakarta Validation.
- Hibernate.
- H2 Database.
- Maven.
- IntelliJ IDEA.

### Pruebas y herramientas

- JUnit 4.
- MockK.
- Kotlin Coroutines Test.
- JaCoCo.
- Postman.
- Git.
- GitHub.
- Trello.

## 4. Arquitectura del proyecto

La aplicación utiliza una arquitectura MVVM, separando
la interfaz de usuario, la lógica de negocio y el acceso
a los datos.

Los microservicios utilizan una arquitectura por capas:

- Controller: exposición de endpoints REST.
- Service: lógica de negocio.
- Repository: acceso a la base de datos.
- Model: representación de las entidades.

La comunicación entre Android y los microservicios
se realiza mediante Retrofit y mensajes JSON.

## 5. Estructura del repositorio

```text
VITAIROS-M/
├── app/
│   ├── src/main/
│   ├── src/test/
│   ├── src/androidTest/
│   └── build.gradle.kts
├── backend/
│   ├── emocion-service/
│   │   ├── src/
│   │   ├── pom.xml
│   │   └── mvnw.cmd
│   └── solicitud-service/
│       ├── src/
│       ├── pom.xml
│       └── mvnw.cmd
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 6. Microservicios REST

### 6.1. Microservicio de emociones

Responsable de gestionar los registros emocionales
mediante operaciones CRUD.

- Puerto: 8081.
- Base de datos: H2 persistente.
- URL: http://localhost:8081/api/emociones

| Método | Endpoint | Operación |
|---|---|---|
| GET | /api/emociones | Listar emociones |
| POST | /api/emociones | Registrar emoción |
| PUT | /api/emociones/{id} | Actualizar emoción |
| DELETE | /api/emociones/{id} | Eliminar emoción |

### 6.2. Microservicio de solicitudes

Responsable de gestionar las solicitudes simuladas
de atención profesional.

- Puerto: 8082.
- Base de datos: H2 persistente.
- URL: http://localhost:8082/api/solicitudes

| Método | Endpoint | Operación |
|---|---|---|
| GET | /api/solicitudes | Listar solicitudes |
| POST | /api/solicitudes | Crear solicitud |
| PUT | /api/solicitudes/{id} | Actualizar solicitud |
| DELETE | /api/solicitudes/{id} | Eliminar solicitud |

Ambos microservicios fueron probados mediante Postman
y se encuentran integrados con la aplicación Android.

## 7. Consumo de API externa

La aplicación integra ZenQuotes API mediante Retrofit
para obtener frases motivacionales.

Las frases se presentan en la pantalla Recursos de Bienestar,
incluyendo el contenido, el autor y la atribución al proveedor.

Proveedor: https://zenquotes.io/

Esta funcionalidad requiere conexión a Internet.

## 8. Persistencia de datos

La aplicación utiliza Room y SQLite para la persistencia
local de información.

Los microservicios utilizan bases de datos H2 persistentes
e independientes.

- Emociones: ./data/emocionesdb
- Solicitudes: ./data/solicitudesdb

Las bases de datos se generan localmente durante la ejecución
de los servicios y no se incluyen en el repositorio.

## 9. Pruebas unitarias

Se implementaron pruebas unitarias para validar la lógica
de negocio, los formularios y los ViewModels.

| Clase de prueba | Pruebas |
|---|---:|
| ExampleUnitTest | 1 |
| LoginValidatorTest | 10 |
| PerfilValidatorTest | 11 |
| RegistroEmocionValidatorTest | 10 |
| EmocionViewModelTest | 6 |
| LoginViewModelTest | 10 |
| PerfilViewModelTest | 5 |
| SolicitudHoraViewModelTest | 6 |
| Total | 59 |

### Resultados obtenidos

- Pruebas ejecutadas: 59.
- Pruebas exitosas: 59.
- Pruebas fallidas: 0.
- Pruebas omitidas: 0.
- Tasa de éxito: 100%.

### Cobertura de código

| Paquete | Cobertura de instrucciones |
|---|---:|
| validation | 100% |
| viewmodel | 83% |

Los porcentajes corresponden a la cobertura de instrucciones
de los paquetes indicados, no a la cobertura global de la
aplicación ni a la cobertura de ramas.

### Ejecutar pruebas unitarias

Desde la raíz del proyecto, en PowerShell:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

### Generar reporte de cobertura

```powershell
.\gradlew.bat :app:createDebugUnitTestCoverageReport
```

### Abrir reporte de pruebas

```powershell
start .\app\build\reports\tests\testDebugUnitTest\index.html
```

### Abrir reporte de cobertura

```powershell
start .\app\build\reports\coverage\test\debug\index.html
```

## 10. Instrucciones de ejecución

### Requisitos

- Android Studio.
- IntelliJ IDEA o una terminal compatible con Maven.
- JDK 21 para los microservicios.
- Android SDK 37.
- Emulador Android.
- Conexión a Internet para ZenQuotes API.

### Clonar el repositorio

```powershell
git clone -b evaluacion-4 https://github.com/Velkin98/VITAIROS-M.git
cd VITAIROS-M
```

### Ejecutar el microservicio de emociones

Desde la raíz del repositorio, en una terminal:

```powershell
cd backend/emocion-service
.\mvnw.cmd spring-boot:run
```

### Ejecutar el microservicio de solicitudes

Desde la raíz del repositorio, en otra terminal:

```powershell
cd backend/solicitud-service
.\mvnw.cmd spring-boot:run
```

También es posible abrir cada microservicio como proyecto
Maven independiente en IntelliJ IDEA y ejecutar su clase
principal.

### Ejecutar la aplicación Android

1. Abrir el repositorio en Android Studio.
2. Sincronizar las dependencias de Gradle.
3. Iniciar ambos microservicios.
4. Iniciar un emulador Android.
5. Ejecutar el módulo app.

La aplicación utiliza las siguientes direcciones para
comunicarse con el equipo anfitrión desde el emulador:

- http://10.0.2.2:8081/
- http://10.0.2.2:8082/

Los servicios deben permanecer activos durante las pruebas.

## 11. APK firmado

Se generó un APK firmado correspondiente a la variante release
mediante Android Studio.

- Nombre: app-release.apk.
- Versión: 1.0.
- Application ID: com.example.vitairos_m.
- Alias de firma: vitairos.
- Archivo de firma: vitairos-release.jks.
- SDK mínimo: 26.
- SDK objetivo: 36.

Ruta del APK generado:

```text
app/build/outputs/apk/release/app-release.apk
```

El APK fue instalado y probado en un emulador
Pixel 10 Pro con API 37.

Se desactivó la optimización y minificación de la variante
release para resolver un error de inicialización relacionado
con R8 y las bibliotecas de Kotlin y AndroidX.

La llave privada de firma y sus contraseñas no se incluyen
en el repositorio público por motivos de seguridad.

## 12. Verificación funcional

Se realizaron las siguientes comprobaciones:

- Instalación y apertura del APK firmado.
- Inicio de sesión y navegación.
- Registro de emociones.
- Edición y eliminación de emociones.
- Creación de solicitudes.
- Edición y eliminación de solicitudes.
- Visualización de frases motivacionales de ZenQuotes API.

## 13. Control de versiones

El desarrollo se encuentra disponible en GitHub.

Repositorio:
https://github.com/Velkin98/VITAIROS-M

Rama de evaluación:
evaluacion-4

Se utilizó Git para registrar los cambios realizados durante
el desarrollo, la integración de microservicios, las pruebas
unitarias y la corrección del APK release.

## 14. Consideraciones de seguridad y alcance

VITAIROS-M es un proyecto académico desarrollado como
un producto mínimo viable.

- Utiliza datos ficticios y escenarios simulados.
- No realiza diagnósticos médicos.
- No reemplaza la atención profesional.
- No realiza reservas médicas reales.
- Los microservicios se ejecutan en un entorno local.
- La comunicación HTTP local se utiliza exclusivamente
  para fines de desarrollo y evaluación.
- No está destinado a un entorno productivo.

## 15. Conclusión

VITAIROS-M integra una aplicación móvil nativa, persistencia
local, microservicios REST, operaciones CRUD, consumo de una
API externa, pruebas unitarias y distribución mediante un
APK firmado.

El proyecto demuestra la integración entre frontend móvil
y backend, aplicando separación de responsabilidades,
validación de datos y control de versiones.

---

VITAIROS-M | Duoc UC | Evaluación Parcial 4 | 2026