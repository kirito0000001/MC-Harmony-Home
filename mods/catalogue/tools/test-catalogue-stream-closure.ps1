$root = Split-Path -Parent $PSScriptRoot
$config = Join-Path $root 'common\src\main\java\com\mrcrayfish\catalogue\client\Config.java'
$platform = Join-Path $root 'neoforge\src\main\java\com\mrcrayfish\catalogue\platform\NeoForgePlatformHelper.java'

$failures = [System.Collections.Generic.List[string]]::new()
$configSource = Get-Content -LiteralPath $config -Raw
$platformSource = Get-Content -LiteralPath $platform -Raw

if($configSource -notmatch 'try\s*\(\s*FileInputStream\s+\w+\s*=\s*new\s+FileInputStream\(file\)\s*\)')
{
    $failures.Add('Config.load must read catalogue.properties through a try-with-resources FileInputStream.')
}

if($platformSource -notmatch 'try\s*\(\s*InputStream\s+\w+\s*=\s*Files\.newInputStream\(path\)\s*\)')
{
    $failures.Add('NeoForgePlatformHelper.loadNativeImage must read PNG data through a try-with-resources InputStream.')
}

if($failures.Count -gt 0)
{
    $failures | ForEach-Object { Write-Error $_ }
    exit 1
}

Write-Output 'Catalogue stream-closure regression checks passed.'
