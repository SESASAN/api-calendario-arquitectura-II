# API Calendario — Taller 1 Arquitectura de Software

**Instituto Tecnológico Metropolitano (ITM)** · Arquitectura de Software 2

**Entrega 2:** diagramas de arquitectura de la API Calendario (Spring Boot + PostgreSQL).

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

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/calendario/generar/{año}` | Guarda en la base de datos todos los días del año clasificados y responde `true` si el proceso terminó con éxito |
| GET | `/api/calendario/listar/{año}` | Devuelve el calendario del año con cada día clasificado |

### Tipos de día

| Id | Tipo | Regla |
|---|---|---|
| 1 | Día laboral | Lunes a viernes que no es festivo |
| 2 | Fin de semana | Sábado o domingo que no es festivo |
| 3 | Día festivo | Fecha que aparece en la lista de la API Festivos |

## Diagramas

| Diagrama | Archivo |
|---|---|
| Arquitectura cebolla (onion) | [diagramas/diagrama-arquitectura-calendario.md](diagramas/diagrama-arquitectura-calendario.md) |
| Modelo relacional de la base de datos | [diagramas/diagrama-relacional-calendario.md](diagramas/diagrama-relacional-calendario.md) |
