# API Calendario — Taller 1 Arquitectura de Software

**Instituto Tecnológico Metropolitano (ITM)** · Arquitectura de Software 2

**Entrega 2:** diagramas de arquitectura de la API Calendario (Spring Boot + PostgreSQL).

**Entrega 4:** implementación de la API Calendario con arquitectura Onion y seguridad por token (Taller 3).

## Integrantes

| Nombre | Correo |
|---|---|
| Nathalie Gabriela Miranda Rejón | nathaliemiranda1117738@correo.itm.edu.co |
| Samuel Quiroz Rincón | samuelquiroz1113937@correo.itm.edu.co |
| Sebastián Jesús Pérez Araujo | sebastianperez1116036@correo.itm.edu.co |
| Kevin Daniel Mendoza Castillo | kevinmendoza1111192@correo.itm.edu.co |

## Contexto

La API Calendario es un servicio web en Spring Boot + PostgreSQL que genera el calendario laboral de
un año. Es cliente de la API Festivos: le pide la lista de festivos del año
(`GET /api/festivos/obtener/{año}`) y con ella clasifica cada día.

### Operaciones

Todas las rutas, salvo `validar`, exigen el encabezado `Authorization: Bearer <token>`. Sin token
válido la API responde `403 Forbidden`.

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/usuarios/validar/{usuario}/{clave}` | Login: devuelve el usuario y el token de seguridad |
| GET | `/api/calendario/festivos/{año}` | Lista los festivos del año obtenidos de la API Festivos |
| GET | `/api/calendario/generar/{año}` | Guarda en la base de datos todos los días del año clasificados y responde `true` si el proceso terminó con éxito |
| GET | `/api/calendario/listar/{año}` | Devuelve el calendario del año con cada día clasificado |
| GET | `/api/tipos/listar` | Lista los tipos de día |
| GET | `/api/tipos/obtener/{id}` | Obtiene un tipo de día |
| GET | `/api/tipos/buscar/{nombre}` | Busca tipos cuyo nombre contenga el texto |
| POST | `/api/tipos/agregar` | Agrega un tipo (`{"tipo": "Día cívico"}`) |
| PUT | `/api/tipos/modificar` | Modifica un tipo (`{"id": 4, "tipo": "Día de vacaciones"}`) |
| DELETE | `/api/tipos/eliminar/{id}` | Elimina un tipo; los tipos 1-3 y los asignados a días no se pueden eliminar |

Generar un año que ya existe lo reemplaza (no duplica días). Para clasificar, la API pide una sola vez
la lista de festivos del año a la API Festivos y la consulta en memoria; si la API Festivos no responde,
devuelve `502 Bad Gateway` y no modifica la base de datos.

Ejemplo de `listar/2023`:

```json
[
  { "id": 1, "fecha": "2023-01-01", "tipo": { "id": 3, "tipo": "Día festivo" }, "descripcion": "Domingo" },
  { "id": 2, "fecha": "2023-01-02", "tipo": { "id": 1, "tipo": "Día laboral" }, "descripcion": "Lunes" }
]
```

### Tipos de día

| Id | Tipo | Regla |
|---|---|---|
| 1 | Día laboral | Lunes a viernes que no es festivo |
| 2 | Fin de Semana | Sábado o domingo que no es festivo |
| 3 | Día festivo | Fecha que aparece en la lista de la API Festivos |

## Arquitectura Onion

Proyecto Maven multi-módulo; las dependencias apuntan siempre hacia el centro.

| Módulo | Contenido |
|---|---|
| `dominio` | Entidades `Calendario`, `Tipo`, `Usuario` y DTOs `FestivoDto`, `UsuarioLoginDto` |
| `core` | Interfaces de repositorios (`ICalendarioRepositorio`, `ITipoRepositorio`, `IUsuarioRepositorio`), servicios, integración (`IFestivoServicioExterno`) y excepciones |
| `aplicacion` | `CalendarioServicio`, `TipoServicio`, `UsuarioServicio` y el paquete `seguridad` (JWT, filtro, `UserDetailsService`) |
| `infraestructura` | Entidades JPA, repositorios Spring Data, mapeadores y `FestivoServicioExterno` (RestClient) |
| `presentacion` | `ApiApplication`, controladores, `ConfiguracionSeguridad`, `SwaggerConfig` y manejo de errores |

## Ejecución

### Requisitos

| Software | Versión | Linux | macOS | Windows |
|---|---|---|---|---|
| JDK (no basta el JRE) | 21 o superior | `sudo apt install openjdk-21-jdk` / `sudo pacman -S jdk21-openjdk` | `brew install openjdk@21` | `winget install EclipseAdoptium.Temurin.21.JDK` |
| PostgreSQL | 14 o superior | `sudo apt install postgresql` / `sudo pacman -S postgresql` | `brew install postgresql@17` | `winget install PostgreSQL.PostgreSQL.17` |
| API Festivos | en ejecución | ver su README | ver su README | ver su README |

Maven no hace falta: el proyecto trae Maven Wrapper (`mvnw` / `mvnw.cmd`), que lo descarga solo.

Comprobar la instalación (`javac` confirma que es un JDK):

```bash
javac -version
psql --version
```

### Variables de entorno

Todas son opcionales; si no se definen se usa el valor por defecto.

| Variable | Defecto |
|---|---|
| `PORT` | `8081` |
| `DB_URL` | `jdbc:postgresql://localhost:5432/calendariolaboral` |
| `DB_USUARIO` / `DB_CLAVE` | `postgres` / `postgres` |
| `FESTIVOS_API_URL` | `http://localhost:8080` |
| `JWT_SECRETO` | clave de desarrollo (cambiarla fuera de desarrollo) |
| `JWT_MINUTOS` | `30` |
| `USUARIO_INICIAL` / `CLAVE_INICIAL` | `admin` / `admin123` |

