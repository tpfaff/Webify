# Webify - KMP Spotify SDK

A Kotlin Multiplatform library for interacting with the Spotify Web API, targeting Android and iOS.

## Project Structure

```
Webify/
├── shared/                    # KMP Shared Library (Core SDK)
│   └── src/
│       ├── commonMain/        # Platform-agnostic code
│       │   └── kotlin/org/jetbrains/greeting/
│       │       ├── Webify.kt              # Main SDK entry point
│       │       ├── ApiClient.kt           # HTTP client implementation
│       │       ├── TokenProvider.kt       # OAuth token management
│       │       └── responses/             # Data models
│       ├── androidMain/       # Android-specific (OkHttp client)
│       └── iosMain/           # iOS-specific (Darwin client)
├── composeApp/                # Demo Android app (Compose)
└── iosApp/                    # Demo iOS app (SwiftUI)
```

## Key Classes

| Class | Purpose |
|-------|---------|
| `Webify` | Main SDK entry point, builder pattern |
| `ApiClient` | Ktor HTTP client with auth |
| `TokenProvider` | Thread-safe token caching with Mutex |
| `SpotifySearchResult` | Search response model |
| `TrackAudioFeatures` | Audio analysis data |

## Commands

```bash
# Build shared library
./gradlew :shared:build

# Build Android demo app
./gradlew :composeApp:assembleDebug

# Run Android demo
./gradlew :composeApp:installDebug

# Build iOS framework
./gradlew :shared:linkDebugFrameworkIosArm64

# Clean build
./gradlew clean
```

## SDK Usage

### Initialization
```kotlin
// Android
val webify = Webify.Builder
    .clientId("your_client_id")
    .clientSecret("your_client_secret")
    .applicationContext(applicationContext)
    .build()

// iOS
val webify = Webify.Builder
    .clientId("your_client_id")
    .clientSecret("your_client_secret")
    .build()
```

### Search for Tracks
```kotlin
val result = webify.searchForTrack("Imagine Dragons")
result.onSuccess { searchResult ->
    searchResult.tracks.items.forEach { track ->
        println("${track.name} by ${track.artists.first().name}")
    }
}
```

### Get Audio Analysis
```kotlin
val result = webify.getTrackAnalysis(trackId)
result.onSuccess { analysis ->
    println("Key: ${analysis.track.key}, Mode: ${analysis.track.mode}")
    println("Tempo: ${analysis.track.tempo} BPM")
}
```

## Authentication

Uses **Client Credentials Flow** (server-to-server, no user auth):
- Endpoint: `https://accounts.spotify.com/api/token`
- Grant type: `client_credentials`
- Tokens auto-cached and refreshed

## API Coverage

### Implemented
- `GET /v1/search` - Track search
- `GET /v1/audio-analysis/{id}` - Audio analysis

### Not Implemented
- User endpoints (profile, top tracks)
- Playlist CRUD
- Album/Artist details
- Player controls
- Authorization Code Flow

## Dependencies

- **Ktor** 3.0.0 - HTTP client (OkHttp/Darwin)
- **Kotlinx Serialization** - JSON parsing
- **Kotlinx Coroutines** 1.9.0 - Async
- **Kotlinx Datetime** 0.4.0 - Time handling
- **Napier** 2.7.1 - Logging

## Platform Configuration

| Platform | Min Version | HTTP Engine |
|----------|-------------|-------------|
| Android | API 24 | OkHttp |
| iOS | - | Darwin |

## Data Models

### TrackAudioFeatures
- `bars` - Musical bar markers
- `beats` - Beat timing
- `sections` - Song sections with key/mode/tempo
- `segments` - Detailed audio segments
- `tatums` - Rhythmic subdivisions

### MusicalKey
Enum for pitch classes (C, C#, D, etc.) with chord type detection.

### MusicalMode
Major/Minor mode with pattern definitions for chord construction.

## Known TODOs

1. Enforce credentials via builder pattern validation
2. Improve error handling in Result types
3. iOS demo app needs SDK integration
4. Reorganize UI helper code in MusicalKey.kt
