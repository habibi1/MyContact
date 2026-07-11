# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MyContact is a single-module Android contacts app written in Kotlin with Jetpack Compose (Material 3). It has no backend or persistence — contact data is a hardcoded in-memory list (`ContactsData`), and all state lives in a single `MainViewModel` shared across screens.

## Build and Test Commands

```bash
./gradlew assembleDebug                    # Build debug APK
./gradlew test                             # Run local unit tests
./gradlew connectedAndroidTest             # Run instrumentation/Compose UI tests (requires a running emulator or device)

# Run a single test class
./gradlew test --tests "com.habibi.mycontact.ExampleUnitTest"
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.habibi.mycontact.ListContactInstrumentedTest
```

Notes:
- Toolchain: Gradle 9.6.1 (wrapper committed), AGP 9.2.1 (requires JDK 17+), Kotlin 2.4.0. The Compose compiler ships with Kotlin via the `org.jetbrains.kotlin.plugin.compose` Gradle plugin — its version must always match the Kotlin plugin version in the root `build.gradle`.
- Compose library versions come from the Compose BOM (`androidx.compose:compose-bom`) in `app/build.gradle`; individual `androidx.compose.*` dependencies are declared without versions. `material-icons-core` is pinned separately (frozen at 1.7.8, no longer pulled in by material3).
- SDK levels: compileSdk/targetSdk 37, minSdk 23 (Compose 1.11+ requires 23). Java/Kotlin target 17.
- Image loading is Coil 3: imports use `coil3.compose.AsyncImage`, and network URL support comes from the `io.coil-kt.coil3:coil-network-okhttp` artifact — keep it when touching Coil dependencies or remote images silently stop loading.
- Meaningful test coverage is in the instrumentation tests (`app/src/androidTest/`), which exercise the Compose UI with `createComposeRule`; the lone local unit test is a placeholder.

## Architecture

All code is under `app/src/main/java/com/habibi/mycontact/`.

- **Single Activity, Compose navigation**: `MainActivity` hosts a `NavHost` with three destinations defined in `navigation/Screen.kt` (a sealed class of routes): `ListContact` (start), `DetailContact` (`listContact/{id}` with a string `id` argument), and `AboutPage`.
- **One shared ViewModel**: `MainViewModel` is created in `MainActivity` and passed into screens directly (no Hilt/DI, no per-screen ViewModels). It owns the contact list, the search query, and the add-contact dialog visibility.
- **Unidirectional event flow**: screens send user actions to the ViewModel as `ui/common/ListViewEvent` sealed-class events (`Add`, `Remove`, `Filter`) via `viewModel.handleEvent(...)`. The filtered list is exposed as a `StateFlow<List<Contact>>` through `consumableState()`; simpler UI state (query, dialog flag) uses Compose `mutableStateOf` on the ViewModel.
- **Data**: `model/Contact` is a plain data class; `model/ContactsData` is the static seed list. New contacts get `id = list.size.toString()`. Everything resets on process death.
- **UI layout**: screen-level composables (`ListContact.kt`, `DetailProfile.kt`, `AboutPage.kt`) sit at the package root; reusable pieces (`SearchBar`, `SettingsDialog`, `FormInput`, `TopAppBar`, `ContactItem`, `EmptyLayout`) live in `ui/components/`; theme in `ui/theme/`. Remote contact photos load with Coil's `AsyncImage`.
- **Testability hooks**: instrumentation tests find the contact list via `Modifier.testTag` (tag string comes from `R.string.scroll`) and other nodes via content descriptions from string resources — keep those resources stable or update the tests in `app/src/androidTest/` alongside them.
