# Garment Shop Android App (Kotlin, XML Views)

This is a traditional Android app (no Compose) built with the Declarative Gradle DSL. It implements:
- Product listing with search
- Product details
- Cart management
- Login / Register (mock)
- Checkout (mock)

The app uses a MockApiService so it runs fully offline. Replace MockApiService with a real API implementation to integrate with the `garment_store_database` backend.

## Build

```bash
./gradlew :app:assembleDebug
```

## Install and Run (device/emulator connected)

```bash
./gradlew :app:installDebug
```

Launch "Garment Shop". The launcher forwards to the Home screen.

## Code Structure

- app/src/main/kotlin/org/example/app/data/... : models, repositories, mock API
- app/src/main/kotlin/org/example/app/ui/... : activities and adapters
- XML layouts in app/src/main/res/layout

No secrets are hard-coded. For production, store base URLs / tokens using environment-driven build config or secure storage.

## Backend Integration Notes

- ApiService defines the contract to the backend named "garment_store_database".
- Swap MockApiService for a Retrofit-based implementation and wire it in GarmentApp.onCreate().
- Configure base URLs using BuildConfig fields driven by environment variables (do not hardcode).