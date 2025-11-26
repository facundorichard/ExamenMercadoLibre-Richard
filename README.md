#Link de Render


# Mutant Detector API 🧬

API REST desarrollada en Java con Spring Boot para detectar mutantes basándose en su secuencia de ADN. Proyecto realizado para el examen técnico de MercadoLibre.

## 🚀 Tecnologías

*   **Java 17**
*   **Spring Boot 3**
*   **H2 Database** (Base de datos en memoria para alto rendimiento)
*   **JUnit 5 & Mockito** (Testing unitario y de integración)
*   **Jacoco** (Reportes de cobertura de código > 90%)
*   **Docker** (Despliegue contenerizado)
*   **Lombok** (Reducción de boilerplate)
*   **Swagger/OpenAPI** (Documentación viva de la API)

## 📋 Características

*   **Detección de Mutantes**: Algoritmo optimizado para detectar secuencias de ADN (horizontal, vertical, diagonal).
*   **Optimización**: Uso de *Early Termination* y manejo eficiente de arrays para minimizar la complejidad temporal.
*   **Persistencia**: Almacenamiento de resultados para evitar re-análisis de ADNs ya verificados.
*   **API REST**: Endpoints claros y documentados.
*   **Calidad de Código**: Arquitectura hexagonal/capas, manejo de excepciones global y validaciones robustas.

## 🛠️ Instalación y Ejecución

### Prerrequisitos
*   Java 17 o superior
*   Gradle (opcional, se incluye wrapper)

### Ejecutar localmente
1.  Clonar el repositorio:
    ```bash
    git clone https://github.com/tu-usuario/mutant-detector.git
    cd mutant-detector
    ```
2.  Ejecutar la aplicación:
    ```bash
    ./gradlew bootRun
    ```
    La API iniciará en `http://localhost:8080`.

### Ejecutar con Docker 🐳
```bash
docker build -t mutant-detector .
docker run -p 8080:8080 mutant-detector
```

## 📡 Uso de la API

Puedes ver la documentación interactiva en Swagger UI:
👉 `http://localhost:8080/swagger-ui.html`

### 1. Detectar Mutante
Analiza una secuencia de ADN.

*   **URL**: `/mutant`
*   **Método**: `POST`
*   **Body**:
    ```json
    {
      "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
      ]
    }
    ```
*   **Respuestas**:
    *   `200 OK`: Es Mutante 👽
    *   `403 Forbidden`: Es Humano 👤
    *   `400 Bad Request`: ADN inválido (caracteres erróneos, matriz no cuadrada).

### 2. Ver Estadísticas
Obtiene el conteo de mutantes, humanos y el ratio.

*   **URL**: `/stats`
*   **Método**: `GET`
*   **Respuesta**:
    ```json
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }
    ```

## 🧪 Testing y Cobertura

El proyecto cuenta con una suite de tests exhaustiva (Unitarios + Integración).

Para ejecutar los tests y generar el reporte de cobertura:
```bash
./gradlew test jacocoTestReport
```
El reporte HTML estará disponible en `build/reports/jacoco/test/html/index.html`.
