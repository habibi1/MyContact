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
- The `gradle/wrapper/` directory (wrapper jar + properties) is not committed, so `./gradlew` will fail as-is. Either generate it (`gradle wrapper`) or use a locally installed Gradle compatible with AGP 7.3.1 (Gradle 7.4+).
- Toolchain is pinned old: AGP 7.3.1, Kotlin 1.6.10, Compose compiler extension 1.1.1, Compose UI 1.3.1 (`compose_version` in root `build.gradle`), compile/target SDK 33, min SDK 21, JVM target 1.8. Kotlin and the Compose compiler versions are coupled — don't bump one without the other.
- Meaningful test coverage is in the instrumentation tests (`app/src/androidTest/`), which exercise the Compose UI with `createComposeRule`; the lone local unit test is a placeholder.

## Architecture

All code is under `app/src/main/java/com/habibi/mycontact/`.

- **Single Activity, Compose navigation**: `MainActivity` hosts a `NavHost` with three destinations defined in `navigation/Screen.kt` (a sealed class of routes): `ListContact` (start), `DetailContact` (`listContact/{id}` with a string `id` argument), and `AboutPage`.
- **One shared ViewModel**: `MainViewModel` is created in `MainActivity` and passed into screens directly (no Hilt/DI, no per-screen ViewModels). It owns the contact list, the search query, and the add-contact dialog visibility.
- **Unidirectional event flow**: screens send user actions to the ViewModel as `ui/common/ListViewEvent` sealed-class events (`Add`, `Remove`, `Filter`) via `viewModel.handleEvent(...)`. The filtered list is exposed as a `StateFlow<List<Contact>>` through `consumableState()`; simpler UI state (query, dialog flag) uses Compose `mutableStateOf` on the ViewModel.
- **Data**: `model/Contact` is a plain data class; `model/ContactsData` is the static seed list. New contacts get `id = list.size.toString()`. Everything resets on process death.
- **UI layout**: screen-level composables (`ListContact.kt`, `DetailProfile.kt`, `AboutPage.kt`) sit at the package root; reusable pieces (`SearchBar`, `SettingsDialog`, `FormInput`, `TopAppBar`, `ContactItem`, `EmptyLayout`) live in `ui/components/`; theme in `ui/theme/`. Remote contact photos load with Coil's `AsyncImage`.
- **Testability hooks**: instrumentation tests find the contact list via `Modifier.testTag` (tag string comes from `R.string.scroll`) and other nodes via content descriptions from string resources — keep those resources stable or update the tests in `app/src/androidTest/` alongside them.
