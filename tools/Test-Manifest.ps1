$ErrorActionPreference = 'Stop'

$repositoryRoot = Split-Path -Parent $PSScriptRoot
$pack = Get-Content -Raw -LiteralPath (Join-Path $repositoryRoot 'pack\manifest.json') | ConvertFrom-Json
$jep = @($pack.mods | Where-Object { $_.modId -eq 'justenoughprofessions' })

if ($jep.Count -ne 1) {
    throw 'JEP is missing from pack manifest'
}
if ($jep[0].side -ne 'client_only') {
    throw 'JEP must be client_only'
}
if ($jep[0].sha256 -ne '8D9702B337C2BFF018A3B37896BB07B2F3161BB881D4114C9142A01C86F5DFCC') {
    throw 'Unexpected JEP hash'
}

$server = Get-Content -Raw -LiteralPath (Join-Path $repositoryRoot 'profiles\server\manifest.json') | ConvertFrom-Json
if (@($server.mods).Count -ne 0) {
    throw 'Server manifest must stay empty until a server mod is audited'
}
