# Diagrama de arquitectura — API Calendario

Arquitectura cebolla (onion) de la API Calendario (Spring Boot + PostgreSQL).

```mermaid
graph TB
    %% -----------------------------------------------------------
    %% NIVEL 1: CAPA DE PRESENTACIÓN / ENTRADA
    %% -----------------------------------------------------------
    subgraph Presentacion ["Capa 1: Presentación (API / Web)"]
        ApiApp["ApiApplication<br/>@SpringBootApplication"]
        Controladores["CalendarioControlador"]
        Handlers["ExcepcionesGlobalesHandler"]
        DtosPresentacion["ErrorRespuesta"]
        Configuracion["SwaggerConfig"]
    end

    %% -----------------------------------------------------------
    %% NIVEL 2: CAPA DE APLICACIÓN
    %% -----------------------------------------------------------
    subgraph Aplicacion ["Capa 2: Aplicación (Casos de Uso)"]
        ServiciosApp["CalendarioServicio"]
    end

    %% -----------------------------------------------------------
    %% NIVEL 3: CAPA CORE Y DOMINIO (CENTRO / REGLAS DE NEGOCIO)
    %% -----------------------------------------------------------
    subgraph Negocio ["Capa 3: Core y Dominio"]
        subgraph Core ["Interfaces / Puertos (Core)"]
            InterfacesServicio["ICalendarioServicio"]
            InterfacesRepo["ICalendarioRepositorio<br/>ITipoRepositorio"]
            InterfacesIntegracion["IFestivoServicioExterno"]
        end

        subgraph Dominio ["Modelos y DTOs (Dominio)"]
            Entidades["Calendario / Tipo"]
            DTOs["FestivoDto"]
        end
    end

    %% -----------------------------------------------------------
    %% NIVEL 4: CAPA DE INFRAESTRUCTURA (ADAPTADORES / SALIDA)
    %% -----------------------------------------------------------
    subgraph Infraestructura ["Capa 4: Infraestructura (Adaptadores y Persistencia)"]
        RepositoriosImpl["CalendarioRepositorio<br/>TipoRepositorio"]
        IntegracionExt["FestivoServicioExterno<br/>HttpServicio"]
        Mapeadores["CalendarioMapeador<br/>TipoMapeador"]
        RepositoriosJPA["ICalendarioRepositorioJpa<br/>ITipoRepositorioJpa"]
        EntidadesJPA["CalendarioEntidad<br/>TipoEntidad"]
    end

    %% -----------------------------------------------------------
    %% NIVEL 5: SISTEMAS EXTERNOS
    %% -----------------------------------------------------------
    subgraph Externos ["Recursos Externos"]
        DB[("PostgreSQL<br/>CalendarioLaboral")]
        APIExterna["API Festivos<br/>Express JS + MongoDB"]
    end

    %% -----------------------------------------------------------
    %% FLUJO DESCENDENTE ENTRE CAPAS
    %% -----------------------------------------------------------
    %% Flujo interno Presentación
    Handlers -->|Usa| DtosPresentacion

    %% Presentación -> Aplicación & Core
    Controladores -->|Inyecta| InterfacesServicio

    %% Aplicación -> Core & Dominio
    ServiciosApp -.->|Implementa| InterfacesServicio
    ServiciosApp -->|Inyecta| InterfacesRepo
    ServiciosApp -->|Inyecta| InterfacesIntegracion

    %% Core -> Dominio
    InterfacesServicio -->|Usa| Entidades
    InterfacesRepo -->|Usa| Entidades
    InterfacesIntegracion -->|Usa| DTOs

    %% Infraestructura -> Core (Inversión de dependencias)
    RepositoriosImpl -.->|Implementa| InterfacesRepo
    IntegracionExt -.->|Implementa| InterfacesIntegracion
    Mapeadores <-->|Transforma| Entidades

    %% Infraestructura Interna
    RepositoriosImpl -->|Usa| Mapeadores
    RepositoriosImpl -->|Inyecta| RepositoriosJPA
    Mapeadores <-->|Transforma| EntidadesJPA

    %% Infraestructura -> Sistemas Externos
    RepositoriosJPA -->|Spring Data JPA| DB
    EntidadesJPA -->|Mapeo ORM| DB
    IntegracionExt -->|RestTemplate / GET| APIExterna

    %% -----------------------------------------------------------
    %% ESTILOS VISUALES
    %% -----------------------------------------------------------
    classDef default fill:#ffffff,stroke:#334155,stroke-width:1.5px,color:#0f172a;

    style Presentacion fill:#f8fafc,stroke:#64748b,stroke-width:2px,color:#1e293b
    style Aplicacion fill:#f0fdf4,stroke:#16a34a,stroke-width:2px,color:#14532d
    style Negocio fill:#faf5ff,stroke:#9333ea,stroke-width:2px,color:#581c87
    style Core fill:#ffffff,stroke:#c084fc,stroke-width:1.5px,color:#581c87
    style Dominio fill:#ffffff,stroke:#fbbf24,stroke-width:1.5px,color:#78350f
    style Infraestructura fill:#f1f5f9,stroke:#475569,stroke-width:2px,color:#0f172a
    style Externos fill:#ecfeff,stroke:#0891b2,stroke-width:2px,color:#155e75
```
