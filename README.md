# 🚇 MetroLima GO

**Planificador de rutas del Metro de Lima y transporte integrado.**

Este proyecto es una aplicación para Android nativa, desarrollada en 7 días, con el objetivo de brindar a los ciudadanos y visitantes una forma rápida y visual de planificar sus viajes en el Metro de Lima.

---

## 🎯 Objetivo del Proyecto

Brindar a los ciudadanos y visitantes una forma rápida y visual de **planificar sus viajes en el Metro de Lima** y rutas integradas (alimentadores, corredores y transporte complementario).

## ✨ Prototipo (Diseño de Referencia)

El desarrollo de la UI se basa en un prototipo de alta fidelidad, disponible en Figma.

**[➡️ Ver el prototipo interactivo en Figma aquí](https://www.figma.com/make/XvgVWkEB3NT0I4RmlToEsc/MetroLima-GO-App-Design?node-id=0-4&t=qVmSMh7lhdh5iBws-1)**

---

## 🚀 Stack Tecnológico

Este proyecto está construido 100% en Kotlin y sigue los principios de [Modern Android Development (MAD)](https://developer.android.com/modern-android-development).

* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Arquitectura:** MVVM (ViewModel)
* **Navegación:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
* **Base de Datos:** [Room](https://developer.android.com/training/data-storage/room) (para datos locales)
* **Networking:** [Retrofit 2](https://square.github.io/retrofit/) (para consumo de API)
* **Asincronía:** Coroutines & Kotlin Flow

---

## 📁 Estructura del Proyecto

El proyecto sigue una arquitectura limpia y modular, basada en la estructura definida el Día 1:

<img width="941" height="869" alt="image" src="https://github.com/user-attachments/assets/696ed9b0-0c6e-4678-bcea-62b69142034d" />


---

## 📋 Funcionalidades (Requerimientos)

El alcance del proyecto se divide en 5 módulos principales:

### 1️⃣ Módulo de Estaciones
* [ ] Registrar, listar y consultar estaciones del Metro.
* [ ] Mostrar datos: nombre, línea, distrito, horario.
* [ ] Permitir búsqueda de estaciones por nombre o línea.
* [ ] Visualizar detalles individuales de cada estación.

### 2️⃣ Módulo de Rutas
* [ ] Seleccionar estación de origen y destino.
* [ ] Calcular tiempo estimado del recorrido (simulado).
* [ ] Mostrar pasos o estaciones intermedias.
* [ ] Permitir guardar rutas favoritas.

### 3️⃣ Módulo de Datos Externos
* [ ] Consumir datos de una API o JSON remoto (Retrofit).
* [ ] Actualizar información de horarios, alertas o mantenimiento.
* [ ] Mostrar mensajes o notificaciones relevantes.

### 4️⃣ Módulo de Configuración
* [ ] Activar modo claro/oscuro.
* [ ] Cambiar idioma (español / inglés).
* [ ] Mostrar versión de la app y créditos.

### 5️⃣ Módulo de Inicio (Home)
* [ ] Pantalla principal con acceso a todas las funciones.
* [ ] Botones para "Estaciones", "Rutas", "Configuración".
* [ ] Mostrar información general del servicio del Metro.

---

## 🧑‍💻 Equipo y Docente

* **Docente:** JUAN LEON S.
* **Equipo de Desarrollo:**
    * TOLEDO LA FUENTE, LUIS MIGUEL - Líder/Arquitecto
    * VALERIANO COLAN, CARLOS ALBERTO - Diseñador UI/Assets
    * VASQUEZ CHAVEZ, FLAVIO FABRIZIO LUCAS - Constructor UI


