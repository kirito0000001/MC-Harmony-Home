param(
    [string[]]$GradleArgs = @('build', '--offline', '--no-daemon'),
    [string]$JavaHome = 'C:\Users\liuyu\.gradle\jdks\eclipse_adoptium-21-amd64-windows.2'
)

$root = Split-Path -Parent $PSScriptRoot
$gradle = 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.10-bin\deqhafrv1ntovfmgh0nh3npr9\gradle-8.10\bin\gradle.bat'
$java = Join-Path $JavaHome 'bin\java.exe'

if(-not (Test-Path -LiteralPath $java))
{
    throw "Codex Catalogue Bridge requires JDK 21: $JavaHome"
}

if(-not (Test-Path -LiteralPath $gradle))
{
    throw "Pinned Gradle 8.10 is unavailable: $gradle"
}

if($root -match '[^\x00-\x7F]')
{
    throw "Codex Catalogue Bridge must be built from an ASCII-only repository path. Move or rename the repository before running this script."
}

$env:JAVA_HOME = $JavaHome
$env:Path = "$JavaHome\bin;$env:Path"

Push-Location -LiteralPath (Join-Path $root 'mods\codex-catalogue-bridge')
try
{
    & $gradle @GradleArgs
    $exitCode = $LASTEXITCODE
}
finally
{
    Pop-Location
}

exit $exitCode
