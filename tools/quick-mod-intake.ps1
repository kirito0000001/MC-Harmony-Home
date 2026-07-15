param(
    [Parameter(Mandatory)]
    [ValidateScript({ Test-Path -LiteralPath $_ -PathType Leaf })]
    [string]$JarPath
)

Add-Type -AssemblyName System.IO.Compression.FileSystem

function Get-TomlValue {
    param(
        [Parameter(Mandatory)]
        [string]$Text,
        [Parameter(Mandatory)]
        [string]$Name
    )

    $pattern = '(?m)^{0}\s*=\s*"([^"]*)"' -f [regex]::Escape($Name)
    $match = [regex]::Match($Text, $pattern)
    if($match.Success)
    {
        return $match.Groups[1].Value
    }

    return $null
}

$archive = [System.IO.Compression.ZipFile]::OpenRead((Resolve-Path -LiteralPath $JarPath))
try
{
    $metadata = $archive.Entries | Where-Object { $_.FullName -eq 'META-INF/neoforge.mods.toml' } | Select-Object -First 1
    if($null -eq $metadata)
    {
        throw 'This JAR does not contain META-INF/neoforge.mods.toml and cannot be quick-scanned as a NeoForge mod.'
    }

    $reader = [System.IO.StreamReader]::new($metadata.Open(), [System.Text.Encoding]::UTF8, $true)
    try
    {
        $toml = $reader.ReadToEnd()
    }
    finally
    {
        $reader.Dispose()
    }

    $entries = @($archive.Entries | ForEach-Object { $_.FullName })
}
finally
{
    $archive.Dispose()
}

$modId = Get-TomlValue -Text $toml -Name 'modId'
if([string]::IsNullOrWhiteSpace($modId))
{
    throw 'No modId was found in neoforge.mods.toml.'
}

$modIdPath = [regex]::Escape($modId)
$hasItemModels = $entries -match "^assets/$modIdPath/models/item/"
$hasGameplayData = $entries -match "^data/$modIdPath/(loot_tables|recipes|tags|advancements)/"
$description = Get-TomlValue -Text $toml -Name 'description'
$descriptionText = if($null -eq $description) { '' } else { $description }
$looksLikeClientUi = $descriptionText -match '(?i)\b(menu|screen|ui|client|hud|mod list|catalogue)\b'

[pscustomobject]@{
    jar = (Resolve-Path -LiteralPath $JarPath).Path
    modId = $modId
    displayName = Get-TomlValue -Text $toml -Name 'displayName'
    version = Get-TomlValue -Text $toml -Name 'version'
    license = Get-TomlValue -Text $toml -Name 'license'
    sourceDescription = $description
    preliminaryItems = if($hasItemModels -or $hasGameplayData) { 'possible_items_or_gameplay_data' } else { 'no_item_resource_evidence' }
    preliminaryServer = if($looksLikeClientUi -and -not ($hasItemModels -or $hasGameplayData)) { 'likely_client_only_requires_confirmation' } else { 'requires_code_review' }
    nextAction = 'Write a Chinese one-sentence introduction, then confirm code, server, localization, catalogue entry, and rarity stages as needed.'
} | ConvertTo-Json -Compress
