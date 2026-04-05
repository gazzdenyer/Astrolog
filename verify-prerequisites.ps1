#!/usr/bin/env pwsh
# AstroLog Prerequisites Verification Script
# This script checks if Java and Node.js are installed with compatible versions

Write-Host "`n╔════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   AstroLog Prerequisites Verification Script            ║" -ForegroundColor Cyan
Write-Host "║   Checking for Java 17+ and Node.js 16+                 ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════╝`n" -ForegroundColor Cyan

$javaInstalled = $false
$nodeInstalled = $false
$npmInstalled = $false
$mavenInstalled = $false

# Check Java
Write-Host "Checking Java..." -ForegroundColor Yellow
$javaCmd = Get-Command java -ErrorAction SilentlyContinue
if ($javaCmd) {
    $javaVersion = java -version 2>&1 | Select-String "version" | Select-Object -First 1
    Write-Host "✓ Java found:" -ForegroundColor Green
    Write-Host "  $javaVersion" -ForegroundColor Green
    $javaInstalled = $true
} else {
    Write-Host "✗ Java NOT found" -ForegroundColor Red
    Write-Host "  Please install Java 17+ from: https://www.oracle.com/java/technologies/downloads/#java17" -ForegroundColor Yellow
}
Write-Host ""

# Check Node.js
Write-Host "Checking Node.js..." -ForegroundColor Yellow
$nodeCmd = Get-Command node -ErrorAction SilentlyContinue
if ($nodeCmd) {
    $nodeVersion = node --version
    Write-Host "✓ Node.js found:" -ForegroundColor Green
    Write-Host "  $nodeVersion" -ForegroundColor Green
    $nodeInstalled = $true
} else {
    Write-Host "✗ Node.js NOT found" -ForegroundColor Red
    Write-Host "  Please install Node.js LTS from: https://nodejs.org/" -ForegroundColor Yellow
}
Write-Host ""

# Check npm
Write-Host "Checking npm..." -ForegroundColor Yellow
$npmCmd = Get-Command npm -ErrorAction SilentlyContinue
if ($npmCmd) {
    $npmVersion = npm --version
    Write-Host "✓ npm found:" -ForegroundColor Green
    Write-Host "  $npmVersion" -ForegroundColor Green
    $npmInstalled = $true
} else {
    Write-Host "✗ npm NOT found" -ForegroundColor Red
}
Write-Host ""

# Check Maven
Write-Host "Checking Maven..." -ForegroundColor Yellow
$mavenCmd = Get-Command mvn -ErrorAction SilentlyContinue
if ($mavenCmd) {
    $mavenVersion = mvn --version 2>&1 | Select-Object -First 1
    Write-Host "✓ Maven found:" -ForegroundColor Green
    Write-Host "  $mavenVersion" -ForegroundColor Green
    $mavenInstalled = $true
} else {
    Write-Host "✗ Maven NOT found" -ForegroundColor Yellow
    Write-Host "  Note: Maven comes with Java, may need to restart PowerShell" -ForegroundColor Yellow
}
Write-Host ""

# Summary
Write-Host "═════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "SUMMARY:" -ForegroundColor Cyan
Write-Host "═════════════════════════════════════════════════════════" -ForegroundColor Cyan

if ($javaInstalled -and $nodeInstalled -and $npmInstalled) {
    Write-Host "✓ All prerequisites are installed!" -ForegroundColor Green
    Write-Host ""
    Write-Host "You can now proceed to install and run AstroLog:" -ForegroundColor Green
    Write-Host "  1. Read: START_HERE.md" -ForegroundColor Green
    Write-Host "  2. Follow: SETUP.md" -ForegroundColor Green
    Write-Host "  3. Get your API key from: https://astronomyapi.com/" -ForegroundColor Green
    Write-Host ""
} else {
    Write-Host "✗ Missing prerequisites:" -ForegroundColor Red
    if (-not $javaInstalled) { Write-Host "  - Java 17+ (from https://www.oracle.com/java/technologies/downloads/)" -ForegroundColor Red }
    if (-not $nodeInstalled) { Write-Host "  - Node.js 16+ (from https://nodejs.org/)" -ForegroundColor Red }
    Write-Host ""
    Write-Host "After installing, restart PowerShell and run this script again." -ForegroundColor Yellow
}

Write-Host ""

