# FinanciaPlus - App de Onboarding Digital

Aplicación móvil para solicitud de tarjeta de crédito con validaciones AML, scoring crediticio y captura biométrica.

## 🚀 Tecnologías

**App Android:**
- Kotlin + Jetpack Compose
- Clean Architecture
- Hilt, Retrofit, Navigation Compose

**APIs Backend:**
- Spring Boot (Kotlin)
- REST APIs mockeadas

- **Repositorios separados:**
- [AML API](https://github.com/Elizabeth186/financia-plus-aml-api) - Puerto 8081
- [BANK API](https://github.com/Elizabeth186/financia-plus-bank-api) - Puerto 8082

---

**Instrucciones de ejecución:**
```bash
# Clonar App
git clone https://github.com/tu_usuario/financia-plus-app.git
cd ruta/repositorio
Run

# Clonar e iniciar AML API
git clone https://github.com/tu_usuario/financia-plus-aml-api.git
cd ruta/repositorio
./gradlew bootRun

# Clonar e iniciar Bank API
git clone https://github.com/tu_usuario/financia-plus-bank-api.git
cd ruta/repositorio
./gradlew bootRun
```
> **Nota1:** También puede descargar el archivo .zip desde GitHub, descomprimir y abrir cada proyecto en el IDE de su preferencia.
> >**Nota2:** El api AML admite 2 tipos de busqueda por Nombre y Por Docmuento 
---

## Requisitos

- Android Studio Panda 1 | 2025.3.1
- JDK 17+
- Emulador Android o dispositivo físico

---

### 2. Ejecutar la App Android

1. Abre `financia-plus-app` en Android Studio
2. Sync Gradle
3. Run en emulador

**Para dispositivo físico:**
- Obtén tu IP local: `ifconfig` (Mac/Linux) o `ipconfig` (Windows)
- Edita `build.gradle.kts` flavor `dev`:
```kotlin
  buildConfigField("String", "AML_BASE_URL", "\"http://TU_IP:8081/\"")
  buildConfigField("String", "BANK_BASE_URL", "\"http://TU_IP:8082/\"")
```
- Dispositivo y PC en la misma red WiFi

>**Nota:** Las URLs y API keys están en `build.gradle.kts` para facilitar la evaluación. En producción, usar `local.properties` u otra alternativa segura.


---

## 🔐 Credenciales APIs

**Header requerido:**
```
X-API-KEY: dev-aml-key    (AML API)
X-API-KEY: dev-bank-key   (Bank API)
```
>**Nota:** Estas API keys son solo para desarrollo. En producción, usar variables de entorno o servicios de secrets management (AWS Secrets Manager, etc...).

---

## 📊 Datos de Prueba

### Bank API - Clientes Registrados:

| DUI | Nombre | Score | Resultado |
|-----|--------|-------|-----------|
| `12345678-9` | Ana Maria Lopez | 8.5 | ✅ Aprobado |
| `98765432-1` | Juan Carlos Perez | 6.0 | ❌ Bloqueado (score <7.0) |
| `11223344-5` | Sofia Beatriz Ramirez | 9.2 | ✅ Aprobado |

### AML API - Lista Negra:

| DUI | Nombre | Razón | Resultado |
|-----|--------|-------|-----------|
| `01234567-8` | Carlos Alberto Mendez | Lavado de dinero | ❌ Bloqueado |
| `09876543-2` | Maria Elena Gutierrez | Fraude | ❌ Bloqueado |
| `05555555-1` | Roberto Jose Flores | Terrorismo | ❌ Bloqueado |

---

## 🧪 Flujo de Prueba Recomendado

1. Ingresar DUI: `12345678-9` → Cliente existente ✅ → Datos autocompletados
2. Continuar flujo completo → Aprobación por score 8.5 e ingresos

**Alternativa (rechazo):**
- Ingresar DUI: `98765432-1` → Bloqueado por score bajo
- Ingresar DUI: `01234567-8` → Bloqueado por AML

---

## 📁 Estructura del Proyecto
```
financia-plus-app/
├── core/
│   ├── navigation/      # Navegación y rutas
│   ├── network/         # Retrofit + APIs
│   ├── security/        # SessionManager encriptado
│   ├── ui/             # Componentes reutilizables
│   └── utils/          # Máscaras de entrada
├── feature/
│   ├── onboarding/     # Validación de identidad
│   └── origination/    # Solicitud de tarjeta
└── backend/
    ├── aml-api/        # API listas negras (8080)
    └── bank-api/       # API clientes (8081)
```

---

## 📄 Documentación

Ver documento técnico adjunto para detalles sobre:
- Arquitectura completa
- Flujo de usuario
- Puntos críticos y soluciones
- Propuestas de mejora
- Métricas recomendadas

---
