[CmdletBinding()]
param(
    [string]$SourceJar = 'D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\mods\[食物信息显示] appleskin-neoforge-mc1.21-3.0.9-2026Reset.jar',
    [string]$OutputJar = 'D:\MC-Harmony-Home\candidates\appleskin-neoforge-mc1.21-3.0.9-2026Reset.jar'
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem

if (-not (Test-Path -LiteralPath $SourceJar)) {
    throw "AppleSkin source JAR was not found: $SourceJar"
}

$replacements = [ordered]@{
    '"appleskin.configuration.showFoodValuesInTooltip": "显示食物的饥饿值和饱和度"' = '"appleskin.configuration.showFoodValuesInTooltip": "显示物品提示中的食物数值"'
    '"appleskin.configuration.showFoodValuesInTooltip.tooltip": "若启用，按下SHIFT后在信息提示内显示食物的饥饿值和饱和度"' = '"appleskin.configuration.showFoodValuesInTooltip.tooltip": "启用后，按住 Shift 时会在物品提示中显示食物提供的饥饿值和饱和度。"'
    '"appleskin.configuration.showFoodValuesInTooltipAlways": "始终显示食物的饥饿值和饱和度"' = '"appleskin.configuration.showFoodValuesInTooltipAlways": "始终显示物品提示中的食物数值"'
    '"appleskin.configuration.showFoodValuesInTooltipAlways.tooltip": "若启用，自动在信息提示内显示食物的饥饿值和饱和度 (不需要按住SHIFT)"' = '"appleskin.configuration.showFoodValuesInTooltipAlways.tooltip": "启用后，无需按住 Shift 即可在物品提示中显示食物提供的饥饿值和饱和度。"'
    '"appleskin.configuration.showFoodValuesHudOverlay": "显示食用后预计恢复的饥饿值"' = '"appleskin.configuration.showFoodValuesHudOverlay": "显示手持食物预计恢复的饥饿值"'
    '"appleskin.configuration.showFoodValuesHudOverlay.tooltip": "若启用，显示正在拿取的食物预计恢复的饥饿值 (在开启“显示饱和度”后将同时显示饱和度)"' = '"appleskin.configuration.showFoodValuesHudOverlay.tooltip": "启用后，显示手持食物预计恢复的饥饿值；若同时启用饱和度叠加层，也会显示饱和度。"'
    '"appleskin.configuration.showFoodValuesHudOverlayWhenOffhand": "显示副手食物信息"' = '"appleskin.configuration.showFoodValuesHudOverlayWhenOffhand": "显示副手食物的 HUD 叠加层"'
    '"appleskin.configuration.showFoodValuesHudOverlayWhenOffhand.tooltip": "若启用，显示副手正在拿取的食物预计恢复的饥饿值/饱和度/生命值"' = '"appleskin.configuration.showFoodValuesHudOverlayWhenOffhand.tooltip": "启用后，为副手食物显示饥饿值、饱和度和生命恢复预览。"'
    '"appleskin.configuration.showFoodHealthHudOverlay": "显示食用后预计恢复的生命值"' = '"appleskin.configuration.showFoodHealthHudOverlay": "显示手持食物预计恢复的生命值"'
    '"appleskin.configuration.showFoodHealthHudOverlay.tooltip": "若启用，显示食物食用后预计恢复的生命值"' = '"appleskin.configuration.showFoodHealthHudOverlay.tooltip": "启用后，在生命条上显示手持食物预计恢复的生命值。"'
    '"appleskin.configuration.showFoodExhaustionHudUnderlay": "显示消耗度"' = '"appleskin.configuration.showFoodExhaustionHudUnderlay": "显示疲劳值叠加层"'
    '"appleskin.configuration.showFoodExhaustionHudUnderlay.tooltip": "若启用，在饥饿条后显示消耗度"' = '"appleskin.configuration.showFoodExhaustionHudUnderlay.tooltip": "启用后，在饥饿条下方显示当前疲劳值进度。"'
    '"appleskin.configuration.showSaturationHudOverlay": "显示饱和度"' = '"appleskin.configuration.showSaturationHudOverlay": "显示饱和度叠加层"'
    '"appleskin.configuration.showSaturationHudOverlay.tooltip": "若启用，在饥饿条上显示饱和度"' = '"appleskin.configuration.showSaturationHudOverlay.tooltip": "启用后，在饥饿条上叠加显示当前饱和度。"'
    '"appleskin.configuration.showFoodStatsInDebugOverlay": "在调试屏幕中显示饥饿信息"' = '"appleskin.configuration.showFoodStatsInDebugOverlay": "在 F3 调试界面显示食物数值"'
    '"appleskin.configuration.showFoodStatsInDebugOverlay.tooltip":"若开启，在调试屏幕中显示你的饥饿值、饱和度和消耗度"' = '"appleskin.configuration.showFoodStatsInDebugOverlay.tooltip": "启用后，在 F3 调试界面显示当前饥饿值、饱和度和疲劳值。"'
    '"appleskin.configuration.showVanillaAnimationsOverlay": "启用匹配原版HUD图标的动画"' = '"appleskin.configuration.showVanillaAnimationsOverlay": "启用与原版 HUD 图标一致的动画"'
    '"appleskin.configuration.showVanillaAnimationsOverlay.tooltip": "若开启，生命条/饥饿条将抖动以与Minecraft图标动画匹配"' = '"appleskin.configuration.showVanillaAnimationsOverlay.tooltip": "启用后，生命和饥饿叠加层会随原版图标动画抖动。"'
    '"appleskin.configuration.maxHudOverlayFlashAlpha": "设置HUD闪烁图标的透明度"' = '"appleskin.configuration.maxHudOverlayFlashAlpha": "HUD 闪烁图标的最大不透明度"'
    '"appleskin.configuration.maxHudOverlayFlashAlpha.tooltip": "HUD闪烁图标最可见点的透明度 (1.0=不透明 0.0=完全透明)"' = '"appleskin.configuration.maxHudOverlayFlashAlpha.tooltip": "HUD 闪烁图标最明显时的不透明度（1.0 = 完全不透明，0.0 = 完全透明）。"'
    '"fml.menu.mods.info.description.appleskin": "增加了各种与食物相关的HUD改进"' = '"fml.menu.mods.info.description.appleskin": "为食物相关信息提供多项 HUD 增强。"'
}

$outputDirectory = Split-Path -Parent $OutputJar
New-Item -ItemType Directory -Path $outputDirectory -Force | Out-Null
Copy-Item -LiteralPath $SourceJar -Destination $OutputJar -Force

$zip = [System.IO.Compression.ZipFile]::Open($OutputJar, [System.IO.Compression.ZipArchiveMode]::Update)
try {
    $entryPath = 'assets/appleskin/lang/zh_cn.json'
    $entry = $zip.GetEntry($entryPath)
    if ($null -eq $entry) {
        throw "Missing translation resource: $entryPath"
    }

    $reader = [System.IO.StreamReader]::new($entry.Open(), [System.Text.Encoding]::UTF8)
    try {
        $content = $reader.ReadToEnd()
    }
    finally {
        $reader.Dispose()
    }

    foreach ($replacement in $replacements.GetEnumerator()) {
        $count = [regex]::Matches($content, [regex]::Escape($replacement.Key)).Count
        if ($count -ne 1) {
            throw "Expected exactly one source translation for replacement, found ${count}: $($replacement.Key)"
        }
        $content = $content.Replace($replacement.Key, $replacement.Value)
    }

    $entry.Delete()
    $newEntry = $zip.CreateEntry($entryPath, [System.IO.Compression.CompressionLevel]::Optimal)
    $writer = [System.IO.StreamWriter]::new($newEntry.Open(), [System.Text.UTF8Encoding]::new($false))
    try {
        $writer.Write($content)
    }
    finally {
        $writer.Dispose()
    }
}
finally {
    $zip.Dispose()
}

Write-Output "Created AppleSkin localization candidate: $OutputJar"
