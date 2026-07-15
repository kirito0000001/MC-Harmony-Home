param(
    [switch]$Online,
    [string[]]$GradleArgs = @(':neoforge:build', '--offline', '--no-daemon'),
    [string]$JavaHome = 'C:\Users\liuyu\.gradle\jdks\eclipse_adoptium-21-amd64-windows.2'
)

$root = Split-Path -Parent $PSScriptRoot
$project = Join-Path $root 'mods\jeed'
$gradle = 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.10-bin\deqhafrv1ntovfmgh0nh3npr9\gradle-8.10\bin\gradle.bat'
$java = Join-Path $JavaHome 'bin\java.exe'

if($root -match '[^\x00-\x7F]')
{
    throw "JEED must be built from an ASCII-only repository path. Current root: $root"
}

if(-not (Test-Path -LiteralPath $java))
{
    throw "JEED requires JDK 21: $JavaHome"
}

if(-not (Test-Path -LiteralPath $gradle))
{
    throw "Pinned Gradle 8.10 is unavailable: $gradle"
}

$args = [System.Collections.Generic.List[string]]::new($GradleArgs)
if($Online)
{
    $args.Remove('--offline')
    $internetSettings = Get-ItemProperty 'HKCU:\Software\Microsoft\Windows\CurrentVersion\Internet Settings'
    if($internetSettings.ProxyEnable -ne 1 -or [string]::IsNullOrWhiteSpace($internetSettings.ProxyServer))
    {
        throw 'Windows system proxy is not enabled. Enable it before running the first online JEED dependency resolution.'
    }

    $endpoint = $internetSettings.ProxyServer
    if($endpoint -match '(?i)(?:^|;)https?=([^;]+)')
    {
        $endpoint = $Matches[1]
    }

    $proxy = [Uri]("http://" + $endpoint)
    $args.Add("-Dhttp.proxyHost=$($proxy.Host)")
    $args.Add("-Dhttp.proxyPort=$($proxy.Port)")
    $args.Add("-Dhttps.proxyHost=$($proxy.Host)")
    $args.Add("-Dhttps.proxyPort=$($proxy.Port)")
    $args.Add('-Dorg.gradle.internal.http.connectionTimeout=15000')
    $args.Add('-Dorg.gradle.internal.http.socketTimeout=30000')
}

$env:JAVA_HOME = $JavaHome
$env:Path = "$JavaHome\bin;$env:Path"

Push-Location -LiteralPath $project
try
{
    & $gradle @args
    $exitCode = $LASTEXITCODE
}
finally
{
    Pop-Location
}

exit $exitCode
