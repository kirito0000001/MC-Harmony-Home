$root = Split-Path -Parent $PSScriptRoot
$en = Get-Content -LiteralPath (Join-Path $root 'common\src\main\resources\assets\catalogue\lang\en_us.json') -Raw | ConvertFrom-Json -AsHashtable
$zh = Get-Content -LiteralPath (Join-Path $root 'common\src\main\resources\assets\catalogue\lang\zh_cn.json') -Raw | ConvertFrom-Json -AsHashtable
$bridgeRoot = Join-Path (Split-Path -Parent $root) 'codex-catalogue-bridge\src\main\resources\assets\codex_catalogue_bridge\lang'
$bridgeEn = Get-Content -LiteralPath (Join-Path $bridgeRoot 'en_us.json') -Raw | ConvertFrom-Json -AsHashtable
$bridgeZh = Get-Content -LiteralPath (Join-Path $bridgeRoot 'zh_cn.json') -Raw | ConvertFrom-Json -AsHashtable
$screenSource = Get-Content -LiteralPath (Join-Path $root 'common\src\main\java\com\mrcrayfish\catalogue\client\screen\CatalogueModListScreen.java') -Raw
$descriptionKey = 'fml.menu.mods.info.description.catalogue'

$failures = [System.Collections.Generic.List[string]]::new()
$missing = $en.Keys | Where-Object { -not $zh.ContainsKey($_) }
$extra = $zh.Keys | Where-Object { -not $en.ContainsKey($_) }

if($missing)
{
    $failures.Add("zh_cn is missing Catalogue UI keys: $($missing -join ', ')")
}

if($extra)
{
    $failures.Add("zh_cn contains unexpected Catalogue UI keys: $($extra -join ', ')")
}

if(($zh.Values | Where-Object { [string]::IsNullOrWhiteSpace($_) }).Count -gt 0)
{
    $failures.Add('zh_cn contains an empty Catalogue UI translation.')
}

if(-not $bridgeEn.ContainsKey($descriptionKey) -or -not $bridgeZh.ContainsKey($descriptionKey))
{
    $failures.Add('Catalogue requires bilingual description overrides in the Catalogue bridge.')
}

if($screenSource -notmatch 'Component\.translatable\("catalogue\.gui\.website"\)')
{
    $failures.Add('Catalogue website button must use a translatable language key.')
}

if($screenSource -notmatch 'Component\.translatable\("catalogue\.gui\.submit_bug"\)')
{
    $failures.Add('Catalogue bug-report button must use a translatable language key.')
}

if($failures.Count -gt 0)
{
    $failures | ForEach-Object { Write-Error $_ }
    exit 1
}

Write-Output 'Catalogue localization regression checks passed.'
