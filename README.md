# ecoARsystem

# English

## AR Environmental Monitoring System

An Android mobile application for environmental monitoring and real-time visualization of ecological data using Augmented Reality (AR).

The project combines environmental monitoring technologies with AR visualization to help users better understand air quality indicators and ecological conditions directly in their surrounding environment.

## Features

* Real-time environmental monitoring
* Augmented Reality visualization of ecological indicators
* Air Quality Index (AQI) calculation and display
* Interactive map with pollution markers
* Environmental statistics and history tracking
* User authentication and authorization
* Export of environmental reports
* Weather and air quality integration through external APIs

## Environmental Indicators

The application supports monitoring of:

* PM2.5
* PM10
* NO₂ (Nitrogen Dioxide)
* O₃ (Ozone)
* CO (Carbon Monoxide)
* Temperature
* Humidity
* Atmospheric pressure
* Wind speed
* Wind direction
* AQI (Air Quality Index)

## Technologies Used

### Mobile Application

* Kotlin
* Jetpack Compose
* ARCore
* SceneView
* Android SDK

### Backend

* REST API
* JSON data processing
* JWT authentication
* BCrypt password hashing

### External APIs

* OpenWeatherMap API
* IQAir API

## System Architecture

The system consists of:

1. Android mobile client
2. Backend server
3. Database for storing environmental records
4. External environmental data providers

## Main Functionality

### AR Visualization

The application displays environmental data as 3D objects in augmented reality using the smartphone camera.

### Interactive Map

Users can:

* Search locations
* View pollution markers
* Analyze air quality in different regions

### Environmental Reports

The system allows users to:

* Save geolocation-based environmental records
* View historical statistics
* Export reports in PDF and CSV formats

### Authentication

* User registration and login
* Secure JWT authorization
* Cloud synchronization of saved data

## Project Goals

The main goal of the project is to create a modern AR-based environmental monitoring system adapted to Ukrainian conditions and accessible to both environmental specialists and ordinary users.

---

# Українська

## AR-система екологічного моніторингу

Мобільний Android-застосунок для екологічного моніторингу та візуалізації екологічних даних у режимі реального часу з використанням технологій доповненої реальності (AR).

Проєкт поєднує технології екологічного моніторингу та AR-візуалізації для більш наочного відображення показників якості повітря та стану довкілля безпосередньо у просторі навколо користувача.

## Основні можливості

* Моніторинг екологічних показників у реальному часі
* AR-візуалізація екологічних даних
* Розрахунок та відображення індексу якості повітря (AQI)
* Інтерактивна карта забруднення
* Перегляд статистики та історії вимірювань
* Реєстрація та автентифікація користувачів
* Експорт екологічних звітів
* Інтеграція з API погодних та екологічних сервісів

## Підтримувані показники

Застосунок підтримує моніторинг таких показників:

* PM2.5
* PM10
* NO₂ (діоксид азоту)
* O₃ (озон)
* CO (чадний газ)
* Температура
* Вологість
* Атмосферний тиск
* Швидкість вітру
* Напрямок вітру
* AQI (індекс якості повітря)

## Використані технології

### Мобільний застосунок

* Kotlin
* Jetpack Compose
* ARCore
* SceneView
* Android SDK

### Серверна частина

* REST API
* Обробка JSON-даних
* JWT-автентифікація
* Хешування паролів BCrypt

### Зовнішні API

* OpenWeatherMap API
* IQAir API

## Архітектура системи

Система складається з:

1. Android-клієнта
2. Серверної частини
3. Бази даних для збереження екологічних записів
4. Зовнішніх джерел екологічних даних

## Основний функціонал

### AR-візуалізація

Застосунок відображає екологічні показники у вигляді тривимірних AR-об’єктів через камеру смартфона.

### Інтерактивна карта

Користувач може:

* Шукати локації
* Переглядати маркери забруднення
* Аналізувати якість повітря в різних регіонах

### Екологічні звіти

Система дозволяє:

* Зберігати геоприв’язані екологічні записи
* Переглядати історію та статистику
* Експортувати звіти у PDF та CSV

### Автентифікація

* Реєстрація та вхід користувачів
* Захищена JWT-авторизація
* Хмарна синхронізація даних

## Мета проєкту

Основною метою проєкту є створення сучасної AR-системи екологічного моніторингу, адаптованої до українських умов та доступної як для екологів і дослідників, так і для звичайних користувачів.

* Підтримка офлайн-моніторингу
