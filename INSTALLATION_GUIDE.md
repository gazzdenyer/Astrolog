# Installation Guide for AstroLog Prerequisites

## Status: ✅ ALL PREREQUISITES INSTALLED

Your system has all required components:
- ✅ Java Development Kit (JDK) 25.0.2
- ✅ Node.js v24.14.1
- ✅ npm 11.11.0
- ✅ Maven 3.9.14

## Installed Versions

| Component | Version | Location |
|-----------|---------|----------|
| Java JDK | 25.0.2 | C:\Program Files\Java\jdk-25.0.2 |
| Node.js | v24.14.1 | (System PATH) |
| npm | 11.11.0 | (Included with Node.js) |
| Maven | 3.9.14 | C:\maven\apache-maven-3.9.14 |

---

## Installation Instructions

### For Reference: Installation History

The following components were previously installed on your system. If you need to reinstall any component, follow the instructions below.

### Option 1: Manual Installation (Reference Only)

**Download:**
- Go to: https://www.oracle.com/java/technologies/downloads/
- Select: Windows x64 installer
- Download the `.exe` file (Java 17 or higher)

**Install:**
1. Run the downloaded installer
2. Follow the installation wizard
3. Accept default settings
4. Choose installation location (e.g., `C:\Program Files\Java\jdk-17`)
5. Click "Install"
6. Finish and restart your computer

**Verify:**
Open PowerShell and run:
```powershell
java -version
```
Expected output: `java version "17.x.x"` or higher

#### Step 2: Install Node.js (Reference Only)

**Download:**
- Go to: https://nodejs.org/
- Download the LTS (Long Term Support) version
- Download the `.msi` installer for Windows

**Install:**
1. Run the downloaded installer
2. Follow the installation wizard
3. Accept default settings
4. When asked about tools, you can use default options
5. Click "Install"
6. Finish and restart your computer

**Verify:**
Open PowerShell and run:
```powershell
node --version
npm --version
```
Expected output: 
- `v18.x.x` (or higher)
- `9.x.x` (or higher)

---

### Option 2: Automated Installation with Chocolatey

If you have Chocolatey installed, you can use these commands:

```powershell
# Install Java 17
choco install adoptopenjdk17 -y

# Install Node.js LTS
choco install nodejs-lts -y
```

After installation, restart PowerShell and verify.

---

### Option 3: Use Scoop Package Manager

If you prefer Scoop:

```powershell
# Install Java 17
scoop install openjdk17

# Install Node.js
scoop install nodejs
```

---

## Verification Checklist

Your system has been verified to have all required components installed:

```powershell
# ✅ Check Java
java -version
# Output: openjdk version "25.0.2"

# ✅ Check Node.js
node --version
# Output: v24.14.1

# ✅ Check npm
npm --version
# Output: 11.11.0

# ✅ Check Maven
mvn --version
# Output: Apache Maven 3.9.14
```

All prerequisites are met! ✅

---

## Important Notes

1. **Restart Required**: You must restart your computer after installation
2. **PATH Configuration**: Installation wizards usually handle PATH setup, but if commands don't work after restart, manually add to PATH:
   - Java: Add `C:\Program Files\Java\jdk-17\bin` to PATH
   - Node.js: Usually auto-configured

3. **Administrator Rights**: You may need administrator rights to install

4. **Disk Space**: Ensure you have at least 2GB free space

---

## Troubleshooting

### "java command not found"
- Java is not in your PATH
- Solution: Restart PowerShell or add Java bin folder to PATH

### "node command not found"
- Node.js is not in your PATH
- Solution: Restart PowerShell or add Node.js to PATH

### Version Not Compatible
- If you get old versions, uninstall and reinstall latest LTS versions

---

## Next Steps After Installation

✅ **Installation Complete!** All prerequisites are installed and verified.

Now you can start developing AstroLog:

1. **Get Your API Key:**
   - Visit: https://astronomyapi.com/
   - Sign up for a free account
   - Get your API key and Username

2. **Read the Documentation:**
   - Start with: `START_HERE.md`
   - Then follow: `SETUP.md`
   - Reference: `ARCHITECTURE.md`

3. **Configure Your Application:**
   - Add your AstronomyAPI credentials to the backend configuration
   - See `SETUP.md` for detailed configuration steps

4. **Start Development:**
   ```powershell
   # Terminal 1: Start the backend
   cd "C:\IntelliJ Projects\AstroLog\backend"
   mvn spring-boot:run
   ```
   
   ```powershell
   # Terminal 2: Start the frontend
   cd "C:\IntelliJ Projects\AstroLog\frontend"
   npm install
   npm run dev
   ```

5. **Access the Application:**
   - Frontend: http://localhost:5173 (or the URL shown by Vite)
   - Backend: http://localhost:8080
   - API Docs: http://localhost:8080/swagger-ui.html (if enabled)

---

## Installation Time Estimate

Since all components are already installed:
- **Setup Time**: 5-10 minutes (configuration only)
- **Ready to Code**: Immediately

---

## Need More Help?

- Java Documentation: https://docs.oracle.com/en/java/javase/25/
- Node.js Guide: https://nodejs.org/en/docs/guides/getting-started-guide/
- Maven Setup: https://maven.apache.org/install.html
- AstronomyAPI Docs: https://astronomyapi.com/docs
- Spring Boot Guide: https://spring.io/guides/gs/spring-boot/

---

**Quick Verification Command:**

```powershell
Write-Host "=== AstroLog Prerequisites Check ===" -ForegroundColor Green
Write-Host ""
Write-Host "Java Version:" -ForegroundColor Cyan
java -version 2>&1
Write-Host ""
Write-Host "Node.js Version:" -ForegroundColor Cyan
node --version
Write-Host ""
Write-Host "npm Version:" -ForegroundColor Cyan
npm --version
Write-Host ""
Write-Host "Maven Version:" -ForegroundColor Cyan
mvn --version
Write-Host ""
Write-Host "✅ All prerequisites are installed and ready!" -ForegroundColor Green
```

---

**Status**: ✅ All components installed
**Next Step**: Read START_HERE.md
**Time to Ready**: < 5 minutes (just configuration)

---

*Updated: April 5, 2026*
*For: AstroLog Project Setup*