Al arrancar se crean las tablas y los tipos de día (`schema.sql` / `data.sql`) y el usuario inicial si no
existe; su clave se guarda cifrada con BCrypt.

### Linux / macOS

```bash
psql -h localhost -U postgres -c "CREATE DATABASE calendariolaboral;"
./mvnw clean package
DB_CLAVE=tu_clave java -jar presentacion/target/apicalendario.jar
```

### Windows (PowerShell)

```powershell
psql -h localhost -U postgres -c "CREATE DATABASE calendariolaboral;"
.\mvnw.cmd clean package
$env:DB_CLAVE = "tu_clave"
java -jar presentacion\target\apicalendario.jar
```

Si la API Festivos corre en otro puerto, definir `FESTIVOS_API_URL` antes de arrancar
(`FESTIVOS_API_URL=http://localhost:3000` en Linux/macOS, `$env:FESTIVOS_API_URL = "http://localhost:3000"`
en PowerShell).

Para recompilar, primero detener la API: reemplazar el `.jar` con la API en ejecución provoca errores 500.

La API queda en `http://localhost:8081` y Swagger en `http://localhost:8081/swagger-ui.html`
(botón **Authorize** para pegar el token).

### Flujo de prueba

Linux / macOS:

```bash
TOKEN=$(curl -s localhost:8081/api/usuarios/validar/admin/admin123 | jq -r .token)
curl -H "Authorization: Bearer $TOKEN" localhost:8081/api/calendario/generar/2023
curl -H "Authorization: Bearer $TOKEN" localhost:8081/api/calendario/listar/2023
```

Windows (PowerShell):

```powershell
$token = (Invoke-RestMethod http://localhost:8081/api/usuarios/validar/admin/admin123).token
$h = @{ Authorization = "Bearer $token" }
Invoke-RestMethod http://localhost:8081/api/calendario/generar/2023 -Headers $h
Invoke-RestMethod http://localhost:8081/api/calendario/listar/2023 -Headers $h
```

Si `generar` responde `502`, la API Festivos no está en ejecución o `FESTIVOS_API_URL` apunta a otro puerto.

## Diagramas

| Diagrama | Archivo |
|---|---|
| Arquitectura cebolla (onion) | [diagramas/diagrama-arquitectura-calendario.md](diagramas/diagrama-arquitectura-calendario.md) |
| Modelo relacional de la base de datos | [diagramas/diagrama-relacional-calendario.md](diagramas/diagrama-relacional-calendario.md) |
