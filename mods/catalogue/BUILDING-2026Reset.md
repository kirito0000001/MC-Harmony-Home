# Catalogue 2026Reset Build

This source tree builds the NeoForge 1.21.1 Catalogue variant used by MC Harmony Home.

## Prerequisites

- JDK 21.
- Gradle 8.10. The wrapper is pinned to this version.

The local build entry point disables automatic JDK downloads. It uses the value of `CATALOGUE_JAVA_HOME` when provided, otherwise it uses this workstation's verified Gradle JDK cache.

## Commands

Run the regression checks:

```powershell
.\tools\test-catalogue-stream-closure.ps1
.\tools\test-catalogue-build-environment.ps1
```

Build the NeoForge JAR:

```powershell
.\tools\build-neoforge.ps1
```

The default command reuses Gradle outputs. Use a clean rebuild only when required:

```powershell
.\tools\build-neoforge.ps1 -GradleArgs @('clean', 'build', '--no-daemon')
```

Use another JDK 21 installation without modifying global environment variables:

```powershell
.\tools\build-neoforge.ps1 -JavaHome 'D:\path\to\jdk-21'
```

The output JAR is written to `neoforge\build\libs`. The `2026Reset` changes only close configuration and image input streams; they do not change Catalogue gameplay or UI behavior.
