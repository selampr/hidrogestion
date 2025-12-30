# HidroGestión

HidroGestión es una aplicación Android desarrollada como **Trabajo de Fin de Grado (TFG)** del ciclo de **Técnico Superior en Desarrollo de Aplicaciones Multiplataforma**.

La aplicación está orientada a **trabajadores del suministro de agua y entidades municipales**, con el objetivo de **gestionar cortes de agua y comunicar incidencias a los ciudadanos de forma clara y centralizada**, sin necesidad de que los ciudadanos dispongan de una cuenta o registro previo.

El proyecto fue desarrollado de forma **individual** en un **plazo aproximado de 3 meses**, abarcando todas las fases del desarrollo de software: análisis, diseño, implementación, pruebas y documentación.

---

## 🎓 Contexto académico

- Tipo de proyecto: Trabajo de Fin de Grado  
- Ciclo formativo: Técnico Superior en Desarrollo de Aplicaciones Multiplataforma  
- Duración: 3 meses  
- Metodología: Scrum  
- Número de sprints: 7  
- Dedicación estimada: ~147 horas  
- Desarrollo: individual  

---

## 🎯 Objetivo del proyecto

El objetivo principal de HidroGestión es **facilitar la gestión de cortes de agua** por parte de los trabajadores responsables del servicio, mejorando la comunicación con los ciudadanos afectados.

La aplicación permite centralizar la información de los cortes, asociarlos a zonas concretas y notificar a los vecinos de forma directa, reduciendo la dependencia de otros canales de comunicación y mejorando la eficiencia del servicio.

---

## 👥 Usuarios del sistema

- **Trabajador**  
  Usuario principal de la aplicación. Puede iniciar sesión, registrar cortes de agua, seleccionar zonas afectadas, enviar notificaciones y consultar el historial de cortes.

- **Vecino**  
  Usuario pasivo que recibe notificaciones relacionadas con cortes de agua en su zona, sin necesidad de registro ni interacción directa con la aplicación.

---

## 📱 Funcionalidades principales

- Registro e inicio de sesión de trabajadores
- Creación y gestión de cortes de agua
- Selección y marcado de zonas afectadas mediante mapa
- Comprobación de vecinos afectados por zona
- Envío de notificaciones informativas
- Consulta del historial de cortes
- Visualización del detalle de cada corte

---

## 🧱 Arquitectura y organización del proyecto

El proyecto presenta una **separación básica de responsabilidades**, inspirada en el patrón **MVVM**, aunque **no se aplica de forma estricta**.

La aplicación incluye:
- Clases de interfaz (Activities y Fragments)
- ViewModels para la gestión de datos y estado
- Clases de modelo y acceso a datos

En algunos puntos, la lógica de negocio se encuentra acoplada a la capa de interfaz, algo identificado durante el desarrollo como **área de mejora y aprendizaje**.  
Esta aproximación permitió comprender los conceptos principales del patrón MVVM y sentar una base para futuras refactorizaciones.

---

## 🛠️ Tecnologías utilizadas

- Kotlin
- XML para layouts
- Android SDK
- Android Studio
- Room (persistencia de datos)
- Google Maps (gestión de zonas)
- Gradle
- Git y GitHub (control de versiones)

---

## ⚙️ Requisitos

- Android Studio (versión estable recomendada)
- Android SDK configurado
- Emulador o dispositivo Android físico

---
