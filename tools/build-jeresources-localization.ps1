[CmdletBinding()]
param(
    [string]$SourceJar = 'D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\mods\JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar',
    [string]$OutputJar = 'D:\MC-Harmony-Home\candidates\JustEnoughResources-NeoForge-1.21.1-1.6.0.17-2026Reset.jar'
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem

if (-not (Test-Path -LiteralPath $SourceJar)) {
    throw "JER source JAR was not found: $SourceJar"
}

$replacements = [ordered]@{
    '"jer.config.diyData.description": "The build in compat code will not load and jsons will be loaded （需要重启Minecraft）"' = '"jer.config.diyData.description": "内置兼容代码不会加载，改为读取 JSON 数据（需要重启 Minecraft）。"'
    '"jer.config.dimensionsBlacklist.description": "来自于JEI分析扫描的黑名单维度"' = '"jer.config.dimensionsBlacklist.description": "不分析这些维度中的资源生成信息。"'
    '"jer.dungeon.title": "地牢资源"' = '"jer.dungeon.title": "地牢战利品"'
    '"jer.dungeon.village_temple": "村庙"' = '"jer.dungeon.village_temple": "村庄神庙"'
    '"jer.dungeon.village_desert_house": "乡村沙漠之家"' = '"jer.dungeon.village_desert_house": "沙漠村庄房屋"'
    '"jer.dungeon.village_plains_house": "乡村平原别墅"' = '"jer.dungeon.village_plains_house": "平原村庄房屋"'
    '"jer.dungeon.village_taiga_house": "村针叶林屋"' = '"jer.dungeon.village_taiga_house": "针叶林村庄房屋"'
    '"jer.dungeon.village_snowy_house": "乡村雪屋"' = '"jer.dungeon.village_snowy_house": "雪原村庄房屋"'
    '"jer.dungeon.village_savanna_house": "萨凡纳乡村别墅"' = '"jer.dungeon.village_savanna_house": "热带草原村庄房屋"'
    '"jer.dungeon.bastion_treasure": "堡垒藏宝"' = '"jer.dungeon.bastion_treasure": "堡垒遗迹宝藏"'
    '"jer.dungeon.bastion_other": "堡垒其他"' = '"jer.dungeon.bastion_other": "堡垒遗迹其他箱子"'
    '"jer.dungeon.bastion_bridge": "堡垒桥"' = '"jer.dungeon.bastion_bridge": "堡垒遗迹桥梁箱子"'
    '"jer.dungeon.bastion_hoglin_stable": "堡垒霍格林马厩"' = '"jer.dungeon.bastion_hoglin_stable": "堡垒遗迹疣猪兽马厩"'
    '"jer.enchantments.title": "可用附魔属性"' = '"jer.enchantments.title": "可获得的附魔"'
    '"jer.worldgen.dimensions": "有效生物群系"' = '"jer.worldgen.dimensions": "有效维度"'
    '"jer.worldgen.chance": "有%s的几率"' = '"jer.worldgen.chance": "生成概率：%s"'
    '"jer.mob.title": "生物资源"' = '"jer.mob.title": "生物掉落"'
    '"jer.villager.poi": "工作站点方块"' = '"jer.villager.poi": "工作站方块"'
    '"jer.pastWorldTime.text": "掉落条件：时间在%s之前"' = '"jer.pastWorldTime.text": "掉落条件：时间在%s之后"'
    '"jer.beforeWorldTime.text": "掉落条件：时间在%s之后"' = '"jer.beforeWorldTime.text": "掉落条件：时间在%s之前"'
    '"jer.belowLooting.text": "掉落条件：掠夺附魔等级大于%s"' = '"jer.belowLooting.text": "掉落条件：掠夺附魔等级小于%s"'
}

$outputDirectory = Split-Path -Parent $OutputJar
New-Item -ItemType Directory -Path $outputDirectory -Force | Out-Null
Copy-Item -LiteralPath $SourceJar -Destination $OutputJar -Force

$zip = [System.IO.Compression.ZipFile]::Open($OutputJar, [System.IO.Compression.ZipArchiveMode]::Update)
try {
    $entryPath = 'assets/jeresources/lang/zh_cn.json'
    $entry = $zip.GetEntry($entryPath)
    if ($null -eq $entry) {
        throw "Missing translation resource: $entryPath"
    }

    $reader = [System.IO.StreamReader]::new($entry.Open(), [System.Text.Encoding]::UTF8)
    try {
        $content = $reader.ReadToEnd()
    } finally {
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
    } finally {
        $writer.Dispose()
    }
} finally {
    $zip.Dispose()
}

Write-Output "Created JER localization candidate: $OutputJar"
