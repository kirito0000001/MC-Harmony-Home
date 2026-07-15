param(
    [string[]]$GradleArgs = @('build', '--no-daemon'),
    [string]$JavaHome = $env:CATALOGUE_JAVA_HOME
)

$root = Split-Path -Parent $PSScriptRoot
if([string]::IsNullOrWhiteSpace($JavaHome))
{
    $JavaHome = 'C:\Users\liuyu\.gradle\jdks\eclipse_adoptium-21-amd64-windows.2'
}

$java = Join-Path $JavaHome 'bin\java.exe'

if(-not (Test-Path -LiteralPath $java))
{
    throw "Catalogue requires JDK 21. Set CATALOGUE_JAVA_HOME or pass -JavaHome."
}

$env:JAVA_HOME = $JavaHome
$env:Path = "$JavaHome\bin;$env:Path"
$env:TARGET_LOADER = 'neoforge'

Push-Location -LiteralPath $root
try
{
    & .\gradlew.bat @GradleArgs
    $exitCode = $LASTEXITCODE
}
finally
{
    Pop-Location
}

exit $exitCode
