[CmdletBinding()]
param(
    [string]$SourceJar = 'D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\mods\[JEI拼音搜索] jecharacters-1.21.1-neoforge-4.5.26-2026Reset.jar',
    [string]$OutputJar = 'D:\MC-Harmony-Home\candidates\jecharacters-1.21.1-neoforge-4.5.26-2026Reset.jar'
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression.FileSystem

if (-not (Test-Path -LiteralPath $SourceJar)) {
    throw "JEC source JAR was not found: $SourceJar"
}

$replacements = [ordered]@{
    '"jecharacters.chat.saved": "搞定！报告已导出至 logs/jecharacters.txt。"' = '"jecharacters.chat.saved": "分析完成，报告已保存至 logs/jecharacters.txt。"'
    '"jecharacters.chat.error": "卧槽，写入文件时出现了一个问题。"' = '"jecharacters.chat.error": "写入分析报告时发生错误。"'
    '"jecharacters.chat.help": "/jech [profile] / [verbose true/false]"' = '"jecharacters.chat.help": "用法：/jech [profile] / [verbose true/false]"'
}

$outputDirectory = Split-Path -Parent $OutputJar
New-Item -ItemType Directory -Path $outputDirectory -Force | Out-Null
Copy-Item -LiteralPath $SourceJar -Destination $OutputJar -Force

$zip = [System.IO.Compression.ZipFile]::Open($OutputJar, [System.IO.Compression.ZipArchiveMode]::Update)
try {
    $entryPath = 'assets/jecharacters/lang/zh_cn.json'
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

Write-Output "Created JEC localization candidate: $OutputJar"
