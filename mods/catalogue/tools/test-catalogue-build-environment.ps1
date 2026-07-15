$root = Split-Path -Parent $PSScriptRoot
$wrapper = Get-Content -LiteralPath (Join-Path $root 'gradle\wrapper\gradle-wrapper.properties') -Raw
$properties = Get-Content -LiteralPath (Join-Path $root 'gradle.properties') -Raw
$buildScript = Join-Path $PSScriptRoot 'build-neoforge.ps1'

$failures = [System.Collections.Generic.List[string]]::new()

if($wrapper -notmatch 'distributionUrl=.*gradle-8\.10-bin\.zip')
{
    $failures.Add('Catalogue must use the locally cached Gradle 8.10 wrapper distribution.')
}

if($properties -match 'org.gradle.java.installations.paths=')
{
    $failures.Add('Catalogue must not publish a machine-specific Java path in gradle.properties.')
}

if($properties -notmatch 'org.gradle.java.installations.auto-download=false')
{
    $failures.Add('Catalogue must fail fast instead of downloading a Java toolchain during builds.')
}

if(-not (Test-Path -LiteralPath $buildScript))
{
    $failures.Add('Catalogue must provide a NeoForge build entry point that sets JAVA_HOME explicitly.')
}
elseif((Get-Content -LiteralPath $buildScript -Raw) -notmatch 'CATALOGUE_JAVA_HOME')
{
    $failures.Add('Catalogue build entry point must allow callers to provide their own JDK 21 path.')
}

if($failures.Count -gt 0)
{
    $failures | ForEach-Object { Write-Error $_ }
    exit 1
}

Write-Output 'Catalogue build environment regression checks passed.'
