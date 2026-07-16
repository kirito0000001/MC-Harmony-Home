[CmdletBinding()]
param(
    [switch]$Offline,
    [string[]]$Tasks = @(':NeoForge:build')
)

$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $PSScriptRoot
$project = Join-Path $root 'mods\jei'
$javaHome = 'C:\Users\liuyu\.gradle\jdks\eclipse_adoptium-21-amd64-windows.2'
$gradleHome = Join-Path $root '.gradle-user-home'
$wrapper = Join-Path $project 'gradlew.bat'

if (-not (Test-Path -LiteralPath $wrapper)) {
    throw "JEI source checkout was not found: $project"
}
if (-not (Test-Path -LiteralPath (Join-Path $javaHome 'bin\java.exe'))) {
    throw "JDK 21 was not found: $javaHome"
}

$env:JAVA_HOME = $javaHome
New-Item -ItemType Directory -Path $gradleHome -Force | Out-Null
$env:GRADLE_USER_HOME = $gradleHome

$internetSettings = Get-ItemProperty -Path 'HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings'
if ($internetSettings.ProxyEnable -eq 1 -and -not [string]::IsNullOrWhiteSpace($internetSettings.ProxyServer)) {
    $proxyServer = ($internetSettings.ProxyServer -split ';' | Select-Object -First 1)
    if ($proxyServer -notmatch '^[a-z]+://') {
        $proxyServer = "http://$proxyServer"
    }
    $proxyUri = [Uri]$proxyServer
    $proxyOptions = "-Dhttp.proxyHost=$($proxyUri.Host) -Dhttp.proxyPort=$($proxyUri.Port) -Dhttps.proxyHost=$($proxyUri.Host) -Dhttps.proxyPort=$($proxyUri.Port)"
    $env:JAVA_TOOL_OPTIONS = (@($env:JAVA_TOOL_OPTIONS, $proxyOptions) | Where-Object { -not [string]::IsNullOrWhiteSpace($_) }) -join ' '
}

$gradleArgs = @('--no-daemon', '-PBUILD_NUMBER=366-2026Reset') + $Tasks
if ($Offline) {
    $gradleArgs += '--offline'
}

Push-Location $project
try {
    & $wrapper @gradleArgs
} finally {
    Pop-Location
}
