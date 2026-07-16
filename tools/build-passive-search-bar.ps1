param(
    [string[]]$GradleArgs = @('build', '--offline', '--no-daemon'),
    [string]$JavaHome = 'C:\Program Files\Java\jdk-23'
)

$root = Split-Path -Parent $PSScriptRoot
$project = Join-Path $root 'mods\passivesearchbar'
$java = Join-Path $JavaHome 'bin\java.exe'
$gradle = Join-Path $project 'gradlew.bat'

if (-not (Test-Path -LiteralPath $java)) {
    throw "Passive Search Bar requires JDK 23: $JavaHome"
}

if (-not (Test-Path -LiteralPath $gradle)) {
    throw "Passive Search Bar Gradle Wrapper is unavailable: $gradle"
}

if ($root -match '[^\x00-\x7F]') {
    throw 'Passive Search Bar must be built from an ASCII-only repository path.'
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
