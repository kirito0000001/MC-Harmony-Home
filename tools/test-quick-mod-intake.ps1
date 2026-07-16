$tool = Join-Path $PSScriptRoot 'quick-mod-intake.ps1'
$jar = 'D:\MC-Harmony-Home\mods\catalogue\neoforge\build\libs\catalogue-neoforge-1.21.1-1.11.2-2026Reset.jar'

if(-not (Test-Path -LiteralPath $tool))
{
    Write-Error 'Quick intake tool is missing.'
    exit 1
}

$report = & $tool -JarPath $jar | ConvertFrom-Json
$failures = [System.Collections.Generic.List[string]]::new()

if($report.modId -ne 'catalogue')
{
    $failures.Add('Quick intake must read the Catalogue mod ID from neoforge.mods.toml.')
}

if($report.preliminaryItems -ne 'no_item_resource_evidence')
{
    $failures.Add('Quick intake must identify Catalogue as having no item resource evidence.')
}

if($report.preliminaryServer -ne 'likely_client_only_requires_confirmation')
{
    $failures.Add('Quick intake must identify Catalogue as likely client-only without treating it as final.')
}

if($failures.Count -gt 0)
{
    $failures | ForEach-Object { Write-Error $_ }
    exit 1
}

Write-Output 'Quick mod intake regression checks passed.'
