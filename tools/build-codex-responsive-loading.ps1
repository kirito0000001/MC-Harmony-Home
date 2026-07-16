param(
    [string[]]$GradleArgs = @('build', '--offline', '--no-daemon'),
    [string]$JavaHome = 'C:\Program Files\Java\jdk-23'
)

$root = Split-Path -Parent $PSScriptRoot
$project = Join-Path $root 'mods\codex-responsive-loading'
$java = Join-Path $JavaHome 'bin\java.exe'
$gradle = Join-Path $project 'gradlew.bat'

if (-not (Test-Path -LiteralPath $java)) {
    throw "Codex Responsive Loading requires JDK 23: $JavaHome"
}

if (-not (Test-Path -LiteralPath $gradle)) {
    throw "Codex Responsive Loading Gradle Wrapper is unavailable: $gradle"
}

if ($root -match '[^\x00-\x7F]') {
    throw 'Codex Responsive Loading must be built from an ASCII-only repository path.'
}

$env:JAVA_HOME = $JavaHome
$env:Path = "$JavaHome\bin;$env:Path"

Push-Location -LiteralPath $project
try {
    & $gradle @GradleArgs
    $exitCode = $LASTEXITCODE
}
finally {
    Pop-Location
}

exit $exitCode
