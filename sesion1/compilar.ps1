# Obtener todos los argumentos pasados al script
$arguments = $args

# Crear la carpeta compilacion si no existe
if (-Not (Test-Path "compilacion")) {
    New-Item -ItemType Directory -Path "compilacion" | Out-Null
}

# 1️⃣ Compilar todos los ficheros .java en la carpeta compilacion
Write-Host "Compilando archivos Java..."
javac -d compilacion *.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error en la compilación. Abortando."
    exit 1
}

# 3️⃣ Generar el JAR dentro de la carpeta compilacion
Write-Host "Generando rushhour.jar..."
jar cfm compilacion\rushhour.jar compilacion\manifest.txt -C compilacion .

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al crear el JAR. Abortando."
    exit 1
}

# # 4️⃣ Ejecutar el JAR con los argumentos pasados al script
# Write-Host "Ejecutando el JAR con argumentos: $arguments `n"
# java -jar compilacion\rushhour.jar @arguments
